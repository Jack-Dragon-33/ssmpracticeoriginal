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
public class OrderProjectVO implements Serializable {
    private static final long serialVersionUID = 3392171951580507268L;
    private Integer id;
    private String projectName;
    private String launchName;
    private String returnContent;
    private Integer returnCount;
    private Integer supportPrice;
    private Integer freight;
    private Integer orderId;
    private Integer signalPurchase;
    private Integer purchase;
}
