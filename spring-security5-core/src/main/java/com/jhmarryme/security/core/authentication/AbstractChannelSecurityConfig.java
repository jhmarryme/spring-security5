package com.jhmarryme.security.core.authentication;

import com.jhmarryme.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 表单登录的相关配置
 * @author JiaHao Wang
 * @date 2021/1/12 12:44
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.formLogin()
                // 当未登录时, 跳转的路径
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATED_URL)
                // 登录请求的处理路径, 提交username和password的URL, 默认配置 UsernamePasswordAuthenticationFilter中 login
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                // 认证 成功/失败 的处理逻辑
                .successHandler(imoocAuthenticationSuccessHandler)
                .failureHandler(imoocAuthenticationFailureHandler);
    }
}
