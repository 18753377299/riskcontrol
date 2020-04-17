package com.picc.riskctrl.common.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "UTIFORMULA")
public class UtiFormula implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private UtiFormulaId id;
	/**计算公式*/
	private String content;
	/**集合类型*/
	private String listType;
	/**有效标志位*/
	private String validStatus;
	private Date insertTimeForHis;
	private Date operateTimeForHis;
	
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "riskModel", column = @Column(name = "RISKMODEL")),
			@AttributeOverride(name = "factorNo", column = @Column(name = "FACTORNO")), 
			@AttributeOverride(name = "dangerType", column = @Column(name = "DANGERTYPE"))})
	public UtiFormulaId getId() {
		return id;
	}
	public void setId(UtiFormulaId id) {
		this.id = id;
	}
	
	@Column(name = "CONTENT", nullable = false)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "LISTTYPE", nullable = false)	
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
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
