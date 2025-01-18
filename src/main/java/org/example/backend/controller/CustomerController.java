package org.example.backend.controller;

import jakarta.persistence.*;
import org.example.backend.database.DatabaseManager;
import org.example.backend.model.Customer;
import org.example.backend.model.Response;

import java.util.List;

public class CustomerController {
    private EntityManagerFactory emf = DatabaseManager.getInstance().getEmf();

//    public Response<Payment> add(Payment entity) {
//        Response<Payment> response = new Response<>();
//        EntityManager em = emf.createEntityManager();
//        try{
//            paymentRepository.add(entity);
//            em.getTransaction().begin();
//            RentalAgreement agreement = entity.getRentalAgreement();
//            agreement.setPayment(entity);
//            em.merge(agreement);
//            em.getTransaction().commit();
//            handleSuccess(response,"Add Success", 200,entity);
//        } catch (CustomException e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            e.printStackTrace();
//            handleException(response, e.getMessage(),404);
//        } finally {
//            em.close();
//        }
//        return response;
//    }
    public Response<Customer> addCustomer(Customer customer) {
        Response<Customer> response = new Response<>();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
        em.close();
        return response;
    }

    public void updateCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(customer);
        em.getTransaction().commit();
        em.close();
    }

    public List<Customer> searchCustomerByName(String name, boolean ascending) {
        EntityManager em = emf.createEntityManager();
        String order = ascending ? "ASC" : "DESC";
        List<Customer> customers = em.createQuery("SELECT c FROM Customer c WHERE c.name LIKE :name ORDER BY c.name " + order, Customer.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
        em.close();
        return customers;
    }
}
