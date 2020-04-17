package com.picc.riskctrl.exception;

/**
 * 返回data为error的异常处理
 *
 * @author wangwenjie
 * @date 2020-02-19
 */
public class ApiErrorDataException extends ServiceException {
    public ApiErrorDataException() {
    }

    public ApiErrorDataException(String message) {
        super(message);
    }

    public ApiErrorDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
