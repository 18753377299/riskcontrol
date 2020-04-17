package com.picc.riskctrl.riskcheck.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RiskCheckMainResponseVoo对象")
public class RiskCheckMainResponseVo {

	private static final long serialVersionUID = 1L;
	/**巡检编号*/
	private String riskCheckNo;
	/**被保险人*/
//	private String insuredType;
	/**被保险人名称*/
	private String insuredName;
	/**巡检人*/
	private String checker;
	/**巡检机构*/
	private String checkComCode;
	/**归属机构*/
	private String comCode;
	/**归属机构中文*/
	private String comCodeCName;
	/**巡检日期*/
	private Date checkDate;
	/**巡检地址*/
	private String addressDetail;
	/**来源**/
	private String mobileFlag;
	
	/**行业类型代码*/
	private String businessClass;
	/**国民经济行业代码*/
	private String businessSource;
	/**巡检报告类型*/
	private String checkModel;
	/**审核状态
	 * T:未处理
	 * 1：已完成
	 */
	private String underwriteFlag;
	/**环境风险值*/
	private BigDecimal envDanger;
	/**重要风险值*/
	private BigDecimal impDanger;
	/**建筑风险值*/
	private BigDecimal buildDanger;
	/**存货风险值*/
	private BigDecimal cargoDanger;
	/**防台防汛风险值*/
	private BigDecimal TypDanger;
	/**防汛制度风险值*/
	private BigDecimal FloodDanger;
	
	public String getMobileFlag() {
		return mobileFlag;
	}
	public void setMobileFlag(String mobileFlag) {
		this.mobileFlag = mobileFlag;
	}
	public String getRiskCheckNo() {
		return riskCheckNo;
	}
	public void setRiskCheckNo(String riskCheckNo) {
		this.riskCheckNo = riskCheckNo;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getComCodeCName() {
		return comCodeCName;
	}
	public void setComCodeCName(String comCodeCName) {
		this.comCodeCName = comCodeCName;
	}
	public String getCheckComCode() {
		return checkComCode;
	}
	public void setCheckComCode(String checkComCode) {
		this.checkComCode = checkComCode;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	
	public String getUnderwriteFlag() {
		return underwriteFlag;
	}
	public void setUnderwriteFlag(String underwriteFlag) {
		this.underwriteFlag = underwriteFlag;
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
		return TypDanger;
	}
	public void setTypDanger(BigDecimal typDanger) {
		TypDanger = typDanger;
	}
	public BigDecimal getFloodDanger() {
		return FloodDanger;
	}
	public void setFloodDanger(BigDecimal floodDanger) {
		FloodDanger = floodDanger;
	}
	public String getBusinessClass() {
		return businessClass;
	}
	public void setBusinessClass(String businessClass) {
		this.businessClass = businessClass;
	}
	public String getBusinessSource() {
		return businessSource;
	}
	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}
	public String getCheckModel() {
		return checkModel;
	}
	public void setCheckModel(String checkModel) {
		this.checkModel = checkModel;
	}
	
	
}
