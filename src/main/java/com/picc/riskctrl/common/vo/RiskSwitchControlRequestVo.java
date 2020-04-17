package com.picc.riskctrl.common.vo;

public class RiskSwitchControlRequestVo {
	
	private RiskSwitchControlVo riskSwitchControlVo;
	/**修改标志**/
	private String editModel;
	/** 页号 */
	private int pageNo;
	/** 每页的记录条数 */
	private int pageSize;
	public RiskSwitchControlVo getRiskSwitchControlVo() {
		return riskSwitchControlVo;
	}
	public void setRiskSwitchControlVo(RiskSwitchControlVo riskSwitchControlVo) {
		this.riskSwitchControlVo = riskSwitchControlVo;
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
	public String getEditModel() {
		return editModel;
	}
	public void setEditModel(String editModel) {
		this.editModel = editModel;
	}
	

	
}
