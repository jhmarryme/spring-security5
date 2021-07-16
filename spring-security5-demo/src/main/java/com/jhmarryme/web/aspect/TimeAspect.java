package com.jhmarryme.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;
import java.util.Date;

/**
 * spring aop
 *      可以拿到方法参数, 拿不到请求响应对象
 * @author JiaHao Wang
 * @date 2020/9/10 17:28
 */
@Aspect
//@Component
public class TimeAspect {

    @Around("execution(* com.jhmarryme.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint point) throws Throwable {

        System.out.println("time aspect start");
        // 获取请求的参数
        Arrays.stream(point.getArgs()).forEach(System.out::println);

        long start = System.currentTimeMillis();

        // 执行方法, 获取请求的结果
        Object o = point.proceed();

        System.out.println("time aspect 耗时:" + (System.currentTimeMillis() - start));
        System.out.println("time aspect end");

        return o;
    }

}
