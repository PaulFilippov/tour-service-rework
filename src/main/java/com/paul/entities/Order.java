package com.paul.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tour")
    private Tour tour;
    private boolean confirmed;
    //    @OneToMany(mappedBy = "userOrders")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    User userOrders;

    public Order() {
    }

    public Order(User user, Tour tour) {
        this.userOrders = user;
        this.tour = tour;
        user.userOrders.add(this);
        tour.ordersOfTour.add(this);
    }

    public Long getId_order() {
        return id_order;
    }

    public Tour getTour() {
        return tour;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public User getUserOrders() {
        return userOrders;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id_order=" + id_order +
                ", tour=" + tour +
                ", confirmed=" + confirmed +
                ", usersOfOrder=" + userOrders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id_order, order.id_order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_order);
    }
}
