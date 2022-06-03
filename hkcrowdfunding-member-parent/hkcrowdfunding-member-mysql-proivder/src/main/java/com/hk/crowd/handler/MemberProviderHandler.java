package com.hk.crowd.handler;

import com.hk.crowd.entity.po.MemberPO;
import com.hk.crowd.mapper.MemberPOMapper;
import com.hk.crowd.service.api.MemberPOService;
import com.hk.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dragon
 * @version 1.0.0
 */
@RestController
public class MemberProviderHandler {
    @Autowired
    public MemberPOService service;
    @RequestMapping("/save/member")
    ResultEntity<String> save(@RequestBody MemberPO memberPO){
        ResultEntity<String> resultEntity=service.save(memberPO);
        return resultEntity;
    }
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct) {
        try {
            MemberPO memberPO = service.getMemberByAcct(loginacct);
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e) {
            return ResultEntity.failed("没有此用户");
        }
    }


}
