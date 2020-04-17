package com.picc.riskctrl.common.po;

import lombok.Data;

@Data
public class ResponseMessage {
    private String responseCode;
    private UserInfo userInfo;
    private String errorMessage;
    private String token;
    private String returnPassword;
}
