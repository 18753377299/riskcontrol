package com.picc.riskctrl.common.vo;

import java.util.List;

public class RiskDnaturalRequestVo {
	
	/**风控报告查询*/
	private RiskDnaturalVo riskDnaturalVo;
	/**基本信息查询*/
	private  RiskBasicInfoTendVo riskBasicInfoTendVo;
	
	private RiskBasicInfoProfession riskBasicInfoProfession;
	
	private RiskBasicInfoReason riskBasicInfoReason;
	
	private RiskBasicInfoSender riskBasicInfoSender;
	
	private RiskDcodeVo riskDcodeVo;
	/** 页号 */
	private int pageNo;
	/** 每页的记录条数 */
	private int pageSize;
	/**环境信息序号*/
	private String serialNo;
	/**环境信息序号集合*/
	private List serialNoList;
	/**修改的标志*/
	private String modeifyFlag;
	/**新增或修改标志位*/
	private String editModel;
	
	public RiskDnaturalVo getRiskDnaturalVo() {
		return riskDnaturalVo;
	}
	public void setRiskDnaturalVo(RiskDnaturalVo riskDnaturalVo) {
		this.riskDnaturalVo = riskDnaturalVo;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public List getSerialNoList() {
		return serialNoList;
	}
	public void setSerialNoList(List serialNoList) {
		this.serialNoList = serialNoList;
	}
	public RiskBasicInfoTendVo getRiskBasicInfoTendVo() {
		return riskBasicInfoTendVo;
	}
	public void setRiskBasicInfoTendVo(RiskBasicInfoTendVo riskBasicInfoTendVo) {
		this.riskBasicInfoTendVo = riskBasicInfoTendVo;
	}
	public RiskBasicInfoProfession getRiskBasicInfoProfession() {
		return riskBasicInfoProfession;
	}
	public void setRiskBasicInfoProfession(
			RiskBasicInfoProfession riskBasicInfoProfession) {
		this.riskBasicInfoProfession = riskBasicInfoProfession;
	}
	public RiskBasicInfoReason getRiskBasicInfoReason() {
		return riskBasicInfoReason;
	}
	public void setRiskBasicInfoReason(RiskBasicInfoReason riskBasicInfoReason) {
		this.riskBasicInfoReason = riskBasicInfoReason;
	}
	public RiskBasicInfoSender getRiskBasicInfoSender() {
		return riskBasicInfoSender;
	}
	public void setRiskBasicInfoSender(RiskBasicInfoSender riskBasicInfoSender) {
		this.riskBasicInfoSender = riskBasicInfoSender;
	}
	public String getModeifyFlag() {
		return modeifyFlag;
	}
	public void setModeifyFlag(String modeifyFlag) {
		this.modeifyFlag = modeifyFlag;
	}
	public RiskDcodeVo getRiskDcodeVo() {
		return riskDcodeVo;
	}
	public void setRiskDcodeVo(RiskDcodeVo riskDcodeVo) {
		this.riskDcodeVo = riskDcodeVo;
	}
	public String getEditModel() {
		return editModel;
	}
	public void setEditModel(String editModel) {
		this.editModel = editModel;
	}
	
	
	
}
