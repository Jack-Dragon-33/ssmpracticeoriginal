package com.hk.crowd.mvc.config;

import com.hk.crowd.entity.Admin;
import com.hk.crowd.entity.Role;
import com.hk.crowd.service.AdminService;
import com.hk.crowd.service.AuthService;
import com.hk.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Component
public class CrowdUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;
    @Override
    public UserDetails loadUserByUsername(String loginAcct) throws UsernameNotFoundException {
        Admin admin = adminService.getAdminByLoginAcct(loginAcct);
        List<Role> assignedRole = roleService.getAssignedRole(admin.getId());
        List<String> allAuths = authService.getAllAuths(admin.getId());
        List<GrantedAuthority> authorities=new ArrayList<>();
        for (Role role : assignedRole) {
            String name = role.getName();
            name="ROLE_"+name;
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(name);
            authorities.add(simpleGrantedAuthority);
        }
        for (String allAuth : allAuths) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(allAuth);
            authorities.add(simpleGrantedAuthority);
        }
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);
        System.out.println(authorities);
        return securityAdmin;
    }
}
