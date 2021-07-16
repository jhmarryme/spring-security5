package com.jhmarryme.security.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 并发登录引起的session失效
 * @author JiaHao Wang
 * @date 2021/1/28 17:26
 */
public class ImoocExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {
    public ImoocExpiredSessionStrategy(String destinationUrl) {
        super(destinationUrl);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent eventØ) throws IOException, ServletException {
        onSessionInvalid(eventØ.getRequest(), eventØ.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }

}
