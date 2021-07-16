package com.jhmarryme.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhmarryme.security.core.properties.LoginType;
import com.jhmarryme.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功的处理逻辑
 * @author JiaHao Wang
 * @date 2020/11/30 18:47
 */
@Component("imoocAuthenticationSuccessHandler")
@Slf4j
public class ImoocAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("登录成功");
        // 登录的返回类型判断: 只对JSON的处理, REDIRECT采用框架默认的处理
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            // 将authentication信息显示, 调试用
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
