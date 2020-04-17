package com.picc.riskctrl.riskinfo.claim.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RiskInfoClaimRequestVoo对象")
public class RiskInfoClaimRequestVo {
	/**典型案例信息查询 */
	private RiskClaimVo riskClaimVo;
	/**典型案例信息 */
	private RiskInfoClaimVo riskInfoClaimVo;
	/** 页号 */
	private int pageNo;
	/** 每页的记录条数 */
	private int pageSize;
	/** 排序方式 */
	private String orderType;
	/** 排序字段 */
	private String orderColumn;

	public RiskClaimVo getRiskClaimVo() {
		return riskClaimVo;
	}
	public void setRiskClaimVo(RiskClaimVo riskClaimVo) {
		this.riskClaimVo = riskClaimVo;
	}
	public RiskInfoClaimVo getRiskInfoClaimVo() {
		return riskInfoClaimVo;
	}
	public void setRiskInfoClaimVo(RiskInfoClaimVo riskInfoClaimVo) {
		this.riskInfoClaimVo = riskInfoClaimVo;
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

}
