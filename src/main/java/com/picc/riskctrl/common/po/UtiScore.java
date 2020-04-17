package com.picc.riskctrl.common.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "UTISCORE")
public class UtiScore implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private UtiScoreId id;
	/**分值*/
	private BigDecimal factorScore;
	/**有效标志位*/
	private String validStatus;
	private Date insertTimeForHis;
	private Date operateTimeForHis;
	
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "riskModel", column = @Column(name = "RISKMODEL")),
			@AttributeOverride(name = "factorNo", column = @Column(name = "FACTORNO")), 
			@AttributeOverride(name = "dangerType", column = @Column(name = "DANGERTYPE")),
			@AttributeOverride(name = "factorValue", column = @Column(name = "FACTORVALUE"))})
	public UtiScoreId getId() {
		return id;
	}
	public void setId(UtiScoreId id) {
		this.id = id;
	}
	
	@Column(name = "FACTORSCORE", nullable = false)
	public BigDecimal getFactorScore() {
		return factorScore;
	}
	public void setFactorScore(BigDecimal factorScore) {
		this.factorScore = factorScore;
	}
	
	@Column(name = "VALIDSTATUS")		
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	
	@Column(name = "INSERTTIMEFORHIS", insertable = false, updatable = false)
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}
	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	
	@Column(name = "OPERATETIMEFORHIS", insertable = false)
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}
	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
	
	
}

