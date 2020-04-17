package com.picc.riskctrl.common.schema;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RiskGroupId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 合编组*/
	private String groupNo;
	/** 分编组*/
	private String subGroupNo;


	public RiskGroupId() {
	}

	@Column(name = "GROUPNO")
	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	@Column(name = "SUBGROUPNO")
	public String getSubGroupNo() {
		return this.subGroupNo;
	}

	public void setSubGroupNo(String subGroupNo) {
		this.subGroupNo = subGroupNo;
	}

	@Override
    public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof RiskGroupId)) {
			return false;
		}
		RiskGroupId castOther = (RiskGroupId) other;

		return ((this.getGroupNo() == castOther.getGroupNo()) || (this
				.getGroupNo() != null
				&& castOther.getGroupNo() != null && this.getGroupNo()
				.equals(castOther.getGroupNo())))
				&& ((this.getSubGroupNo() == castOther.getSubGroupNo()) || (this
						.getSubGroupNo() != null
						&& castOther.getSubGroupNo() != null && this
						.getSubGroupNo().equals(castOther.getSubGroupNo())));
	}

	@Override
    public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getGroupNo() == null ? 0 : this.getGroupNo()
						.hashCode());
		result = 37 * result
				+ (getSubGroupNo() == null ? 0 : this.getSubGroupNo().hashCode());
		return result;
	}

}
