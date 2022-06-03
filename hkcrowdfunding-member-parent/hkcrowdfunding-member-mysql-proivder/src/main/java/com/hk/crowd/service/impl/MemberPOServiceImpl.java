package com.hk.crowd.service.impl;

import com.hk.crowd.constant.CrowdConstant;
import com.hk.crowd.entity.po.MemberPO;
import com.hk.crowd.entity.po.MemberPOExample;
import com.hk.crowd.mapper.MemberPOMapper;
import com.hk.crowd.service.api.MemberPOService;
import com.hk.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Transactional(readOnly = true)
@Service
public class MemberPOServiceImpl implements MemberPOService {
    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberByAcct(String loginacct) {
        // 1.创建 Example 对象
        MemberPOExample example = new MemberPOExample();
// 2.创建 Criteria 对象
        MemberPOExample.Criteria criteria = example.createCriteria();
// 3.封装查询条件
        criteria.andLoginacctEqualTo(loginacct);
// 4.执行查询
        List<MemberPO> list = memberPOMapper.selectByExample(example);
        return list.get(0);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW,readOnly = false,rollbackFor = Exception.class)
    @Override
    public ResultEntity<String> save(MemberPO memberPO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //对memberPO的密码进行加密
        String userpswd = memberPO.getUserpswd();
        String newPasd = bCryptPasswordEncoder.encode(userpswd);
        memberPO.setUserpswd(newPasd);
        try {
            memberPOMapper.insertSelective(memberPO);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return ResultEntity.failed(CrowdConstant.LOGIN_ACCT_IN_USE);
            }
        }
        return ResultEntity.successWithoutData();
    }
}
