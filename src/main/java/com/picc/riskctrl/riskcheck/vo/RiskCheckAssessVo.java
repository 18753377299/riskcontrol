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
@ApiModel("RiskCheckAssessVoo对象")
public class RiskCheckAssessVo {

	/**编号*/
	private String riskCheckNo;
	/**环境风险值*/
	private BigDecimal envDanger;
	/**重要风险值*/
	private BigDecimal impDanger;
	/**建筑风险值*/
	private BigDecimal buildDanger;
	/**存货风险值*/
	private BigDecimal cargoDanger;
	/**防台防汛风险值*/
	private BigDecimal typDanger;
	/**防汛制度风险值*/
	private BigDecimal floodDanger;
	public String getRiskCheckNo() {
		return riskCheckNo;
	}
	public void setRiskCheckNo(String riskCheckNo) {
		this.riskCheckNo = riskCheckNo;
	}
	public BigDecimal getEnvDanger() {
		return envDanger;
	}
	public void setEnvDanger(BigDecimal envDanger) {
		this.envDanger = envDanger;
	}
	public BigDecimal getImpDanger() {
		return impDanger;
	}
	public void setImpDanger(BigDecimal impDanger) {
		this.impDanger = impDanger;
	}
	public BigDecimal getBuildDanger() {
		return buildDanger;
	}
	public void setBuildDanger(BigDecimal buildDanger) {
		this.buildDanger = buildDanger;
	}
	public BigDecimal getCargoDanger() {
		return cargoDanger;
	}
	public void setCargoDanger(BigDecimal cargoDanger) {
		this.cargoDanger = cargoDanger;
	}
	public BigDecimal getTypDanger() {
		return typDanger;
	}
	public void setTypDanger(BigDecimal typDanger) {
		this.typDanger = typDanger;
	}
	public BigDecimal getFloodDanger() {
		return floodDanger;
	}
	public void setFloodDanger(BigDecimal floodDanger) {
		this.floodDanger = floodDanger;
	}

	
	
	
}
