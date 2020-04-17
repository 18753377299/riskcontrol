package com.picc.riskctrl.common.schema;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 作者 E-mail: liqiankun
 * @version 1.0
 * @date 创建时间：2017年11月30日 上午9:58:18
 * @parameter
 * @return
 * @since
 */
@Entity
@Table(name = "PRPDCOMPANY")
public class PrpDcompanyFk implements Serializable {
    private static final long serialVersionUID = 1L;
    private String comCode;
    private String comCName;
    private String comShortName;
    private String comEName;
    private String addressCName;
    private String addressEName;
    private String postCode;
    private String phoneNumber;
    private String faxNumber;
    private String upperComCode;
    private String insurerName;
    private String comType;
    private String comFlag;

    private String centerFlag;
    private BigDecimal comLevel;
    private String branchType;
    private String upperPath;
    private String comCodeCIRC;
    private String licenseNo;
    private String email;
    private String remark1;
    private String comKind;
    private String manager;
    private String accountant;
    private String remark;
    private String newComCode;
    private Date validDate;
    private Date invalidDate;
    private String validStatus;
    private String acntUnit;
    private String articleCode;
    private String updateFlag;
    private Date updateDate;
    private String operatorComCode;
    private String flag;
    private String specialFlag;
    private String hrlevelCode;
    private String workTime;
    private String longitude;
    private String latitude;
    private Date insertTimeForHis;
    private Date operateTimeForHis;

    @Id
    @Column(name = "COMCODE", nullable = false)
    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    @Column(name = "COMSHORTNAME", nullable = false)
    public String getComShortName() {
        return comShortName;
    }

    public void setComShortName(String comShortName) {
        this.comShortName = comShortName;
    }

    @Column(name = "POSTCODE")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Column(name = "PHONENUMBER")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "FAXNUMBER")
    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    @Column(name = "INSURERNAME")
    public String getInsurerName() {
        return insurerName;
    }

    public void setInsurerName(String insurerName) {
        this.insurerName = insurerName;
    }

    @Column(name = "COMTYPE")
    public String getComType() {
        return comType;
    }

    public void setComType(String comType) {
        this.comType = comType;
    }

    @Column(name = "COMFLAG")
    public String getComFlag() {
        return comFlag;
    }

    public void setComFlag(String comFlag) {
        this.comFlag = comFlag;
    }

    @Column(name = "CENTERFLAG")
    public String getCenterFlag() {
        return centerFlag;
    }

    public void setCenterFlag(String centerFlag) {
        this.centerFlag = centerFlag;
    }

    @Column(name = "COMLEVEL")
    public BigDecimal getComLevel() {
        return comLevel;
    }

    public void setComLevel(BigDecimal comLevel) {
        this.comLevel = comLevel;
    }

    @Column(name = "BRANCHTYPE")
    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    @Column(name = "UPPERPATH")
    public String getUpperPath() {
        return upperPath;
    }

    public void setUpperPath(String upperPath) {
        this.upperPath = upperPath;
    }

    @Column(name = "LICENSENO")
    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "REMARK1")
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    @Column(name = "COMKIND")
    public String getComKind() {
        return comKind;
    }

    public void setComKind(String comKind) {
        this.comKind = comKind;
    }

    @Column(name = "MANAGER")
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "VALIDDATE")
    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "INVALIDDATE")
    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    @Column(name = "VALIDSTATUS", nullable = false)
    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    @Column(name = "ACNTUNIT")
    public String getAcntUnit() {
        return acntUnit;
    }

    public void setAcntUnit(String acntUnit) {
        this.acntUnit = acntUnit;
    }

    @Column(name = "ARTICLECODE")
    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    @Column(name = "UPDATEFLAG")
    public String getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATEDATE")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "FLAG")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Column(name = "SPECIALFLAG")
    public String getSpecialFlag() {
        return specialFlag;
    }

    public void setSpecialFlag(String specialFlag) {
        this.specialFlag = specialFlag;
    }

    @Column(name = "HRLEVELCODE")
    public String getHrlevelCode() {
        return hrlevelCode;
    }

    public void setHrlevelCode(String hrlevelCode) {
        this.hrlevelCode = hrlevelCode;
    }

    @Column(name = "WORKTIME")
    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    @Column(name = "LONGITUDE")
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Column(name = "LATITUDE")
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Column(name = "COMCNAME", nullable = false)
    public String getComCName() {
        return comCName;
    }

    public void setComCName(String comCName) {
        this.comCName = comCName;
    }

    @Column(name = "COMENAME")
    public String getComEName() {
        return comEName;
    }

    public void setComEName(String comEName) {
        this.comEName = comEName;
    }

    @Column(name = "ADDRESSCNAME")
    public String getAddressCName() {
        return addressCName;
    }

    public void setAddressCName(String addressCName) {
        this.addressCName = addressCName;
    }

    @Column(name = "ADDRESSENAME")
    public String getAddressEName() {
        return addressEName;
    }

    public void setAddressEName(String addressEName) {
        this.addressEName = addressEName;
    }

    @Column(name = "UPPERCOMCODE")
    public String getUpperComCode() {
        return upperComCode;
    }

    public void setUpperComCode(String upperComCode) {
        this.upperComCode = upperComCode;
    }

    @Column(name = "COMCODECIRC")
    public String getComCodeCIRC() {
        return comCodeCIRC;
    }

    public void setComCodeCIRC(String comCodeCIRC) {
        this.comCodeCIRC = comCodeCIRC;
    }

    @Column(name = "ACCOUNTANT")
    public String getAccountant() {
        return accountant;
    }

    public void setAccountant(String accountant) {
        this.accountant = accountant;
    }

    @Column(name = "NEWCOMCODE", nullable = false)
    public String getNewComCode() {
        return newComCode;
    }

    public void setNewComCode(String newComCode) {
        this.newComCode = newComCode;
    }

    @Column(name = "OPERATORCOMCODE")
    public String getOperatorComCode() {
        return operatorComCode;
    }

    public void setOperatorComCode(String operatorComCode) {
        this.operatorComCode = operatorComCode;
    }

    @Column(name = "INSERTTIMEFORHIS", insertable = false, updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    @Column(name = "OPERATETIMEFORHIS", insertable = false)
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }

}
