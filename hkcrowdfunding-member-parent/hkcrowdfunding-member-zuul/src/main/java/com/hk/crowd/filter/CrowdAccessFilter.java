package com.hk.crowd.filter;

import com.hk.crowd.constant.AccessPassRescources;
import com.hk.crowd.constant.CrowdConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Component
public class CrowdAccessFilter extends ZuulFilter {
    @Override
    public String filterType() {
        // 这里返回“pre”意思是在目标微服务前执行过滤
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String servletPath = request.getServletPath();
        return !(AccessPassRescources.PASS_RES_ST.contains(servletPath)||
                AccessPassRescources.judgeIsStaticRescources(servletPath));
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpSession session = currentContext.getRequest().getSession();
        Object attribute = session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        if(Objects.isNull(attribute)){
            HttpServletResponse response = currentContext.getResponse();
            try {
                session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE,"请您登录后再进行操作");
                response.sendRedirect("/member/to/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
