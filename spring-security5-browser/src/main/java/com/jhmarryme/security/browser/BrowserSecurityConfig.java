package com.jhmarryme.security.browser;

import com.jhmarryme.security.browser.session.ImoocExpiredSessionStrategy;
import com.jhmarryme.security.browser.session.ImoocInvalidSessionStrategy;
import com.jhmarryme.security.core.authentication.AbstractChannelSecurityConfig;
import com.jhmarryme.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.jhmarryme.security.core.properties.SecurityConstants;
import com.jhmarryme.security.core.properties.SecurityProperties;
import com.jhmarryme.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * springSecurity 配置
 * @author JiaHao Wang
 * @date 2020/10/29 12:23
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    /**
     * rememberme 配置
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/7 9:15
     * @return org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 在首次运行时 在数据库中创建表
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 开启表单登录相关配置
        applyPasswordAuthenticationConfig(http);

        http
            .apply(validateCodeSecurityConfig)
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            // 配置remember me功能
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getTokenValiditySeconds())
                .userDetailsService(userDetailsService)
                .and()
            .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                // 最大session时, 组织后续用户登录
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and()
                .and()
            .logout()
                .logoutUrl("/signOut")
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .and()
            .authorizeRequests()
                // 不需要登录的路径
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATED_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getSignUpUrl(),
//                        securityProperties.getBrowser().getSignOutUrl(),
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".html",
                        // 该路径由于只有业务系统知道, 还需进一步抽取
                        "/user/register"
                ).permitAll()
                // 其他所有请求都需要认证
                .anyRequest()
                .authenticated()
                .and()
            .csrf()
                .disable();
    }
}
