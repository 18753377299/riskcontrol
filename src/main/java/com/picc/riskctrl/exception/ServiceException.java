package com.picc.riskctrl.exception;

/**
 * service异常
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
public class ServiceException extends ApplicationException {

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
