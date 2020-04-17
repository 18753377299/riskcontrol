package com.picc.riskctrl.riskins.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "riskreport_machine")
public class RiskReportMachine implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**风控档案编号*/
	private String riskFileNo;
	/**是否提供设备投保清单
	 	A 是
	 	B 否**/
	private String insuranceFlag;
	/**是否存在选择性承保
	 	A 是
	 	B 否**/
	private String seleInsured;
	/**是否属于限制承保业务
		A 是
		B 否**/
	private String compLimited;
	/**主要设备运营时间
		A 是
		B 否**/
	private BigDecimal runYear;
	/**是否有接近或超过年限设备
		A 是
		B 否**/
	private String overageFlag;
	/**设备是否存在已知重大缺陷
		A 是
		B 否**/
	private String seriousDefect;
	/**关键设备是否有原型机
		A 是
		B 否**/
	private String prototype;
	/**是否有合格证书或进出口商品检验证书
	 	A 是
	 	B 否**/
	private String cerQualified;
	/**是否有安装试车证书
		A 是
		B 否**/
	private String installTest;
	/**人员是否经过培训且持证上岗
		A 是
		B 否**/
	private String holderWork;
	/**是否有完备操作指导书
		A 是
		B 否**/
	private String instruction;
	/**是否建立安全生产责任制度
		A 是
		B 否**/
	private String proSystem;
	/**是否建立设备状态检测制度
		A 是
		B 否**/
	private String testSystem;
	/**周围照明、湿度及清洁状况是否良好
		A 是
		B 否**/
	private String goodCondition;
	/**是否有异常震动或噪音
		A 是
		B 否**/
	private String noiseFlag;
	/**是否有泄漏情况
		A 是
		B 否**/
	private String leak;
	/**设备负荷是否正常
		A 是
		B 否**/
	private String loadEquip;
	/**供水、供电、供气是否正常
		A 是
		B 否**/
	private String waterPowerGas;
	/**是否有后备供水、供电、供气设备能运行
		A 是
		B 否**/
	private String standbyEquip;
	/**是否有设备运行记录
		A 是
		B 否**/
	private String runRecord;
	/**近三年历史出险记录
		A 是
		B 否**/
	private String historyRecord;
	/**是否建立设备维修保养制度
		A 是
		B 否**/
	private String maintenance;
	/**是否定期维护保养
		A 是
		B 否**/
	private String byTheSystem;
	/**是否有产品保修或保证规定
		A 是
		B 否**/
	private String proWarranty;
	/**是否配备稳定电压装置
		A 是
		B 否**/
	private String stableEle;
	/**安全保护装置是否测试
		A 是
		B 否**/
	private String protectiveGear;
	/**是否有设备应急预案
		A 是
		B 否**/
	private String urgencyPlan;
	/**主要设备维修商
		A 国内维修商
		B 国外维修商**/
	private String repairman;
	/**是否有备件及备件管理制度
		A 是
		B 否**/
	private String spareParts;
	/**近三年设备保养维修状况**/
	private String repairSystem;
	/**是否投保锅炉
		A 是
		B 否**/
	private String boilerFlag;
	/**是否全使用标准水质的水
		A 是
		B 否**/
	private String standardWater;
	/**锅炉使用性质
		A 生产用
		B 生活用**/
	private String boilerUsage;
	/**是否有当局或主管部门签发上岗合格证
		A 是
		B 否**/
	private String operatorCard;
	/**是否按规定日常检查、维护及保养
		A 是
		B 否**/
	private String checkBoiler;
	/**近两年是否受过损坏
		A 是
		B 否**/
	private String boilerDamage;
	/**扩展类附加险**/
	private Integer extensionRisk;
	/** 风控主表 **/
	private RiskReportMain riskReportMain;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RISKFILENO", nullable = false, insertable = false, updatable = false)
	public RiskReportMain getRiskReportMain() {
		return riskReportMain;
	}
	public void setRiskReportMain(RiskReportMain riskReportMain) {
		this.riskReportMain = riskReportMain;
	}
	@Id
	@Column(name = "RISKFILENO", nullable = false)
	public String getRiskFileNo() {
		return riskFileNo;
	}
	public void setRiskFileNo(String riskFileNo) {
		this.riskFileNo = riskFileNo;
	}
	@Column(name = "INSURANCEFLAG")
	public String getInsuranceFlag() {
		return insuranceFlag;
	}
	public void setInsuranceFlag(String insuranceFlag) {
		this.insuranceFlag = insuranceFlag;
	}
	@Column(name = "SELEINSURED")
	public String getSeleInsured() {
		return seleInsured;
	}
	public void setSeleInsured(String seleInsured) {
		this.seleInsured = seleInsured;
	}
	@Column(name = "COMPLIMITED")
	public String getCompLimited() {
		return compLimited;
	}
	public void setCompLimited(String compLimited) {
		this.compLimited = compLimited;
	}
	@Column(name = "RUNYEAR")
	public BigDecimal getRunYear() {
		return runYear;
	}
	public void setRunYear(BigDecimal runYear) {
		this.runYear = runYear;
	}
	@Column(name = "OVERAGEFLAG")
	public String getOverageFlag() {
		return overageFlag;
	}
	public void setOverageFlag(String overageFlag) {
		this.overageFlag = overageFlag;
	}
	@Column(name = "SERIOUSDEFECT")
	public String getSeriousDefect() {
		return seriousDefect;
	}
	public void setSeriousDefect(String seriousDefect) {
		this.seriousDefect = seriousDefect;
	}
	@Column(name = "PROTOTYPE")
	public String getPrototype() {
		return prototype;
	}
	public void setPrototype(String prototype) {
		this.prototype = prototype;
	}
	@Column(name = "CERQUALIFIED")
	public String getCerQualified() {
		return cerQualified;
	}
	public void setCerQualified(String cerQualified) {
		this.cerQualified = cerQualified;
	}
	@Column(name = "INSTALLTEST")
	public String getInstallTest() {
		return installTest;
	}
	public void setInstallTest(String installTest) {
		this.installTest = installTest;
	}
	@Column(name = "HOLDERWORK")
	public String getHolderWork() {
		return holderWork;
	}
	public void setHolderWork(String holderWork) {
		this.holderWork = holderWork;
	}
	@Column(name = "INSTRUCTION")
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	@Column(name = "PROSYSTEM")
	public String getProSystem() {
		return proSystem;
	}
	public void setProSystem(String proSystem) {
		this.proSystem = proSystem;
	}
	@Column(name = "TESTSYSTEM")
	public String getTestSystem() {
		return testSystem;
	}
	public void setTestSystem(String testSystem) {
		this.testSystem = testSystem;
	}
	@Column(name = "GOODCONDITION")
	public String getGoodCondition() {
		return goodCondition;
	}
	public void setGoodCondition(String goodCondition) {
		this.goodCondition = goodCondition;
	}
	@Column(name = "NOISEFLAG")
	public String getNoiseFlag() {
		return noiseFlag;
	}
	public void setNoiseFlag(String noiseFlag) {
		this.noiseFlag = noiseFlag;
	}
	@Column(name = "LEAK")
	public String getLeak() {
		return leak;
	}
	public void setLeak(String leak) {
		this.leak = leak;
	}
	@Column(name = "LOADEQUIP")
	public String getLoadEquip() {
		return loadEquip;
	}
	public void setLoadEquip(String loadEquip) {
		this.loadEquip = loadEquip;
	}
	@Column(name = "WATERPOWERGAS")
	public String getWaterPowerGas() {
		return waterPowerGas;
	}
	public void setWaterPowerGas(String waterPowerGas) {
		this.waterPowerGas = waterPowerGas;
	}
	@Column(name = "STANDBYEQUIP")
	public String getStandbyEquip() {
		return standbyEquip;
	}
	public void setStandbyEquip(String standbyEquip) {
		this.standbyEquip = standbyEquip;
	}
	@Column(name = "RUNRECORD")
	public String getRunRecord() {
		return runRecord;
	}
	public void setRunRecord(String runRecord) {
		this.runRecord = runRecord;
	}
	@Column(name = "HISTORYRECORD")
	public String getHistoryRecord() {
		return historyRecord;
	}
	public void setHistoryRecord(String historyRecord) {
		this.historyRecord = historyRecord;
	}
	@Column(name = "MAINTENANCE")
	public String getMaintenance() {
		return maintenance;
	}
	public void setMaintenance(String maintenance) {
		this.maintenance = maintenance;
	}
	@Column(name = "BYTHESYSTEM")
	public String getByTheSystem() {
		return byTheSystem;
	}
	public void setByTheSystem(String byTheSystem) {
		this.byTheSystem = byTheSystem;
	}
	@Column(name = "PROWARRANTY")
	public String getProWarranty() {
		return proWarranty;
	}
	public void setProWarranty(String proWarranty) {
		this.proWarranty = proWarranty;
	}
	@Column(name = "STABLEELE")
	public String getStableEle() {
		return stableEle;
	}
	public void setStableEle(String stableEle) {
		this.stableEle = stableEle;
	}
	@Column(name = "PROTECTIVEGEAR")
	public String getProtectiveGear() {
		return protectiveGear;
	}
	public void setProtectiveGear(String protectiveGear) {
		this.protectiveGear = protectiveGear;
	}
	@Column(name = "URGENCYPLAN")
	public String getUrgencyPlan() {
		return urgencyPlan;
	}
	public void setUrgencyPlan(String urgencyPlan) {
		this.urgencyPlan = urgencyPlan;
	}
	@Column(name = "REPAIRMAN")
	public String getRepairman() {
		return repairman;
	}
	public void setRepairman(String repairman) {
		this.repairman = repairman;
	}
	@Column(name = "SPAREPARTS")
	public String getSpareParts() {
		return spareParts;
	}
	public void setSpareParts(String spareParts) {
		this.spareParts = spareParts;
	}
	@Column(name = "REPAIRSYSTEM")
	public String getRepairSystem() {
		return repairSystem;
	}
	public void setRepairSystem(String repairSystem) {
		this.repairSystem = repairSystem;
	}
	@Column(name = "BOILERFLAG")
	public String getBoilerFlag() {
		return boilerFlag;
	}
	public void setBoilerFlag(String boilerFlag) {
		this.boilerFlag = boilerFlag;
	}
	@Column(name = "standardwater")
	public String getStandardWater() {
		return standardWater;
	}
	public void setStandardWater(String standardWater) {
		this.standardWater = standardWater;
	}
	@Column(name = "BOILERUSAGE")
	public String getBoilerUsage() {
		return boilerUsage;
	}
	public void setBoilerUsage(String boilerUsage) {
		this.boilerUsage = boilerUsage;
	}
	@Column(name = "OPERATORCARD")
	public String getOperatorCard() {
		return operatorCard;
	}
	public void setOperatorCard(String operatorCard) {
		this.operatorCard = operatorCard;
	}
	@Column(name = "CHECKBOILER")
	public String getCheckBoiler() {
		return checkBoiler;
	}
	public void setCheckBoiler(String checkBoiler) {
		this.checkBoiler = checkBoiler;
	}
	@Column(name = "BOILERDAMAGE")
	public String getBoilerDamage() {
		return boilerDamage;
	}
	public void setBoilerDamage(String boilerDamage) {
		this.boilerDamage = boilerDamage;
	}
	@Column(name = "EXTENSIONRISK")
	public Integer getExtensionRisk() {
		return extensionRisk;
	}
	public void setExtensionRisk(Integer extensionRisk) {
		this.extensionRisk = extensionRisk;
	}

}
