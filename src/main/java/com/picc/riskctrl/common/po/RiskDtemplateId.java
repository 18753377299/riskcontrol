package com.picc.riskctrl.common.po;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RiskDtemplateId implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	/** 险类代码 */
	private String classCode;
	/** 产品代码 */
	private String riskCode;
	/** 行业代码/工程类别代码 */
	private String codeCode;
	/** 模版代码 */
	private String templateCode;
	
	@Column(name = "classCode", nullable = false)
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	
	@Column(name = "riskCode", nullable = false)
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	
	@Column(name = "codeCode", nullable = false)
	public String getCodeCode() {
		return codeCode;
	}
	public void setCodeCode(String codeCode) {
		this.codeCode = codeCode;
	}
	@Column(name = "templateCode", nullable = false)
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classCode == null) ? 0 : classCode.hashCode());
		result = prime * result + ((codeCode == null) ? 0 : codeCode.hashCode());
		result = prime * result + ((riskCode == null) ? 0 : riskCode.hashCode());
		result = prime * result + ((templateCode == null) ? 0 : templateCode.hashCode());
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
		RiskDtemplateId other = (RiskDtemplateId) obj;
		if (classCode == null) {
			if (other.classCode != null) {
                return false;
            }
		} else if (!classCode.equals(other.classCode)) {
            return false;
        }
		if (codeCode == null) {
			if (other.codeCode != null) {
                return false;
            }
		} else if (!codeCode.equals(other.codeCode)) {
            return false;
        }
		if (riskCode == null) {
			if (other.riskCode != null) {
                return false;
            }
		} else if (!riskCode.equals(other.riskCode)) {
            return false;
        }
		if (templateCode == null) {
			if (other.templateCode != null) {
                return false;
            }
		} else if (!templateCode.equals(other.templateCode)) {
            return false;
        }
		return true;
	}
	
}
