package com.picc.riskctrl.riskprice.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "extendinsure")
public class Extendinsure implements java.io.Serializable{
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
	
	private RiskReportPricing pricing;
	
	@Id
	@Column(name = "serialno")
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	@Column(name = "riskfileno")
	public String getRiskFileNo() {
		return riskFileNo;
	}
	public void setRiskFileNo(String riskFileNo) {
		this.riskFileNo = riskFileNo;
	}
	@Column(name = "insurancecode")
	public String getInsuranceCode() {
		return insuranceCode;
	}
	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}
	
	@Column(name = "insurancetype")
	public String getInsuranceType() {
		return insuranceType;
	}
	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}
	@Column(name = "insurancerate")
	public BigDecimal getInsuranceRate() {
		return insuranceRate;
	}
	public void setInsuranceRate(BigDecimal insuranceRate) {
		this.insuranceRate = insuranceRate;
	}
	@Column(name = "insurancefee")
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "riskFileNo", nullable = false, insertable = false, updatable = false)
	public RiskReportPricing getPricing() {
		return pricing;
	}
	public void setPricing(RiskReportPricing pricing) {
		this.pricing = pricing;
	}
}
	
