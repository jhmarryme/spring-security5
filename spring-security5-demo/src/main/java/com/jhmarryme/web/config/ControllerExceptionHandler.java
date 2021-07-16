package com.jhmarryme.web.config;

import com.jhmarryme.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用异常处理
 * @author Jiahao Wang
 * @date 2020/9/10 17:50
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleUserNotExistException(UserNotExistException ex) {
        // 封装异常信息
        Map<String, Object> result = new HashMap<>();
        result.put("id", ex.getId());
        result.put("message", ex.getMessage());
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception ex) {
        // 封装异常信息
        Map<String, Object> result = new HashMap<>();
        result.put("message", ex.getMessage());
        return result;
    }

}