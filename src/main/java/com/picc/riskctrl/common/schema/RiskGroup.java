package com.picc.riskctrl.common.schema;

import javax.persistence.*;

@Entity
@Table(name = "RISKGROUP")
public class RiskGroup implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private RiskGroupId id;
	
	/** 标志字段 */
	private String flag;

	public RiskGroup() {
	}

	/**       
	 * 币别
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "groupNo", column = @Column(name = "GROUPNO")),
			@AttributeOverride(name = "subGroupNo", column = @Column(name = "SUBGROUPNO")) })
	public RiskGroupId getId() {
		return this.id;
	}

	public void setId(RiskGroupId id) {
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

}
