/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.dao.repository;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import iot.dao.entity.OrderDetail;
import java.util.ArrayList;
import java.util.Collection;
import iot.dao.entity.CustomerPrice;
import iot.dao.entity.Product;
import iot.dao.entity.Product_;
import iot.dao.repository.exceptions.IllegalOrphanException;
import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.dao.repository.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import iot.response.Response;
import javax.persistence.NoResultException;

/**
 *
 * @author hatanococoro
 */
public class ProductDAO implements Serializable {

    public ProductDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) throws PreexistingEntityException, Exception {
        if (product.getOrderDetailMasterCollection() == null) {
            product.setOrderDetailMasterCollection(new ArrayList<OrderDetail>());
        }
        if (product.getCustomerPriceMasterCollection() == null) {
            product.setCustomerPriceMasterCollection(new ArrayList<CustomerPrice>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<OrderDetail> attachedOrderDetailMasterCollection = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailMasterCollectionOrderDetailToAttach : product.getOrderDetailMasterCollection()) {
                orderDetailMasterCollectionOrderDetailToAttach = em.getReference(orderDetailMasterCollectionOrderDetailToAttach.getClass(), orderDetailMasterCollectionOrderDetailToAttach.getOrddetailMasterId());
                attachedOrderDetailMasterCollection.add(orderDetailMasterCollectionOrderDetailToAttach);
            }
            product.setOrderDetailMasterCollection(attachedOrderDetailMasterCollection);
            Collection<CustomerPrice> attachedCustomerPriceMasterCollection = new ArrayList<CustomerPrice>();
            for (CustomerPrice customerPriceMasterCollectionCustomerPriceToAttach : product.getCustomerPriceMasterCollection()) {
                customerPriceMasterCollectionCustomerPriceToAttach = em.getReference(customerPriceMasterCollectionCustomerPriceToAttach.getClass(), customerPriceMasterCollectionCustomerPriceToAttach.getCusPriceMasterId());
                attachedCustomerPriceMasterCollection.add(customerPriceMasterCollectionCustomerPriceToAttach);
            }
            product.setCustomerPriceMasterCollection(attachedCustomerPriceMasterCollection);
            em.persist(product);
            for (OrderDetail orderDetailMasterCollectionOrderDetail : product.getOrderDetailMasterCollection()) {
                Product oldProductMasterIdOfOrderDetailMasterCollectionOrderDetail = orderDetailMasterCollectionOrderDetail.getProductMasterId();
                orderDetailMasterCollectionOrderDetail.setProductMasterId(product);
                orderDetailMasterCollectionOrderDetail = em.merge(orderDetailMasterCollectionOrderDetail);
                if (oldProductMasterIdOfOrderDetailMasterCollectionOrderDetail != null) {
                    oldProductMasterIdOfOrderDetailMasterCollectionOrderDetail.getOrderDetailMasterCollection().remove(orderDetailMasterCollectionOrderDetail);
                    oldProductMasterIdOfOrderDetailMasterCollectionOrderDetail = em.merge(oldProductMasterIdOfOrderDetailMasterCollectionOrderDetail);
                }
            }
            for (CustomerPrice customerPriceMasterCollectionCustomerPrice : product.getCustomerPriceMasterCollection()) {
                Product oldProductMasterIdOfCustomerPriceMasterCollectionCustomerPrice = customerPriceMasterCollectionCustomerPrice.getProductMasterId();
                customerPriceMasterCollectionCustomerPrice.setProductMasterId(product);
                customerPriceMasterCollectionCustomerPrice = em.merge(customerPriceMasterCollectionCustomerPrice);
                if (oldProductMasterIdOfCustomerPriceMasterCollectionCustomerPrice != null) {
                    oldProductMasterIdOfCustomerPriceMasterCollectionCustomerPrice.getCustomerPriceMasterCollection().remove(customerPriceMasterCollectionCustomerPrice);
                    oldProductMasterIdOfCustomerPriceMasterCollectionCustomerPrice = em.merge(oldProductMasterIdOfCustomerPriceMasterCollectionCustomerPrice);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProduct(product.getProductMasterId()) != null) {
                throw new PreexistingEntityException("Product " + product + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getProductMasterId());
            Collection<OrderDetail> orderDetailMasterCollectionOld = persistentProduct.getOrderDetailMasterCollection();
            Collection<OrderDetail> orderDetailMasterCollectionNew = product.getOrderDetailMasterCollection();
            Collection<CustomerPrice> customerPriceMasterCollectionOld = persistentProduct.getCustomerPriceMasterCollection();
            Collection<CustomerPrice> customerPriceMasterCollectionNew = product.getCustomerPriceMasterCollection();
            List<String> illegalOrphanMessages = null;
            for (OrderDetail orderDetailMasterCollectionOldOrderDetail : orderDetailMasterCollectionOld) {
                if (!orderDetailMasterCollectionNew.contains(orderDetailMasterCollectionOldOrderDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderDetail " + orderDetailMasterCollectionOldOrderDetail + " since its productMasterId field is not nullable.");
                }
            }
            for (CustomerPrice customerPriceMasterCollectionOldCustomerPrice : customerPriceMasterCollectionOld) {
                if (!customerPriceMasterCollectionNew.contains(customerPriceMasterCollectionOldCustomerPrice)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CustomerPrice " + customerPriceMasterCollectionOldCustomerPrice + " since its productMasterId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<OrderDetail> attachedOrderDetailMasterCollectionNew = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailMasterCollectionNewOrderDetailToAttach : orderDetailMasterCollectionNew) {
                orderDetailMasterCollectionNewOrderDetailToAttach = em.getReference(orderDetailMasterCollectionNewOrderDetailToAttach.getClass(), orderDetailMasterCollectionNewOrderDetailToAttach.getOrddetailMasterId());
                attachedOrderDetailMasterCollectionNew.add(orderDetailMasterCollectionNewOrderDetailToAttach);
            }
            orderDetailMasterCollectionNew = attachedOrderDetailMasterCollectionNew;
            product.setOrderDetailMasterCollection(orderDetailMasterCollectionNew);
            Collection<CustomerPrice> attachedCustomerPriceMasterCollectionNew = new ArrayList<CustomerPrice>();
            for (CustomerPrice customerPriceMasterCollectionNewCustomerPriceToAttach : customerPriceMasterCollectionNew) {
                customerPriceMasterCollectionNewCustomerPriceToAttach = em.getReference(customerPriceMasterCollectionNewCustomerPriceToAttach.getClass(), customerPriceMasterCollectionNewCustomerPriceToAttach.getCusPriceMasterId());
                attachedCustomerPriceMasterCollectionNew.add(customerPriceMasterCollectionNewCustomerPriceToAttach);
            }
            customerPriceMasterCollectionNew = attachedCustomerPriceMasterCollectionNew;
            product.setCustomerPriceMasterCollection(customerPriceMasterCollectionNew);
            product = em.merge(product);
            for (OrderDetail orderDetailMasterCollectionNewOrderDetail : orderDetailMasterCollectionNew) {
                if (!orderDetailMasterCollectionOld.contains(orderDetailMasterCollectionNewOrderDetail)) {
                    Product oldProductMasterIdOfOrderDetailMasterCollectionNewOrderDetail = orderDetailMasterCollectionNewOrderDetail.getProductMasterId();
                    orderDetailMasterCollectionNewOrderDetail.setProductMasterId(product);
                    orderDetailMasterCollectionNewOrderDetail = em.merge(orderDetailMasterCollectionNewOrderDetail);
                    if (oldProductMasterIdOfOrderDetailMasterCollectionNewOrderDetail != null && !oldProductMasterIdOfOrderDetailMasterCollectionNewOrderDetail.equals(product)) {
                        oldProductMasterIdOfOrderDetailMasterCollectionNewOrderDetail.getOrderDetailMasterCollection().remove(orderDetailMasterCollectionNewOrderDetail);
                        oldProductMasterIdOfOrderDetailMasterCollectionNewOrderDetail = em.merge(oldProductMasterIdOfOrderDetailMasterCollectionNewOrderDetail);
                    }
                }
            }
            for (CustomerPrice customerPriceMasterCollectionNewCustomerPrice : customerPriceMasterCollectionNew) {
                if (!customerPriceMasterCollectionOld.contains(customerPriceMasterCollectionNewCustomerPrice)) {
                    Product oldProductMasterIdOfCustomerPriceMasterCollectionNewCustomerPrice = customerPriceMasterCollectionNewCustomerPrice.getProductMasterId();
                    customerPriceMasterCollectionNewCustomerPrice.setProductMasterId(product);
                    customerPriceMasterCollectionNewCustomerPrice = em.merge(customerPriceMasterCollectionNewCustomerPrice);
                    if (oldProductMasterIdOfCustomerPriceMasterCollectionNewCustomerPrice != null && !oldProductMasterIdOfCustomerPriceMasterCollectionNewCustomerPrice.equals(product)) {
                        oldProductMasterIdOfCustomerPriceMasterCollectionNewCustomerPrice.getCustomerPriceMasterCollection().remove(customerPriceMasterCollectionNewCustomerPrice);
                        oldProductMasterIdOfCustomerPriceMasterCollectionNewCustomerPrice = em.merge(oldProductMasterIdOfCustomerPriceMasterCollectionNewCustomerPrice);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = product.getProductMasterId();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProductMasterId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<OrderDetail> orderDetailMasterCollectionOrphanCheck = product.getOrderDetailMasterCollection();
            for (OrderDetail orderDetailMasterCollectionOrphanCheckOrderDetail : orderDetailMasterCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the OrderDetail " + orderDetailMasterCollectionOrphanCheckOrderDetail + " in its orderDetailMasterCollection field has a non-nullable productMasterId field.");
            }
            Collection<CustomerPrice> customerPriceMasterCollectionOrphanCheck = product.getCustomerPriceMasterCollection();
            for (CustomerPrice customerPriceMasterCollectionOrphanCheckCustomerPrice : customerPriceMasterCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the CustomerPrice " + customerPriceMasterCollectionOrphanCheckCustomerPrice + " in its customerPriceMasterCollection field has a non-nullable productMasterId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：獲取未刪除的產品列表信息，用於新增客戶產品單價
     * 
     * @param productId
     * @return 
     ********************************************************************************/
        public Response findProductListByProductId(String productId) {
        EntityManager em = getEntityManager();
        try {
            //創建查詢工廠
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //創建查詢主語句
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            //定義實體類型
            Root<Product> product = cq.from(Product.class);
            
            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.like(product.get(Product_.productId), "%" + productId + "%"));
            predicatesList.add(cb.equal(product.get(Product_.deleteStatus), false));

            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            Query q = em.createQuery(cq);
            
            q.setMaxResults(10);
            
            List list = q.getResultList();
            
            if(list.isEmpty()){
                return findProductByProductName(productId, false, 0, 10);
            }
            return new Response().success("產品查詢成功", q.getResultList(),0);

        } finally {
            em.close();
        }
    }
        
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：通過產品編號獲取產品實體
     * 
     * @param productId
     * @return 
     ********************************************************************************/
        public Response findProductByProductId(String productId) {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            //定义实体类型
            Root<Product> product = cq.from(Product.class);

            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.equal(product.get(Product_.productId), productId));
            predicatesList.add(cb.equal(product.get(Product_.deleteStatus), false));
            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            Query q = em.createQuery(cq);

            return new Response().success("產品查詢成功", q.getSingleResult());

        }catch(NoResultException e){
            return new Response().Empty("產品編號【"+ productId +"】的產品不存在");
        } finally {
            em.close();
        }
    }
        
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：通過產品名稱獲取產品實體
     * 
     * @param productName
     * @param all
     * @param firstResult
     * @param maxResult
     * @return 
     ********************************************************************************/
        public Response findProductByProductName(String productName,boolean all, int firstResult, int maxResult) {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            //定义实体类型
            Root<Product> product = cq.from(Product.class);

            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.like(product.get(Product_.productName), "%" + productName + "%"));
            predicatesList.add(cb.equal(product.get(Product_.deleteStatus), false));
            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            Query q = em.createQuery(cq);
            
            if(!all){
                q.setFirstResult(firstResult);
                q.setMaxResults(maxResult);
            }

            return new Response().success("查詢產品實體列表成功", q.getResultList());

        } finally {
            em.close();
        }
    }
        
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：通過產品名稱查詢全部產品實體
     * 
     * @param productName
     * @return 
     ********************************************************************************/
    public Response findProductByProductName(String productName){
        return findProductByProductName(productName, true, -1, -1);
    }
    
     /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：通過產品名稱獲取相似的產品名稱
     * 
     * @param productName
     * @param all
     * @param firstResult
     * @param maxResult
     * @return 
     ********************************************************************************/
        public Response findProductNameListByProductName(String productName,boolean all, int firstResult, int maxResult) {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            //定义实体类型
            Root<Product> product = cq.from(Product.class);
            cq.select(cb.array(product.get(Product_.productName)));
            cq.distinct(true);

            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.like(product.get(Product_.productName), "%" + productName + "%"));
            predicatesList.add(cb.equal(product.get(Product_.deleteStatus), false));
            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            Query q = em.createQuery(cq);
            
            if(!all){
                q.setFirstResult(firstResult);
                q.setMaxResults(maxResult);
            }

            return new Response().success("查詢產品名稱列表成功", q.getResultList());

        } finally {
            em.close();
        }
    }
    
}
