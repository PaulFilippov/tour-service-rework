package com.paul.entities;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    USER;

    UserRole() {
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
