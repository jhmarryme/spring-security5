package com.jhmarryme.security.browser;

import com.jhmarryme.security.browser.logout.DefaultLogoutSuccessHandler;
import com.jhmarryme.security.browser.session.ImoocExpiredSessionStrategy;
import com.jhmarryme.security.browser.session.ImoocInvalidSessionStrategy;
import com.jhmarryme.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 *
 * @author JiaHao Wang
 * @date 2021/1/29 11:41
 */
@Configuration
public class BrowserSecurityBeanConfig {


    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new ImoocInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new ImoocExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new DefaultLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
    }

}
