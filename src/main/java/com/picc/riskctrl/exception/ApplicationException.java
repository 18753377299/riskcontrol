package com.picc.riskctrl.exception;

/**
 * 基础异常
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
public abstract class ApplicationException extends RuntimeException {

    //保存出现异常的数据，如单号等
    private Object errorData;

    public Object getErrorData() {
        return errorData;
    }

    public ApplicationException setErrorData(Object errorData) {
        this.errorData = errorData;
        return this;
    }

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
