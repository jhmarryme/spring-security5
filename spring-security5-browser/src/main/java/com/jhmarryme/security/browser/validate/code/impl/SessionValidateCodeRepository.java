package com.jhmarryme.security.browser.validate.code.impl;

import com.jhmarryme.security.core.validate.code.ValidateCodeRepository;
import com.jhmarryme.security.core.validate.code.base.ValidateCode;
import com.jhmarryme.security.core.validate.code.base.ValidateCodeTypeEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * 基于session的 验证码存储器
 * @author JiaHao Wang
 * @date 2021/2/23 12:35
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * key的前缀
     */
    private static final String SESSION_KEY_PREFIX = "SESSION_KEY_IMAGE_CODE_";

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeTypeEnum validateCodeTypeEnum) {
        getSession().setAttribute(getSessionKey(validateCodeTypeEnum), code);
    }

    @Override
    public Optional<ValidateCode> get(ServletWebRequest request, ValidateCodeTypeEnum validateCodeTypeEnum) {
        Object value = getSession().getAttribute(getSessionKey(validateCodeTypeEnum));
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of((ValidateCode) value);
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeTypeEnum validateCodeTypeEnum) {
        getSession().removeAttribute(getSessionKey(validateCodeTypeEnum));
    }

    /**
     * 构建验证码放入session时的key
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/30 10:46
     * @return java.lang.String
     */
    private String getSessionKey(ValidateCodeTypeEnum validateCodeTypeEnum) {
        return SESSION_KEY_PREFIX + validateCodeTypeEnum.toString().toUpperCase();
    }

    public HttpSession getSession() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }
}
