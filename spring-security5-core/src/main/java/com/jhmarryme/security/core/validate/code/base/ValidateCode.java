package com.jhmarryme.security.core.validate.code.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author JiaHao Wang
 * @date 2020/12/3 18:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCode implements Serializable {

    /**
     * 验证码
     */
    private String code;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;


    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    /**
     * 验证码是否过期
     * <br/>
     * @author Jiahao Wang
     * @date 2020/11/30 20:05
     * @param
     * @return boolean
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
