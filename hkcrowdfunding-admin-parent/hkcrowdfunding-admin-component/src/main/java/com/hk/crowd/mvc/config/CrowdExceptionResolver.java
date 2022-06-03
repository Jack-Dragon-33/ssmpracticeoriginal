package com.hk.crowd.mvc.config;

import com.google.gson.Gson;
import com.hk.crowd.constant.CrowdConstant;
import com.hk.crowd.exception.AccessForbiddenException;
import com.hk.crowd.exception.LoginAcctAlreadyInUseException;
import com.hk.crowd.exception.LoginFailedException;
import com.hk.crowd.util.CrowdUtil;
import com.hk.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Dragon
 * @version 1.0.0
 */
@ControllerAdvice
public class CrowdExceptionResolver {
    @ExceptionHandler(LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(
            LoginFailedException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String viewName = "admin-login";
        return commonResolver(viewName, response, request, exception);
    }
    @ExceptionHandler(LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(
            LoginAcctAlreadyInUseException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String viewName = null;
        if (Objects.equals(CrowdConstant.LOGIN_ACCT_IN_USE,exception.getMessage())){
            viewName = "admin-add";
        }else if(Objects.equals(CrowdConstant.UPDATE_ACCT_IN_USE,exception.getMessage())){
            viewName= "system-error";
        }

        return commonResolver(viewName, response, request, exception);
    }
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView resolverNullPointerException(RuntimeException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName="system-error";
        return commonResolver(viewName,response,request,e);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView resolverException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolver(viewName, response, request, e);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ModelAndView resolverNullPointerException2(NullPointerException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolver(viewName, response, request, e);
    }
    private ModelAndView commonResolver(String viewName,HttpServletResponse response,HttpServletRequest request,Exception exception) throws IOException {
        boolean b = CrowdUtil.judgeRequestType(request);
        //如果是ajax请求
        if(b){
            ResultEntity<Object> failed = ResultEntity.failed(exception.getMessage());
            Gson gson = new Gson();

            response.getWriter().write(gson.toJson(failed));
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
