package com.hk.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Dragon
 * @version 1.0.0
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AddressVO implements Serializable {
    private static final long serialVersionUID = -5270493277044693468L;
    private Integer id;
    private String receiveName;
    private String phoneNum;
    private String address;
    private Integer memberId;
}
