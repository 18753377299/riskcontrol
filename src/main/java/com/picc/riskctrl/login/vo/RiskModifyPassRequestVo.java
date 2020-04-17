package com.picc.riskctrl.login.vo;

import lombok.Data;

@Data
public class RiskModifyPassRequestVo {
    /*用户名*/
    private String username;
    /*旧密码*/
    private String checkPassword;
    /*新密码*/
    private String newPassword;
    /*确认密码*/
    private String confirmPassword;
    /*登录前后的标志*/
    private String beforeFlag;
    /*手机号*/
    private String  phoneName;
    /*邮箱*/
    private String email;
    /*身份证号*/
    private String identifyNumber;
    /*选择验证方式，1：手机号，2：邮箱，3：身份证号*/
    private String modifyWay;
}
