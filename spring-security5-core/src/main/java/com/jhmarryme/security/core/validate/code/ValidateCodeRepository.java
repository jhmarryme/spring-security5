package com.jhmarryme.security.core.validate.code;

import com.jhmarryme.security.core.validate.code.base.ValidateCode;
import com.jhmarryme.security.core.validate.code.base.ValidateCodeTypeEnum;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Optional;

/**
 * 验证码存取器 接口
 * @author JiaHao Wang
 * @date 2021/2/23 12:20
 */
public interface ValidateCodeRepository {
    /**
     * 保存验证码
     *
     * @param request 请求HttpRequest 和HttpResponse的封装
     * @param code 验证码
     * @param validateCodeTypeEnum 验证码类型
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeTypeEnum validateCodeTypeEnum);

    /**
     * 获取验证码
     *
     * @param request
     * @param validateCodeTypeEnum
     * @return Optional<ValidateCode>
     */
    Optional<ValidateCode> get(ServletWebRequest request, ValidateCodeTypeEnum validateCodeTypeEnum);

    /**
     * 移除验证码
     *
     * @param request
     * @param validateCodeTypeEnum
     */
    void remove(ServletWebRequest request, ValidateCodeTypeEnum validateCodeTypeEnum);
}
