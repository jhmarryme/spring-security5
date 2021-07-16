package com.jhmarryme.security.core.validate.code.impl;

import com.jhmarryme.security.core.validate.code.ValidateCodeRepository;
import com.jhmarryme.security.core.validate.code.base.ValidateCode;
import com.jhmarryme.security.core.validate.code.ValidateCodeGenerator;
import com.jhmarryme.security.core.validate.code.ValidateCodeProcessor;
import com.jhmarryme.security.core.validate.code.base.ValidateCodeException;
import com.jhmarryme.security.core.validate.code.base.ValidateCodeTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;
import java.util.Optional;

/**
 * 抽象方法模式——算法骨架
 * 对验证码的一些公有的业务逻辑进行抽离，做到复用
 * @author JiaHao Wang
 * @date 2020/12/3 20:19
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 通过spring的依赖查找机制, 收集 {@link ValidateCodeGenerator} 接口的所有实现
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGeneratorMap;

    /**
     * 验证码的存储介质
     */
    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        // 生成
        C validateCode = generate(request);
        // 存储
        save(request, validateCode);
        // 发送 （抽象方法 由具体的子类实现各自的发送逻辑）
        send(request, validateCode);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeTypeEnum validateCodeType = getValidateCodeType();

        Optional<ValidateCode> codeOpt = validateCodeRepository.get(request, validateCodeType);
        ValidateCode valCodeInStorage = codeOpt.orElseThrow(() -> new ValidateCodeException("验证码不存在"));

        String codeInRequest;

        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    validateCodeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("从请求中获取验证码失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(validateCodeType + "验证码的值不能为空");
        }

        if (valCodeInStorage.isExpired()) {
            validateCodeRepository.remove(request, validateCodeType);
            throw new ValidateCodeException(validateCodeType + "验证码已过期");
        }

        if (!StringUtils.equals(valCodeInStorage.getCode(), codeInRequest)) {
            throw new ValidateCodeException(validateCodeType + "验证码不匹配");
        }

        validateCodeRepository.remove(request, validateCodeType);
    }


    /**
     * 根据类型的不同 生成校验码
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/3 20:25
     * @param request
     * @return C
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType().toString().toLowerCase();
        String generateName = type + "CodeGenerator";
        ValidateCodeGenerator validateCodeGenerator =
                Optional.ofNullable(validateCodeGeneratorMap.get(generateName))
                        .orElseThrow(() -> new ValidateCodeException("验证码生成器: " + generateName + "不存在"));
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存校验码到session
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/30 10:47
     * @param request
     * @param validateCode
     * @return void
     */
    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, getValidateCodeType());
    }

    /**
     * 发送校验码，由子类实现
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/30 10:46
     * @param request
     * @param validateCode
     * @return void
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 获取验证码的类型
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/30 10:43
     * @return com.com.jhmarryme.security.core.validate.code.base.ValidateCodeTypeEnum
     */
    private ValidateCodeTypeEnum getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeTypeEnum.valueOf(type.toUpperCase());
    }

}
