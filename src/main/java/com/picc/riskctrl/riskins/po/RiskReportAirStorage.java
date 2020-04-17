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
@Table(name = "riskreport_airstorage")
public class RiskReportAirStorage implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 风控档案编号 */
    private String riskFileNo;
    /**
     * 是否存在露天堆放财产 A 有 B 无
     */
    private String airStorageFlag;
    /** 主要堆放物 */
    private String storageGoods;
    /**
     * 是否有防护措施 A 有 B 无
     */
    private String protectionFlag;
    /** 防护措施补充说明 */
    private String addProtection;
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

    @Column(name = "AIRSTORAGEFLAG")
    public String getAirStorageFlag() {
        return airStorageFlag;
    }

    public void setAirStorageFlag(String airStorageFlag) {
        this.airStorageFlag = airStorageFlag;
    }

    @Column(name = "STORAGEGOODS")
    public String getStorageGoods() {
        return storageGoods;
    }

    public void setStorageGoods(String storageGoods) {
        this.storageGoods = storageGoods;
    }

    @Column(name = "PROTECTIONFLAG")
    public String getProtectionFlag() {
        return protectionFlag;
    }

    public void setProtectionFlag(String protectionFlag) {
        this.protectionFlag = protectionFlag;
    }

    @Column(name = "ADDPROTECTION")
    public String getAddProtection() {
        return addProtection;
    }

    public void setAddProtection(String addProtection) {
        this.addProtection = addProtection;
    }

    // @Column(name = "INSERTTIMEFORHIS", insertable=false,updatable = false)
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