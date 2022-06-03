package com.hk.crowd.handler;

import com.hk.crowd.api.MySQLRemoteService;
import com.hk.crowd.constant.CrowdConstant;
import com.hk.crowd.entity.po.MemberPO;
import com.hk.crowd.entity.vo.AddressVO;
import com.hk.crowd.entity.vo.OrderProjectVO;
import com.hk.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Controller
public class OrderHandler {
    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO, HttpSession session) {
        try {
            mySQLRemoteService.saveAddress(addressVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        Integer returnCount = orderProjectVO.getReturnCount();
        return "redirect:http://localhost/order/confirm/order/" + returnCount;
    }

    @RequestMapping("/confirm/order/{returnCount}")
    public String showConfirmOrderInfo(
            @PathVariable("returnCount") Integer returnCount,
            HttpSession session) {
// 1.把接收到的回报数量合并到 Session 域
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        orderProjectVO.setReturnCount(returnCount);
        session.setAttribute("orderProjectVO", orderProjectVO);
// 2.获取当前已登录用户的 id
        MemberPO memberLoginVO = (MemberPO)
                session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();
// 3.查询目前的收货地址数据
        ResultEntity<List<AddressVO>> resultEntity =
                mySQLRemoteService.getAddressVORemote(memberId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getOperationResult())) {
            List<AddressVO> list = resultEntity.getQueryData();
            session.setAttribute("addressVOList", list);
        }
        return "confirm_order";
    }

    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirm(@PathVariable("projectId") Integer projectId,
                                    @PathVariable("returnId") Integer returnId,
                                    HttpSession session) {
        ResultEntity<OrderProjectVO> resultEntity =
                mySQLRemoteService.getOrderProjectVORemote(projectId, returnId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getOperationResult())) {
            OrderProjectVO orderProjectVO = resultEntity.getQueryData();
            session.setAttribute("orderProjectVO", orderProjectVO);
        }
        return "confirm_return";
    }
}
