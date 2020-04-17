package com.picc.riskctrl.dataquery.util;

/**
 * @deprecation 企业关键字精准查询基本信息
 */
public class BasicInfor {
    /**keyNo*/
    private String keyNo;
    /**企业名称*/
    private String name;
    /**更新日期*/
    private String updatedDate;
    /**企业法人*/
    private String operName;
    /**成立日期*/
    private String startDate;
    /**状态*/
    private String status;
    /**电话*/
    private String phoneNumber;
    /**地址*/
    private String address;
    public String getKeyNo() {
        return keyNo;
    }
    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
    public String getOperName() {
        return operName;
    }
    public void setOperName(String operName) {
        this.operName = operName;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
