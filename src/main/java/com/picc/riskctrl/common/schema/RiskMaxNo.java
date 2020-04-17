package com.picc.riskctrl.common.schema;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RISKMAXNO")
public class RiskMaxNo {

	private static final long serialVersionUID = 1L;

	private RiskMaxNoId id;
	
	/** 标志字段 */
	private String flag;
	
	/** 插入时间 */
	private Date insertTimeForHis;
	
	/** 更新时间*/
	private Date operateTimeForHis;

	public RiskMaxNo() {
	}

	/**       
	 * 币别
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "groupNo", column = @Column(name = "GROUPNO")),
			@AttributeOverride(name = "tableName", column = @Column(name = "TABLENAME")),
			@AttributeOverride(name = "maxNo", column = @Column(name = "MAXNO")) })
	public RiskMaxNoId getId() {
		return this.id;
	}

	public void setId(RiskMaxNoId id) {
		this.id = id;
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
