package com.picc.riskctrl.exception;

/**
 * 基本信息service异常类
 *
 * @author wangwenjie
 * @date 2020-02-06
 */
public class RiskDnaturalServiceException extends ServiceException {
    public RiskDnaturalServiceException() {
        super();
    }

    public RiskDnaturalServiceException(String message) {
        super(message);
    }

    public RiskDnaturalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
