package com.hk.crowd.mvc.interceptor;

import com.hk.crowd.constant.CrowdConstant;
import com.hk.crowd.entity.Admin;
import com.hk.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dragon
 * @version 1.0.0
 */
public class LoginIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //首先看session域中有没有登录用户
        Admin admin = (Admin)request.getSession().getAttribute(CrowdConstant.ATTR_NAME_LOGINADMIN);
        if(admin==null){
            throw new AccessForbiddenException("您还没有登录,请先登录");
        }
        return true;
    }
}
