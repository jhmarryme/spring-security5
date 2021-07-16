package com.jhmarryme.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 * @author Jiahao Wang
 * @date 2021/3/2 12:33
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {

    /** 操作模块 */
    String operModule() default "";
    /** 操作类型 */
    String operType() default "";
    /** 操作说明 */
    String operDesc() default "";
}