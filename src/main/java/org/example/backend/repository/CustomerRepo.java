package org.example.backend.repository;

import org.example.backend.model.Customer;

import java.util.List;

public class CustomerRepo extends Repository {
    @Override
    public Customer get(String id) {

        Customer customer = emf.createEntityManager().find(Customer.class,id);
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = emf.createEntityManager()
                .createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        return customers;
    }
}
