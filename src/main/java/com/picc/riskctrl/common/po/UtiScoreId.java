package com.picc.riskctrl.common.po;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UtiScoreId implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**风控模板号*/
	private String riskModel;
	/**因子编号*/
	private String factorNo;
	/**灾因*/
	private String dangerType;
	/**因子取值*/
	private String factorValue;
	@Column(name = "RISKMODEL", nullable = false)
	public String getRiskModel() {
		return riskModel;
	}
	public void setRiskModel(String riskModel) {
		this.riskModel = riskModel;
	}
	@Column(name = "FACTORNO", nullable = false)
	public String getFactorNo() {
		return factorNo;
	}
	public void setFactorNo(String factorNo) {
		this.factorNo = factorNo;
	}
	
	@Column(name = "DANGERTYPE", nullable = false)
	public String getDangerType() {
		return dangerType;
	}
	public void setDangerType(String dangerType) {
		this.dangerType = dangerType;
	}
	
	@Column(name = "FACTORVALUE", nullable = false)
	public String getFactorValue() {
		return factorValue;
	}
	public void setFactorValue(String factorValue) {
		this.factorValue = factorValue;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dangerType == null) ? 0 : dangerType.hashCode());
		result = prime * result + ((factorNo == null) ? 0 : factorNo.hashCode());
		result = prime * result + ((factorValue == null) ? 0 : factorValue.hashCode());
		result = prime * result + ((riskModel == null) ? 0 : riskModel.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (getClass() != obj.getClass()) {
            return false;
        }
		UtiScoreId other = (UtiScoreId) obj;
		if (dangerType == null) {
			if (other.dangerType != null) {
                return false;
            }
		} else if (!dangerType.equals(other.dangerType)) {
            return false;
        }
		if (factorNo == null) {
			if (other.factorNo != null) {
                return false;
            }
		} else if (!factorNo.equals(other.factorNo)) {
            return false;
        }
		if (factorValue == null) {
			if (other.factorValue != null) {
                return false;
            }
		} else if (!factorValue.equals(other.factorValue)) {
            return false;
        }
		if (riskModel == null) {
			if (other.riskModel != null) {
                return false;
            }
		} else if (!riskModel.equals(other.riskModel)) {
            return false;
        }
		return true;
	}
	
	
	
	
}
