package com.jhmarryme.security.core.properties;

import lombok.Data;

/**
 * 图形验证码相关配置, 如果yml中配置了相应属性,会覆盖这里的默认配置
 * @author JiaHao Wang
 * @date 2020/12/1 12:17
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties{

    private int width = 67;

    private int height = 23;

    public ImageCodeProperties() {
        setLength(4);
    }
}
