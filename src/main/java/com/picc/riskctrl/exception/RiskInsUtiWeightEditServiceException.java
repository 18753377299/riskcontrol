package com.picc.riskctrl.exception;

/**
 * 灾因维护sevice异常
 *
 * @author wangwenjie
 * @date 2020-02-06
 */
public class RiskInsUtiWeightEditServiceException extends ServiceException{
    public RiskInsUtiWeightEditServiceException() {
    }

    public RiskInsUtiWeightEditServiceException(String message) {
        super(message);
    }

    public RiskInsUtiWeightEditServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
