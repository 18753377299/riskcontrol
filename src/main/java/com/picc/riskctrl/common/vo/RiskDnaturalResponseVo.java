package com.picc.riskctrl.common.vo;

import java.util.List;

@SuppressWarnings("rawtypes")
public class RiskDnaturalResponseVo {
	/**数据信息 */
	private List dataList;
	/**一条环境信息数据 */
	RiskDnaturalVo riskDnaturalVo;
	/**数据总条数 */
	private long totalCount;
	/**数据总页数*/
	private long totalPage;
	/**错误信息*/
	private String message;
	/**异常信息*/
	private String QCexception;

	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	
	public RiskDnaturalVo getRiskDnaturalVo() {
		return riskDnaturalVo;
	}
	public void setRiskDnaturalVo(RiskDnaturalVo riskDnaturalVo) {
		this.riskDnaturalVo = riskDnaturalVo;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getQCexception() {
		return QCexception;
	}
	public void setQCexception(String qCexception) {
		QCexception = qCexception;
	}

}
