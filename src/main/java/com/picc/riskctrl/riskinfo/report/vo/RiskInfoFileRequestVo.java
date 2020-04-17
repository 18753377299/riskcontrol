package com.picc.riskctrl.riskinfo.report.vo;


public class RiskInfoFileRequestVo {
	/**优秀风控报告信息查询 */
	private RiskFileVo riskFileVo;
	/**优秀风控报告信息 */
	private RiskInfoFileVo riskInfoFileVo;
	/** 页号 */
	private int pageNo;
	/** 每页的记录条数 */
	private int pageSize;
	/** 排序方式 */
	private String orderType;
	/** 排序字段 */
	private String orderColumn;
	/** 用户代码 */
	private String userCode;	
	/** 归属机构 */
	private String comCode;	
	
	public RiskFileVo getRiskFileVo() {
		return riskFileVo;
	}
	public void setRiskFileVo(RiskFileVo riskFileVo) {
		this.riskFileVo = riskFileVo;
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
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public RiskInfoFileVo getRiskInfoFileVo() {
		return riskInfoFileVo;
	}
	public void setRiskInfoFileVo(RiskInfoFileVo riskInfoFileVo) {
		this.riskInfoFileVo = riskInfoFileVo;
	}
}
