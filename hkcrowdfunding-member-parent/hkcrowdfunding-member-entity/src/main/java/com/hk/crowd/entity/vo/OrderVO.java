package com.hk.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO implements Serializable {
    private static final long serialVersionUID = 3534712102845022988L;
    // 主键
    private Integer id;
    // 订单号
    private String orderNum;
    // 支付宝流水单号
    private String payOrderNum;
    // 订单金额
    private Double orderAmount;
    // 是否开发票
    private Integer invoice;
    // 发票抬头
    private String invoiceTitle;
    // 备注
    private String orderRemark;
    private String  addressId;
    private OrderProjectVO orderProjectVO;
}