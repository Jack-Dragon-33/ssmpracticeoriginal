package com.hk.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Dragon
 * @version 1.0.0
 */
@Configuration
public class CrowdWebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String urlPath="/member/to/regist.html";
        String viewName="/member-regist";
        registry.addViewController(urlPath).setViewName(viewName);
        registry.addViewController("/member/to/login").setViewName("member-login");
        registry.addViewController("/member/to/vip").setViewName("member-vip");
        registry.addViewController("/member/to/mycrowwdfunding").setViewName("my-crowdfunding");
    }
}
