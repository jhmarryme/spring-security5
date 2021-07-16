package com.jhmarryme.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * 自定义过滤器, 如果使用@Component 则不需要额外配置
 *      过滤器的优先级会高于拦截器
 *      可以拿到原始的请求, 但无法获取请求控制器和控制器中的方法信息
 *
 * @author JiaHao Wang
 * @date 2020/9/10 15:57
 */
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("time filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        System.out.println("time filter start");
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        System.out.println("time filter 耗时:" + (System.currentTimeMillis() - start));
        System.out.println("time filter finish");
    }

    @Override
    public void destroy() {
        System.out.println("time filter destroy");
    }
}
