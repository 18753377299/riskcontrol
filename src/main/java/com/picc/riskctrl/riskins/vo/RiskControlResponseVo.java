package com.picc.riskctrl.riskins.vo;

public class RiskControlResponseVo {
	//返回报文头部信息
//	private ResponseHead responsehead;
	//返回报文体信息
	private ReturnRiskControlDataDto returnRiskControlDataDto;
//	public ResponseHead getResponsehead() {
//		return responsehead;
//	}
//	public void setResponsehead(ResponseHead responsehead) {
//		this.responsehead = responsehead;
//	}
	public ReturnRiskControlDataDto getReturnRiskControlDataDto() {
		return returnRiskControlDataDto;
	}
	public void setReturnRiskControlDataDto(ReturnRiskControlDataDto returnRiskControlDataDto) {
		this.returnRiskControlDataDto = returnRiskControlDataDto;
	}
	

}
