package com.picc.riskctrl.common.po;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "UTIBACKRULECONFIG")
public class UtiBackRuleConfig implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private UtiBackRuleConfigId id;
    /**规则取值*/
    private String ruleValue;
    /**有效标志位*/
    private String validStatus;
    /**最后修改人*/
    private String updaterCode;
    /**创建人*/
    private String creatorCode;
    /**生效日期*/
    private Date validDate;
    /**失效日期*/
    private Date invalidDate;
    /**备注*/
    private String remark;
    /**标志字段*/
    private String flag;
    /**插入时间*/
    private Date insertTimeForHis;
    /**更新时间*/
    private Date operateTimeForHis;

    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "ruleCode", column = @Column(name = "RULECODE")),
            @AttributeOverride(name = "comCode", column = @Column(name = "COMCODE"))})
    public UtiBackRuleConfigId getId() {
        return id;
    }
    public void setId(UtiBackRuleConfigId id) {
        this.id = id;
    }
    @Column(name = "RULEVALUE")
    public String getRuleValue() {
        return ruleValue;
    }
    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }
    @Column(name = "VALIDSTATUS")
    public String getValidStatus() {
        return validStatus;
    }
    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }
    @Column(name = "UPDATERCODE")
    public String getUpdaterCode() {
        return updaterCode;
    }
    public void setUpdaterCode(String updaterCode) {
        this.updaterCode = updaterCode;
    }
    @Column(name = "CREATORCODE")
    public String getCreatorCode() {
        return creatorCode;
    }
    public void setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
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
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name = "FLAG")
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
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
