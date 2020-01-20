package com.paul.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_user;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private boolean active;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private UserRole authorities;
    @OneToMany(mappedBy = "userOrders", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Order> userOrders;


    public User() {

    }

    public User(String first_name, String last_name, String email, String password, boolean active, Date birthday, UserRole authorities) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.active = active;
        this.birthday = birthday;
        this.userOrders = new HashSet<>();
        this.authorities = authorities;
    }



    public Long getId_user() {
        return id_user;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setAuthorities(UserRole authorities) {
        this.authorities = authorities;
    }

    public Set<Order> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(Set<Order> userOrders) {
        this.userOrders = userOrders;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id_user +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", birthday=" + birthday +
                ", authorities=" + authorities +
                ", userOrders=" + userOrders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id_user, user.id_user) &&
                Objects.equals(last_name, user.last_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_user, last_name);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserRole> authority = new ArrayList<UserRole>();
        authority.add(this.authorities);
        return authority;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
