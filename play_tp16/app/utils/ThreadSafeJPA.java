package utils;

import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.hibernate.stat.Statistics;

import play.Logger;
import play.Play;
import play.db.DB;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPDataSource;

public class ThreadSafeJPA {
    
    public static class Test {
        
        private static final EntityManagerFactory testEmf = Persistence.createEntityManagerFactory("test-H2");
        
        public static EntityManager em() {
            ThreadLocal<EntityManager> t = new ThreadLocal<EntityManager>();
            t.set(testEmf.createEntityManager());
            return t.get();
        }
    }

    /**
     * Default Persistence Unit name
     */
    private static String UNIT_NAME = Play.application().configuration().getString("jpa.default");

    /**
     * The EMF SINGLETON
     */
    private static final EntityManagerFactory emf;

    static {
        Logger.debug("Creating EntityManagerFactory (EMF) for Persistence Unit: {}", UNIT_NAME);
        emf = Persistence.createEntityManagerFactory(UNIT_NAME);
    }
    
    public static interface Block<R> {
        public R apply(EntityManager em);
    }

    public static EntityManager em() {
        ThreadLocal<EntityManager> t = new ThreadLocal<EntityManager>();
        t.set(emf.createEntityManager());
        return t.get();
    }

    public static Session session() {
        return em().unwrap(Session.class);
    }

    public static SessionFactory sessionFactory() {
        SessionFactory sessionFactory = ((HibernateEntityManagerFactory) emf).getSessionFactory();
        return sessionFactory;
    }

    public static <T> T withTransaction(boolean readOnly, Block<T> block) throws Throwable {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {

            em = em();

            if (!readOnly) {
                tx = em.getTransaction();
                tx.begin();
            }

            T result = block.apply(em);

            if (tx != null) {
                if (tx.getRollbackOnly()) {
                    tx.rollback();
                } else {
                    tx.commit();
                }
            }

            return result;

        } catch (Throwable t) {
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Throwable e) {
                }
            }
            throw t;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Get Hibernate stats
     */
    public static Statistics getHibernateStatistics() {
        return sessionFactory().getStatistics();
    }

    /**
     * Get BoneCP stats
     */
    public static com.jolbox.bonecp.Statistics getBoneCPStatistics() {
        BoneCPDataSource boneCPdatasource = (BoneCPDataSource) DB.getDataSource();
        try {
            Field field = boneCPdatasource.getClass().getDeclaredField("pool");
            field.setAccessible(true);
            BoneCP boneCP = (BoneCP) field.get(boneCPdatasource);
            return boneCP.getStatistics();
        } catch (Exception e) {
            Logger.error(e.toString(), e);
        }
        return null;
    }
}
