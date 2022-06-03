package com.hk.crowd.service.impl;

import com.hk.crowd.entity.Auth;
import com.hk.crowd.entity.AuthExample;
import com.hk.crowd.mapper.AuthMapper;
import com.hk.crowd.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;
    @Override
    public List<Auth> getAll() {
        List<Auth> auths = authMapper.selectByExample(new AuthExample());
        return auths;
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
       List<Integer> list= authMapper.getAssignedAuthIdByRoleId(roleId);
        return list;
    }

    @Override
    public void saveRoleAuthRelathinship(Map<String, List<Integer>> map) {
        //获取参数
        Integer roleId = map.get("roleId").get(0);
        List<Integer> integers = map.get("selectArray");
        //首先根据roleId将其权限全部删除
        authMapper.deleteByRoleId(roleId);
        //然后将权限再重新赋予
        authMapper.saveRoleAuth(roleId,integers);
    }

    @Override
    public List<String> getAllAuths(Integer adminId) {
        List<String> auths=authMapper.getAllAuths(adminId);
        return auths;
    }
}
