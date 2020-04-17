package com.picc.riskctrl.riskins.vo;

import java.util.Date;

public class RiskInsUnderwriteVo {
	/**节点名称*/
	private String nodeName;
	/**操作人员*/
	private String operateName;
	/**操作时间*/
	private Date operateTime;
	/**打回原因*/
	public String repulsesugggest;
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getRepulsesugggest() {
		return repulsesugggest;
	}
	public void setRepulsesugggest(String repulsesugggest) {
		this.repulsesugggest = repulsesugggest;
	}
	
}
