package com.jhmarryme.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 验证码业务
 * @author Jiahao Wang
 * @date 2020/11/30 12:37
 */
@RestController
public class ValidateController {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 创建验证码, 根据类型不同, 调用不同的 {@link ValidateCodeProcessor} 接口实现
     * <br/>
     * @author Jiahao Wang
     * @date 2020/12/3 21:07
     * @param request
     * @param response
     * @param type
     * @return void
     */
    @GetMapping("/code/{type}")
    public void imageCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }

}
