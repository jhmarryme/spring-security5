package com.jhmarryme.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 * @author JiaHao Wang
 * @date 2020/12/3 20:19
 */
public interface ValidateCodeProcessor {

    String SESSION_KEY_PREFIX = "SESSION_KEY_IMAGE_CODE_";

    /**
     * 创建验证码
     * <br/>
     * @param request
     * @return void
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/28 12:38
     * @param request
     * @return void
     */
    void validate(ServletWebRequest request);

}
