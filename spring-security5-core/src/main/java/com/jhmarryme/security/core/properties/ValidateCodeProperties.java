package com.jhmarryme.security.core.properties;

import lombok.Data;

/**
 *
 * @author JiaHao Wang
 * @date 2020/12/1 12:16
 */
@Data
public class ValidateCodeProperties {

    /**
     * 图形验证码
     */
    private ImageCodeProperties image = new ImageCodeProperties();

    /**
     * 短信验证码
     */
    private SmsCodeProperties smsCode = new SmsCodeProperties();
}
