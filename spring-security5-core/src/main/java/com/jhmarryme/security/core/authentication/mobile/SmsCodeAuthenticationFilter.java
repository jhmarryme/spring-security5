package com.jhmarryme.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 参考{@code UsernamePasswordAuthenticationFilter} 编写
 *      1. 修改匹配的路径
 *      2. 修改登录参数 KEY, parameter. 因为是手机登录, 所以是"mobile"
 *      3. 删除不需要的字段和方法
 *      4. attemptAuthentication方法编写
 * @author JiaHao Wang
 * @date 2020/12/7 12:29
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    
    /**  
     * 请求中的 参数名称
     */
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";
    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        // 匹配的路径
        super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if (this.postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            // 从请求中取出手机号
            String mobile = this.obtainMobile(request);
            if (mobile == null) {
                mobile = "";
            }

            mobile = mobile.trim();
            // 实例化 smsCodeAuthenticationToken
            SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
            // 将请求的详情存入
            this.setDetails(request, authRequest);
            // 交给 authenticationManager 处理
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    /**
     * 获取手机号
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/7 21:09
     * @param request
     * @return java.lang.String
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(this.mobileParameter);
    }

    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Username parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return this.mobileParameter;
    }

}
