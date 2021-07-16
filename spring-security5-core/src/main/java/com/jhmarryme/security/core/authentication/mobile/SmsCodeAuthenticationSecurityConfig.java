package com.jhmarryme.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 短信登录请求配置
 * @author JiaHao Wang
 * @date 2020/12/8 12:05
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<
        DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 自定义的filter
        AbstractAuthenticationProcessingFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();

        // filter中需要设置AuthenticationManager
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        // 失败/成功处理器
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(imoocAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);

        // 自定义的认证逻辑
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

        // 添加配置 Provider & filter
        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
