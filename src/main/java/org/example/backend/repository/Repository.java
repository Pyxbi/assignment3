package org.example.backend.repository;
/**
 * @author <Butter>
 */
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.backend.database.DatabaseManager;
import org.example.backend.utils.ErrorException;


import java.util.List;

public abstract class Repository<T> {
    protected static final EntityManagerFactory emf = DatabaseManager.getInstance().getEmf();

    public void add(T item) throws ErrorException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            if( em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new ErrorException(e.getMessage(),400);
        }
    }

    public void update(T item) throws ErrorException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(item);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            if( em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new ErrorException(e.getMessage(),400);
        }
    }

    public void delete(T item) throws ErrorException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(item);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            if( em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new ErrorException(e.getMessage(),404);
        }
    }

    public abstract T get(String id);

    public abstract List<T> getAll();

}