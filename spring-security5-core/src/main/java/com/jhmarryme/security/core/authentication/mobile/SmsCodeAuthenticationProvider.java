package com.jhmarryme.security.core.authentication.mobile;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 需要实现 {@code AuthenticationProvider}
 *
 * @author JiaHao Wang
 * @date 2020/12/7 21:10
 */
@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * 根据用户信息组装token
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/21 14:06
     * @param authentication
     * @return org.springframework.security.core.Authentication
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 根据用户名加载用户信息
        UserDetails user = userDetailsService.loadUserByUsername((String) authentication.getPrincipal());

        // 因为是短信登录, 所以只需要手机号对应的用户存在即可
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 组装 token
        SmsCodeAuthenticationToken authenticationToken = new SmsCodeAuthenticationToken(user,
                user.getAuthorities());
        authenticationToken.setDetails(authentication.getDetails());
        return authenticationToken;
    }

    /**
     * 处理的是 SmsCodeAuthenticationToken
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/7 21:12
     * @param aClass
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
