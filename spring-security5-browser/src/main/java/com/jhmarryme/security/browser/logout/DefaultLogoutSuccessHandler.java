package com.jhmarryme.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhmarryme.security.core.support.SimpleResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的退出登陆逻辑
 * @author JiaHao Wang
 * @date 2021/2/7 11:32
 */
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultLogoutSuccessHandler.class);

    private final String signOutUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public DefaultLogoutSuccessHandler(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

        String username = ((User) authentication.getPrincipal()).getUsername();

        LOG.info("退出成功: 用户名{}", username);
        if (StringUtils.isBlank(signOutUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        } else {
            response.sendRedirect(signOutUrl);
        }
    }
}
