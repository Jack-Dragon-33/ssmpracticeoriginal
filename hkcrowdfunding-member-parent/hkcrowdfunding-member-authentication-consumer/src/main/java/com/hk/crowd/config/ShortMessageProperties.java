package com.hk.crowd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Dragon
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix ="short.message" )
public class ShortMessageProperties {
    private String host;
    private String path;
    private String method;
    private String appCode;
    private String templateId;

}
