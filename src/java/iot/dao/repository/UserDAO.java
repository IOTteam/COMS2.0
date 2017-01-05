/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iot.dao.repository;

import iot.dao.entity.User;
import iot.dao.entity.User_;
import iot.dao.repository.exceptions.NonexistentEntityException;
import iot.dao.repository.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author David Su
 */
public class UserDAO implements Serializable {

    public UserDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUser(user.getUserMasterId()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.merge(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = user.getUserMasterId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUserMasterId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    //通过用户名和密码来查找用户

    public User getUserByNameAndPassword(String username, String userpass) {

        //创建实体工厂，并使用工厂构建实体管理
        EntityManager em = getEntityManager();

        try {

            //创建安全查询工厂
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            //定义实体类型
            Root<User> user = cq.from(User.class);
            //构造过滤条件
            Predicate condition = cb.and(cb.equal(user.get(User_.userName), username),
                    cb.equal(user.get(User_.userPass), userpass));
            cq.where(condition);
            //创建查询
            Query q = em.createQuery(cq);
            //返回查询结果
            User userInfo = (User) q.getSingleResult();
            return userInfo;

        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }

    }

    //通過賬戶查詢
    public User getUserByUserName(String userName) {
        EntityManager em = getEntityManager();
        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            //创建查询主语句
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            //定义实体类型
            Root<User> user = cq.from(User.class);
            //构造过滤条件
            Predicate condition = cb.and(cb.equal(user.get(User_.userName), userName),
                    cb.equal(user.get(User_.deleteStatus), false));
            cq.where(condition);
            //创建查询
            Query q = em.createQuery(cq);
            //返回查询结果
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    //鎖定帳號自動解鎖
    public void autoUnlock(User umn) {
        //創建實體管理工具
        final EntityManager em = getEntityManager();
        //實例化user實體
        final User user = umn;
        //實例化一個timer
        Timer timer = new Timer();
        //设定指定任务task在指定时间time执行 schedule
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //事務開始
                em.getTransaction().begin();
                //鎖定狀態置爲false
                user.setIslocked(false);
                //鎖定時間置爲null
                user.setLockedTime(null);
                //輸錯密碼記錄數置爲0
                user.setWrongUserpassTimes(0);
                //保存修改後的實體
                em.merge(user);
                //提交事務
                em.getTransaction().commit();
            }
        }, 60000);// 設定自動解鎖帳號的時間，測試時設置爲60秒
    }

}
