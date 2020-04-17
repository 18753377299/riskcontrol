package com.picc.riskctrl.exception;

/**
 * RiskInsSaleService 自定义异常类
 *
 * @author wangwenjie
 * @date 2020-02-18
 */
public class RiskInsSaleServiceException extends ServiceException{

    public RiskInsSaleServiceException() {
    }

    public RiskInsSaleServiceException(String message) {
        super(message);
    }

    public RiskInsSaleServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
