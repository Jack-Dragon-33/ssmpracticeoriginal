package com.hk.crowd.handler;

import com.hk.crowd.api.MySQLRemoteService;
import com.hk.crowd.constant.CrowdConstant;
import com.hk.crowd.entity.vo.PortalTypeVO;
import com.hk.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Controller
public class PortalHandler {
    @Autowired
    private MySQLRemoteService mySQLRemoteService;
    @RequestMapping("/")
    public String showPortalPage(Model model){
        ResultEntity<List<PortalTypeVO>> portalTypeProjectDataRemote = mySQLRemoteService.getPortalTypeProjectDataRemote();
        String operationResult = portalTypeProjectDataRemote.getOperationResult();
        if(ResultEntity.SUCCESS.equals(operationResult)){
            List<PortalTypeVO> queryData = portalTypeProjectDataRemote.getQueryData();
            model.addAttribute(CrowdConstant.ATTR_TEMP_PORTAL_DATA,queryData);
        }
        return "portal";
    }
}
