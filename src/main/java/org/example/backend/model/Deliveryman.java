package org.example.backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import java.util.Set;

@Entity
@Table(name = "deliverymen")
@BatchSize(size = 10)
public class Deliveryman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String phoneNumber;

    @OneToMany(mappedBy = "deliveryman", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders;

    // Constructors, Getters, and Setters
    public Deliveryman() {
    }

    public Deliveryman(Integer id, String name, String phoneNumber, Set<Order> orders) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Deliveryman{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", orders=" + orders +
                '}';
    }
}
