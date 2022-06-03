package com.hk.crowd.service.impl;

import com.hk.crowd.entity.po.AddressPO;
import com.hk.crowd.entity.po.AddressPOExample;
import com.hk.crowd.entity.po.OrderPO;
import com.hk.crowd.entity.po.OrderProjectPO;
import com.hk.crowd.entity.vo.AddressVO;
import com.hk.crowd.entity.vo.OrderProjectVO;
import com.hk.crowd.entity.vo.OrderVO;
import com.hk.crowd.mapper.AddressPOMapper;
import com.hk.crowd.mapper.OrderPOMapper;
import com.hk.crowd.mapper.OrderProjectPOMapper;
import com.hk.crowd.service.api.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderPOMapper orderPOMapper;
    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;
    @Autowired
    private AddressPOMapper addressPOMapper;
    @Override
    public OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId) {
        return orderProjectPOMapper.selectOrderProjectVO(returnId);
    }

    @Override
    public List<AddressVO> getAddressList(Integer memberId) {
        AddressPOExample addressPOExample = new AddressPOExample();
        AddressPOExample.Criteria criteria = addressPOExample.createCriteria();
        criteria.andMemberIdEqualTo(memberId);
        List<AddressPO> addressPOS = addressPOMapper.selectByExample(addressPOExample);
        List<AddressVO> list=new ArrayList<>();
        for (AddressPO addressPO : addressPOS) {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO,addressVO);
            list.add(addressVO);
        }
        return list;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public void addAddress(AddressPO addressPO) {
        addressPOMapper.insert(addressPO);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public void saveOrder(OrderVO orderVO) {
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO,orderPO);
        OrderProjectPO orderProjectPO = new OrderProjectPO();
        BeanUtils.copyProperties(orderVO.getOrderProjectVO(),orderProjectPO);
        orderPOMapper.insert(orderPO);
        orderProjectPO.setOrderId(orderPO.getId());
        orderProjectPOMapper.insert(orderProjectPO);
    }
}
