package com.picc.riskctrl.riskins.vo;

public class RiskControlConditionDto {
	//风控报告编号
	private String riskFileNo;
	// 保单号
	private String policyNo;
	// 投保单号
	private String proposalNo;

	public String getRiskFileNo() {
		return riskFileNo;
	}

	public void setRiskFileNo(String riskFileNo) {
		this.riskFileNo = riskFileNo;
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
