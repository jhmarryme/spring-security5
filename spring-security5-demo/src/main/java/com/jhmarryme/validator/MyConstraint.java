package com.jhmarryme.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义validator 验证注解
 *      Target 作用范围   Retention运行时   Constraint 用于校验的具体约束
 * @author JiaHao Wang
 * @date 2020/9/9 13:05
 */

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyConstraint {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
