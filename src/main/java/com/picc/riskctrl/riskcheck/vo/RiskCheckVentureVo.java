package com.picc.riskctrl.riskcheck.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RiskCheckVentureVoo对象")
public class RiskCheckVentureVo{

	/**编号*/
	private String riskCheckNo;
//	/**企业是否有失信信息*/
//	private String compFaithFlag;
//	/**失信信息详情*/
//	private String compFaithMess;
//	/**不同类型失信信息标志位*/
//	private String fifShow;
	/**区域历史积水记录*/
	private String rainRecord;
	private String characteristics;
	public String getCharacteristics() {
		return characteristics;
	}
	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}
	/**与周边水体距离*/
	private String itemDistance;
	/**厂区地面相比周边地势高差*/
	private String comparedDegree;
	/**所处地形*/
	private String comparedTerrain;
	/**是否临近山边、山坡*/
	private String itemEnvironment;
	/**周围有无大型施工工程*/
	private String largeProjects;
	/**大型施工工程影响*/
	private String largeProImpact;
	/**设备水敏感性*/
	private String waterSensitivity;
	/**有无地下资产*/
	private String underAssetsFlag;
	/**历史水渍线高度*/
	private String historicWater;
	/**企业经营情况*/
	private String manSituation;
	/**厂区内有无投保资产位于低洼区域*/
	private String lowEquipment;
	/**厂房所有权性质*/
	private String ownership;
	/**建筑结构*/
	private String constructBuild;
	/**厂区内是否有露天堆放资产*/
	private String airStorageFlag;
	/**钢结构建筑年限*/
	private String buildYears;
	/**门窗是够完好*/
	private String doorFlag;
	/**仓库是否有顶峰错层结构*/
	private String staggeredFlag;
	/**屋顶排水方式*/
	private String drainageMethod;
	/**室内排水管道维护状况*/
	private String drainageBlock;
	/**排水沟/井疏通状况*/
	private String  dredgeCondition;
	/**排水沟（管）与河道是否相连*/
	private String connectedFlag;
	/**屋顶排水是否通畅*/
	private String unobstructedFlag;
	/**建筑物内部地面是否有水井盖或管渠*/
	private String haveCanal;
	/**存货水敏性*/
	private String cargoWaterSen;
	/**存放形式*/
	private String stoForm;
	/**存放位置*/
	private String stoLocation;
	/**厂区雨水排放形式*/
	private String emiForm;
	/**厂区防汛挡水物资*/
	private String conMaterials;
	/**是否设置紧急排水装备*/
	private String draEquipment;
	/**企业有无汛期抢险救灾应急预案*/
	private String conPlan;
	/**汛期是否实行24小时值班制度*/
	private String dutyFlag;
	/**汛期是否对重点区域进行监控*/
	private String monitorFlag;
	/**汛期是否有可行的紧急转移制度*/
	private String transferFlag;
	public String getRiskCheckNo() {
		return riskCheckNo;
	}
	public void setRiskCheckNo(String riskCheckNo) {
		this.riskCheckNo = riskCheckNo;
	}
//	public String getCompFaithFlag() {
//		return compFaithFlag;
//	}
//	public void setCompFaithFlag(String compFaithFlag) {
//		this.compFaithFlag = compFaithFlag;
//	}
//	public String getCompFaithMess() {
//		return compFaithMess;
//	}
//	public void setCompFaithMess(String compFaithMess) {
//		this.compFaithMess = compFaithMess;
//	}
	public String getItemDistance() {
		return itemDistance;
	}
	public void setItemDistance(String itemDistance) {
		this.itemDistance = itemDistance;
	}
	public String getComparedDegree() {
		return comparedDegree;
	}
	public void setComparedDegree(String comparedDegree) {
		this.comparedDegree = comparedDegree;
	}
	public String getComparedTerrain() {
		return comparedTerrain;
	}
	public void setComparedTerrain(String comparedTerrain) {
		this.comparedTerrain = comparedTerrain;
	}
	public String getItemEnvironment() {
		return itemEnvironment;
	}
	public void setItemEnvironment(String itemEnvironment) {
		this.itemEnvironment = itemEnvironment;
	}

	public String getWaterSensitivity() {
		return waterSensitivity;
	}
	public void setWaterSensitivity(String waterSensitivity) {
		this.waterSensitivity = waterSensitivity;
	}
	public String getUnderAssetsFlag() {
		return underAssetsFlag;
	}
	public void setUnderAssetsFlag(String underAssetsFlag) {
		this.underAssetsFlag = underAssetsFlag;
	}

	public String getLowEquipment() {
		return lowEquipment;
	}
	public void setLowEquipment(String lowEquipment) {
		this.lowEquipment = lowEquipment;
	}
	public String getOwnership() {
		return ownership;
	}
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}
	public String getConstructBuild() {
		return constructBuild;
	}
	public void setConstructBuild(String constructBuild) {
		this.constructBuild = constructBuild;
	}
	public String getAirStorageFlag() {
		return airStorageFlag;
	}
	public void setAirStorageFlag(String airStorageFlag) {
		this.airStorageFlag = airStorageFlag;
	}

	public String getStaggeredFlag() {
		return staggeredFlag;
	}
	public void setStaggeredFlag(String staggeredFlag) {
		this.staggeredFlag = staggeredFlag;
	}
	public String getDrainageMethod() {
		return drainageMethod;
	}
	public void setDrainageMethod(String drainageMethod) {
		this.drainageMethod = drainageMethod;
	}
	public String getDrainageBlock() {
		return drainageBlock;
	}
	public void setDrainageBlock(String drainageBlock) {
		this.drainageBlock = drainageBlock;
	}
	public String getDredgeCondition() {
		return dredgeCondition;
	}
	public void setDredgeCondition(String dredgeCondition) {
		this.dredgeCondition = dredgeCondition;
	}
	public String getConnectedFlag() {
		return connectedFlag;
	}
	public void setConnectedFlag(String connectedFlag) {
		this.connectedFlag = connectedFlag;
	}

	public String getCargoWaterSen() {
		return cargoWaterSen;
	}
	public void setCargoWaterSen(String cargoWaterSen) {
		this.cargoWaterSen = cargoWaterSen;
	}

	public String getDutyFlag() {
		return dutyFlag;
	}
	public void setDutyFlag(String dutyFlag) {
		this.dutyFlag = dutyFlag;
	}

	public String getTransferFlag() {
		return transferFlag;
	}
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	public String getRainRecord() {
		return rainRecord;
	}
	public void setRainRecord(String rainRecord) {
		this.rainRecord = rainRecord;
	}
	public String getLargeProjects() {
		return largeProjects;
	}
	public void setLargeProjects(String largeProjects) {
		this.largeProjects = largeProjects;
	}
	public String getLargeProImpact() {
		return largeProImpact;
	}
	public void setLargeProImpact(String largeProImpact) {
		this.largeProImpact = largeProImpact;
	}
	public String getHistoricWater() {
		return historicWater;
	}
	public void setHistoricWater(String historicWater) {
		this.historicWater = historicWater;
	}
	public String getManSituation() {
		return manSituation;
	}
	public void setManSituation(String manSituation) {
		this.manSituation = manSituation;
	}
	public String getBuildYears() {
		return buildYears;
	}
	public void setBuildYears(String buildYears) {
		this.buildYears = buildYears;
	}
	public String getDoorFlag() {
		return doorFlag;
	}
	public void setDoorFlag(String doorFlag) {
		this.doorFlag = doorFlag;
	}
	public String getUnobstructedFlag() {
		return unobstructedFlag;
	}
	public void setUnobstructedFlag(String unobstructedFlag) {
		this.unobstructedFlag = unobstructedFlag;
	}
	public String getHaveCanal() {
		return haveCanal;
	}
	public void setHaveCanal(String haveCanal) {
		this.haveCanal = haveCanal;
	}
	public String getStoForm() {
		return stoForm;
	}
	public void setStoForm(String stoForm) {
		this.stoForm = stoForm;
	}
	public String getStoLocation() {
		return stoLocation;
	}
	public void setStoLocation(String stoLocation) {
		this.stoLocation = stoLocation;
	}
	public String getEmiForm() {
		return emiForm;
	}
	public void setEmiForm(String emiForm) {
		this.emiForm = emiForm;
	}
	public String getConMaterials() {
		return conMaterials;
	}
	public void setConMaterials(String conMaterials) {
		this.conMaterials = conMaterials;
	}
	public String getDraEquipment() {
		return draEquipment;
	}
	public void setDraEquipment(String draEquipment) {
		this.draEquipment = draEquipment;
	}
	public String getConPlan() {
		return conPlan;
	}
	public void setConPlan(String conPlan) {
		this.conPlan = conPlan;
	}
	public String getMonitorFlag() {
		return monitorFlag;
	}
	public void setMonitorFlag(String monitorFlag) {
		this.monitorFlag = monitorFlag;
	}
//	public String getFifShow() {
//		return fifShow;
//	}
//	public void setFifShow(String fifShow) {
//		this.fifShow = fifShow;
//	}
	
	
	
}
