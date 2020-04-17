package com.picc.riskctrl.riskins.vo;

import java.util.Date;

public class UnfinishBusinessVo {
	/** 业务类型*/
	private String businessType;
	
	/** 业务代码*/
	private String businessNo;
	
	/** 扩充字段1
	 * 对于已反馈照片档案为照片类别*/
	private String ext1;
	
	/** 扩充字段2
	 * 对于已反馈照片档案为照片名称*/
	private String ext2;
	
	/** 转入时间*/
	private Date sendtime;
	
	/** 风控报告模板 */
	private String riskModel;
	private String hybrid;

	public String getHybrid() {
		return hybrid;
	}

	public void setHybrid(String hybrid) {
		this.hybrid = hybrid;
	}

	public String getRiskModel() {
		return riskModel;
	}

	public void setRiskModel(String riskModel) {
		this.riskModel = riskModel;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	
}
