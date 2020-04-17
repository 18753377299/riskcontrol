package com.picc.riskctrl.exception;

/**
 * 系统开关维护service异常处理
 *
 * @author wangwenjie
 * @date 2020-02-06
 */
public class RiskSwitchControlServiceException extends ServiceException {

    public RiskSwitchControlServiceException() {
    }

    public RiskSwitchControlServiceException(String message) {
        super(message);
    }

    public RiskSwitchControlServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
