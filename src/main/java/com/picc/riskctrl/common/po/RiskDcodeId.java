package com.picc.riskctrl.common.po;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RiskDcodeId implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	/** 代码类型 */
	@Column(name = "codetype", nullable = false)
	private String codeType;
	/** 行业代码/工程类别代码 */
	@Column(name = "codecode", nullable = false)
	private String codeCode;
	
	public String getCodeCode() {
		return codeCode;
	}
	public void setCodeCode(String codeCode) {
		this.codeCode = codeCode;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeCode == null) ? 0 : codeCode.hashCode());
		result = prime * result + ((codeType == null) ? 0 : codeType.hashCode());
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
		RiskDcodeId other = (RiskDcodeId) obj;
		if (codeCode == null) {
			if (other.codeCode != null) {
                return false;
            }
		} else if (!codeCode.equals(other.codeCode)) {
            return false;
        }
		if (codeType == null) {
			if (other.codeType != null) {
                return false;
            }
		} else if (!codeType.equals(other.codeType)) {
            return false;
        }
		return true;
	}

}
