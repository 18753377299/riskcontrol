package com.picc.riskctrl.riskprice.vo;

import java.math.BigDecimal;
import java.util.Date;

public class PricingTreatmentVo {
	/*编号*/
	private String riskFileNo;
	/*投保险种*/
	private String riskCode;
	/*行业类型*/
	private String industryCode;
	/*起保日期*/
	private Date effectiveDate;
	/*标的所在地*/
	private String subjectBelongs;
	private String comCode;
	/*终保日期*/
	private Date expirationDate;
	/*投保金额*/
	private BigDecimal insuredAmount;
	/*存货保额*/
	private BigDecimal inventoriesAmount;
	/*免赔额*/
	private BigDecimal franChise;
	/*免赔率*/
	private BigDecimal franChiseRate;
	/*渠道*/
	private String businessNature;
	/*拟签单费率*/
	private BigDecimal orderRate;
	/*跟单佣金比例*/
	private BigDecimal commissionScale;
	/*跟单佣金比例上限*/
	private BigDecimal commissionScaleUpper;
	/*风控系统综合评分*/
	private BigDecimal compreHensiveScore;
	/*再保分出比例*/
	private BigDecimal reinsureScale;
	/*再保摊加手续费*/
	private BigDecimal reimsureProcedRate;
	private String industryCodeCname;
	private String riskCodeName;
	private String comCodeName;
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getRiskFileNo() {
		return riskFileNo;
	}
	public void setRiskFileNo(String riskFileNo) {
		this.riskFileNo = riskFileNo;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getSubjectBelongs() {
		return subjectBelongs;
	}
	public void setSubjectBelongs(String subjectBelongs) {
		this.subjectBelongs = subjectBelongs;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public BigDecimal getInsuredAmount() {
		return insuredAmount;
	}
	public void setInsuredAmount(BigDecimal insuredAmount) {
		this.insuredAmount = insuredAmount;
	}
	public BigDecimal getInventoriesAmount() {
		return inventoriesAmount;
	}
	public void setInventoriesAmount(BigDecimal inventoriesAmount) {
		this.inventoriesAmount = inventoriesAmount;
	}
	public BigDecimal getFranChise() {
		return franChise;
	}
	public void setFranChise(BigDecimal franChise) {
		this.franChise = franChise;
	}
	public BigDecimal getFranChiseRate() {
		return franChiseRate;
	}
	public void setFranChiseRate(BigDecimal franChiseRate) {
		this.franChiseRate = franChiseRate;
	}
	public String getBusinessNature() {
		return businessNature;
	}
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}
	public BigDecimal getOrderRate() {
		return orderRate;
	}
	public void setOrderRate(BigDecimal orderRate) {
		this.orderRate = orderRate;
	}
	public BigDecimal getCommissionScale() {
		return commissionScale;
	}
	public void setCommissionScale(BigDecimal commissionScale) {
		this.commissionScale = commissionScale;
	}
	public BigDecimal getCommissionScaleUpper() {
		return commissionScaleUpper;
	}
	public void setCommissionScaleUpper(BigDecimal commissionScaleUpper) {
		this.commissionScaleUpper = commissionScaleUpper;
	}
	public BigDecimal getCompreHensiveScore() {
		return compreHensiveScore;
	}
	public void setCompreHensiveScore(BigDecimal compreHensiveScore) {
		this.compreHensiveScore = compreHensiveScore;
	}
	public BigDecimal getReinsureScale() {
		return reinsureScale;
	}
	public void setReinsureScale(BigDecimal reinsureScale) {
		this.reinsureScale = reinsureScale;
	}
	public BigDecimal getReimsureProcedRate() {
		return reimsureProcedRate;
	}
	public void setReimsureProcedRate(BigDecimal reimsureProcedRate) {
		this.reimsureProcedRate = reimsureProcedRate;
	}
	public String getIndustryCodeCname() {
		return industryCodeCname;
	}
	public void setIndustryCodeCname(String industryCodeCname) {
		this.industryCodeCname = industryCodeCname;
	}
	public String getRiskCodeName() {
		return riskCodeName;
	}
	public void setRiskCodeName(String riskCodeName) {
		this.riskCodeName = riskCodeName;
	}
	public String getComCodeName() {
		return comCodeName;
	}
	public void setComCodeName(String comCodeName) {
		this.comCodeName = comCodeName;
	}
	
}
