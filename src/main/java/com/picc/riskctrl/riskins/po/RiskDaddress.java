package com.picc.riskctrl.riskins.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "riskdaddress")
public class RiskDaddress implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 地址代码 */
    private String addressCode;
    /** 地址名称 */
    private String address;
    /** 地区名称 */
    private String countyName;
    /** 上级代码 */
    private String upperCode;
    /** 所属级别 */
    private String gradeFlag;
    /** 有效标志位 */
    private String validStatus;
    /** 插入时间 */
    private Date insertTimeForHis;
    /** 更新时间 */
    private Date operateTimeForHis;

    @Id
    @Column(name = "ADDRESSCODE")
    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "COUNTYNAME")
    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    @Column(name = "UPPERCODE")
    public String getUpperCode() {
        return upperCode;
    }

    public void setUpperCode(String upperCode) {
        this.upperCode = upperCode;
    }

    @Column(name = "GRADEFLAG")
    public String getGradeFlag() {
        return gradeFlag;
    }

    public void setGradeFlag(String gradeFlag) {
        this.gradeFlag = gradeFlag;
    }

    @Column(name = "VALIDSTATUS")
    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    // @Column(name = "INSERTTIMEFORHIS")
    @CreatedDate
    @Column(name = "INSERTTIMEFORHIS", updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    // @Column(name = "OPERATETIMEFORHIS")
    @LastModifiedDate
    @Column(name = "OPERATETIMEFORHIS")
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }

}