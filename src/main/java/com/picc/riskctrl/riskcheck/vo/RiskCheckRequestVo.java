package com.picc.riskctrl.riskcheck.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RiskCheckRequestVoo对象")
public class RiskCheckRequestVo {

	/**汛期检查查询*/
	private RiskCheckMainQueryVo riskCheckMainQueryVo;
	
	private RiskCheckMainVo riskCheckMainVo;
	
	private List<RiskCheckVentureVo> riskCheckVentureList;
	
	private List<RiskCheckImageVo> riskCheckImageList;

	private List<RiskCheckAssessVo> riskCheckAssessList;
	private String outSystemFlag;

	/**归属机构*/
	private List<Object> list;
	/** 页号 */
	private int pageNo;
	/** 每页的记录条数 */
	private int pageSize;
	/** 排序方式 */
	private String orderType;
	/** 排序字段 */
	private String orderColumn;
	
	public RiskCheckMainQueryVo getRiskCheckMainQueryVo() {
		return riskCheckMainQueryVo;
	}
	public void setRiskCheckMainQueryVo(RiskCheckMainQueryVo riskCheckMainQueryVo) {
		this.riskCheckMainQueryVo = riskCheckMainQueryVo;
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
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	public RiskCheckMainVo getRiskCheckMainVo() {
		return riskCheckMainVo;
	}
	public void setRiskCheckMainVo(RiskCheckMainVo riskCheckMainVo) {
		this.riskCheckMainVo = riskCheckMainVo;
	}
	public List<RiskCheckVentureVo> getRiskCheckVentureList() {
		return riskCheckVentureList;
	}
	public void setRiskCheckVentureList(List<RiskCheckVentureVo> riskCheckVentureList) {
		this.riskCheckVentureList = riskCheckVentureList;
	}
	public List<RiskCheckImageVo> getRiskCheckImageList() {
		return riskCheckImageList;
	}
	public void setRiskCheckImageList(List<RiskCheckImageVo> riskCheckImageList) {
		this.riskCheckImageList = riskCheckImageList;
	}
	public List<RiskCheckAssessVo> getRiskCheckAssessList() {
		return riskCheckAssessList;
	}
	public void setRiskCheckAssessList(List<RiskCheckAssessVo> riskCheckAssessList) {
		this.riskCheckAssessList = riskCheckAssessList;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderColumn() {
		return orderColumn;
	}
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
	public String getOutSystemFlag() {
		return outSystemFlag;
	}
	public void setOutSystemFlag(String outSystemFlag) {
		this.outSystemFlag = outSystemFlag;
	}
	
	
	
}
