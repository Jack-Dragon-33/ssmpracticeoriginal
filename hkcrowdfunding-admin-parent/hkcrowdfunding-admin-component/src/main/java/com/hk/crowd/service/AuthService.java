package com.hk.crowd.service;

import com.hk.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @author Dragon
 * @version 1.0.0
 */
public interface AuthService {
    List<Auth> getAll();
    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);
    void saveRoleAuthRelathinship(Map<String,List<Integer>>map);
    List<String> getAllAuths(Integer adminId);
}
