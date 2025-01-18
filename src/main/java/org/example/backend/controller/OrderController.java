package org.example.backend.controller;

import jakarta.persistence.*;
import org.example.backend.database.DatabaseManager;
import org.example.backend.model.Order;

import java.util.Date;
import java.util.List;

public class OrderController {
    private EntityManagerFactory emf = DatabaseManager.getInstance().getEmf();
    public void addOrder(Order order) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(order);
        em.getTransaction().commit();
        em.close();
    }

    public void updateOrder(Order order) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(order);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteOrder(Integer orderId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Order order = em.find(Order.class, orderId);
        if (order != null) {
            em.remove(order);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Order> searchOrderByDate(Date date, boolean ascending) {
        EntityManager em = emf.createEntityManager();
        String order = ascending ? "ASC" : "DESC";
        List<Order> orders = em.createQuery("SELECT o FROM Order o WHERE o.createdDate = :date ORDER BY o.createdDate " + order, Order.class)
                .setParameter("date", date)
                .getResultList();
        em.close();
        return orders;
    }
}
