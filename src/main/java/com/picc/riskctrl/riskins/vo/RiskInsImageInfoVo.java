package com.picc.riskctrl.riskins.vo;

import java.util.List;
public class RiskInsImageInfoVo {
	/**
	 * 突出风险照片集合名称
	 */
	private String riskReminders;
	/**突出风险照片路径集合*/
	private List<String> picturePath;
	
	public String getRiskReminders() {
		return riskReminders;
	}
	public void setRiskReminders(String riskReminders) {
		this.riskReminders = riskReminders;
	}
	public List<String> getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(List<String> picturePath) {
		this.picturePath = picturePath;
	}
	
}
