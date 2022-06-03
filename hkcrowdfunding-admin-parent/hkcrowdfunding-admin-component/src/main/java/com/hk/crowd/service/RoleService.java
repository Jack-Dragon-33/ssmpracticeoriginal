package com.hk.crowd.service;

import com.github.pagehelper.PageInfo;
import com.hk.crowd.entity.Role;

import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);
    void save(Role role);
    Role getRole(Integer id);
    void updateRole(Role role);
    void deleteRole(List<Integer> list);
    List<Role> getAssignedRole(Integer adminId);
    List<Role> getUnAssignedRole(Integer adminId);
    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}
