package com.picc.riskctrl.riskins.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RiskReportMainResponseVo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 风险档案编号
     */
    private String riskFileNo;
    /**
     * 被保险人姓名/企业名称
     */
    private String insuredName;
    /**
     * 投保险种
     */
    private String riskCode;
    /**
     * 查勘人
     */
    private String explorer;
    /**
     * 归属机构
     */
    private String comCode;
    /**
     * 查勘日期
     */
    private Date exploreDate;
    /**
     * 查勘类别
     * 1 初勘
     * 2 复勘
     */
    private String exploreType;
    /**
     * 核保标志
     */
    private String underwriteFlag;

    /**
     * 查勘人中文
     */
    private String explorerCName;

    /**
     * 归属机构中文
     */
    private String comCodeCName;

    /**
     * 风控报告模板
     * 001:专职版
     * 002:兼职版
     * 004:机器损失险
     * 005:营业中断险
     */
    private String riskModel;

    /**
     * 综合得分
     */
    private BigDecimal score;
    /**
     * 风控建议
     */
    private String riskSuggest;
    /**
     * 突出风险
     */
    private String highlightRisk;
    /**
     * 火灾风险值
     */
    private BigDecimal fireDanger;
    /**
     * 水灾风险值
     */
    private BigDecimal waterDanger;
    /**
     * 风灾风险值
     */
    private BigDecimal windDanger;
    /**
     * 雷灾风险值
     */
    private BigDecimal thunderDanger;
    /**
     * 雪灾风险值
     */
    private BigDecimal snowDanger;
    /**
     * 盗抢风险值
     */
    private BigDecimal theftDanger;
    /**
     * 地震风险值
     */
    private BigDecimal earthquakeDanger;
    /**
     * 地质灾害风险值
     */
    private BigDecimal geologyDanger;

    /**
     * 操作系统来源
     **/
    private String mobileFlag;

    /**
     * 流程ID
     */
    private String executionId;

    /**
     * 审核状态
     */
    private String underwriteFlagCode;

	public String getRiskFileNo() {
		return riskFileNo;
	}

	public void setRiskFileNo(String riskFileNo) {
		this.riskFileNo = riskFileNo;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getExplorer() {
		return explorer;
	}

	public void setExplorer(String explorer) {
		this.explorer = explorer;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
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

	public String getUnderwriteFlag() {
		return underwriteFlag;
	}

	public void setUnderwriteFlag(String underwriteFlag) {
		this.underwriteFlag = underwriteFlag;
	}

	public String getExplorerCName() {
		return explorerCName;
	}

	public void setExplorerCName(String explorerCName) {
		this.explorerCName = explorerCName;
	}

	public String getComCodeCName() {
		return comCodeCName;
	}

	public void setComCodeCName(String comCodeCName) {
		this.comCodeCName = comCodeCName;
	}

	public String getRiskModel() {
		return riskModel;
	}

	public void setRiskModel(String riskModel) {
		this.riskModel = riskModel;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getRiskSuggest() {
		return riskSuggest;
	}

	public void setRiskSuggest(String riskSuggest) {
		this.riskSuggest = riskSuggest;
	}

	public String getHighlightRisk() {
		return highlightRisk;
	}

	public void setHighlightRisk(String highlightRisk) {
		this.highlightRisk = highlightRisk;
	}

	public BigDecimal getFireDanger() {
		return fireDanger;
	}

	public void setFireDanger(BigDecimal fireDanger) {
		this.fireDanger = fireDanger;
	}

	public BigDecimal getWaterDanger() {
		return waterDanger;
	}

	public void setWaterDanger(BigDecimal waterDanger) {
		this.waterDanger = waterDanger;
	}

	public BigDecimal getWindDanger() {
		return windDanger;
	}

	public void setWindDanger(BigDecimal windDanger) {
		this.windDanger = windDanger;
	}

	public BigDecimal getThunderDanger() {
		return thunderDanger;
	}

	public void setThunderDanger(BigDecimal thunderDanger) {
		this.thunderDanger = thunderDanger;
	}

	public BigDecimal getSnowDanger() {
		return snowDanger;
	}

	public void setSnowDanger(BigDecimal snowDanger) {
		this.snowDanger = snowDanger;
	}

	public BigDecimal getTheftDanger() {
		return theftDanger;
	}

	public void setTheftDanger(BigDecimal theftDanger) {
		this.theftDanger = theftDanger;
	}

	public BigDecimal getEarthquakeDanger() {
		return earthquakeDanger;
	}

	public void setEarthquakeDanger(BigDecimal earthquakeDanger) {
		this.earthquakeDanger = earthquakeDanger;
	}

	public BigDecimal getGeologyDanger() {
		return geologyDanger;
	}

	public void setGeologyDanger(BigDecimal geologyDanger) {
		this.geologyDanger = geologyDanger;
	}

	public String getMobileFlag() {
		return mobileFlag;
	}

	public void setMobileFlag(String mobileFlag) {
		this.mobileFlag = mobileFlag;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getUnderwriteFlagCode() {
		return underwriteFlagCode;
	}

	public void setUnderwriteFlagCode(String underwriteFlagCode) {
		this.underwriteFlagCode = underwriteFlagCode;
	}


}
