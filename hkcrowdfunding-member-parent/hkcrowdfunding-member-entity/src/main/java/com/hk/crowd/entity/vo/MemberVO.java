package com.hk.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
    private String loginacct;
    private String userpswd;
    private String username;
    private String email;
    private String phoneNum;
    private String checkCode;
}
