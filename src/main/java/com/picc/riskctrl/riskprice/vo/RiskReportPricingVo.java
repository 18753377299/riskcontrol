package com.picc.riskctrl.riskprice.vo;

import com.picc.riskctrl.riskprice.po.Extendinsure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RiskReportPricingVo {
	/*编号*/
	private String riskFileNo;
	/*归属机构*/
	private String comCode;
	/*投保险种*/
	private String riskCode;
	/*备注信息*/
	private String reMark;
	/*备注标识*/
	private String Flag;
	/*基准费率(考虑再保险)*/
	private BigDecimal basicRateReinsure;
	/*基准保费(考虑再保险)*/
	private BigDecimal basicPremiumReinsure;
	/*基准费率(不考虑再保险)*/
	private BigDecimal basicRate;
	/*基准保费(不考虑再保险)*/
	private BigDecimal basicPremium;
	/*拟签单费率*/
	private BigDecimal orderRate;
	/*拟签单保费*/
	private BigDecimal orderPremium;
	/*续保分类*/
	private String renewalType;
	/*现行费率*/
	private BigDecimal currentRate;
	/*一级核保最低费率（考虑再保险）*/
	private BigDecimal underWriteRate1re;
	/*一级核保最低保费(考虑再保险)*/
	private BigDecimal underWritePremium1re;
	/*一级核保最低费率（不考虑再保险）*/
	private BigDecimal underWriteRate1;
	/*一级核保最低保费(不考虑再保险)*/
	private BigDecimal underWritePremium1;
	/*二级核保最低费率(考虑再保险)*/
	private BigDecimal underWriteRate2re;
	/*二级核保最低保费(考虑再保险)*/
	private BigDecimal underWritePremium2re;
	/*二级核保最低费率(不考虑再保险*/
	private BigDecimal underWriteRate2;
	/*二级核保最低保费(不考虑再保险)*/
	private BigDecimal underWritePremium2;
	/*费率描述*/
	private String rateDetails;
	/*费效比*/
	private BigDecimal rateScale;
	/*主风险费率*/
	private BigDecimal mainRiskRate;
	/*风险费率*/
	private BigDecimal riskRate;
	/*客户可接受费率*/
	private BigDecimal customerAcceptRate;
	/*加权费率*/
	private BigDecimal weightingRate;
	/*报价费率*/
	private BigDecimal quoteRate;
	/*报价保费*/
	private BigDecimal quotePremium;
	/*报价风险费率*/
	private BigDecimal quoteRiskrate;
	/*报价赔付率R*/
	private BigDecimal quotecomPensateRate;
	/*费用剩余*/
	private BigDecimal remainingCost;
	/*超额剩余*/
	private BigDecimal excessSurplus;
	/*跟单佣金比*/
	private BigDecimal commissionScale;
	/*跟单佣金*/
	private BigDecimal commossion;
	/*业务人员费用率*/
	private BigDecimal handlerRate;
	/*业务人员费用*/
	private BigDecimal handlerFee;
	
	/*销售费用率*/
	private BigDecimal saleRate;
	/*销售费用*/
	private BigDecimal saleFee;
	/*预期利润率*/
	private BigDecimal proFitRate;
	/*预期利润*/
	private BigDecimal proFit;
	/*赔付率R*/
	private BigDecimal compenSateRate;
	/*自留风险赔付率R*/
	private BigDecimal ownCompenRate;
	/*风控系统综合评分调整系数*/
	private BigDecimal comScoreCoefficient;
	/*总部费用分摊*/
	private BigDecimal headQuartFeeCof;
	/*营业税及附加*/
	private BigDecimal businessTaxCof;
	/*保险保障基金*/
	private BigDecimal guaranteeFundCof;
	/*固定营业费用率*/
	private BigDecimal fixedChargesRateCof;
	/*营业费用分摊率*/
	private BigDecimal businessExpenseRateCof;
	/*默认业务人员费用率*/
	private BigDecimal defaultBusinessrateCof;
	/*平均佣金跟单比例*/
	private BigDecimal ageCommissionRateCof;
	/*平均销售费用率*/
	private BigDecimal ageSaleRateCof;
	/*预定利润率*/
	private BigDecimal proFitRateCof;
	/*业务人员费用上限*/
	private BigDecimal upBusinessRateCof;
	/*销售费用率上限*/
	private BigDecimal upSaleRateCof;
	private String riskCodeName;
	private String comCodeName;
	
	public String getComCodeName() {
		return comCodeName;
	}
	public void setComCodeName(String comCodeName) {
		this.comCodeName = comCodeName;
	}
	public String getRiskCodeName() {
		return riskCodeName;
	}
	public void setRiskCodeName(String riskCodeName) {
		this.riskCodeName = riskCodeName;
	}
	private List<Extendinsure> extendinsureList = new ArrayList<Extendinsure>(0);
	
	public String getRiskFileNo() {
		return riskFileNo;
	}
	public void setRiskFileNo(String riskFileNo) {
		this.riskFileNo = riskFileNo;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getReMark() {
		return reMark;
	}
	public void setReMark(String reMark) {
		this.reMark = reMark;
	}
	public String getFlag() {
		return Flag;
	}
	public void setFlag(String flag) {
		Flag = flag;
	}
	public BigDecimal getBasicRateReinsure() {
		return basicRateReinsure;
	}
	public void setBasicRateReinsure(BigDecimal basicRateReinsure) {
		this.basicRateReinsure = basicRateReinsure;
	}
	public BigDecimal getBasicPremiumReinsure() {
		return basicPremiumReinsure;
	}
	public void setBasicPremiumReinsure(BigDecimal basicPremiumReinsure) {
		this.basicPremiumReinsure = basicPremiumReinsure;
	}
	public BigDecimal getBasicRate() {
		return basicRate;
	}
	public void setBasicRate(BigDecimal basicRate) {
		this.basicRate = basicRate;
	}
	public BigDecimal getBasicPremium() {
		return basicPremium;
	}
	public void setBasicPremium(BigDecimal basicPremium) {
		this.basicPremium = basicPremium;
	}
	public BigDecimal getOrderRate() {
		return orderRate;
	}
	public void setOrderRate(BigDecimal orderRate) {
		this.orderRate = orderRate;
	}
	public BigDecimal getOrderPremium() {
		return orderPremium;
	}
	public void setOrderPremium(BigDecimal orderPremium) {
		this.orderPremium = orderPremium;
	}
	public String getRenewalType() {
		return renewalType;
	}
	public void setRenewalType(String renewalType) {
		this.renewalType = renewalType;
	}
	public BigDecimal getCurrentRate() {
		return currentRate;
	}
	public void setCurrentRate(BigDecimal currentRate) {
		this.currentRate = currentRate;
	}
	public BigDecimal getUnderWriteRate1re() {
		return underWriteRate1re;
	}
	public void setUnderWriteRate1re(BigDecimal underWriteRate1re) {
		this.underWriteRate1re = underWriteRate1re;
	}
	public BigDecimal getUnderWritePremium1re() {
		return underWritePremium1re;
	}
	public void setUnderWritePremium1re(BigDecimal underWritePremium1re) {
		this.underWritePremium1re = underWritePremium1re;
	}
	public BigDecimal getUnderWriteRate1() {
		return underWriteRate1;
	}
	public void setUnderWriteRate1(BigDecimal underWriteRate1) {
		this.underWriteRate1 = underWriteRate1;
	}
	public BigDecimal getUnderWritePremium1() {
		return underWritePremium1;
	}
	public void setUnderWritePremium1(BigDecimal underWritePremium1) {
		this.underWritePremium1 = underWritePremium1;
	}
	public BigDecimal getUnderWriteRate2re() {
		return underWriteRate2re;
	}
	public void setUnderWriteRate2re(BigDecimal underWriteRate2re) {
		this.underWriteRate2re = underWriteRate2re;
	}
	public BigDecimal getUnderWritePremium2re() {
		return underWritePremium2re;
	}
	public void setUnderWritePremium2re(BigDecimal underWritePremium2re) {
		this.underWritePremium2re = underWritePremium2re;
	}
	public BigDecimal getUnderWriteRate2() {
		return underWriteRate2;
	}
	public void setUnderWriteRate2(BigDecimal underWriteRate2) {
		this.underWriteRate2 = underWriteRate2;
	}
	public BigDecimal getUnderWritePremium2() {
		return underWritePremium2;
	}
	public void setUnderWritePremium2(BigDecimal underWritePremium2) {
		this.underWritePremium2 = underWritePremium2;
	}
	public String getRateDetails() {
		return rateDetails;
	}
	public void setRateDetails(String rateDetails) {
		this.rateDetails = rateDetails;
	}
	public BigDecimal getRateScale() {
		return rateScale;
	}
	public void setRateScale(BigDecimal rateScale) {
		this.rateScale = rateScale;
	}
	public BigDecimal getMainRiskRate() {
		return mainRiskRate;
	}
	public void setMainRiskRate(BigDecimal mainRiskRate) {
		this.mainRiskRate = mainRiskRate;
	}
	public BigDecimal getRiskRate() {
		return riskRate;
	}
	public void setRiskRate(BigDecimal riskRate) {
		this.riskRate = riskRate;
	}
	public BigDecimal getCustomerAcceptRate() {
		return customerAcceptRate;
	}
	public void setCustomerAcceptRate(BigDecimal customerAcceptRate) {
		this.customerAcceptRate = customerAcceptRate;
	}
	public BigDecimal getWeightingRate() {
		return weightingRate;
	}
	public void setWeightingRate(BigDecimal weightingRate) {
		this.weightingRate = weightingRate;
	}
	public BigDecimal getQuoteRate() {
		return quoteRate;
	}
	public void setQuoteRate(BigDecimal quoteRate) {
		this.quoteRate = quoteRate;
	}
	public BigDecimal getQuotePremium() {
		return quotePremium;
	}
	public void setQuotePremium(BigDecimal quotePremium) {
		this.quotePremium = quotePremium;
	}
	public BigDecimal getQuoteRiskrate() {
		return quoteRiskrate;
	}
	public void setQuoteRiskrate(BigDecimal quoteRiskrate) {
		this.quoteRiskrate = quoteRiskrate;
	}
	public BigDecimal getQuotecomPensateRate() {
		return quotecomPensateRate;
	}
	public void setQuotecomPensateRate(BigDecimal quotecomPensateRate) {
		this.quotecomPensateRate = quotecomPensateRate;
	}
	public BigDecimal getRemainingCost() {
		return remainingCost;
	}
	public void setRemainingCost(BigDecimal remainingCost) {
		this.remainingCost = remainingCost;
	}
	public BigDecimal getExcessSurplus() {
		return excessSurplus;
	}
	public void setExcessSurplus(BigDecimal excessSurplus) {
		this.excessSurplus = excessSurplus;
	}
	public BigDecimal getCommissionScale() {
		return commissionScale;
	}
	public void setCommissionScale(BigDecimal commissionScale) {
		this.commissionScale = commissionScale;
	}
	public BigDecimal getCommossion() {
		return commossion;
	}
	public void setCommossion(BigDecimal commossion) {
		this.commossion = commossion;
	}
	public BigDecimal getHandlerRate() {
		return handlerRate;
	}
	public void setHandlerRate(BigDecimal handlerRate) {
		this.handlerRate = handlerRate;
	}
	public BigDecimal getHandlerFee() {
		return handlerFee;
	}
	public void setHandlerFee(BigDecimal handlerFee) {
		this.handlerFee = handlerFee;
	}
	public BigDecimal getSaleRate() {
		return saleRate;
	}
	public void setSaleRate(BigDecimal saleRate) {
		this.saleRate = saleRate;
	}
	public BigDecimal getSaleFee() {
		return saleFee;
	}
	public void setSaleFee(BigDecimal saleFee) {
		this.saleFee = saleFee;
	}
	public BigDecimal getProFitRate() {
		return proFitRate;
	}
	public void setProFitRate(BigDecimal proFitRate) {
		this.proFitRate = proFitRate;
	}
	public BigDecimal getProFit() {
		return proFit;
	}
	public void setProFit(BigDecimal proFit) {
		this.proFit = proFit;
	}
	public BigDecimal getCompenSateRate() {
		return compenSateRate;
	}
	public void setCompenSateRate(BigDecimal compenSateRate) {
		this.compenSateRate = compenSateRate;
	}
	public BigDecimal getOwnCompenRate() {
		return ownCompenRate;
	}
	public void setOwnCompenRate(BigDecimal ownCompenRate) {
		this.ownCompenRate = ownCompenRate;
	}
	public BigDecimal getComScoreCoefficient() {
		return comScoreCoefficient;
	}
	public void setComScoreCoefficient(BigDecimal comScoreCoefficient) {
		this.comScoreCoefficient = comScoreCoefficient;
	}
	public BigDecimal getHeadQuartFeeCof() {
		return headQuartFeeCof;
	}
	public void setHeadQuartFeeCof(BigDecimal headQuartFeeCof) {
		this.headQuartFeeCof = headQuartFeeCof;
	}
	public BigDecimal getBusinessTaxCof() {
		return businessTaxCof;
	}
	public void setBusinessTaxCof(BigDecimal businessTaxCof) {
		this.businessTaxCof = businessTaxCof;
	}
	public BigDecimal getGuaranteeFundCof() {
		return guaranteeFundCof;
	}
	public void setGuaranteeFundCof(BigDecimal guaranteeFundCof) {
		this.guaranteeFundCof = guaranteeFundCof;
	}
	public BigDecimal getFixedChargesRateCof() {
		return fixedChargesRateCof;
	}
	public void setFixedChargesRateCof(BigDecimal fixedChargesRateCof) {
		this.fixedChargesRateCof = fixedChargesRateCof;
	}
	public BigDecimal getBusinessExpenseRateCof() {
		return businessExpenseRateCof;
	}
	public void setBusinessExpenseRateCof(BigDecimal businessExpenseRateCof) {
		this.businessExpenseRateCof = businessExpenseRateCof;
	}
	public BigDecimal getDefaultBusinessrateCof() {
		return defaultBusinessrateCof;
	}
	public void setDefaultBusinessrateCof(BigDecimal defaultBusinessrateCof) {
		this.defaultBusinessrateCof = defaultBusinessrateCof;
	}
	public BigDecimal getAgeCommissionRateCof() {
		return ageCommissionRateCof;
	}
	public void setAgeCommissionRateCof(BigDecimal ageCommissionRateCof) {
		this.ageCommissionRateCof = ageCommissionRateCof;
	}
	public BigDecimal getAgeSaleRateCof() {
		return ageSaleRateCof;
	}
	public void setAgeSaleRateCof(BigDecimal ageSaleRateCof) {
		this.ageSaleRateCof = ageSaleRateCof;
	}
	public BigDecimal getProFitRateCof() {
		return proFitRateCof;
	}
	public void setProFitRateCof(BigDecimal proFitRateCof) {
		this.proFitRateCof = proFitRateCof;
	}
	public BigDecimal getUpBusinessRateCof() {
		return upBusinessRateCof;
	}
	public void setUpBusinessRateCof(BigDecimal upBusinessRateCof) {
		this.upBusinessRateCof = upBusinessRateCof;
	}
	public BigDecimal getUpSaleRateCof() {
		return upSaleRateCof;
	}
	public void setUpSaleRateCof(BigDecimal upSaleRateCof) {
		this.upSaleRateCof = upSaleRateCof;
	}
	public List<Extendinsure> getExtendinsureList() {
		return extendinsureList;
	}
	public void setExtendinsureList(List<Extendinsure> extendinsureList) {
		this.extendinsureList = extendinsureList;
	}
}
