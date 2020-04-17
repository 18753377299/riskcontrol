package com.picc.riskctrl.common.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;


@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "utihighlightrisk")
public class UtiHighlightRisk implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private UtiHighlightRiskId id;
	/**取值是否为突出风险*/
	private String riskFlag;
	/**突出风险描述*/
	private String riskReminder;
	/**有效标志位*/
	private String validStatus;
	private Date insertTimeForHis;
	private Date operateTimeForHis;
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "riskModel", column = @Column(name = "RISKMODEL")),
			@AttributeOverride(name = "fromTable", column = @Column(name = "FROMTABLE")),
			@AttributeOverride(name = "fromColumn", column = @Column(name = "FROMCOLUMN")),
			@AttributeOverride(name = "riskValue", column = @Column(name = "RISKVALUE"))})
	public UtiHighlightRiskId getId() {
		return id;
	}
	public void setId(UtiHighlightRiskId id) {
		this.id = id;
	}
	
	@Column(name = "riskflag")	
	public String getRiskFlag() {
		return riskFlag;
	}
	public void setRiskFlag(String riskFlag) {
		this.riskFlag = riskFlag;
	}
	@Column(name = "riskreminder")	
	public String getRiskReminder() {
		return riskReminder;
	}
	public void setRiskReminder(String riskReminder) {
		this.riskReminder = riskReminder;
	}
	@Column(name = "validstatus")	
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	
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
	
}
