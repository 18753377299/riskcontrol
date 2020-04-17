package com.picc.riskctrl.riskprice.vo;

import java.math.BigDecimal;

public class ExtendinsureVo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**编号*/
	private Integer serialNo;
	/**风控报告编号*/
	private String riskFileNo;
	/**附加险种代码*/
	private String insuranceCode;
	/**--**附加险类型
	--**E:扩展类
	--**L:限制类
	--**S:规范类
	 */
	private String insuranceType;
	/**附加险费率*/
	private BigDecimal insuranceRate;
	/**附加险保费*/
	private BigDecimal insuranceFee;
	
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	public String getRiskFileNo() {
		return riskFileNo;
	}
	public void setRiskFileNo(String riskFileNo) {
		this.riskFileNo = riskFileNo;
	}
	public String getInsuranceCode() {
		return insuranceCode;
	}
	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}
	public String getInsuranceType() {
		return insuranceType;
	}
	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}
	public BigDecimal getInsuranceRate() {
		return insuranceRate;
	}
	public void setInsuranceRate(BigDecimal insuranceRate) {
		this.insuranceRate = insuranceRate;
	}
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
}
