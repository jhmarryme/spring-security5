package com.jhmarryme.security.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session 默认超时失效策略
 * @author JiaHao Wang
 * @date 2021/1/29 11:44
 */
public class ImoocInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {
    public ImoocInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException
            , ServletException {
        onSessionInvalid(request, response);
    }

}
