package com.jhmarryme.security.core.validate.code.config;

import com.jhmarryme.security.core.properties.SecurityProperties;
import com.jhmarryme.security.core.validate.code.ValidateCodeGenerator;
import com.jhmarryme.security.core.validate.code.image.ImageCodeGenerator;
import com.jhmarryme.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.jhmarryme.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码相关的Bean配置
 *      方法1: imageCodeGenerator
 *      方法2: smsCodeGenerator
 * @author JiaHao Wang
 * @date 2020/12/1 17:30
 */
@Configuration
public class ValidateBeanConfig {

    /**
     * 系统配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当不存在name为 imageCodeGenerator的bean时, 创建一个
     * 如果用户创建了, 则不创建该bean
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/1 17:57
     * @param
     * @return com.com.jhmarryme.security.core.validate.code.ValidateCodeGenerator
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }

    /**
     * 当不存在实现了 SmsCodeSender接口的类时, 创建一个
     * 如果用户实现了, 则不创建该bean
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/3 19:16
     * @param
     * @return com.com.jhmarryme.security.core.validate.code.sms.SmsCodeSender
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}
