package com.jhmarryme.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 框架的配置入口
 * @author JiaHao Wang
 * @date 2020/11/9 11:36
 */
@ConfigurationProperties(prefix = "imooc.security")
@Data
public class SecurityProperties {

    /**
     * 针对browser的项目相关配置
     */
    private BrowserProperties browser = new BrowserProperties();

    /**
     * 验证码的相关配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    /**
     * oauth2的相关配置
     */
    private Oauth2Properties oauth2 = new Oauth2Properties();
}
