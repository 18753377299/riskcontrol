package com.picc.riskctrl.riskinfo.claim.vo;

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
@ApiModel("RiskInfoClaimVoo对象")
public class RiskInfoClaimVo {
	/**序号*/
	private Integer serialNo;
	/**机构*/
	private String comCode;
	/**归属机构名称*/
	private String comCodeCName;
	/**维护人代码*/
	private String operatorCode;
	/**维护人名称*/
	private String operatorName;
	/**案例名称*/
	private String claimName;
	/**产品名称*/
	private String riskCname;
	/**出险年度*/
	private String claimYear;
	/**险种*/
	private String riskName;
	/**险种中文翻译*/
	private String  riskNameC;
	/**赔款金额*/
	private  BigDecimal  claimAmount;
	/**行业*/
	private String profession;
	/**案件来源*/
	private String sender;
	/**出险原因*/
	private String claimReason;
	/**案例模板*/
	private String url;
	/**有效标志位*/
	private String validStatus;
	/**有效标志位中文*/
	private String validStatusName;
	/**插入时间*/
	private Date insertTimeForHis;
	/**更新时间*/
	private Date operateTimeForHis;
	/**打回意见*/
	private String remark;
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
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
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
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
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public String getRiskNameC() {
		return riskNameC;
	}
	public void setRiskNameC(String riskNameC) {
		this.riskNameC = riskNameC;
	}
	public BigDecimal getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(BigDecimal claimAmount) {
		this.claimAmount = claimAmount;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getClaimReason() {
		return claimReason;
	}
	public void setClaimReason(String claimReason) {
		this.claimReason = claimReason;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	public String getValidStatusName() {
		return validStatusName;
	}
	public void setValidStatusName(String validStatusName) {
		this.validStatusName = validStatusName;
	}
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}
	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}
	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}

