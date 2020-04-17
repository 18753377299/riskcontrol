package com.picc.riskctrl.exception;

/**
 * 单号查询异常
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
public class RiskNoCanNotFoundException extends ServiceException {

    private static final String defaultMsg = "未查到该单号相关信息";

    public RiskNoCanNotFoundException() {
        this(defaultMsg);
    }

    public RiskNoCanNotFoundException(String message) {
        super(message);
    }

    public RiskNoCanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
