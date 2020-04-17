package com.picc.riskctrl.riskins.po;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RiskReportAddressId implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	/**风控档案编号*/
	private String riskFileNo;
	/**顺序号
	 * 清分顺序增加，起始为1*/
	private Integer serialNo;
	
	@Column(name = "RISKFILENO", nullable = false)
	public String getRiskFileNo() {
		return riskFileNo;
	}
	public void setRiskFileNo(String riskFileNo) {
		this.riskFileNo = riskFileNo;
	}
	
	@Column(name = "SERIALNO", nullable = false)
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((riskFileNo == null) ? 0 : riskFileNo.hashCode());
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
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
		RiskReportAddressId other = (RiskReportAddressId) obj;
		if (riskFileNo == null) {
			if (other.riskFileNo != null) {
                return false;
            }
		} else if (!riskFileNo.equals(other.riskFileNo)) {
            return false;
        }
		if (serialNo == null) {
			if (other.serialNo != null) {
                return false;
            }
		} else if (!serialNo.equals(other.serialNo)) {
            return false;
        }
		return true;
	}
	
	
}
