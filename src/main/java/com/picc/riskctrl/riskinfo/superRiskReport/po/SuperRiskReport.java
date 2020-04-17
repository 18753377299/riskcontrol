package com.picc.riskctrl.riskinfo.superRiskReport.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "riskinfo_riskfile")
public class SuperRiskReport implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 序号
     */
    private Integer serialNo;
    /**
     * 风控报告名字
     */
    private String riskFileName;
    /**
     * 机构
     */
    private String comCode;
    /**
     * 归属机构名称
     */
    private String comCodeCName;
    /**
     * 维护人代码
     */
    private String operatorCode;
    /**
     * 维护人名称
     */
    private String operatorName;
    /**
     * 险种
     */
    private String riskName;
    /**
     * 险种中文名
     */
    private String riskNameC;
    /**
     * 行业
     */
    private String profession;
    /**
     * 出具报告年度
     */
    private String riskYear;
    /**
     * 风控报告来源
     */
    private String ascName;
    /**
     * 出具报告机构
     */
    private String sender;
    /**
     * 报告模板
     */
    private String url;
    /**
     * 有效标志位
     */
    private String validStatus;
    /**
     * 有效标志位中文
     */
    private String validStatusName;
    /**
     * 插入时间
     */
    private Date insertTimeForHis;
    /**
     * 更新时间
     */
    private Date operateTimeForHis;
    /**
     * 打回意见
     */
    private String remark;

    @Id
    @Column(name = "SERIALNO")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    @Column(name = "RISKFILENAME")
    public String getRiskFileName() {
        return riskFileName;
    }

    public void setRiskFileName(String riskFileName) {
        this.riskFileName = riskFileName;
    }

    @Column(name = "RISKNAME")
    public String getRiskName() {
        return riskName;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    @Column(name = "PROFESSION")
    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Column(name = "RISKYEAR")
    public String getRiskYear() {
        return riskYear;
    }

    public void setRiskYear(String riskYear) {
        this.riskYear = riskYear;
    }

    @Column(name = "ASCNAME")
    public String getAscName() {
        return ascName;
    }

    public void setAscName(String ascName) {
        this.ascName = ascName;
    }

    @Column(name = "SENDER")
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "VALIDSTATUS")
    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    @Transient
    public String getValidStatusName() {
        return validStatusName;
    }

    public void setValidStatusName(String validStatusName) {
        this.validStatusName = validStatusName;
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

    @Column(name = "COMCODE")
    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    @Transient
    public String getComCodeCName() {
        return comCodeCName;
    }

    public void setComCodeCName(String comCodeCName) {
        this.comCodeCName = comCodeCName;
    }

    @Column(name = "OPERATORCODE")
    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    @Column(name = "OPERATORNAME")
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Transient
    public String getRiskNameC() {
        return riskNameC;
    }

    public void setRiskNameC(String riskNameC) {
        this.riskNameC = riskNameC;
    }
}
