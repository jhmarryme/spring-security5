package com.jhmarryme.security.browser.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhmarryme.security.core.support.SimpleResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session失效策略处理
 * @author JiaHao Wang
 * @date 2021/1/29 11:27
 */
@Data
public abstract class AbstractSessionStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractSessionStrategy.class);

    /**
     * 跳转的url
     */
    private String destinationUrl;
    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    /**
     * 跳转前是否创建新的session
     */
    private boolean createNewSession = true;

    private ObjectMapper objectMapper = new ObjectMapper();

    public AbstractSessionStrategy(String invalidSessionUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }

    public void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (createNewSession) {
            request.getSession();
        }
        String requestUri = request.getRequestURI();

        // 不同的请求 处理
        if (StringUtils.endsWithIgnoreCase(requestUri, ".html")) {
            String targetUrl = this.destinationUrl + ".html";
            response.setContentType("text/html");
            LOG.info("session失效,跳转到" + targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("session已失效" + (isConcurrency() ? ",可能是并发登录导致的" : ""))));
        }


    }

    protected boolean isConcurrency() {
        return false;
    }
}
