/*******************************************************************************
* 建立者：Saulden  建立日期：2016/12/13  最後修訂日期：2017/01/09
* 功能簡述：客戶產品單價管理DAO
* 
********************************************************************************/
package iot.dao.repository;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import iot.dao.entity.Customer;
import iot.dao.entity.CustomerPrice;
import iot.dao.entity.CustomerPrice_;
import iot.dao.entity.Product;
import iot.dao.repository.exceptions.JPAQueryException;
import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.dao.repository.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.validation.ValidationException;
import iot.response.Response;
import java.util.ArrayList;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;

public class CustomerPriceDAO implements Serializable {

    public CustomerPriceDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Response create(CustomerPrice customerPrice) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customerMasterId = customerPrice.getCustomerMasterId();
            if (customerMasterId != null) { //若有設置客戶外鍵，查詢客戶實體
                customerMasterId = em.getReference(customerMasterId.getClass(), customerMasterId.getCustomerMasterId());
                customerPrice.setCustomerMasterId(customerMasterId);
            }
            Product productMasterId = customerPrice.getProductMasterId();
            if (productMasterId != null) {//若有設置產品外鍵的實體，查詢該實體
                productMasterId = em.getReference(productMasterId.getClass(), productMasterId.getProductMasterId());
                customerPrice.setProductMasterId(productMasterId);
            }
            em.persist(customerPrice);
            if (customerMasterId != null) {//將客戶產品單價添加到客戶的映射集合中
                customerMasterId.getCustomerPriceMasterCollection().add(customerPrice);
                customerMasterId = em.merge(customerMasterId);
            }
            if (productMasterId != null) {//將客戶產品單價添加到產品的映射集合中
                productMasterId.getCustomerPriceMasterCollection().add(customerPrice);
                productMasterId = em.merge(productMasterId);
            }
            em.getTransaction().commit();
            
            return new Response().success("新增客戶產品單價資訊成功",customerPrice);
            
        } catch (Exception ex) {
            if (!findCustomerPriceByCustomerPriceId(customerPrice.getCusPriceMasterId()).isEmpty()) {
                customerPrice.setCustomerPriceId(getCustomerPriceId());
                create(customerPrice);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Response edit(CustomerPrice customerPrice) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CustomerPrice persistentCustomerPrice = em.find(CustomerPrice.class, customerPrice.getCusPriceMasterId());
            Customer customerMasterIdOld = persistentCustomerPrice.getCustomerMasterId();
            Customer customerMasterIdNew = customerPrice.getCustomerMasterId();
            Product productMasterIdOld = persistentCustomerPrice.getProductMasterId();
            Product productMasterIdNew = customerPrice.getProductMasterId();
            //查詢客戶、產品外鍵實體最新狀態，並設置到修改後的實體
            if (customerMasterIdNew != null) {
                customerMasterIdNew = em.getReference(customerMasterIdNew.getClass(), customerMasterIdNew.getCustomerMasterId());
                customerPrice.setCustomerMasterId(customerMasterIdNew);
            }
            if (productMasterIdNew != null) {
                productMasterIdNew = em.getReference(productMasterIdNew.getClass(), productMasterIdNew.getProductMasterId());
                customerPrice.setProductMasterId(productMasterIdNew);
            }
            //更新實體到資料庫
            customerPrice = em.merge(customerPrice);
            //若原客戶產品單價實體外鍵存在且與當前外鍵不同，從原實體的外鍵客戶的映射集合移除該客戶產品單價
            if (customerMasterIdOld != null && !customerMasterIdOld.equals(customerMasterIdNew)) {
                customerMasterIdOld.getCustomerPriceMasterCollection().remove(customerPrice);
                customerMasterIdOld = em.merge(customerMasterIdOld);
            }
            //若修改後的客戶產品單價實體外鍵存在且與原外鍵不同，將該客戶產品單價添加到新外鍵客戶的映射集合中
            if (customerMasterIdNew != null && !customerMasterIdNew.equals(customerMasterIdOld)) {
                customerMasterIdNew.getCustomerPriceMasterCollection().add(customerPrice);
                customerMasterIdNew = em.merge(customerMasterIdNew);
            }
            //
            if (productMasterIdOld != null && !productMasterIdOld.equals(productMasterIdNew)) {
                productMasterIdOld.getCustomerPriceMasterCollection().remove(customerPrice);
                productMasterIdOld = em.merge(productMasterIdOld);
            }
            if (productMasterIdNew != null && !productMasterIdNew.equals(productMasterIdOld)) {
                productMasterIdNew.getCustomerPriceMasterCollection().add(customerPrice);
                productMasterIdNew = em.merge(productMasterIdNew);
            }
            em.getTransaction().commit();
            
            return new Response().success("修改客戶產品單價資訊成功",customerPrice);
        } catch(OptimisticLockException optimisticLockException){
            throw new OptimisticLockException("客戶產品單價編號為【"+ customerPrice.getCustomerPriceId() +"】的資料已被修改，請重試");
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = customerPrice.getCustomerPriceId();
                if (findCustomerPriceByCustomerPriceId(id).isEmpty()) {
                    throw new NonexistentEntityException("編號為【 " + id + "】的客戶產品單價資訊不存在");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CustomerPrice customerPrice;
            try {
                customerPrice = em.getReference(CustomerPrice.class, id);
                customerPrice.getCusPriceMasterId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customerPrice with id " + id + " no longer exists.", enfe);
            }
            Customer customerMasterId = customerPrice.getCustomerMasterId();
            if (customerMasterId != null) {
                customerMasterId.getCustomerPriceMasterCollection().remove(customerPrice);
                customerMasterId = em.merge(customerMasterId);
            }
            Product productMasterId = customerPrice.getProductMasterId();
            if (productMasterId != null) {
                productMasterId.getCustomerPriceMasterCollection().remove(customerPrice);
                productMasterId = em.merge(productMasterId);
            }
            em.remove(customerPrice);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CustomerPrice> findCustomerPriceEntities() {
        return findCustomerPriceEntities(true, -1, -1);
    }

    public List<CustomerPrice> findCustomerPriceEntities(int maxResults, int firstResult) {
        return findCustomerPriceEntities(false, maxResults, firstResult);
    }

    private List<CustomerPrice> findCustomerPriceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CustomerPrice.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CustomerPrice findCustomerPrice(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CustomerPrice.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerPriceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CustomerPrice> rt = cq.from(CustomerPrice.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：通過客戶產品單價編號查詢客戶實體
     * 
     * @param customerPriceId
     * @return 
     ********************************************************************************/
    public Response findCustomerPriceByCustomerPriceId(String customerPriceId) {
        EntityManager em = getEntityManager();
        try {
            //創建查詢工廠
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //創建查詢語句
            CriteriaQuery<CustomerPrice> cq = cb.createQuery(CustomerPrice.class);
            //定義實體類型
            Root<CustomerPrice> customerPrice = cq.from(CustomerPrice.class);

            //創建過濾條件
            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.equal(customerPrice.get(CustomerPrice_.customerPriceId), customerPriceId));
            predicatesList.add(cb.equal(customerPrice.get(CustomerPrice_.deleteStatus), false));
            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            
            Query q = em.createQuery(cq);

            return new Response().success("查詢客戶產品單價實體成功", q.getSingleResult());

        }catch(NoResultException e){
            return new Response().Empty("無此編號的客戶產品單價資訊");
        } finally {
            em.close();
        }
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2017/01/09
     * 功能簡述：條件查詢客戶產品單價
     * 
     * @param customerList  客戶實體列表
     * @param productList   產品實體列表
     * @param priceMin      價格起始
     * @param priceMax      價格終值
     * @param rangeMax      數量級終值
     * @param rangeMin      數量級起始
     * @param pageNo        要查詢的頁碼
     * @return 
     ********************************************************************************/
    public Response findCustomerPricesByCondition(List<Customer> customerList, List<Product> productList,String priceMin, String priceMax, String rangeMin, String rangeMax ,int pageNo) throws JPAQueryException{
        EntityManager em = getEntityManager(); 
        try { 
        //創建查詢工廠
        CriteriaBuilder cb = em.getCriteriaBuilder();
        //創建查詢語句
        CriteriaQuery<CustomerPrice> cq = cb.createQuery(CustomerPrice.class);
        //定義實體類型
        Root<CustomerPrice> customerPrice = cq.from(CustomerPrice.class);
        
        //創建過濾條件
        List<Predicate> predicatesList = new ArrayList<>();
        
        //將客戶資訊加入查詢條件
        Predicate customer = cb.or();
        for(int m = 0;m < customerList.size();m++){
            customer = cb.or(customer,cb.equal(customerPrice.get(CustomerPrice_.customerMasterId), customerList.get(m)));
        }
        predicatesList.add(customer);
        
        //將產品資訊加入查詢條件
        Predicate product = cb.or();
        for(int n = 0;n < productList.size();n++){
            product = cb.or(product,cb.equal(customerPrice.get(CustomerPrice_.productMasterId), productList.get(n)));
        }
        predicatesList.add(product);
        
        //當用戶輸入了價格起始時，將其加入條件
        if(priceMin.length() > 0){
            predicatesList.add(cb.ge(customerPrice.get(CustomerPrice_.rangePrice), Float.parseFloat(priceMin)));
        }
        //當用戶輸入了價格終點時，將其加入條件
        if(priceMax.length() > 0){
            predicatesList.add(cb.le(customerPrice.get(CustomerPrice_.rangePrice), Float.parseFloat(priceMax)));
        }
        
        //當用戶輸入了數量級起始時，將其加入條件
        if(rangeMin.length() > 0){
            predicatesList.add(cb.or(cb.ge(customerPrice.get(CustomerPrice_.rangeMin), Integer.parseInt(rangeMin)), cb.and(cb.le(customerPrice.get(CustomerPrice_.rangeMin), Integer.parseInt(rangeMin)),cb.ge(customerPrice.get(CustomerPrice_.rangeMax), Integer.parseInt(rangeMin)))));
        }
        //當用戶輸入了數量級終點時，將其加入條件
        if(rangeMax.length() > 0){
            predicatesList.add(cb.or(cb.le(customerPrice.get(CustomerPrice_.rangeMax), Integer.parseInt(rangeMax)),cb.and(cb.ge(customerPrice.get(CustomerPrice_.rangeMax), Integer.parseInt(rangeMax)),cb.le(customerPrice.get(CustomerPrice_.rangeMin), Integer.parseInt(rangeMax))))); 
        }
        
        //添加過濾條件，未被邏輯刪除
        predicatesList.add(cb.equal(customerPrice.get(CustomerPrice_.deleteStatus), false));
        
        //將過濾條件加入語句
        cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
        
        //設置查詢排序
        cq.orderBy(cb.asc(customerPrice.get(CustomerPrice_.customerMasterId)),cb.asc(customerPrice.get(CustomerPrice_.productMasterId)),cb.asc(customerPrice.get(CustomerPrice_.rangeMin)));

        //創建查詢對象
        Query q = em.createQuery(cq);
        
        //設置分頁
        q.setFirstResult(pageNo*10);
        q.setMaxResults(10);
        
        int count_int = 0;
        if(pageNo == 0){
            //查詢數據總量
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            countQuery.select(cb.count(customerPrice));
                
            countQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            Query count = em.createQuery(countQuery);
            count_int = ((Long)count.getSingleResult()).intValue();
                
            if(count_int == 0){//查詢結果空，返回資訊
                return new Response().Empty("無此範圍內的客戶產品單價資料");
            }
        }
        
        return new Response().success("客戶查詢成功", q.getResultList(),count_int);
            
        }catch(NumberFormatException numberFormatException){
            throw new ValidationException("請輸入正確的數量或價格");
        }catch(Exception e){
            throw new JPAQueryException("條件查詢客戶產品單價失敗", e);
        }finally {
            em.close();
        }

    }
    
     /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2017/01/09
     * 功能簡述：查詢數量級、價格輸入框下拉列表數據
     * 
     * @param inputId       前台聚焦的輸入框id
     * @param customerList  客戶實體列表
     * @param productList   產品實體列表
     * @param priceMin      價格起始
     * @param rangeMax      數量級終值
     * @param rangeMin      數量級起始
     * @param all           是否查詢全部
     * @param firstResult   查詢起始位置
     * @param maxResult     最大查詢數量
     * @return 
     * @throws java.lang.NoSuchFieldException 
     ********************************************************************************/
    public Response findRangesAndPriceListByCondition(String inputId, List<Customer> customerList, List<Product> productList,String priceMin, String rangeMin, String rangeMax ,boolean all,int firstResult, int maxResult) throws NoSuchFieldException{
        EntityManager em = getEntityManager();
        try {
            //創建查詢工廠
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //創建查詢語句，設置實體類型
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<CustomerPrice> customerPrice = cq.from(CustomerPrice.class);
            
            //構造客戶過濾條件
            Predicate customerPredicate = cb.or();
            for(int m = 0;m < customerList.size();m++){
                customerPredicate = cb.or(customerPredicate,cb.equal(customerPrice.get(CustomerPrice_.customerMasterId), customerList.get(m)));
            }
            //構造產品過濾條件
            Predicate productPredicate = cb.or();
            for(int n = 0;n < productList.size();n++){
                productPredicate = cb.or(productPredicate,cb.equal(customerPrice.get(CustomerPrice_.productMasterId), productList.get(n)));
            }
            //構造數量級過濾條件
            Predicate rangeMinPredicate = cb.or(cb.ge(customerPrice.get(CustomerPrice_.rangeMin), Integer.parseInt(rangeMin)), cb.and(cb.le(customerPrice.get(CustomerPrice_.rangeMin), Integer.parseInt(rangeMin)),cb.ge(customerPrice.get(CustomerPrice_.rangeMax), Integer.parseInt(rangeMin))));
            Predicate rangeMaxPredicate = cb.or(cb.le(customerPrice.get(CustomerPrice_.rangeMax), Integer.parseInt(rangeMax)),cb.and(cb.ge(customerPrice.get(CustomerPrice_.rangeMax), Integer.parseInt(rangeMax)),cb.le(customerPrice.get(CustomerPrice_.rangeMin), Integer.parseInt(rangeMax))));
            Predicate priceMinPredicate = cb.ge(customerPrice.get(CustomerPrice_.rangePrice), Float.parseFloat(priceMin));
            Predicate notdeletePredicate = cb.equal(customerPrice.get(CustomerPrice_.deleteStatus), false);
            String successMessage = "";
            Boolean isInputIdillegal = true;

            //查詢數量級起始列表
            if(inputId.equals("range_min_input")){
                cq.select(cb.array(customerPrice.get(CustomerPrice_.rangeMin)));
                cq.distinct(true);
                cq.where(cb.and(customerPredicate,productPredicate,rangeMinPredicate,notdeletePredicate));
                cq.orderBy(cb.asc(customerPrice.get(CustomerPrice_.rangeMin)));
                isInputIdillegal = false;
                successMessage = "查詢數量級起始列表成功";
                
            }
            
            //查詢數量級終值列表
            if(inputId.equals("range_max_input")){
                cq.select(cb.array(customerPrice.get(CustomerPrice_.rangeMax)));
                cq.distinct(true);
                cq.where(cb.and(customerPredicate,productPredicate,rangeMinPredicate,rangeMaxPredicate,notdeletePredicate));
                cq.orderBy(cb.asc(customerPrice.get(CustomerPrice_.rangeMax)));
                isInputIdillegal = false;
                successMessage = "查詢數量級終值列表成功";
            }
            
            //查詢價格列表
            if(inputId.equals("price_min_input")){
                cq.select(cb.array(customerPrice.get(CustomerPrice_.rangePrice)));
                cq.distinct(true);
                cq.where(cb.and(customerPredicate,productPredicate,rangeMinPredicate,rangeMaxPredicate,priceMinPredicate,notdeletePredicate));
                cq.orderBy(cb.asc(customerPrice.get(CustomerPrice_.rangePrice)));
                isInputIdillegal = false;
                successMessage = "查詢價格列表成功";
            }
            
            if(isInputIdillegal == true){
                throw new NoSuchFieldException("輸入框ID非法");
            }
            
            Query q = em.createQuery(cq);
            
            if(!all){
                q.setFirstResult(firstResult);
                q.setMaxResults(maxResult);
           }
            
            return new Response().success(successMessage, q.getResultList());

        }catch(NumberFormatException numberFormatException){
            throw new ValidationException("請輸入正確的數量或價格");
        } finally {
            em.close();
        }
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：2017/01/09
     * 功能簡述：查詢新增客戶產品單價編號
     * 
     * @return 
     ********************************************************************************/
    public String getCustomerPriceId() {
        EntityManager em = getEntityManager();
        try {
            //創建查詢工廠
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //創建查詢語句
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            //定義實體類型
            Root<CustomerPrice> customerPrice = cq.from(CustomerPrice.class);
            //設置查詢字段
            cq.select(cb.count(customerPrice));
            
            Query count = em.createQuery(cq);
            int count_int = ((Long)count.getSingleResult()).intValue() + 1;

            return "CUSPRO" + String.format("%06d", count_int);

        } finally {
            em.close();
        }
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：檢驗數量區間是否與其他區間有交叉
     * 
     * @param customer  客戶實體
     * @param product   產品實體
     * @param rangeMin  數量級起始
     * @param rangeMax  數量級終值
     * @return  
     ********************************************************************************/
    public Response validateRanges(Customer customer,Product product,int rangeMin,int rangeMax){
        EntityManager em = getEntityManager();
        try {
            //創建查詢工廠
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //創建查詢語句
             CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            //定義實體類型
            Root<CustomerPrice> customerPrice = cq.from(CustomerPrice.class);
            cq.select(cb.count(customerPrice));
            
            if(rangeMin > rangeMax){
                throw new ValidationException("數量級區間大小有誤");
            }
            
//            Predicate predicate_range_min = cb.and(cb.le(customerPrice.get(CustomerPrice_.rangeMin), rangeMin),cb.ge(customerPrice.get(CustomerPrice_.rangeMax), rangeMin));
//            Predicate predicate_range_max = cb.and(cb.le(customerPrice.get(CustomerPrice_.rangeMin), rangeMax),cb.ge(customerPrice.get(CustomerPrice_.rangeMax), rangeMax));
            Predicate predicate_range = cb.and(cb.ge(customerPrice.get(CustomerPrice_.rangeMax), rangeMin),cb.le(customerPrice.get(CustomerPrice_.rangeMin), rangeMax));
            Predicate predicate_cus_pro = cb.and(cb.equal(customerPrice.get(CustomerPrice_.customerMasterId), customer),cb.equal(customerPrice.get(CustomerPrice_.productMasterId), product));
            Predicate predicate_not_delete = cb.equal(customerPrice.get(CustomerPrice_.deleteStatus), false);
            cq.where(cb.and(predicate_cus_pro,predicate_range,predicate_not_delete));
            
            Query count = em.createQuery(cq);
            
            int count_int = ((Long)count.getSingleResult()).intValue();
            
            if(count_int > 0){
            
                throw new ValidationException("數量級區間與其他客戶產品單價資訊有交叉區域");
            }

            return new Response().success("驗證數量級區間無誤");

        } finally {
            em.close();
        }
    }
    
    /*******************************************************************************
     * 建立者：David  建立日期：-  最後修訂日期：2017/01/07
     * 功能簡述：條件查詢客戶產品單價
     * 
     * 2017/01/07 新增訂單時查詢條件應有客戶
     * 
     * @param orderQty
     * @param product
     * @param customer
     * @return 
     ********************************************************************************/
    public Response queryCustomerPriceByOrderQty(int orderQty,Product product,Customer customer){
    EntityManager em = getEntityManager();
        try {
            //創建安全查詢工廠
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //創建查詢主語句
            CriteriaQuery<CustomerPrice> cq = cb.createQuery(CustomerPrice.class);
           //定義實體類型
            Root<CustomerPrice> customerPrice = cq.from(CustomerPrice.class);

            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.equal(customerPrice.get(CustomerPrice_.customerMasterId), customer));
            predicatesList.add(cb.equal(customerPrice.get(CustomerPrice_.productMasterId), product));
            predicatesList.add(cb.le(customerPrice.get(CustomerPrice_.rangeMin), orderQty));
            predicatesList.add(cb.ge(customerPrice.get(CustomerPrice_.rangeMax), orderQty));
            predicatesList.add(cb.equal(customerPrice.get(CustomerPrice_.deleteStatus), false));
            
            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            Query q = em.createQuery(cq);
            

            return new Response().success("查詢產品實體成功", q.getSingleResult());

        } catch(NoResultException e){
            return new Response().Empty("無此範圍內客戶產品單價資料");
        }finally {
            em.close();
        }
    }
    
    public List<CustomerPrice> findCustomerPriceByProductMasterId(Product productMasterId,Customer customerMasterId) {
        EntityManager entityManager = getEntityManager();
        try {
            //創建安全查詢工廠
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            //創建查詢主語句
            CriteriaQuery<CustomerPrice>  criteriaQuery = criteriaBuilder.createQuery(CustomerPrice.class);
            //定義實體類型
            Root<CustomerPrice> customerPrice = criteriaQuery.from(CustomerPrice.class);
            
            //利用Predicate過濾多個查詢條件
            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(criteriaBuilder.equal(customerPrice.get(CustomerPrice_.productMasterId),productMasterId));
            predicatesList.add(criteriaBuilder.equal(customerPrice.get(CustomerPrice_.customerMasterId),customerMasterId));
            //查詢有效數據
            predicatesList.add(criteriaBuilder.equal(customerPrice.get(CustomerPrice_.deleteStatus),false));
            criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            criteriaQuery.orderBy(criteriaBuilder.asc(customerPrice.get(CustomerPrice_.rangeMin)));
            
            //創建查詢
            Query query = entityManager.createQuery(criteriaQuery);
            return query.getResultList();                    
        } finally{
            entityManager.close();
        }
    }

    public List<CustomerPrice> findCustomerPriceByProductMasterId(Product productMasterId) {
        EntityManager entityManager = getEntityManager();
        try {
            //創建安全查詢工廠
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            //創建查詢主語句
            CriteriaQuery<CustomerPrice>  criteriaQuery = criteriaBuilder.createQuery(CustomerPrice.class);
            //定義實體類型
            Root<CustomerPrice> customerPrice = criteriaQuery.from(CustomerPrice.class);
            
            //利用Predicate過濾多個查詢條件
            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(criteriaBuilder.equal(customerPrice.get(CustomerPrice_.productMasterId),productMasterId));
            //查詢有效數據
            predicatesList.add(criteriaBuilder.equal(customerPrice.get(CustomerPrice_.deleteStatus),false));
            criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            
            //創建查詢
            Query query = entityManager.createQuery(criteriaQuery);
            return query.getResultList();                    
        } finally{
            entityManager.close();
        }
    }

    public CustomerPrice findCustomerPriceByCusPriceMasterId(String cusPriceMasterId) {
        EntityManager entityManager = getEntityManager();
        try {
            //創建安全查詢工廠
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            //創建查詢主語句
            CriteriaQuery<CustomerPrice>  criteriaQuery = criteriaBuilder.createQuery(CustomerPrice.class);
            //定義實體類型
            Root<CustomerPrice> customerPrice = criteriaQuery.from(CustomerPrice.class);
            
            //利用Predicate過濾多個查詢條件
            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(criteriaBuilder.equal(customerPrice.get(CustomerPrice_.cusPriceMasterId),cusPriceMasterId));
            //查詢有效數據
            predicatesList.add(criteriaBuilder.equal(customerPrice.get(CustomerPrice_.deleteStatus),false));
            criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            
            //創建查詢
            Query query = entityManager.createQuery(criteriaQuery);
            return (CustomerPrice) query.getSingleResult();                    
        } finally{
            entityManager.close();
        }
    }

    public CustomerPrice findCustomerPriceByConditions(Product productMasterId, Customer customerMasterId, int rangeMin, int rangeMax) {
        EntityManager entityManager = getEntityManager();
        try {
            //創建安全查詢工廠
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            //創建查詢主語句
            CriteriaQuery<CustomerPrice>  criteriaQuery = criteriaBuilder.createQuery(CustomerPrice.class);
            //定義實體類型
            Root<CustomerPrice> customerPrice = criteriaQuery.from(CustomerPrice.class);
            
            //利用Predicate過濾多個查詢條件
            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(criteriaBuilder.equal(customerPrice.get(CustomerPrice_.productMasterId),productMasterId));
            predicatesList.add(criteriaBuilder.equal(customerPrice.get(CustomerPrice_.customerMasterId),customerMasterId));
            predicatesList.add(criteriaBuilder.equal(customerPrice.get(CustomerPrice_.rangeMin),rangeMin));
            predicatesList.add(criteriaBuilder.equal(customerPrice.get(CustomerPrice_.rangeMax),rangeMax));
            //查詢有效數據
            predicatesList.add(criteriaBuilder.equal(customerPrice.get(CustomerPrice_.deleteStatus),false));
            criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            
            //創建查詢
            Query query = entityManager.createQuery(criteriaQuery);
            return (CustomerPrice) query.getSingleResult();                    
        } finally{
            entityManager.close();
        }
    }
    
    
}
