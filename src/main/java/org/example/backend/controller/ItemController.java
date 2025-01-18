package org.example.backend.controller;

import jakarta.persistence.*;
import org.example.backend.database.DatabaseManager;
import org.example.backend.model.Item;

import java.util.List;

public class ItemController {
    private EntityManagerFactory emf = DatabaseManager.getInstance().getEmf();
    public void addItem(Item item) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
        em.close();
    }

    public void updateItem(Item item) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(item);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteItem(Integer itemId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Item item = em.find(Item.class, itemId);
        if (item != null) {
            em.remove(item);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Item> searchItemByName(String name, boolean ascending) {
        EntityManager em = emf.createEntityManager();
        String order = ascending ? "ASC" : "DESC";
        List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.name LIKE :name ORDER BY i.name " + order, Item.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
        em.close();
        return items;
    }
}
