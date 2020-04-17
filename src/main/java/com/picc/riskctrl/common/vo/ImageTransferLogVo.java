package com.picc.riskctrl.common.vo;

import java.util.Date;

public class ImageTransferLogVo {
	
	/** 异常队列信息编号*/
	private String businessNo;
	/** 业务类型 */
	private String businessType;
	/** 业务状态  0:已处理   1：待处理*/
	private String businessStatus;
	/** 报文消息 */
	private String mqMessage;
	/** 异常信息 */
	private String errMessage;
	/** 上次推送时间 */
	private Date lastTransferTime;
	/** 插入时间 */
	private Date insertTimeForHis;
	/** 更新时间 */
	private Date operateTimeForHis;
	/** comCode */
	private String comCode;
	
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getBusinessStatus() {
		return businessStatus;
	}
	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public Date getLastTransferTime() {
		return lastTransferTime;
	}
	public void setLastTransferTime(Date lastTransferTime) {
		this.lastTransferTime = lastTransferTime;
	}
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}
	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}
	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
	public String getMqMessage() {
		return mqMessage;
	}
	public void setMqMessage(String mqMessage) {
		this.mqMessage = mqMessage;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	
}
