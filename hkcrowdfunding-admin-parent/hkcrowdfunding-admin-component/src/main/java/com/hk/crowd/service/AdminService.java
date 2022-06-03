package com.hk.crowd.service;

import com.github.pagehelper.PageInfo;
import com.hk.crowd.entity.Admin;

import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
public interface AdminService {
    void saveAdmin(Admin admin);
    List<Admin> getAll();
    Admin getAdminByLoginAcct(String username);

    PageInfo<Admin> getPageInfo(String keyWord, String pageNum, String pageSize);
   int removeAdmin(Integer id);

    int deleteMoreAdmin(List<Integer> list);

    Admin getAdminById(Integer id);
    int update(Admin admin);
}
