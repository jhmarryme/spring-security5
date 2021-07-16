package com.jhmarryme.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhmarryme.security.core.support.SimpleResponse;
import com.jhmarryme.security.core.properties.LoginType;
import com.jhmarryme.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败的处理逻辑
 * @author JiaHao Wang
 * @date 2020/11/30 19:04
 */
@Component("imoocAuthenticationFailureHandler")
@Slf4j
public class ImoocAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        log.info("登录失败");
        // 登录的返回类型判断: 只对JSON的处理, REDIRECT采用框架默认的处理
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
        } else {
            super.onAuthenticationFailure(request, response, e);
        }
    }
}
