package com.picc.riskctrl.riskprice.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "pricingtreatment")
public class PricingTreatment implements Serializable{
private static final long serialVersionUID = 1L;
	
	
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
	@Id
	@Column(name = "riskfileno")
	public String getRiskFileNo() {
		return riskFileNo;
	}
	public void setRiskFileNo(String riskFileNo) {
		this.riskFileNo = riskFileNo;
	}
	@Column(name = "riskcode")
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	@Column(name = "industrycode")
	public String getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	@Column(name = "effectivedate")
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	@Column(name = "expirationdate")
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	@Column(name = "insuredamount")
	public BigDecimal getInsuredAmount() {
		return insuredAmount;
	}
	public void setInsuredAmount(BigDecimal insuredAmount) {
		this.insuredAmount = insuredAmount;
	}
	@Column(name = "inventoriesamount")
	public BigDecimal getInventoriesAmount() {
		return inventoriesAmount;
	}
	public void setInventoriesAmount(BigDecimal inventoriesAmount) {
		this.inventoriesAmount = inventoriesAmount;
	}
	@Column(name = "franchise")
	public BigDecimal getFranChise() {
		return franChise;
	}
	public void setFranChise(BigDecimal franChise) {
		this.franChise = franChise;
	}
	@Column(name = "franchiserate")
	public BigDecimal getFranChiseRate() {
		return franChiseRate;
	}
	public void setFranChiseRate(BigDecimal franChiseRate) {
		this.franChiseRate = franChiseRate;
	}
	@Column(name = "businessnature")
	public String getBusinessNature() {
		return businessNature;
	}
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}
	@Column(name = "orderrate")
	public BigDecimal getOrderRate() {
		return orderRate;
	}
	public void setOrderRate(BigDecimal orderRate) {
		this.orderRate = orderRate;
	}
	@Column(name = "commissionscale")
	public BigDecimal getCommissionScale() {
		return commissionScale;
	}
	public void setCommissionScale(BigDecimal commissionScale) {
		this.commissionScale = commissionScale;
	}
	@Column(name = "commissionscaleupper")
	public BigDecimal getCommissionScaleUpper() {
		return commissionScaleUpper;
	}
	public void setCommissionScaleUpper(BigDecimal commissionScaleUpper) {
		this.commissionScaleUpper = commissionScaleUpper;
	}
	@Column(name = "comprehensivescore")
	public BigDecimal getCompreHensiveScore() {
		return compreHensiveScore;
	}
	public void setCompreHensiveScore(BigDecimal compreHensiveScore) {
		this.compreHensiveScore = compreHensiveScore;
	}
	@Column(name = "reinsurescale")
	public BigDecimal getReinsureScale() {
		return reinsureScale;
	}
	public void setReinsureScale(BigDecimal reinsureScale) {
		this.reinsureScale = reinsureScale;
	}
	@Column(name = "reimsureprocedrate")
	public BigDecimal getReimsureProcedRate() {
		return reimsureProcedRate;
	}
	public void setReimsureProcedRate(BigDecimal reimsureProcedRate) {
		this.reimsureProcedRate = reimsureProcedRate;
	}
	@Column(name = "subjectbelongs")
	public String getSubjectBelongs() {
		return subjectBelongs;
	}
	public void setSubjectBelongs(String subjectBelongs) {
		this.subjectBelongs = subjectBelongs;
	}
	
}
