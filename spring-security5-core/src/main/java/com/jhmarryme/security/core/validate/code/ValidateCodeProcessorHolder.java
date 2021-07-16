package com.jhmarryme.security.core.validate.code;

import com.jhmarryme.security.core.validate.code.base.ValidateCodeException;
import com.jhmarryme.security.core.validate.code.base.ValidateCodeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @author JiaHao Wang
 * @date 2020/12/28 12:35
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeTypeEnum type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    /**
     * 根据 类型 查找对应的校验码处理器
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/30 8:48
     * @param type
     * @return com.com.jhmarryme.security.core.validate.code.ValidateCodeProcessor
     */
    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type + ValidateCodeProcessor.class.getSimpleName();

        ValidateCodeProcessor processor = validateCodeProcessorMap.get(name);

        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }

        return processor;
    }
}
