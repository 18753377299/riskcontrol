package com.picc.riskctrl.common.po;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "riskdtemplate")
public class RiskDtemplate implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private RiskDtemplateId id;
	/** 业务类型 */
	private String codeType;
	/** 模版名称 */
	private String templateName;
	/** 模版有效标志 */
	private String validstatus;
	/** 插入时间 */
	private Date insertTimeForHis;
	/** 更新时间 */
	private Date operateTimeForHis;
	
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "classCode", column = @Column(name = "classcode", nullable = false)),
			@AttributeOverride(name = "riskCode", column = @Column(name = "riskcode", nullable = false)),
			@AttributeOverride(name = "codeCode", column = @Column(name = "codeCode", nullable = false)),
			@AttributeOverride(name = "templateCode", column = @Column(name = "templateCode", nullable = false))})
			
	public RiskDtemplateId getId() {
		return id;
	}
	public void setId(RiskDtemplateId id) {
		this.id = id;
	}
	
	@Column(name = "codeType")
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	@Column(name = "templateName")
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	@Column(name = "validstatus")
	public String getValidstatus() {
		return validstatus;
	}
	public void setValidstatus(String validstatus) {
		this.validstatus = validstatus;
	}
	
	@Column(name = "insertTimeForHis", insertable = false, updatable = false)
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
}
