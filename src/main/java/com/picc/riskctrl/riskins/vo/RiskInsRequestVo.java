package com.picc.riskctrl.riskins.vo;

import lombok.Data;

import java.util.List;

/**
 * @author zrw
 * @date 2020/01/13
 */
@Data
public class RiskInsRequestVo {

    /** 风控报告查询 */
    private RiskReportMainQueryVo riskReportMainVo;
    /** 归属机构 */
    private List<Object> list;
    /** 风控报告销售员版基本信息 */
    private RiskReportSaleMainQueryVo riskReportSaleMainVo;
    /** 页号 */
    private int pageNo;
    /** 每页的记录条数 */
    private int pageSize;
	public RiskReportMainQueryVo getRiskReportMainVo() {
		return riskReportMainVo;
	}
	public void setRiskReportMainVo(RiskReportMainQueryVo riskReportMainVo) {
		this.riskReportMainVo = riskReportMainVo;
	}
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	public RiskReportSaleMainQueryVo getRiskReportSaleMainVo() {
		return riskReportSaleMainVo;
	}
	public void setRiskReportSaleMainVo(RiskReportSaleMainQueryVo riskReportSaleMainVo) {
		this.riskReportSaleMainVo = riskReportSaleMainVo;
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

}
