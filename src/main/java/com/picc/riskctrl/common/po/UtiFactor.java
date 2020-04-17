package com.picc.riskctrl.common.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "utifactor")
public class UtiFactor implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UtiFactorId id;
	/**因子类型*/
	private String factorType;
	/**存储表名*/
	private String fromTable;
	/**存储字段名*/
	private String fromColumn;
	/**字段类型*/
	private String columnType;
	/**是否允许为空*/
	private String nullable;
	/**字段中文名*/
	private String columnCName;
	/**最值类型*/
	private String factorExtType;
	/**最值*/
	private BigDecimal factorext;
	/**有效标志位*/
	private String validStatus;
	private Date insertTimeForHis;
	private Date operateTimeForHis;
	
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "riskModel", column = @Column(name = "RISKMODEL")),
			@AttributeOverride(name = "factorNo", column = @Column(name = "FACTORNO")),
			@AttributeOverride(name = "dangerType", column = @Column(name = "DANGERTYPE"))})
	public UtiFactorId getId() {
		return id;
	}
	public void setId(UtiFactorId id) {
		this.id = id;
	}
	
	@Column(name = "factortype", nullable = false)
	public String getFactorType() {
		return factorType;
	}

	public void setFactorType(String factorType) {
		this.factorType = factorType;
	}
	
	@Column(name = "fromtable")
	public String getFromTable() {
		return fromTable;
	}
	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}
	
	@Column(name = "fromcolumn")	
	public String getFromColumn() {
		return fromColumn;
	}
	public void setFromColumn(String fromColumn) {
		this.fromColumn = fromColumn;
	}
	
	@Column(name = "columntype")		
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}	
	
	@Column(name = "nullable")
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	
	
	@Column(name = "columncname")
	public String getColumnCName() {
		return columnCName;
	}
	public void setColumnCName(String columnCName) {
		this.columnCName = columnCName;
	}
	@Column(name = "factorexttype")	
	public String getFactorExtType() {
		return factorExtType;
	}
	public void setFactorExtType(String factorExtType) {
		this.factorExtType = factorExtType;
	}
	
	@Column(name = "factorext")	
	public BigDecimal getFactorExt() {
		return factorext;
	}
	public void setFactorExt(BigDecimal factorExt) {
		this.factorext = factorExt;
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
