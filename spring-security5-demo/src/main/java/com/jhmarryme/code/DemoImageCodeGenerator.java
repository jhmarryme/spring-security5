package com.jhmarryme.code;

import com.jhmarryme.security.core.validate.code.base.ImageCode;
import com.jhmarryme.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 如果有另外的图形验证码生成方式, 可以在这里实现
 *      通过指定名称会覆盖掉默认的bean
 * @author JiaHao Wang
 * @date 2020/12/1 17:43
 */
//@Component("imageValidateCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("另一种生成图形验证码的方式");
        return null;
    }
}
