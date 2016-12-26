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
import iot.dao.entity.Customer;
import iot.dao.entity.Customer_;
import iot.dao.entity.OrderDetail;
import iot.dao.entity.OrderHead;
import iot.dao.entity.OrderHead_;
import iot.dao.repository.exceptions.IllegalOrphanException;
import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.dao.repository.exceptions.PreexistingEntityException;
import iot.response.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author hatanococoro
 */
public class OrderHeadDAO implements Serializable {

    public OrderHeadDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrderHead orderHead) throws PreexistingEntityException, Exception {
        if (orderHead.getOrderDetailMasterCollection() == null) {
            orderHead.setOrderDetailMasterCollection(new ArrayList<OrderDetail>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customerMasterId = orderHead.getCustomerMasterId();
            if (customerMasterId != null) {
                customerMasterId = em.getReference(customerMasterId.getClass(), customerMasterId.getCustomerMasterId());
                orderHead.setCustomerMasterId(customerMasterId);
            }
            Collection<OrderDetail> attachedOrderDetailMasterCollection = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailMasterCollectionOrderDetailToAttach : orderHead.getOrderDetailMasterCollection()) {
                orderDetailMasterCollectionOrderDetailToAttach = em.getReference(orderDetailMasterCollectionOrderDetailToAttach.getClass(), orderDetailMasterCollectionOrderDetailToAttach.getOrddetailMasterId());
                attachedOrderDetailMasterCollection.add(orderDetailMasterCollectionOrderDetailToAttach);
            }
            orderHead.setOrderDetailMasterCollection(attachedOrderDetailMasterCollection);
            em.persist(orderHead);
            if (customerMasterId != null) {
                customerMasterId.getOrderHeadMasterCollection().add(orderHead);
                customerMasterId = em.merge(customerMasterId);
            }
            for (OrderDetail orderDetailMasterCollectionOrderDetail : orderHead.getOrderDetailMasterCollection()) {
                OrderHead oldOrdheadMasterIdOfOrderDetailMasterCollectionOrderDetail = orderDetailMasterCollectionOrderDetail.getOrdheadMasterId();
                orderDetailMasterCollectionOrderDetail.setOrdheadMasterId(orderHead);
                orderDetailMasterCollectionOrderDetail = em.merge(orderDetailMasterCollectionOrderDetail);
                if (oldOrdheadMasterIdOfOrderDetailMasterCollectionOrderDetail != null) {
                    oldOrdheadMasterIdOfOrderDetailMasterCollectionOrderDetail.getOrderDetailMasterCollection().remove(orderDetailMasterCollectionOrderDetail);
                    oldOrdheadMasterIdOfOrderDetailMasterCollectionOrderDetail = em.merge(oldOrdheadMasterIdOfOrderDetailMasterCollectionOrderDetail);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrderHead(orderHead.getOrdheadMasterId()) != null) {
                throw new PreexistingEntityException("OrderHead " + orderHead + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrderHead orderHead) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrderHead persistentOrderHead = em.find(OrderHead.class, orderHead.getOrdheadMasterId());
            Customer customerMasterIdOld = persistentOrderHead.getCustomerMasterId();
            Customer customerMasterIdNew = orderHead.getCustomerMasterId();
            Collection<OrderDetail> orderDetailMasterCollectionOld = persistentOrderHead.getOrderDetailMasterCollection();
            Collection<OrderDetail> orderDetailMasterCollectionNew = orderHead.getOrderDetailMasterCollection();
            List<String> illegalOrphanMessages = null;
            for (OrderDetail orderDetailMasterCollectionOldOrderDetail : orderDetailMasterCollectionOld) {
                if (!orderDetailMasterCollectionNew.contains(orderDetailMasterCollectionOldOrderDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderDetail " + orderDetailMasterCollectionOldOrderDetail + " since its ordheadMasterId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (customerMasterIdNew != null) {
                customerMasterIdNew = em.getReference(customerMasterIdNew.getClass(), customerMasterIdNew.getCustomerMasterId());
                orderHead.setCustomerMasterId(customerMasterIdNew);
            }
            Collection<OrderDetail> attachedOrderDetailMasterCollectionNew = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailMasterCollectionNewOrderDetailToAttach : orderDetailMasterCollectionNew) {
                orderDetailMasterCollectionNewOrderDetailToAttach = em.getReference(orderDetailMasterCollectionNewOrderDetailToAttach.getClass(), orderDetailMasterCollectionNewOrderDetailToAttach.getOrddetailMasterId());
                attachedOrderDetailMasterCollectionNew.add(orderDetailMasterCollectionNewOrderDetailToAttach);
            }
            orderDetailMasterCollectionNew = attachedOrderDetailMasterCollectionNew;
            orderHead.setOrderDetailMasterCollection(orderDetailMasterCollectionNew);
            orderHead = em.merge(orderHead);
            if (customerMasterIdOld != null && !customerMasterIdOld.equals(customerMasterIdNew)) {
                customerMasterIdOld.getOrderHeadMasterCollection().remove(orderHead);
                customerMasterIdOld = em.merge(customerMasterIdOld);
            }
            if (customerMasterIdNew != null && !customerMasterIdNew.equals(customerMasterIdOld)) {
                customerMasterIdNew.getOrderHeadMasterCollection().add(orderHead);
                customerMasterIdNew = em.merge(customerMasterIdNew);
            }
            for (OrderDetail orderDetailMasterCollectionNewOrderDetail : orderDetailMasterCollectionNew) {
                if (!orderDetailMasterCollectionOld.contains(orderDetailMasterCollectionNewOrderDetail)) {
                    OrderHead oldOrdheadMasterIdOfOrderDetailMasterCollectionNewOrderDetail = orderDetailMasterCollectionNewOrderDetail.getOrdheadMasterId();
                    orderDetailMasterCollectionNewOrderDetail.setOrdheadMasterId(orderHead);
                    orderDetailMasterCollectionNewOrderDetail = em.merge(orderDetailMasterCollectionNewOrderDetail);
                    if (oldOrdheadMasterIdOfOrderDetailMasterCollectionNewOrderDetail != null && !oldOrdheadMasterIdOfOrderDetailMasterCollectionNewOrderDetail.equals(orderHead)) {
                        oldOrdheadMasterIdOfOrderDetailMasterCollectionNewOrderDetail.getOrderDetailMasterCollection().remove(orderDetailMasterCollectionNewOrderDetail);
                        oldOrdheadMasterIdOfOrderDetailMasterCollectionNewOrderDetail = em.merge(oldOrdheadMasterIdOfOrderDetailMasterCollectionNewOrderDetail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = orderHead.getOrdheadMasterId();
                if (findOrderHead(id) == null) {
                    throw new NonexistentEntityException("The orderHead with id " + id + " no longer exists.");
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
            OrderHead orderHead;
            try {
                orderHead = em.getReference(OrderHead.class, id);
                orderHead.getOrdheadMasterId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderHead with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<OrderDetail> orderDetailMasterCollectionOrphanCheck = orderHead.getOrderDetailMasterCollection();
            for (OrderDetail orderDetailMasterCollectionOrphanCheckOrderDetail : orderDetailMasterCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This OrderHead (" + orderHead + ") cannot be destroyed since the OrderDetail " + orderDetailMasterCollectionOrphanCheckOrderDetail + " in its orderDetailMasterCollection field has a non-nullable ordheadMasterId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Customer customerMasterId = orderHead.getCustomerMasterId();
            if (customerMasterId != null) {
                customerMasterId.getOrderHeadMasterCollection().remove(orderHead);
                customerMasterId = em.merge(customerMasterId);
            }
            em.remove(orderHead);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrderHead> findOrderHeadEntities() {
        return findOrderHeadEntities(true, -1, -1);
    }

    public List<OrderHead> findOrderHeadEntities(int maxResults, int firstResult) {
        return findOrderHeadEntities(false, maxResults, firstResult);
    }

    private List<OrderHead> findOrderHeadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrderHead.class));
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

    public OrderHead findOrderHead(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrderHead.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderHeadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrderHead> rt = cq.from(OrderHead.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /*******************************************************************************
     * 建立者：Saulden  建立日期：-  最後修訂日期：-
     * 功能簡述：通過客戶實體查詢訂單頭檔數量
     * 
     * @param customer
     * @return 
     ********************************************************************************/
    public Response findOrderHeadByCustomer(Customer customer) {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            //定义实体类型
            Root<OrderHead> orderHead = cq.from(OrderHead.class);
             cq.select(cb.count(orderHead));

            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.equal(orderHead.get(OrderHead_.customerMasterId), customer));
            predicatesList.add(cb.equal(orderHead.get(OrderHead_.deleteStatus), false));
            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            Query q = em.createQuery(cq);
            
            int count = ((Long)q.getSingleResult()).intValue();
            
            if(count > 0){
                return new Response().success("該客戶有訂單存在");
            }

            return new Response().Empty("該客戶無訂單");

        } finally {
            em.close();
        }
    }
    
}
