package com.jhmarryme.security.core.validate.code.base;


import org.springframework.security.core.AuthenticationException;

/**
 * SpringSecurity认证异常类, 需要继承 {@code AuthenticationException}
 * @author JiaHao Wang
 * @date 2020/11/30 17:56
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -302765991473898604L;

    public ValidateCodeException(String explanation) {
        super(explanation);
    }
}
