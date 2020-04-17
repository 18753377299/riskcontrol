package com.picc.riskctrl.riskins.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "riskreport_assess")
public class RiskReportAssess implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 风控档案编号 */
    private String riskFileNo;
    /** 火灾风险值 */
    private BigDecimal fireDanger;
    /** 水灾风险值 */
    private BigDecimal waterDanger;
    /** 风灾风险值 */
    private BigDecimal windDanger;
    /** 雷灾风险值 */
    private BigDecimal thunderDanger;
    /** 雪灾风险值 */
    private BigDecimal snowDanger;
    /** 盗抢风险值 */
    private BigDecimal theftDanger;
    /** 地震风险值 */
    private BigDecimal earthquakeDanger;
    /** 地质灾害风险值 */
    private BigDecimal geologyDanger;
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

    @Column(name = "FIREDANGER")
    public BigDecimal getFireDanger() {
        return fireDanger;
    }

    public void setFireDanger(BigDecimal fireDanger) {
        this.fireDanger = fireDanger;
    }

    @Column(name = "WATERDANGER")
    public BigDecimal getWaterDanger() {
        return waterDanger;
    }

    public void setWaterDanger(BigDecimal waterDanger) {
        this.waterDanger = waterDanger;
    }

    @Column(name = "WINDDANGER")
    public BigDecimal getWindDanger() {
        return windDanger;
    }

    public void setWindDanger(BigDecimal windDanger) {
        this.windDanger = windDanger;
    }

    @Column(name = "THUNDERDANGER")
    public BigDecimal getThunderDanger() {
        return thunderDanger;
    }

    public void setThunderDanger(BigDecimal thunderDanger) {
        this.thunderDanger = thunderDanger;
    }

    @Column(name = "SNOWDANGER")
    public BigDecimal getSnowDanger() {
        return snowDanger;
    }

    public void setSnowDanger(BigDecimal snowDanger) {
        this.snowDanger = snowDanger;
    }

    @Column(name = "THEFTDANGER")
    public BigDecimal getTheftDanger() {
        return theftDanger;
    }

    public void setTheftDanger(BigDecimal theftDanger) {
        this.theftDanger = theftDanger;
    }

    @Column(name = "EARTHQUAKEDANGER")
    public BigDecimal getEarthquakeDanger() {
        return earthquakeDanger;
    }

    public void setEarthquakeDanger(BigDecimal earthquakeDanger) {
        this.earthquakeDanger = earthquakeDanger;
    }

    @Column(name = "GEOLOGYDANGER")
    public BigDecimal getGeologyDanger() {
        return geologyDanger;
    }

    public void setGeologyDanger(BigDecimal geologyDanger) {
        this.geologyDanger = geologyDanger;
    }

    // @Column(name = "INSERTTIMEFORHIS",insertable=false, updatable = false)
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