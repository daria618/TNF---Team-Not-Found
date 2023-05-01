package tnf.back.db.entityes;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    public String getName(){
        return toString();
    }

    @Override
    public String getAuthority() {
        return name();
    }
}