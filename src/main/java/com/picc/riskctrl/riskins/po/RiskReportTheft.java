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
@Table(name = "riskreport_theft")
public class RiskReportTheft implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 风控档案编号 */
    private String riskFileNo;
    /**
     * 被保物吸引力 A 低 B 中 C 高
     */
    private String insuredAttraction;
    /**
     * 是否有技术防盗设施 A 有 B 无
     */
    private String antiTheftFlag;
    /** 物理防盗设施类型 */
    private String antiTheftType;
    /**
     * 防盗报警系统是否与当地公安机关联网 A 是 B 否
     */
    private String netPoliceFlag;
    /**
     * 保安警卫人数 A 0人 B 1-5人 C 6-19人 D 20人以上
     */
    private String securityNumber;
    /**
     * 是否定时巡逻 A 是，且有巡逻记录 B 是，但没有巡逻记录 C 否
     */
    private String timingPatrol;
    /**
     * 所在区域社会治安情况 A 好 B 一般 C 差
     */
    private String publicSecurity;
    /**
     * 是否有被盗抢记录 A 是 B 否
     */
    private String theftHistoryFlag;
    /** 插入时间 */
    private Date insertTimeForHis;
    /** 更新时间 */
    private Date operateTimeForHis;
    /** 风控主表 */
    private RiskReportMain riskReportMain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RISKFILENO", nullable = false, insertable = false, updatable = false)
    public RiskReportMain getRiskReportMain() {
        return riskReportMain;
    }

    public void setRiskReportMain(RiskReportMain riskReportMain) {
        this.riskReportMain = riskReportMain;
    }

    @Id
    @Column(name = "RISKFILENO", nullable = false)
    public String getRiskFileNo() {
        return riskFileNo;
    }

    public void setRiskFileNo(String riskFileNo) {
        this.riskFileNo = riskFileNo;
    }

    @Column(name = "INSUREDATTRACTION")
    public String getInsuredAttraction() {
        return insuredAttraction;
    }

    public void setInsuredAttraction(String insuredAttraction) {
        this.insuredAttraction = insuredAttraction;
    }

    @Column(name = "ANTITHEFTFLAG")
    public String getAntiTheftFlag() {
        return antiTheftFlag;
    }

    public void setAntiTheftFlag(String antiTheftFlag) {
        this.antiTheftFlag = antiTheftFlag;
    }

    @Column(name = "ANTITHEFTTYPE")
    public String getAntiTheftType() {
        return antiTheftType;
    }

    public void setAntiTheftType(String antiTheftType) {
        this.antiTheftType = antiTheftType;
    }

    @Column(name = "NETPOLICEFLAG")
    public String getNetPoliceFlag() {
        return netPoliceFlag;
    }

    public void setNetPoliceFlag(String netPoliceFlag) {
        this.netPoliceFlag = netPoliceFlag;
    }

    @Column(name = "SECURITYNUMBER")
    public String getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }

    @Column(name = "TIMINGPATROL")
    public String getTimingPatrol() {
        return timingPatrol;
    }

    public void setTimingPatrol(String timingPatrol) {
        this.timingPatrol = timingPatrol;
    }

    @Column(name = "PUBLICSECURITY")
    public String getPublicSecurity() {
        return publicSecurity;
    }

    public void setPublicSecurity(String publicSecurity) {
        this.publicSecurity = publicSecurity;
    }

    @Column(name = "THEFTHISTORYFLAG")
    public String getTheftHistoryFlag() {
        return theftHistoryFlag;
    }

    public void setTheftHistoryFlag(String theftHistoryFlag) {
        this.theftHistoryFlag = theftHistoryFlag;
    }

    // @Column(name = "INSERTTIMEFORHIS",insertable=false,updatable = false)
    @CreatedDate
    @Column(name = "INSERTTIMEFORHIS", updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    // @Column(name = "OPERATETIMEFORHIS",insertable=false)
    @LastModifiedDate
    @Column(name = "OPERATETIMEFORHIS")
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }

}