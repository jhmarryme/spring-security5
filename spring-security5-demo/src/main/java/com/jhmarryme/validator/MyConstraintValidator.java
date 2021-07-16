package com.jhmarryme.validator;

import com.jhmarryme.web.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 具体约束类
 *      MyConstraint: 验证的注解是什么, Object: 验证的数据类型是什么, 不需要使用@Component注解
 *
 * @author JiaHao Wang
 * @date 2020/9/9 13:07
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

    // 可以注入任意需要的对象
    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("my validator init");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        // 这里处理验证逻辑
        helloService.greeting("wjh");
        System.out.println(o);
        // 返回验证结果
        return false;
    }
}
