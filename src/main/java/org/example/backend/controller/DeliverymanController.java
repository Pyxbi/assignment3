package org.example.backend.controller;

import jakarta.persistence.*;
import org.example.backend.database.DatabaseManager;
import org.example.backend.model.Deliveryman;

import java.util.List;

public class DeliverymanController {
    private EntityManagerFactory emf = DatabaseManager.getInstance().getEmf();
    public void addDeliveryman(Deliveryman deliveryman) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(deliveryman);
        em.getTransaction().commit();
        em.close();
    }

    public void updateDeliveryman(Deliveryman deliveryman) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(deliveryman);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteDeliveryman(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Deliveryman deliveryman = em.find(Deliveryman.class, id);
        if (deliveryman != null) {
            em.remove(deliveryman);
        }
        em.getTransaction().commit();
        em.close();
    }
}
