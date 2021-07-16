package com.jhmarryme.security.core.properties;

import lombok.Data;

/**
 * 短信验证码相关配置, 如果yml中配置了相应属性,会覆盖这里的默认配置
 * @author JiaHao Wang
 * @date 2020/12/3 19:28
 */
@Data
public class SmsCodeProperties {

    private int length = 6;

    private int expireIn = 180;

    private String url;
}
