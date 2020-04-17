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
@Table(name = "riskreport_interrupt")
public class RiskReportInterrupt implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 风控档案编号 */
    private String riskFileNo;
    /**
     * 电水气等供应中断影响程度 A 对设备及产品的影响程度低 B 对设备及产品的影响程度中 C 对设备及产品的影响程度高
     */
    private String interruptEffect;
    /**
     * 公共供电情况 A 单回路供电 B 单电源双回路供电 C 双电源双回路供电
     */
    private String powerSupply;
    /**
     * 是否拥有断电保护设施 A 无 B UPS（即不间断电源） C 保安电源 D 自备电厂
     */
    private String outagePro;
    /** 信息补充 */
    private String addMessage;
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

    @Column(name = "INTERRUPTEFFECT")
    public String getInterruptEffect() {
        return interruptEffect;
    }

    public void setInterruptEffect(String interruptEffect) {
        this.interruptEffect = interruptEffect;
    }

    @Column(name = "POWERSUPPLY")
    public String getPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
    }

    @Column(name = "OUTAGEPRO")
    public String getOutagePro() {
        return outagePro;
    }

    public void setOutagePro(String outagePro) {
        this.outagePro = outagePro;
    }

    @Column(name = "ADDMESSAGE")
    public String getAddMessage() {
        return addMessage;
    }

    public void setAddMessage(String addMessage) {
        this.addMessage = addMessage;
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