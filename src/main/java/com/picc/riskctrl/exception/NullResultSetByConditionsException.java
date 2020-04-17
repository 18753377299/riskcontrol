package com.picc.riskctrl.exception;

/**
 * 根据指定查询条件查询结果集为空
 *
 * @author wangwenjie
 * @date 2020-02-12
 */
public class NullResultSetByConditionsException extends ServiceException{

    private static final String errMsg = "根据指定查询条件查询结果集为空";

    public NullResultSetByConditionsException(){
        this(errMsg);
    }

    public NullResultSetByConditionsException(String message) {
        super(message);
    }

    public NullResultSetByConditionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
