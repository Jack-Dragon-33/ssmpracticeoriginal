package com.hk.test;

import com.github.pagehelper.PageInfo;
import com.hk.crowd.entity.Admin;
import com.hk.crowd.entity.Role;
import com.hk.crowd.mapper.AdminMapper;
import com.hk.crowd.mapper.RoleMapper;
import com.hk.crowd.service.AdminService;
import com.hk.crowd.util.CrowdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Dragon
 * @version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-persist-mybatis.xml", "classpath:spring/spring-persist-tx.xml"})
public class Test01 {
    @Autowired
    private DataSource ds;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminService service;
    @Autowired
    private RoleMapper roleMapper;
    @Test
    public void DataSourceTest() throws SQLException {
        System.out.println(ds.getConnection());
        System.out.println(adminMapper);
    }
    @Test
    public void Test02() {
        for(int i=0;i<200;i++) {
            Admin admin = new Admin("171370" + i, CrowdUtil.md5("915+sxl.." + i), "Tom" + i, "1713709251" + i + "@qq.com");
            service.saveAdmin(admin);
        }
    }

    @Test
    public void add(){
        for (int i = 0; i < 200; i++) {
            roleMapper.insert(new Role(null,"role"+i));
        }
    }
}

