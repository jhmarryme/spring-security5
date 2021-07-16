package com.jhmarryme.exception;

import lombok.Data;

/**
 * 异常信息
 *
 * @author JiaHao Wang
 * @date 2020/9/10 17:51
 */
@Data
public class UserNotExistException extends RuntimeException {

    private static final long serialVersionUID = -6112780192479692859L;

    private String id;

    public UserNotExistException(String id) {
        super("user not exist");
        this.id = id;
    }

}
