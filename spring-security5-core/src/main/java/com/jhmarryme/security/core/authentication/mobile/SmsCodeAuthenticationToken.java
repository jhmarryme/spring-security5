package com.jhmarryme.security.core.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 认证后的token信息 参考{@code UsernamePasswordAuthenticationToken} 编写
 * @author JiaHao Wang
 * @date 2020/12/7 12:20
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 420L;

    /**
     * 登录成功之前存放验证码, 之后存放用户信息
     */
    private final Object principal;

    public SmsCodeAuthenticationToken(String mobile) {
        super((Collection) null);
        this.principal = mobile;
        this.setAuthenticated(false);
    }

    public SmsCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a " +
                    "GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
