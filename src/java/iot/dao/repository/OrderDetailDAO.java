/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.dao.repository;

import iot.dao.entity.OrderDetail;
import iot.dao.entity.OrderDetail_;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import iot.dao.entity.OrderHead;
import iot.dao.entity.Product;
import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.dao.repository.exceptions.PreexistingEntityException;
import iot.response.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hatanococoro
 */
@Repository
public class OrderDetailDAO implements Serializable {

    @Autowired
    public OrderDetailDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Response create(OrderDetail orderDetail) throws PreexistingEntityException, Exception {
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

            return new Response().success("新增訂單身檔成功", orderDetail);

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

    public Response edit(OrderDetail orderDetail) throws NonexistentEntityException, Exception {
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

            return new Response().success("修改訂單身檔成功", orderDetail);

        }catch(OptimisticLockException ole){
            throw new OptimisticLockException("編號爲："+orderDetail.getOrderDetailId()+"  的訂單身檔已被修改！");
        } 
        catch (Exception ex) {
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

    /**
     * 通過orderHeadMasterId查詢訂單詳細列表
     *
     * @author David
     * @param orderHeadMasterId
     * @return
     */
    /*
    public List<OrderDetail> queryOrderDetailByOrderHeadMasterId(OrderHead orderHeadMasterId) {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<OrderDetail> cq = cb.createQuery(OrderDetail.class);
            //定义实体类型
            Root<OrderDetail> od = cq.from(OrderDetail.class);

            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.equal(od.get(OrderDetail_.ordheadMasterId), orderHeadMasterId));
            predicatesList.add(cb.equal(od.get(OrderDetail_.deleteStatus), false));

            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            cq.orderBy(cb.asc(od.get(OrderDetail_.orderDetailId)));
            Query q = em.createQuery(cq); 
            List<OrderDetail> list = q.getResultList();

            return list;

        } finally {
            em.close();
        }
    }
     */
    //通過訂單頭檔實體查詢訂單身檔
    public Response queryOrderDetailByOrderHeadMasterId(OrderHead orderHeadMasterId) {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<OrderDetail> cq = cb.createQuery(OrderDetail.class);
            //定义实体类型
            Root<OrderDetail> od = cq.from(OrderDetail.class);

            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.equal(od.get(OrderDetail_.ordheadMasterId), orderHeadMasterId));
            predicatesList.add(cb.equal(od.get(OrderDetail_.deleteStatus), false));

            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            cq.orderBy(cb.asc(od.get(OrderDetail_.orderDetailId)));
            Query q = em.createQuery(cq);

            return new Response().success("查詢訂單身檔成功", q.getResultList());

        } catch (NoResultException e) {
            return new Response().Empty("查詢結果不存在");
        } finally {
            em.close();
        }
    }

    //生成訂單身檔編號
    public String generateOrderDetailId() {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            //定义实体类型
            Root<OrderDetail> od = cq.from(OrderDetail.class);
            cq.select(cb.count(od));

            //獲取當前時間
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String odId = "ORDD" + sdf.format(date);

            //構造過濾條件
            Predicate predicate = cb.like(od.get(OrderDetail_.orderDetailId), "%" + odId + "%");
            cq.where(predicate);

            Query count = em.createQuery(cq);
            int count_int = ((Long) count.getSingleResult()).intValue() + 1;

            return odId + String.format("%03d", count_int);

        } finally {
            em.close();
        }
    }

    //通過訂單身檔編號查詢訂單身檔
    public Response queryOrderDetailByOrderDetailId(String orderDetailId) {
        EntityManager em = getEntityManager();
        try {
            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<OrderDetail> cq = cb.createQuery(OrderDetail.class);
            //定义实体类型
            Root<OrderDetail> od = cq.from(OrderDetail.class);

            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(cb.equal(od.get(OrderDetail_.orderDetailId), orderDetailId));
            predicatesList.add(cb.equal(od.get(OrderDetail_.deleteStatus), false));
            cq.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            Query q = em.createQuery(cq);

            return new Response().success("查詢訂單身檔實體成功", q.getSingleResult());

        } catch (NoResultException e) {
            throw new NoResultException("要查詢的訂單身檔不存在");
        } finally {
            em.close();
        }
    }

    public Response deleteOrderDetail(OrderDetail orderDetail) throws PreexistingEntityException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            try {
                orderDetail.setDeleteStatus(true);
                em.merge(orderDetail);
                em.getTransaction().commit();

            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new PreexistingEntityException("删除訂單身檔檔失败");
            }
            return new Response().success("刪除訂單身檔成功");

        } finally {
            em.close();
        }
    }
    
    //根據productMasterId查詢訂單
    public List<OrderDetail> findOrderDetailByProductMasterId(Product productMasterId) {
        EntityManager entityManager = getEntityManager();
        try {
            //創建安全查詢工廠
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            //創建查詢主語句
            CriteriaQuery<OrderDetail>  criteriaQuery = criteriaBuilder.createQuery(OrderDetail.class);
            //定義實體類型
            Root<OrderDetail> orderDetail = criteriaQuery.from(OrderDetail.class);
            
            //利用Predicate過濾多個查詢條件
            List<Predicate> predicatesList = new ArrayList<>();
            predicatesList.add(criteriaBuilder.equal(orderDetail.get(OrderDetail_.productMasterId),productMasterId));
            //查詢有效數據
            predicatesList.add(criteriaBuilder.equal(orderDetail.get(OrderDetail_.deleteStatus),false));
            criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
            
            //創建查詢
            Query query = entityManager.createQuery(criteriaQuery);
            return query.getResultList();                    
        } finally{
            entityManager.close();
        }
        
    }
}
