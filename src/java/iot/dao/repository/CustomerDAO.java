/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.dao.repository;

import iot.dao.entity.Customer;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import iot.dao.entity.CustomerPrice;
import iot.dao.entity.Customer_;
import java.util.ArrayList;
import java.util.Collection;
import iot.dao.entity.OrderHead;
import iot.dao.repository.exceptions.IllegalOrphanException;
import iot.dao.repository.exceptions.JPAQueryException;
import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.dao.repository.exceptions.PreexistingEntityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import iot.response.Response;
import java.util.Iterator;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;

/**
 *
 * @author hatanococoro
 */
public class CustomerDAO implements Serializable {

    public CustomerDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Response create(Customer customer) throws PreexistingEntityException, Exception {
        if (customer.getCustomerPriceMasterCollection() == null) {//客戶下的客戶產品單價資訊集合為null，將其置為空集合
            customer.setCustomerPriceMasterCollection(new ArrayList<>());
        }
        if (customer.getOrderHeadMasterCollection() == null) {//客戶下的訂單資訊集合為null，將其置為空集合
            customer.setOrderHeadMasterCollection(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            //若實體中有客戶產品單價映射的集合，遍歷集合，創建新的客戶產品單價資料到緩存
            Collection<CustomerPrice> attachedCustomerPriceMasterCollection = new ArrayList<>();
            for (CustomerPrice customerPriceMasterCollectionCustomerPriceToAttach : customer.getCustomerPriceMasterCollection()) {
                customerPriceMasterCollectionCustomerPriceToAttach = em.getReference(customerPriceMasterCollectionCustomerPriceToAttach.getClass(), customerPriceMasterCollectionCustomerPriceToAttach.getCusPriceMasterId());
                attachedCustomerPriceMasterCollection.add(customerPriceMasterCollectionCustomerPriceToAttach);
            }
            //設置客戶的客戶產品單價映射集合指向緩存中的客戶產品單價實體集合
            customer.setCustomerPriceMasterCollection(attachedCustomerPriceMasterCollection);

            //若實體中有設置訂單頭檔的映射集合，遍歷集合，創建新的訂單頭檔資料到緩存
            Collection<OrderHead> attachedOrderHeadMasterCollection = new ArrayList<>();
            for (OrderHead orderHeadMasterCollectionOrderHeadToAttach : customer.getOrderHeadMasterCollection()) {
                orderHeadMasterCollectionOrderHeadToAttach = em.getReference(orderHeadMasterCollectionOrderHeadToAttach.getClass(), orderHeadMasterCollectionOrderHeadToAttach.getOrdheadMasterId());
                attachedOrderHeadMasterCollection.add(orderHeadMasterCollectionOrderHeadToAttach);
            }
            //設置客戶的訂單頭檔映射集合指在緩存中已創建的訂單頭檔集合
            customer.setOrderHeadMasterCollection(attachedOrderHeadMasterCollection);
            //新增客戶
            em.persist(customer);

            //遍歷客戶產品單價映射的集合，設置集合中客戶產品單價實體的外鍵為新增的客戶
            for (CustomerPrice customerPriceMasterCollectionCustomerPrice : customer.getCustomerPriceMasterCollection()) {
                Customer oldCustomerMasterIdOfCustomerPriceMasterCollectionCustomerPrice = customerPriceMasterCollectionCustomerPrice.getCustomerMasterId();
                customerPriceMasterCollectionCustomerPrice.setCustomerMasterId(customer);
                customerPriceMasterCollectionCustomerPrice = em.merge(customerPriceMasterCollectionCustomerPrice);

                //若客戶產品單價實體中已存在外鍵，將該客戶產品單價從集合中移除
                if (oldCustomerMasterIdOfCustomerPriceMasterCollectionCustomerPrice != null) {
                    oldCustomerMasterIdOfCustomerPriceMasterCollectionCustomerPrice.getCustomerPriceMasterCollection().remove(customerPriceMasterCollectionCustomerPrice);
                    oldCustomerMasterIdOfCustomerPriceMasterCollectionCustomerPrice = em.merge(oldCustomerMasterIdOfCustomerPriceMasterCollectionCustomerPrice);
                }
            }

            ////遍歷訂單頭檔映射的集合，設置集合中訂單頭檔的外鍵為新增的客戶
            for (OrderHead orderHeadMasterCollectionOrderHead : customer.getOrderHeadMasterCollection()) {
                Customer oldCustomerMasterIdOfOrderHeadMasterCollectionOrderHead = orderHeadMasterCollectionOrderHead.getCustomerMasterId();
                orderHeadMasterCollectionOrderHead.setCustomerMasterId(customer);
                orderHeadMasterCollectionOrderHead = em.merge(orderHeadMasterCollectionOrderHead);
                //若映射的訂單頭檔已存在外鍵，移除該訂單頭檔
                if (oldCustomerMasterIdOfOrderHeadMasterCollectionOrderHead != null) {
                    oldCustomerMasterIdOfOrderHeadMasterCollectionOrderHead.getOrderHeadMasterCollection().remove(orderHeadMasterCollectionOrderHead);
                    oldCustomerMasterIdOfOrderHeadMasterCollectionOrderHead = em.merge(oldCustomerMasterIdOfOrderHeadMasterCollectionOrderHead);
                }
            }
            //提交事務
            em.getTransaction().commit();

            return new Response().success("新增客戶成功", customer);

        } catch (Exception ex) {
            if (!findCustomerByCustomerId(customer.getCustomerId()).isEmpty()) {
                customer.setCustomerId(getCustomerId());
                create(customer);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Response edit(Customer customer) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //查詢客戶實體
            Customer persistentCustomer = em.find(Customer.class, customer.getCustomerMasterId());
            Collection<CustomerPrice> customerPriceMasterCollectionOld = persistentCustomer.getCustomerPriceMasterCollection();
            Collection<CustomerPrice> customerPriceMasterCollectionNew = customer.getCustomerPriceMasterCollection();
            Collection<OrderHead> orderHeadMasterCollectionOld = persistentCustomer.getOrderHeadMasterCollection();
            Collection<OrderHead> orderHeadMasterCollectionNew = customer.getOrderHeadMasterCollection();
            List<String> illegalOrphanMessages = null;
            //檢查修改的客戶中是否映射有全部的客戶產品單價資料
            for (CustomerPrice customerPriceMasterCollectionOldCustomerPrice : customerPriceMasterCollectionOld) {
                if (!customerPriceMasterCollectionNew.contains(customerPriceMasterCollectionOldCustomerPrice)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("必須保留客戶產品單價實體：" + customerPriceMasterCollectionOldCustomerPrice + "，該實體客戶外鍵不為空");
                }
            }
            //檢查修改的客戶中是否映射有全部的訂單頭檔
            for (OrderHead orderHeadMasterCollectionOldOrderHead : orderHeadMasterCollectionOld) {
                if (!orderHeadMasterCollectionNew.contains(orderHeadMasterCollectionOldOrderHead)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("必須保留訂單頭檔實體：" + orderHeadMasterCollectionOldOrderHead + "，該實體客戶外鍵不為空.");
                }
            }
            if (illegalOrphanMessages != null) {//若修改的客戶未完全映射一對多關係，拋出異常
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<CustomerPrice> attachedCustomerPriceMasterCollectionNew = new ArrayList<>();
            //當客戶沒有設置客戶產品單價時，customerPriceMasterCollectionNew為null
            if (customerPriceMasterCollectionNew != null) {//遍歷查詢客戶單價資料，使其保持最新
                for (CustomerPrice customerPriceMasterCollectionNewCustomerPriceToAttach : customerPriceMasterCollectionNew) {
                    customerPriceMasterCollectionNewCustomerPriceToAttach = em.getReference(customerPriceMasterCollectionNewCustomerPriceToAttach.getClass(), customerPriceMasterCollectionNewCustomerPriceToAttach.getCusPriceMasterId());
                    attachedCustomerPriceMasterCollectionNew.add(customerPriceMasterCollectionNewCustomerPriceToAttach);
                }
            }
            //更新客戶下的客戶產品單價集合
            customerPriceMasterCollectionNew = attachedCustomerPriceMasterCollectionNew;
            customer.setCustomerPriceMasterCollection(customerPriceMasterCollectionNew);
            //遍歷查詢訂單頭檔最新資料
            Collection<OrderHead> attachedOrderHeadMasterCollectionNew = new ArrayList<>();
            if (orderHeadMasterCollectionNew != null) {
                for (OrderHead orderHeadMasterCollectionNewOrderHeadToAttach : orderHeadMasterCollectionNew) {
                    orderHeadMasterCollectionNewOrderHeadToAttach = em.getReference(orderHeadMasterCollectionNewOrderHeadToAttach.getClass(), orderHeadMasterCollectionNewOrderHeadToAttach.getOrdheadMasterId());
                    attachedOrderHeadMasterCollectionNew.add(orderHeadMasterCollectionNewOrderHeadToAttach);
                }
            }
            //更新客戶實體中的訂單頭檔資料
            orderHeadMasterCollectionNew = attachedOrderHeadMasterCollectionNew;
            customer.setOrderHeadMasterCollection(orderHeadMasterCollectionNew);
            //修改客戶
            customer = em.merge(customer);

            //更新緩存中的客戶產品單價資料對應外鍵客戶資料
            for (CustomerPrice customerPriceMasterCollectionNewCustomerPrice : customerPriceMasterCollectionNew) {
                if (!customerPriceMasterCollectionOld.contains(customerPriceMasterCollectionNewCustomerPrice)) {
                    Customer oldCustomerMasterIdOfCustomerPriceMasterCollectionNewCustomerPrice = customerPriceMasterCollectionNewCustomerPrice.getCustomerMasterId();
                    customerPriceMasterCollectionNewCustomerPrice.setCustomerMasterId(customer);
                    customerPriceMasterCollectionNewCustomerPrice = em.merge(customerPriceMasterCollectionNewCustomerPrice);
                    //若客戶產品單價中有外鍵對應客戶資料，但不是修改的客戶，將其從客戶的映射集合移除
                    if (oldCustomerMasterIdOfCustomerPriceMasterCollectionNewCustomerPrice != null && !oldCustomerMasterIdOfCustomerPriceMasterCollectionNewCustomerPrice.equals(customer)) {
                        oldCustomerMasterIdOfCustomerPriceMasterCollectionNewCustomerPrice.getCustomerPriceMasterCollection().remove(customerPriceMasterCollectionNewCustomerPrice);
                        oldCustomerMasterIdOfCustomerPriceMasterCollectionNewCustomerPrice = em.merge(oldCustomerMasterIdOfCustomerPriceMasterCollectionNewCustomerPrice);
                    }
                }
            }
            //更新緩存中訂單頭檔中對應外鍵客資料戶
            for (OrderHead orderHeadMasterCollectionNewOrderHead : orderHeadMasterCollectionNew) {
                if (!orderHeadMasterCollectionOld.contains(orderHeadMasterCollectionNewOrderHead)) {
                    Customer oldCustomerMasterIdOfOrderHeadMasterCollectionNewOrderHead = orderHeadMasterCollectionNewOrderHead.getCustomerMasterId();
                    orderHeadMasterCollectionNewOrderHead.setCustomerMasterId(customer);
                    orderHeadMasterCollectionNewOrderHead = em.merge(orderHeadMasterCollectionNewOrderHead);
                    //從客戶映射集合移除不是該客戶名下的訂單頭檔
                    if (oldCustomerMasterIdOfOrderHeadMasterCollectionNewOrderHead != null && !oldCustomerMasterIdOfOrderHeadMasterCollectionNewOrderHead.equals(customer)) {
                        oldCustomerMasterIdOfOrderHeadMasterCollectionNewOrderHead.getOrderHeadMasterCollection().remove(orderHeadMasterCollectionNewOrderHead);
                        oldCustomerMasterIdOfOrderHeadMasterCollectionNewOrderHead = em.merge(oldCustomerMasterIdOfOrderHeadMasterCollectionNewOrderHead);
                    }
                }
            }
            //提交事務
            em.getTransaction().commit();

            return new Response().success("修改客戶資訊成功", customer);

        } catch (OptimisticLockException optimisticLockException) {
            throw new OptimisticLockException("客戶編號為【" + customer.getCustomerId() + "】的資料已被修改，請重試");
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = customer.getCustomerId();
                if (findCustomerByCustomerId(id).isEmpty()) {
                    throw new NonexistentEntityException("客戶編號為【" + id + "】的客戶不存在。");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getCustomerMasterId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CustomerPrice> customerPriceMasterCollectionOrphanCheck = customer.getCustomerPriceMasterCollection();
            for (CustomerPrice customerPriceMasterCollectionOrphanCheckCustomerPrice : customerPriceMasterCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the CustomerPrice " + customerPriceMasterCollectionOrphanCheckCustomerPrice + " in its customerPriceMasterCollection field has a non-nullable customerMasterId field.");
            }
            Collection<OrderHead> orderHeadMasterCollectionOrphanCheck = customer.getOrderHeadMasterCollection();
            for (OrderHead orderHeadMasterCollectionOrphanCheckOrderHead : orderHeadMasterCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the OrderHead " + orderHeadMasterCollectionOrphanCheckOrderHead + " in its orderHeadMasterCollection field has a non-nullable customerMasterId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(customer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customer.class));
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

    public Customer findCustomer(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Customer> rt = cq.from(Customer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    /**
     * *****************************************************************************
     * 建立者：Saulden 建立日期：- 最後修訂日期：- 功能簡述：客戶條件查詢
     *
     * @param customerIdMin
     * @param customerIdMax
     * @param customerName
     * @param pageNo
     * @return
     * ******************************************************************************
     */
    public Response queryCustomerByCondition(String customerIdMin, String customerIdMax, String customerName, int pageNo) throws JPAQueryException {
        EntityManager em = getEntityManager();
        try {
            //創建安全查詢工廠
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //創建查詢主語句
            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            //定義實體類型
            Root<Customer> customer = cq.from(Customer.class);

            //構造過濾條件
            List<Predicate> predicatesList = new ArrayList<>();
            Predicate p1 = cb.greaterThanOrEqualTo(customer.get(Customer_.customerId), customerIdMin);
            predicatesList.add(p1);
            if (!customerIdMax.isEmpty()) {//客戶編號終值不為空
                Predicate p2 = cb.lessThanOrEqualTo(customer.get(Customer_.customerId), customerIdMax);
                predicatesList.add(p2);
            }
            predicatesList.add(cb.like(customer.get(Customer_.customerName), "%" + customerName + "%"));
            predicatesList.add(cb.equal(customer.get(Customer_.deleteStatus), false));
            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            //按客戶編號升序排列
            cq.orderBy(cb.asc(customer.get(Customer_.customerId)));
            //創建Query對象
            Query q = em.createQuery(cq);
            //設置查詢起始位置和最大查詢數量
            q.setFirstResult(pageNo * 10);
            q.setMaxResults(10);

            int count_int = 0;
            if (pageNo == 0) {//當查詢第一頁資料時，查詢資料總數

                CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
                countQuery.select(cb.count(customer));

                countQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
                Query count = em.createQuery(countQuery);
                count_int = ((Long) count.getSingleResult()).intValue();

                if (count_int == 0) {//查詢結果空，返回資訊
                    return new Response().Empty("無此範圍內的客戶資料");
                }
            }
            return new Response<List<Customer>>().success("客戶查詢成功", q.getResultList(), count_int);

        } catch (Exception e) {
            throw new JPAQueryException("條件查詢客戶失敗", e);
        } finally {
            em.close();
        }
    }

    /**
     * *****************************************************************************
     * 建立者：Saulden 建立日期：- 最後修訂日期：- 功能簡述：查詢新增客戶編號
     *
     * @return
     * ******************************************************************************
     */
    public String getCustomerId() {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            //定义实体类型
            Root<Customer> customer = cq.from(Customer.class);
            cq.select(cb.count(customer));

            //獲取當前時間
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
            String customerId = "CUS" + ft.format(date);

            //構造過濾條件
            Predicate predicate = cb.like(customer.get(Customer_.customerId), "%" + customerId + "%");
            cq.where(predicate);

            Query count = em.createQuery(cq);
            int count_int = ((Long) count.getSingleResult()).intValue() + 1;

            return customerId + String.format("%03d", count_int);

        } finally {
            em.close();
        }
    }

    /**
     * *****************************************************************************
     * 建立者：Saulden 建立日期：- 最後修訂日期：- 功能簡述：通過客戶編號查詢客戶實體
     *
     * @param customerId
     * @return
     * ******************************************************************************
     */
    public Response findCustomerByCustomerId(String customerId) {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            //定义实体类型
            Root<Customer> customer = cq.from(Customer.class);

            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.equal(customer.get(Customer_.customerId), customerId));
            predicatesList.add(cb.equal(customer.get(Customer_.deleteStatus), false));
            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            Query q = em.createQuery(cq);

            return new Response().success("查詢客戶實體成功", q.getSingleResult());

        } catch (NoResultException e) {
            return new Response().Empty("客戶編號【" + customerId + "】的客戶不存在");
        } finally {
            em.close();
        }
    }

    /**
     * *****************************************************************************
     * 建立者：Saulden 建立日期：- 最後修訂日期：- 功能簡述：通過客戶姓名查詢客戶實體
     *
     * @param customerName
     * @param all
     * @param firstResult
     * @param maxResult
     * @return
     * ******************************************************************************
     */
    public Response findCustomerByCustomerName(String customerName, boolean all, int firstResult, int maxResult) {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            //定义实体类型
            Root<Customer> customer = cq.from(Customer.class);

            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.like(customer.get(Customer_.customerName), "%" + customerName + "%"));
            predicatesList.add(cb.equal(customer.get(Customer_.deleteStatus), false));
            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));

            Query q = em.createQuery(cq);

            if (!all) {
                q.setFirstResult(firstResult);
                q.setMaxResults(maxResult);
            }

            return new Response().success("客戶查詢成功", q.getResultList());

        } finally {
            em.close();
        }
    }

    /**
     * *****************************************************************************
     * 建立者：Saulden 建立日期：- 最後修訂日期：- 功能簡述：通過客戶姓名查詢全部客戶實體
     *
     * @param customerName
     * @return
     * ******************************************************************************
     */
    public Response findCustomerByCustomerName(String customerName) {
        return findCustomerByCustomerName(customerName, true, -1, -1);
    }

    /**
     * *****************************************************************************
     * 建立者：Saulden 建立日期：- 最後修訂日期：- 功能簡述：通過客戶姓名查詢相似的客戶姓名
     *
     * @param customerName
     * @param all
     * @param firstResult
     * @param maxResult
     * @return
     * ******************************************************************************
     */
    public Response findCustomerNameListByCustomerName(String customerName, boolean all, int firstResult, int maxResult) {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            //定义实体类型
            Root<Customer> customer = cq.from(Customer.class);
            cq.select(cb.array(customer.get(Customer_.customerName)));
            cq.distinct(true);

            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.like(customer.get(Customer_.customerName), "%" + customerName + "%"));
            predicatesList.add(cb.equal(customer.get(Customer_.deleteStatus), false));
            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));

            Query q = em.createQuery(cq);

            if (!all) {
                q.setFirstResult(firstResult);
                q.setMaxResults(maxResult);
            }

            return new Response().success("客戶姓名列表查詢成功", q.getResultList());

        } finally {
            em.close();
        }
    }

    /**
     * *****************************************************************************
     * 建立者：Saulden 建立日期：- 最後修訂日期：- 功能簡述：通過客戶編號查詢相似的客戶編號
     *
     * @param inputId
     * @param customerIdMIn
     * @param customerIdMax
     * @param all
     * @param firstResult
     * @param maxResult
     * @return
     * ******************************************************************************
     */
    public Response findCustomerIdListByCustomerId(String inputId, String customerIdMIn, String customerIdMax, boolean all, int firstResult, int maxResult) {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            //定义实体类型
            Root<Customer> customer = cq.from(Customer.class);
            cq.select(cb.array(customer.get(Customer_.customerId)));
            cq.distinct(true);

            List<Predicate> predicatesList = new ArrayList<>();
            if ("customer_idMin".equals(inputId)) {
                predicatesList.add(cb.like(customer.get(Customer_.customerId), "%" + customerIdMIn + "%"));
                if (!customerIdMax.isEmpty()) {
                    Predicate p1 = cb.lessThanOrEqualTo(customer.get(Customer_.customerId), customerIdMax);
                    predicatesList.add(p1);
                }
            }
            if ("customer_idMax".equals(inputId)) {
                predicatesList.add(cb.like(customer.get(Customer_.customerId), "%" + customerIdMax + "%"));
                if (!customerIdMIn.isEmpty()) {
                    Predicate p2 = cb.greaterThanOrEqualTo(customer.get(Customer_.customerId), customerIdMIn);
                    predicatesList.add(p2);
                }
            }

            predicatesList.add(cb.equal(customer.get(Customer_.deleteStatus), false));
            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));

            //查詢輸入框顯示的值的默認排序——david
            cq.orderBy(cb.asc(customer.get(Customer_.customerId)));
            
            
            Query q = em.createQuery(cq);

            if (!all) {
                q.setFirstResult(firstResult);
                q.setMaxResults(maxResult);
            }

            return new Response().success("客戶編號列表查詢成功", q.getResultList());

        } finally {
            em.close();
        }
    }

    /**
     * *****************************************************************************
     * 建立者：Saulden 建立日期：- 最後修訂日期：- 功能簡述：邏輯刪除客戶，級聯刪除客戶產品單價
     *
     * @param customer
     * @return
     * @throws iot.dao.repository.exceptions.PreexistingEntityException
     * ******************************************************************************
     */
    public Response deleteCustomer(Customer customer) throws PreexistingEntityException, NonexistentEntityException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            try {
                Collection<CustomerPrice> customerPriceCollection = customer.getCustomerPriceMasterCollection();
                Iterator iterator = customerPriceCollection.iterator();
                while (iterator.hasNext()) {
                    CustomerPrice customerPrice = (CustomerPrice) iterator.next();
                    customerPrice.setDeleteStatus(true);
                    em.merge(customerPrice);
                }
                customer.setCustomerPriceMasterCollection(customerPriceCollection);
                customer.setDeleteStatus(true);
                em.merge(customer);
                em.getTransaction().commit();

            } catch (OptimisticLockException optimisticLockException) {
                if (findCustomerByCustomerId(customer.getCustomerId()).isEmpty()) {
                    throw new NonexistentEntityException("該客戶已被刪除");
                }
                throw new OptimisticLockException("客戶資料已被修改，請重試");
            } catch (Exception e) {
                //em.getTransaction().rollback();
                throw new PreexistingEntityException("删除客户失败");
            }
            return new Response().success("刪除客戶成功");

        } finally {
            em.close();
        }
    }

    /**
     * @author David
     *根據客戶名稱查詢客戶實體，存入list 
     * @param customerName
     * @return 
     */
    public List<Customer> getCustomerByCustomerName(String customerName) {
        EntityManager em = getEntityManager();//创建实体管理
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            Root<Customer> customer = cq.from(Customer.class);
            //創建查詢條件，輸入的主鍵id相等，刪除狀態爲假（即未刪除）
            Predicate p1 = cb.and(cb.like(customer.get(Customer_.customerName),"%"+ customerName+"%"), cb.equal(customer.get(Customer_.deleteStatus), false));
            cq.where(p1);
            //創建查詢
            Query q = em.createQuery(cq);
            return (List<Customer>) q.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    /**
     * @author David
     * 查詢客戶信息，用於新增訂單頭檔時選擇顯示信息
     * @param customerId
     * @return 
     */
     public Response queryCustomer(String customerId) {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            //定义实体类型
            Root<Customer> customer = cq.from(Customer.class);
            
            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.like(customer.get(Customer_.customerId), "%" + customerId + "%"));
            predicatesList.add(cb.equal(customer.get(Customer_.deleteStatus), false));

            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            Query q = em.createQuery(cq);
            
            q.setMaxResults(10);
            
            List list = q.getResultList();
            
            if(list.isEmpty()){
                return null;
            }
            return new Response().success("客戶查詢成功", q.getResultList(),0);

        } finally {
            em.close();
        }
    }
     
     public Response findCustomer() {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            //定义实体类型
            Root<Customer> customer = cq.from(Customer.class);
            
            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.equal(customer.get(Customer_.deleteStatus), false));

            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            Query q = em.createQuery(cq);
            
            return new Response().success("客戶查詢成功", q.getResultList());

        } finally {
            em.close();
        }
    }
    
}
