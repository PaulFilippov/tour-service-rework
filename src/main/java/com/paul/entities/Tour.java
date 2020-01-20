package com.paul.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_tour;
    private String name;
    private String description;
    private String location;
    @Temporal(TemporalType.DATE)
    private Date start_date;
    @Temporal(TemporalType.DATE)
    private Date end_date;
    private Integer count_limit;
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Order> ordersOfTour;

    public Tour() {
    }

    public Tour(String name, String description, String location, Date start_date, Date end_date, Integer count_limit) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.start_date = start_date;
        this.end_date = end_date;
        this.count_limit = count_limit;
        this.ordersOfTour = new HashSet<>();
    }

    public Long getId_tour() {
        return id_tour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Integer getCount_limit() {
        return count_limit;
    }

    public void setCount_limit(Integer count_limit) {
        this.count_limit = count_limit;
    }

    public Set<Order> getOrdersOfTour() {
        return ordersOfTour;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return Objects.equals(id_tour, tour.id_tour) &&
                Objects.equals(name, tour.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_tour, name);
    }


    @Override
    public String toString() {
        return "Tour{" +
                "id_tour=" + id_tour +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", count_limit=" + count_limit +
                ", ordersOfTour=" + ordersOfTour +
                '}';
    }
}