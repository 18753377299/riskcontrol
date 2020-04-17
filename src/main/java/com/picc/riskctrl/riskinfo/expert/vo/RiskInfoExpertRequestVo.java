package com.picc.riskctrl.riskinfo.expert.vo;

import io.swagger.annotations.ApiModel;

/**
 * @ClassName: RiskInfoExpertRequestVo
 * @Author: 张日炜
 * @Date: 2020-01-07 15:50
 **/
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
@ApiModel("RiskInfoExpertRequestVo对象")
public class RiskInfoExpertRequestVo {

    /**
     * 风控专家信息查询
     */
    private RiskExpertVo riskExpertVo;
    /**
     * 页号
     */
    private int pageNo;
    /**
     * 每页的记录条数
     */
    private int pageSize;
    /**
     * 排序方式
     */
    private String orderType;
    /**
     * 排序字段
     */
    private String orderColumn;

	public RiskExpertVo getRiskExpertVo() {
		return riskExpertVo;
	}
	public void setRiskExpertVo(RiskExpertVo riskExpertVo) {
		this.riskExpertVo = riskExpertVo;
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
