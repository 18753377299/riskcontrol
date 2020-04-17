package com.picc.riskctrl.riskcheck.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RiskCheckGradeVoo对象")
public class RiskCheckGradeVo {
	private String flag;
	private String errorMessage;
	private BigDecimal score;
	private String highlightRisk;
	private RiskCheckAssessVo riskCheckAssess;
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
	public RiskCheckAssessVo getRiskCheckAssess() {
		return riskCheckAssess;
	}
	public void setRiskCheckAssess(RiskCheckAssessVo riskCheckAssess) {
		this.riskCheckAssess = riskCheckAssess;
	}
	public Integer getUtiWeightId() {
		return utiWeightId;
	}
	public void setUtiWeightId(Integer utiWeightId) {
		this.utiWeightId = utiWeightId;
	}
	
}
