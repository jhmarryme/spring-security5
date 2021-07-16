package com.jhmarryme.security.core.validate.code.sms;

import com.jhmarryme.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import com.jhmarryme.security.core.validate.code.base.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码处理器
 * @author JiaHao Wang
 * @date 2020/12/3 20:49
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    /**
     * 短信验证码发送器
     */
    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws ServletRequestBindingException {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");

        smsCodeSender.send(mobile, validateCode.getCode());
    }

}
