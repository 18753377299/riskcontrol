package com.picc.riskctrl.common.schema;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RiskMaxNoId implements Serializable{

private static final long serialVersionUID = 1L;
	
	/** 编组 */
	private String groupNo;
	/** 表名 */
	private String tableName;
	/** 编号*/
	private String maxNo;

	public RiskMaxNoId() {
	}

	@Column(name = "GROUPNO")
	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	@Column(name = "TABLENAME")
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "MAXNO")
	public String getMaxNo() {
		return this.maxNo;
	}

	public void setMaxNo(String maxNo) {
		this.maxNo = maxNo;
	}

	@Override
    public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)){
			return false;
		}
		if (!(other instanceof RiskMaxNoId)) {
			return false;
		}
		RiskMaxNoId castOther = (RiskMaxNoId) other;
		return ((this.getGroupNo() == castOther.getGroupNo()) || (this
				.getGroupNo() != null
				&& castOther.getGroupNo() != null && this.getGroupNo()
				.equals(castOther.getGroupNo())))
				&& ((this.getTableName() == castOther.getTableName()) || (this
						.getTableName() != null
						&& castOther.getTableName() != null && this
						.getTableName().equals(castOther.getTableName())))
				&& ((this.getMaxNo() == castOther.getMaxNo()) || (this
						.getMaxNo() != null
						&& castOther.getMaxNo() != null && this
						.getMaxNo().equals(castOther.getMaxNo())));
	}

	@Override
    public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getGroupNo() == null ? 0 : this.getGroupNo()
						.hashCode());
		result = 37 * result
				+ (getTableName() == null ? 0 : this.getTableName().hashCode());
		result = 37 * result
				+ (getMaxNo() == null ? 0 : this.getMaxNo().hashCode());
		return result;
	}
}
