package com.hk.crowd.service.api;

import com.hk.crowd.entity.po.MemberPO;
import com.hk.crowd.util.ResultEntity;

/**
 * @author Dragon
 * @version 1.0.0
 */
public interface MemberPOService {
    MemberPO getMemberByAcct(String loginacct);

    ResultEntity<String> save(MemberPO memberPO);
}
