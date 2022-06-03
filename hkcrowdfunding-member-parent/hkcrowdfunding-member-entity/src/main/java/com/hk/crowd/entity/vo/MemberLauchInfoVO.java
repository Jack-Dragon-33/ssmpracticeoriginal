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
public class MemberLauchInfoVO implements Serializable {
    private static final long serialVersionUID = 7227118847744188610L;
    // 简单介绍
    private String descriptionSimple;
    // 详细介绍
    private String descriptionDetail;
    // 联系电话
    private String phoneNum;
    // 客服电话
    private String serviceNum;
}
