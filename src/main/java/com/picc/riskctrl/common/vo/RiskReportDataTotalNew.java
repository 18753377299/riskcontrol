package com.picc.riskctrl.common.vo;

import java.math.BigDecimal;
/**
 * @风控报告数据统计表
 * @author 王坤龙
 * @date 2019-02-20
 * 修改 2020-02-19
 * @author 周东旭
 */
public class RiskReportDataTotalNew {
	/**分公司代码*/
	private String codeCode;
	/**分公司名称*/
	private String codeCname;
	/**专职版总量*/
	private BigDecimal detailCount = BigDecimal.ZERO;
	/**兼职版总量*/
	private BigDecimal pluralismCount = BigDecimal.ZERO;
	/**机器损失险总量*/
	private BigDecimal machineCount = BigDecimal.ZERO;
	/**营业中断险总量*/
	private BigDecimal cloBusinessCount = BigDecimal.ZERO;
	/**机器损失险总量*/
	private BigDecimal riskCheckCount = BigDecimal.ZERO;
	/**营业中断险总量*/
	private BigDecimal riskCheckSimpleCount = BigDecimal.ZERO;
	/**火灾风险排查专业版总量*/
	private BigDecimal riskFireCount = BigDecimal.ZERO;
	/**火灾风险排查简化版总量*/
	private BigDecimal riskFireSimpleCount = BigDecimal.ZERO;
	public String getCodeCode() {
		return codeCode;
	}
	public void setCodeCode(String codeCode) {
		this.codeCode = codeCode;
	}
	public String getCodeCname() {
		return codeCname;
	}
	public void setCodeCname(String codeCname) {
		this.codeCname = codeCname;
	}
	public BigDecimal getDetailCount() {
		return detailCount;
	}
	public void setDetailCount(BigDecimal detailCount) {
		this.detailCount = detailCount;
	}
	public BigDecimal getPluralismCount() {
		return pluralismCount;
	}
	public void setPluralismCount(BigDecimal pluralismCount) {
		this.pluralismCount = pluralismCount;
	}
	public BigDecimal getMachineCount() {
		return machineCount;
	}
	public void setMachineCount(BigDecimal machineCount) {
		this.machineCount = machineCount;
	}
	public BigDecimal getCloBusinessCount() {
		return cloBusinessCount;
	}
	public void setCloBusinessCount(BigDecimal cloBusinessCount) {
		this.cloBusinessCount = cloBusinessCount;
	}
	public BigDecimal getRiskCheckCount() {
		return riskCheckCount;
	}
	public void setRiskCheckCount(BigDecimal riskCheckCount) {
		this.riskCheckCount = riskCheckCount;
	}
	public BigDecimal getRiskCheckSimpleCount() {
		return riskCheckSimpleCount;
	}
	public void setRiskCheckSimpleCount(BigDecimal riskCheckSimpleCount) {
		this.riskCheckSimpleCount = riskCheckSimpleCount;
	}
	public BigDecimal getRiskFireCount() {
		return riskFireCount;
	}
	public void setRiskFireCount(BigDecimal riskFireCount) {
		this.riskFireCount = riskFireCount;
	}
	public BigDecimal getRiskFireSimpleCount() {
		return riskFireSimpleCount;
	}
	public void setRiskFireSimpleCount(BigDecimal riskFireSimpleCount) {
		this.riskFireSimpleCount = riskFireSimpleCount;
	}
	
}
