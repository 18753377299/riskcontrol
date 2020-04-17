package com.picc.riskctrl.riskins.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "RISKREPORT_MESSAGESEND")
public class RiskReportMessageSend {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**顺序号*/
    private Integer id;
    /**业务号*/
    private String businessNo;
    /**业务类型*/
    private String businessType;
    /**关联单号*/
    private String relationNo;
    /**用户代码*/
    private String userCode;
    /**手机操作系统:1 安卓,2 苹果*/
    private String mobileFlag;
    /**信息标题*/
    private String title;
    /**信息内容*/
    private String message;
    /**状态标志位:0 初始状态,1 发送成功,2 发送失败*/
    private String stateFlag;
    /**插入时间*/
    private Date insertTimeForHis;
    /**更新时间*/
    private Date operateTimeForHis;

    @SequenceGenerator(name = "generatorForRiskReportMessageSend", allocationSize = 1, sequenceName = "riskreport_messagesend_sequence")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generatorForRiskReportMessageSend")
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "BUSINESSNO")
    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    @Column(name = "BUSINESSTYPE")
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Column(name = "RELATIONNO")
    public String getRelationNo() {
        return relationNo;
    }

    public void setRelationNo(String relationNo) {
        this.relationNo = relationNo;
    }

    @Column(name = "USERCODE")
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Column(name = "MOBILEFLAG")
    public String getMobileFlag() {
        return mobileFlag;
    }

    public void setMobileFlag(String mobileFlag) {
        this.mobileFlag = mobileFlag;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name = "STATEFLAG")
    public String getStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(String stateFlag) {
        this.stateFlag = stateFlag;
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
