package com.picc.riskctrl.common.schema;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RiskTimeId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1190162964752104227L;

	private String certiNo;

	private String updateType;

	private short serialNo;

	public RiskTimeId() {
	}

	@Column(name = "certino")
	public String getCertiNo() {
		return this.certiNo;
	}

	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	@Column(name = "updatetype")
	public String getUpdateType() {
		return this.updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	@Column(name = "serialno")
	public short getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(short serialNo) {
		this.serialNo = serialNo;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other)) {
            return true;
        }
		if ((other == null)) {
            return false;
        }
		if (!(other instanceof RiskTimeId)) {
            return false;
        }
		RiskTimeId castOther = (RiskTimeId) other;

		return ((this.getCertiNo() == castOther.getCertiNo()) || (this
				.getCertiNo() != null
				&& castOther.getCertiNo() != null && this
				.getCertiNo().equals(castOther.getCertiNo())))
				&& ((this.getUpdateType() == castOther.getUpdateType()) || (this
						.getUpdateType() != null
						&& castOther.getUpdateType() != null && this
						.getUpdateType().equals(castOther.getUpdateType())))
				&& (this.getSerialNo() == castOther.getSerialNo());
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCertiNo() == null ? 0 : this.getCertiNo()
						.hashCode());
		result = 37
				* result
				+ (getUpdateType() == null ? 0 : this.getUpdateType()
						.hashCode());
		result = 37 * result + this.getSerialNo();
		return result;
	}

}
