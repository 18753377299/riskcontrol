package com.picc.riskctrl.riskins.vo;

import java.util.Date;

public class ExportExcelRequestVo {
	private Date dateBegin;
	private Date dateEnd;
	private String proCityFlag;
	private String[] riskFlag;
	private String[] statusFlag;
	public Date getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getProCityFlag() {
		return proCityFlag;
	}
	public void setProCityFlag(String proCityFlag) {
		this.proCityFlag = proCityFlag;
	}
	public String[] getRiskFlag() {
		return riskFlag;
	}
	public void setRiskFlag(String[] riskFlag) {
		this.riskFlag = riskFlag;
	}
	public String[] getStatusFlag() {
		return statusFlag;
	}
	public void setStatusFlag(String[] statusFlag) {
		this.statusFlag = statusFlag;
	}
	
}
