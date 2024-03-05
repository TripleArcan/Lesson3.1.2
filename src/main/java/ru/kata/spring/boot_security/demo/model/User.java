package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String username;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Collection<Role> roles;

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public User(String username, String lastName, String password, Collection<Role> roles) {
        this.username = username;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
    }

    public User(String name, String lastName, String password) {
        this.username = name;
        this.lastName = lastName;
        this.password = password;

    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + username + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
