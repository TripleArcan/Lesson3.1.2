package ru.kata.spring.boot_security.demo.security;

import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class UsersDetails implements UserDetails {

    private final User user;
    private final Collection<Role> roles;

    public UsersDetails(User user) {
        this.user = user;
        this.roles = new ArrayList<>(user.getRoles());
        Hibernate.initialize(this.roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
