package com.hk.crowd.service.api;

import com.hk.crowd.entity.po.AddressPO;
import com.hk.crowd.entity.vo.AddressVO;
import com.hk.crowd.entity.vo.OrderProjectVO;
import com.hk.crowd.entity.vo.OrderVO;

import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
public interface OrderService {
    OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId);

    List<AddressVO> getAddressList(Integer memberId);

    void addAddress(AddressPO addressPO);

    void saveOrder(OrderVO orderVO);
}
