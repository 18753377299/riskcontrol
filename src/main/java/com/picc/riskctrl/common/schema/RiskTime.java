package com.picc.riskctrl.common.schema;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RISKTIME")
public class RiskTime implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5885433441499979752L;

	/** 序号 */
	private RiskTimeId id;

	/** 传入日期 */
	private Date inputTime;

	/** 操作员代码 */
	private String operatorCode ;
	
	/** 客户端ip地址 */
	private String clientIp;
	
	/** 标志字段 */
	private String flag;

	public RiskTime() {
	}

	/**
	 * 序号
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "certino", column = @Column(name = "CERTINO")),
			@AttributeOverride(name = "updatetype", column = @Column(name = "UPDATETYPE")),
			@AttributeOverride(name = "serialno", column = @Column(name = "SERIALNO")) })
	public RiskTimeId getId() {
		return this.id;
	}

	public void setId(RiskTimeId id) {
		this.id = id;
	}

	/**
	 * 传入日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "inputtime")
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	/**
	 * 操作员代码
	 */

	@Column(name = "operatorcode")
	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 标志字段
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/** 插入时间 */
	private Date insertTimeForHis;
	/** 更新时间 */
	private Date operateTimeForHis;

	@Column(name = "inserttimeforhis", insertable = false, updatable = false)
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}

	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}

	@Column(name = "operatetimeforhis", insertable = false)
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}

	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}

	@Column(name = "clientip")
	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
}
