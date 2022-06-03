package com.hk.crowd;

import com.hk.crowd.entity.po.MemberPO;
import com.hk.crowd.entity.po.MemberPOExample;
import com.hk.crowd.mapper.MemberPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MemberPOMapper memberPOMapper;
    Logger logger=LoggerFactory.getLogger(MybatisTest.class);
    @Test
    public void Test1() throws SQLException {
        logger.debug(dataSource.getConnection().toString());
    }
    @Test
    public void Test2(){
        logger.info(memberPOMapper.selectByExample(new MemberPOExample()).toString());
    }
    @Test
    public void testMapper() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String source = "123123";
        String encode = passwordEncoder.encode(source);
        MemberPO memberPO = new MemberPO(null, "jack", encode, " 杰 克 ",
                "jack@qq.com", 1, 1, "杰克", "123123", 2);
        memberPOMapper.insert(memberPO);
    }
    @Test
    public void Test3(){
        // 1.创建 Example 对象
        MemberPOExample example = new MemberPOExample();
// 2.创建 Criteria 对象
        MemberPOExample.Criteria criteria = example.createCriteria();
// 3.封装查询条件
        criteria.andLoginacctEqualTo("jack");
// 4.执行查询
        List<MemberPO> list = memberPOMapper.selectByExample(example);
        logger.info(memberPOMapper.selectByExample(example).get(0).toString());
    }
}
