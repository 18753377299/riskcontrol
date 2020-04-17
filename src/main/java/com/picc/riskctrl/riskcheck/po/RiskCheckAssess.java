package com.picc.riskctrl.riskcheck.po;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "RISKCHECK_Assess")
public class RiskCheckAssess implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String riskCheckNo;
    private BigDecimal envDanger;
    private BigDecimal impDanger;
    private BigDecimal buildDanger;
    private BigDecimal cargoDanger;
    private BigDecimal typDanger;
    private BigDecimal floodDanger;
    private Date insertTimeForHis;
    private Date operateTimeForHis;
	private RiskCheckMain riskCheckMain;

    @Id
    @Column(name = "riskcheckno", nullable = false, length = 22)
    public String getRiskCheckNo() {
        return riskCheckNo;
    }

    public void setRiskCheckNo(String riskCheckNo) {
        this.riskCheckNo = riskCheckNo;
    }

    @Basic
    @Column(name = "envdanger", nullable = true, precision = 2)
    public BigDecimal getEnvDanger() {
        return envDanger;
    }

    public void setEnvDanger(BigDecimal envDanger) {
        this.envDanger = envDanger;
    }

    @Basic
    @Column(name = "impdanger", nullable = true, precision = 2)
    public BigDecimal getImpDanger() {
        return impDanger;
    }

    public void setImpDanger(BigDecimal impDanger) {
        this.impDanger = impDanger;
    }

    @Basic
    @Column(name = "builddanger", nullable = true, precision = 2)
    public BigDecimal getBuildDanger() {
        return buildDanger;
    }

    public void setBuildDanger(BigDecimal buildDanger) {
        this.buildDanger = buildDanger;
    }

    @Basic
    @Column(name = "cargodanger", nullable = true, precision = 2)
    public BigDecimal getCargoDanger() {
        return cargoDanger;
    }

    public void setCargoDanger(BigDecimal cargoDanger) {
        this.cargoDanger = cargoDanger;
    }

    @Basic
    @Column(name = "typdanger", nullable = true, precision = 2)
    public BigDecimal getTypDanger() {
        return typDanger;
    }

    public void setTypDanger(BigDecimal typDanger) {
        this.typDanger = typDanger;
    }

    @Basic
    @Column(name = "flooddanger", nullable = true, precision = 2)
    public BigDecimal getFloodDanger() {
        return floodDanger;
    }

    public void setFloodDanger(BigDecimal floodDanger) {
        this.floodDanger = floodDanger;
    }

    @Basic
    @Column(name = "inserttimeforhis", insertable = false, updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    @Basic
    @Column(name = "operatetimeforhis", insertable = false)
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "riskcheckno", nullable = false, insertable = false, updatable = false)
    public RiskCheckMain getRiskCheckMain() {
		return riskCheckMain;
	}

	public void setRiskCheckMain(RiskCheckMain riskCheckMain) {
		this.riskCheckMain = riskCheckMain;
	}
}
