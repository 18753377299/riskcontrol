package com.picc.riskctrl.riskinfo.claim.vo;

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
@ApiModel("RiskClaimVoo对象")
public class RiskClaimVo {
	//案例名称
	private String claimName; 
	//产品名称
	private String riskCname;
	//出险年度
	private String claimYear;
	//险种
	private String riskNames;
	//最低赔款金额
	private BigDecimal claimAmountLow;
	//最高赔款金额
	private BigDecimal claimAmountHigh;
	//行业
	private String professions;
	//案件来源
	private String senders;
	//出险原因
	private String claimReasons;
	//审核状态
	private String validStatus;
	public String getClaimName() {
		return claimName;
	}
	public void setClaimName(String claimName) {
		this.claimName = claimName;
	}
	public String getRiskCname() {
		return riskCname;
	}
	public void setRiskCname(String riskCname) {
		this.riskCname = riskCname;
	}
	public String getClaimYear() {
		return claimYear;
	}
	public void setClaimYear(String claimYear) {
		this.claimYear = claimYear;
	}
	public String getRiskNames() {
		return riskNames;
	}
	public void setRiskNames(String riskNames) {
		this.riskNames = riskNames;
	}
	public BigDecimal getClaimAmountLow() {
		return claimAmountLow;
	}
	public void setClaimAmountLow(BigDecimal claimAmountLow) {
		this.claimAmountLow = claimAmountLow;
	}
	public BigDecimal getClaimAmountHigh() {
		return claimAmountHigh;
	}
	public void setClaimAmountHigh(BigDecimal claimAmountHigh) {
		this.claimAmountHigh = claimAmountHigh;
	}
	public String getProfessions() {
		return professions;
	}
	public void setProfessions(String professions) {
		this.professions = professions;
	}
	public String getSenders() {
		return senders;
	}
	public void setSenders(String senders) {
		this.senders = senders;
	}
	public String getClaimReasons() {
		return claimReasons;
	}
	public void setClaimReasons(String claimReasons) {
		this.claimReasons = claimReasons;
	}
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	
	
}
