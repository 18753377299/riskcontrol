package com.picc.riskctrl.riskins.vo;

import com.picc.riskctrl.riskins.po.RiskReportAssess;

import java.math.BigDecimal;

public class RiskInsGradeVo {
	private String flag;
	private String errorMessage;
	private BigDecimal score;
	private String highlightRisk;
	private RiskReportAssess riskReportAssess;
	private Integer utiWeightId;

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public String getHighlightRisk() {
		return highlightRisk;
	}
	public void setHighlightRisk(String highlightRisk) {
		this.highlightRisk = highlightRisk;
	}
	public RiskReportAssess getRiskReportAssess() {
		return riskReportAssess;
	}
	public void setRiskReportAssess(RiskReportAssess riskReportAssess) {
		this.riskReportAssess = riskReportAssess;
	}
	public Integer getUtiWeightId() {
		return utiWeightId;
	}
	public void setUtiWeightId(Integer utiWeightId) {
		this.utiWeightId = utiWeightId;
	}
	
}
