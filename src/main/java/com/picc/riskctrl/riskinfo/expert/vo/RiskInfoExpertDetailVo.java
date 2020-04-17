package com.picc.riskctrl.riskinfo.expert.vo;

import com.picc.riskctrl.riskinfo.expert.po.RiskInfoDiscuss;
import com.picc.riskctrl.riskinfo.expert.po.RiskInfoExpert;

import java.util.List;

public class RiskInfoExpertDetailVo {
	/**专家评论信息 */
	private List<RiskInfoDiscuss> riskInfoDiscussList;
	/**专家浏览信息*/
	private RiskInfoExpert riskinfoExpert;
	/**数据总条数 */
	private long totalCount;
	/**数据总页数*/
	private long totalPage;
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

	public RiskInfoExpert getRiskinfoExpert() {
		return riskinfoExpert;
	}
	public void setRiskinfoExpert(RiskInfoExpert riskinfoExpert) {
		this.riskinfoExpert = riskinfoExpert;
	}

	public List<RiskInfoDiscuss> getRiskInfoDiscussList() {
		return riskInfoDiscussList;
	}
	public void setRiskInfoDiscussList(List<RiskInfoDiscuss> riskInfoDiscussList) {
		this.riskInfoDiscussList = riskInfoDiscussList;
	}
}
