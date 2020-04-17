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
@Table(name = "riskreport_claim")
public class RiskReportClaim implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 复合主键 */
    private RiskReportClaimId id;
    /** 出险原因 */
    private String riskReason;
    /** 出险时间 */
    private Date riskDate;
    /** 出险部位和地址 */
    private String riskPosition;
    /** 损失描述 */
    private String loseInfo;
    /**
     * 有无整改 A 有 B 无
     */
    private String abarbeitungFlag;
    /** 整改措施 */
    private String abarbeitungMeasure;
    /** 插入时间 */
    private Date insertTimeForHis;
    /** 更新时间 */
    private Date operateTimeForHis;
    /** 风控主表 */
    private RiskReportMain riskReportMain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RISKFILENO", referencedColumnName = "RISKFILENO", nullable = false, insertable = false,
        updatable = false)
    public RiskReportMain getRiskReportMain() {
        return riskReportMain;
    }

    public void setRiskReportMain(RiskReportMain riskReportMain) {
        this.riskReportMain = riskReportMain;
    }

    /**
     * 联合主键
     */
    @EmbeddedId
    @AttributeOverrides({@AttributeOverride(name = "riskFileNo", column = @Column(name = "RISKFILENO")),
        @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO"))})
    public RiskReportClaimId getId() {
        return id;
    }

    public void setId(RiskReportClaimId id) {
        this.id = id;
    }

    @Column(name = "RISKREASON")
    public String getRiskReason() {
        return riskReason;
    }

    public void setRiskReason(String riskReason) {
        this.riskReason = riskReason;
    }

    @Column(name = "RISKDATE")
    public Date getRiskDate() {
        return riskDate;
    }

    public void setRiskDate(Date riskDate) {
        this.riskDate = riskDate;
    }

    @Column(name = "RISKPOSITION")
    public String getRiskPosition() {
        return riskPosition;
    }

    public void setRiskPosition(String riskPosition) {
        this.riskPosition = riskPosition;
    }

    @Column(name = "LOSEINFO")
    public String getLoseInfo() {
        return loseInfo;
    }

    public void setLoseInfo(String loseInfo) {
        this.loseInfo = loseInfo;
    }

    @Column(name = "ABARBEITUNGFLAG")
    public String getAbarbeitungFlag() {
        return abarbeitungFlag;
    }

    public void setAbarbeitungFlag(String abarbeitungFlag) {
        this.abarbeitungFlag = abarbeitungFlag;
    }

    @Column(name = "ABARBEITUNGMEASURE")
    public String getAbarbeitungMeasure() {
        return abarbeitungMeasure;
    }

    public void setAbarbeitungMeasure(String abarbeitungMeasure) {
        this.abarbeitungMeasure = abarbeitungMeasure;
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