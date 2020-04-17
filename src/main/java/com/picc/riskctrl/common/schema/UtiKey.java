package com.picc.riskctrl.common.schema;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UTIKEY")
public class UtiKey implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	private UtiKeyId id;
	/** 字段名称 */
	private String fieldMeaning;
	/** 流水长度 */
	private Integer colLength;
	/** 单号头 */
	private String headId;
	/** 标志字段*/
	private String flag;
	
	/** 插入时间*/
	private Date insertTimeForHis;
	/** 更新时间 */
	private Date operateTimeForHis;

	public UtiKey() {
	}

	/**       
	 * FieldName
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "tableName", column = @Column(name = "TABLENAME")),
			@AttributeOverride(name = "fieldName", column = @Column(name = "FIELDNAME")) })
	public UtiKeyId getId() {
		return this.id;
	}

	public void setId(UtiKeyId id) {
		this.id = id;
	}

	/**       
	 * FieldMeaning
	 */

	@Column(name = "FIELDMEANING")
	public String getFieldMeaning() {
		return this.fieldMeaning;
	}

	public void setFieldMeaning(String fieldMeaning) {
		this.fieldMeaning = fieldMeaning;
	}

	/**       
	 * ColLength
	 */

	@Column(name = "COLLENGTH")
	public Integer getColLength() {
		return this.colLength;
	}

	public void setColLength(Integer colLength) {
		this.colLength = colLength;
	}

	/**       
	 * HeadId
	 */

	@Column(name = "HEADID")
	public String getHeadId() {
		return this.headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}

	/**       
	 * 标志字段
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**       
	 * 插入时间
	 */

	@Column(name = "inserttimeforhis", insertable = false, updatable = false)
	public Date getInsertTimeForHis() {
		return this.insertTimeForHis;
	}

	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}

	/**       
	 * 更新时间
	 */

	@Column(name = "operatetimeforhis", insertable = false)
	public Date getOperateTimeForHis() {
		return this.operateTimeForHis;
	}

	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
}
