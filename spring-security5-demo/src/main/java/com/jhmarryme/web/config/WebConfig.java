package com.jhmarryme.web.config;

import com.jhmarryme.web.filter.TimeFilter;
import com.jhmarryme.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;

/**
 * 自定义配置类
 *
 * @author JiaHao Wang
 * @date 2020/9/10 16:05
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private TimeInterceptor timeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义的拦截器
//        registry.addInterceptor(timeInterceptor);
    }

    // 通过手动注入的方式添加过滤器
//    @Bean
    public FilterRegistrationBean timeFilter() {

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new TimeFilter());

        // 过滤的url
        ArrayList<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);

        return registrationBean;
    }
}
