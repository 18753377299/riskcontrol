package com.picc.riskctrl.common.vo;

import java.util.Date;

public class RiskDtemplateVo {

	/** 险类代码 */
	private String classCode;
	/** 产品代码 */
	private String riskCode;
	/** 上次风控报告编号 */
	private String oldRiskFileNo;
	/** 行业代码/工程类别代码 */
	private String businessSource;
	/** 模版代码 */
	private String templateCode;
	/** 行业类别 */
	private String businessClass;
	/** 模版名称 */
	private String templateName;
	/** 模版有效标志 */
	private String validstatus;
	/** 查勘员 */
	private String explorer;
	/** 查勘员统一工号 */
	private String explorer_uni;
	/** 查勘日期 */
	private Date exploreDate;
	/** 查勘类型 */
	private String exploreType;
	/** 申请查勘机构 */
	private String exploreComCode;

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

	public String getBusinessSource() {
		return businessSource;
	}

	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getBusinessClass() {
		return businessClass;
	}

	public void setBusinessClass(String businessClass) {
		this.businessClass = businessClass;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getValidstatus() {
		return validstatus;
	}

	public void setValidstatus(String validstatus) {
		this.validstatus = validstatus;
	}

	public String getExplorer() {
		return explorer;
	}

	public void setExplorer(String explorer) {
		this.explorer = explorer;
	}

	public Date getExploreDate() {
		return exploreDate;
	}

	public void setExploreDate(Date exploreDate) {
		this.exploreDate = exploreDate;
	}

	public String getExploreType() {
		return exploreType;
	}

	public void setExploreType(String exploreType) {
		this.exploreType = exploreType;
	}

	public String getExploreComCode() {
		return exploreComCode;
	}

	public void setExploreComCode(String exploreComCode) {
		this.exploreComCode = exploreComCode;
	}

	public String getOldRiskFileNo() {
		return oldRiskFileNo;
	}

	public void setOldRiskFileNo(String oldRiskFileNo) {
		this.oldRiskFileNo = oldRiskFileNo;
	}

	public String getExplorer_uni() {
		return explorer_uni;
	}

	public void setExplorer_uni(String explorerUni) {
		explorer_uni = explorerUni;
	}

}
