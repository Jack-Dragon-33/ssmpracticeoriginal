package com.hk.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hk.crowd.constant.CrowdConstant;
import com.hk.crowd.entity.Admin;
import com.hk.crowd.entity.AdminExample;
import com.hk.crowd.exception.LoginAcctAlreadyInUseException;
import com.hk.crowd.exception.LoginFailedException;
import com.hk.crowd.mapper.AdminMapper;
import com.hk.crowd.service.AdminService;
import com.hk.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public void saveAdmin(Admin admin) {
        try {
            String userPswd = admin.getUserPswd();
            admin.setUserPswd(passwordEncoder.encode(userPswd));
            mapper.insert(admin);
        }catch (Exception e){
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowdConstant.LOGIN_ACCT_IN_USE);
            }
        }
    }
    @Override
    public List<Admin> getAll() {
        return mapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String username) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(username);
        List<Admin> list = mapper.selectByExample(adminExample);
        if(list==null||list.size()==0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if(list.size()>1){
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT);
        }
        Admin admin = list.get(0);
        if(admin==null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyWord, String pageNum, String pageSize) {
        //在mybatis中先配置pagehelper的插件；
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<Admin> list=mapper.selectAdminByPage(keyWord);
        return new PageInfo<>(list);
    }

    @Override
    public int removeAdmin(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteMoreAdmin(List<Integer> list) {
        return mapper.deleteMoreAmdin(list);
    }

    @Override
    public Admin getAdminById(Integer id) {
        Admin admin = mapper.selectByPrimaryKey(id);
        return admin;
    }

    @Override
    public int update(Admin admin) {
        int result=0;
        try{
            result = mapper.updateByPrimaryKeySelective(admin);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowdConstant.UPDATE_ACCT_IN_USE);
            }
        }
        return result;
    }
}
