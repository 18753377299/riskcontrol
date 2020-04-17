package com.picc.riskctrl.riskinfo.report.vo;

public class RiskInfoFileVo {
	/**序号*/
	private Integer serialNo;
	/**风控报告名字*/
	private String riskFileName;
	/**险种*/
	private String riskName;
	/**行业*/
	private String profession;
	/**出具报告年度*/
	private String riskYear;
	/**风控报告来源*/
	private String ascName;
	/**出具报告机构*/
	private String sender;
	/**报告模板*/
	private String url;
	/**有效标志位*/
	private String validStatus;
	/** 打回信息 */
	private String remark;	
	/** 归属机构 */
	private String comCode;	
	/** 操作人员代码 */
	private String operatorCode;		
	/** 操作人员名称 */
	private String operatorName;	
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	public String getRiskFileName() {
		return riskFileName;
	}
	public void setRiskFileName(String riskFileName) {
		this.riskFileName = riskFileName;
	}
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getRiskYear() {
		return riskYear;
	}
	public void setRiskYear(String riskYear) {
		this.riskYear = riskYear;
	}
	public String getAscName() {
		return ascName;
	}
	public void setAscName(String ascName) {
		this.ascName = ascName;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
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
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
}
