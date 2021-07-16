package com.jhmarryme.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器, 使用@Component后还需要在配置类注册
 *      可以拿到控制器和方法, 但拿不到方法参数
 * @author JiaHao Wang
 * @date 2020/9/10 16:36
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object o) throws Exception {
        System.out.println("preHandle");

        // 拦截的类名
        System.out.println(((HandlerMethod) o).getBean().getClass().getName());
        // 拦截的方法名
        System.out.println(((HandlerMethod) o).getMethod().getName());

        // 在request 存入数据
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        // 从request 取出数据
        Long start = (Long) request.getAttribute("startTime");
        System.out.println("time interceptor 耗时:" + (System.currentTimeMillis() - start));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion");
        // 从request 取出数据
        Long start = (Long) request.getAttribute("startTime");
        System.out.println("time interceptor 耗时:" + (System.currentTimeMillis() - start));
        // 查看异常信息
        System.out.println("e is " + e);
    }
}
