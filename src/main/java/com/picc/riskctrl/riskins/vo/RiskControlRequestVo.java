package com.picc.riskctrl.riskins.vo;

public class RiskControlRequestVo {
	
	//接收报文头部信息
//	private RequestHead requesthead;
	//接收报文体信息
	private RiskControlConditionDto riskControlConditionDto;
//	public RequestHead getRequesthead() {
//		return requesthead;
//	}
//	public void setRequesthead(RequestHead requesthead) {
//		this.requesthead = requesthead;
//	}
	public RiskControlConditionDto getRiskControlConditionDto() {
		return riskControlConditionDto;
	}
	public void setRiskControlConditionDto(RiskControlConditionDto riskControlConditionDto) {
		this.riskControlConditionDto = riskControlConditionDto;
	}
	

}
