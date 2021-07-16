package com.jhmarryme.security.core.validate.code;

import com.jhmarryme.security.core.validate.code.config.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

/**
 * 验证码过滤器配置类
 * @author JiaHao Wang
 * @date 2021/1/12 12:22
 */
@Component
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        // 在 AbstractPreAuthenticatedProcessingFilter 前增加一个 ValidateCodeFilter 用于处理图形验证码/短信验证码逻辑
        builder.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }
}
