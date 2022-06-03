package com.hk.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hk.crowd.entity.Role;
import com.hk.crowd.entity.RoleExample;
import com.hk.crowd.mapper.RoleMapper;
import com.hk.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roles = roleMapper.selectBySearch(keyword);
        return new PageInfo<Role>(roles);
    }

    @Override
    public void save(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public Role getRole(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteRole(List<Integer> list) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(list);
        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.getAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.getUnAssignedRole(adminId);
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        roleMapper.deleteByAdminId(adminId);
        if(roleIdList!=null&&roleIdList.size()>0) {
            roleMapper.saveAdminRole(roleIdList,adminId);
        }
    }
}
