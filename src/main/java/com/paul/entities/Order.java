package com.paul.entities;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id_order")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_generator")
    @SequenceGenerator(name="order_id_generator", sequenceName = "order_seq", allocationSize=1)
    private Long id_order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tour")
    private Tour tour;
    private boolean confirmed;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    User userOrders;
    private int countReservedByUser;

    public Order() {
    }

    public Order(User user, Tour tour, int countReservedByUser) {
        this.userOrders = user;
        this.tour = tour;
        this.countReservedByUser = countReservedByUser;
        user.userOrders.add(this);
        tour.ordersOfTour.add(this);
    }


    public Long getId_order() {
        return id_order;
    }

    public Long getTourId() {
        return tour.getId_tour();
    }

    @JsonIgnore
    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Long getUserIdsOrder() {
        return userOrders.getId_user();
    }

    @JsonIgnore
    public User getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(User userOrders) {
        this.userOrders = userOrders;
    }

    public Integer getCountReservedByUser() {
        return countReservedByUser;
    }

    public void setCountReservedByUser(Integer countReservedByUser) {
        this.countReservedByUser = countReservedByUser;
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
