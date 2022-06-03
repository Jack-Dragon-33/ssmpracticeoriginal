package com.hk.crowd.mvc.config;

import com.hk.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Dragon
 * @version 1.0.0
 */
public class SecurityAdmin extends User {
    private static final long serialVersionUID = 2712325188237406142L;
    private Admin admin;
    public SecurityAdmin(Admin admin, Collection<? extends GrantedAuthority> authorities) {
        super(admin.getLoginAcct(),admin.getUserPswd(), authorities);
        this.admin=admin;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
