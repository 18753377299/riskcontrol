package com.picc.riskctrl.riskcheck.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RiskCheckMainQueryVoo对象")
public class RiskCheckMainQueryVo {

	/**巡检编号*/
	private String riskCheckNo;
	/**被保险人*/
	private String insuredType;
	/**被保险人代码*/
	private String insuredCode;
	/**被保险人姓名/企业名称*/
	private String insuredName;
	/**巡检人*/
	private String checker;
	/**申请查勘机构*/
	private String checkComCode;
	/**归属机构*/
	private String comCode;
	/**巡检起期*/
	private Date checkStartDate;
	/**巡检止期*/
	private Date checkEndDate;
	/**巡检报告类型*/
	private String checkModel;
	/**巡检地址*/
	private String addressDetail;
	/**审核状态
	 * T:未处理
	 * 1：已完成
	 */
	private String[] underwriteFlag;
	/**审核标志位
	 * 业务类型 
	 * underwrite 审核
	 * query 查询
	 */
//	private String businessType;
	/**要删除的集合*/
	@SuppressWarnings("rawtypes")
	private List riskCheckNoList;
	
	@SuppressWarnings("rawtypes")
    private List riskCheckLogIdList;
	
	private String [] checkerStatus;
	
	private String proposalNo;
	
	 /**巡检人代码*/
    private String checkerCode;
    
	public List getRiskCheckLogIdList() {
		return riskCheckLogIdList;
	}
	public void setRiskCheckLogIdList(List riskCheckLogIdList) {
		this.riskCheckLogIdList = riskCheckLogIdList;
	}
	public String getProposalNo() {
		return proposalNo;
	}
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}
	public String[] getCheckerStatus() {
		return checkerStatus;
	}
	public void setCheckerStatus(String[] checkerStatus) {
		this.checkerStatus = checkerStatus;
	}
	
	public String getRiskCheckNo() {
		return riskCheckNo;
	}
	public void setRiskCheckNo(String riskCheckNo) {
		this.riskCheckNo = riskCheckNo;
	}
	public String getInsuredType() {
		return insuredType;
	}
	public void setInsuredType(String insuredType) {
		this.insuredType = insuredType;
	}
	
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getInsuredCode() {
		return insuredCode;
	}
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getCheckComCode() {
		return checkComCode;
	}
	public void setCheckComCode(String checkComCode) {
		this.checkComCode = checkComCode;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public Date getCheckStartDate() {
		return checkStartDate;
	}
	public void setCheckStartDate(Date checkStartDate) {
		this.checkStartDate = checkStartDate;
	}
	public Date getCheckEndDate() {
		return checkEndDate;
	}
	public void setCheckEndDate(Date checkEndDate) {
		this.checkEndDate = checkEndDate;
	}
	public String getCheckModel() {
		return checkModel;
	}
	public void setCheckModel(String checkModel) {
		this.checkModel = checkModel;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	public String[] getUnderwriteFlag() {
		return underwriteFlag;
	}
	public void setUnderwriteFlag(String[] underwriteFlag) {
		this.underwriteFlag = underwriteFlag;
	}
//	public String getBusinessType() {
//		return businessType;
//	}
//	public void setBusinessType(String businessType) {
//		this.businessType = businessType;
//	}
	public List getRiskCheckNoList() {
		return riskCheckNoList;
	}
	public void setRiskCheckNoList(List riskCheckNoList) {
		this.riskCheckNoList = riskCheckNoList;
	}
	public String getCheckerCode() {
		return checkerCode;
	}
	public void setCheckerCode(String checkerCode) {
		this.checkerCode = checkerCode;
	}
	
	
	
}
