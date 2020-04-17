package com.picc.riskctrl.riskins.vo;

public class ReturnRiskControlDataDto {
	//风控报告单号
	private String riskFileNo;
	//险类代码
	private String classCode;
	//险种代码
	private String riskCode;
	//归属机构
	private String comCode;
	//风控报告模板号
	private String riskModel;
	//被保险人类型
	private String insuredType;
	//被保险人代码
	private String insuredCode;
	//被保险人名称
	private String insuredName;
	//保单号
	private String policyNo;
	//投保单号
	private String proposalNo;
	public String getRiskFileNo() {
		return riskFileNo;
	}
	public void setRiskFileNo(String riskFileNo) {
		this.riskFileNo = riskFileNo;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getRiskModel() {
		return riskModel;
	}
	public void setRiskModel(String riskModel) {
		this.riskModel = riskModel;
	}
	public String getInsuredType() {
		return insuredType;
	}
	public void setInsuredType(String insuredType) {
		this.insuredType = insuredType;
	}
	public String getInsuredCode() {
		return insuredCode;
	}
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getProposalNo() {
		return proposalNo;
	}
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}
	

}
