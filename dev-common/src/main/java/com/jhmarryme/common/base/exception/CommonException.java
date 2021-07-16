package com.jhmarryme.common.base.exception;
import com.jhmarryme.common.base.interfaces.IResponseEnum;

/**
 * 通用异常类
 * @author JiaHao Wang
 * @date 3/9/21 1:57 AM
 */
public class CommonException extends BaseException {

    private static final long serialVersionUID = 1L;

    public CommonException() {
        super();
    }

    public CommonException(IResponseEnum responseEnum, String... params) {
        super(responseEnum, params);
    }

    public CommonException(IResponseEnum responseEnum, Object data, String... params) {
        super(responseEnum, data, params);
    }

}
