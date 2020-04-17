package com.picc.riskctrl.exception;

/**
 * 流程控制
 * Created by 71400120 on 2020/3/30.
 */
public class ProcessInstanceControlException extends ServiceException{
    public ProcessInstanceControlException() {
    }

    public ProcessInstanceControlException(String message) {
        super(message);
    }

    public ProcessInstanceControlException(String message, Throwable cause) {
        super(message, cause);
    }
}
