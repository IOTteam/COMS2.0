/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.dao.repository;

import iot.dao.entity.OrderDetail;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import iot.dao.entity.OrderHead;
import iot.dao.entity.Product;
import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.dao.repository.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hatanococoro
 */
public class OrderDetailDAO implements Serializable {

    public OrderDetailDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrderDetail orderDetail) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrderHead ordheadMasterId = orderDetail.getOrdheadMasterId();
            if (ordheadMasterId != null) {
                ordheadMasterId = em.getReference(ordheadMasterId.getClass(), ordheadMasterId.getOrdheadMasterId());
                orderDetail.setOrdheadMasterId(ordheadMasterId);
            }
            Product productMasterId = orderDetail.getProductMasterId();
            if (productMasterId != null) {
                productMasterId = em.getReference(productMasterId.getClass(), productMasterId.getProductMasterId());
                orderDetail.setProductMasterId(productMasterId);
            }
            em.persist(orderDetail);
            if (ordheadMasterId != null) {
                ordheadMasterId.getOrderDetailMasterCollection().add(orderDetail);
                ordheadMasterId = em.merge(ordheadMasterId);
            }
            if (productMasterId != null) {
                productMasterId.getOrderDetailMasterCollection().add(orderDetail);
                productMasterId = em.merge(productMasterId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrderDetail(orderDetail.getOrddetailMasterId()) != null) {
                throw new PreexistingEntityException("OrderDetail " + orderDetail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrderDetail orderDetail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrderDetail persistentOrderDetail = em.find(OrderDetail.class, orderDetail.getOrddetailMasterId());
            OrderHead ordheadMasterIdOld = persistentOrderDetail.getOrdheadMasterId();
            OrderHead ordheadMasterIdNew = orderDetail.getOrdheadMasterId();
            Product productMasterIdOld = persistentOrderDetail.getProductMasterId();
            Product productMasterIdNew = orderDetail.getProductMasterId();
            if (ordheadMasterIdNew != null) {
                ordheadMasterIdNew = em.getReference(ordheadMasterIdNew.getClass(), ordheadMasterIdNew.getOrdheadMasterId());
                orderDetail.setOrdheadMasterId(ordheadMasterIdNew);
            }
            if (productMasterIdNew != null) {
                productMasterIdNew = em.getReference(productMasterIdNew.getClass(), productMasterIdNew.getProductMasterId());
                orderDetail.setProductMasterId(productMasterIdNew);
            }
            orderDetail = em.merge(orderDetail);
            if (ordheadMasterIdOld != null && !ordheadMasterIdOld.equals(ordheadMasterIdNew)) {
                ordheadMasterIdOld.getOrderDetailMasterCollection().remove(orderDetail);
                ordheadMasterIdOld = em.merge(ordheadMasterIdOld);
            }
            if (ordheadMasterIdNew != null && !ordheadMasterIdNew.equals(ordheadMasterIdOld)) {
                ordheadMasterIdNew.getOrderDetailMasterCollection().add(orderDetail);
                ordheadMasterIdNew = em.merge(ordheadMasterIdNew);
            }
            if (productMasterIdOld != null && !productMasterIdOld.equals(productMasterIdNew)) {
                productMasterIdOld.getOrderDetailMasterCollection().remove(orderDetail);
                productMasterIdOld = em.merge(productMasterIdOld);
            }
            if (productMasterIdNew != null && !productMasterIdNew.equals(productMasterIdOld)) {
                productMasterIdNew.getOrderDetailMasterCollection().add(orderDetail);
                productMasterIdNew = em.merge(productMasterIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = orderDetail.getOrddetailMasterId();
                if (findOrderDetail(id) == null) {
                    throw new NonexistentEntityException("The orderDetail with id " + id + " no longer exists.");
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
            OrderDetail orderDetail;
            try {
                orderDetail = em.getReference(OrderDetail.class, id);
                orderDetail.getOrddetailMasterId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderDetail with id " + id + " no longer exists.", enfe);
            }
            OrderHead ordheadMasterId = orderDetail.getOrdheadMasterId();
            if (ordheadMasterId != null) {
                ordheadMasterId.getOrderDetailMasterCollection().remove(orderDetail);
                ordheadMasterId = em.merge(ordheadMasterId);
            }
            Product productMasterId = orderDetail.getProductMasterId();
            if (productMasterId != null) {
                productMasterId.getOrderDetailMasterCollection().remove(orderDetail);
                productMasterId = em.merge(productMasterId);
            }
            em.remove(orderDetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrderDetail> findOrderDetailEntities() {
        return findOrderDetailEntities(true, -1, -1);
    }

    public List<OrderDetail> findOrderDetailEntities(int maxResults, int firstResult) {
        return findOrderDetailEntities(false, maxResults, firstResult);
    }

    private List<OrderDetail> findOrderDetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrderDetail.class));
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

    public OrderDetail findOrderDetail(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrderDetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderDetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrderDetail> rt = cq.from(OrderDetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
