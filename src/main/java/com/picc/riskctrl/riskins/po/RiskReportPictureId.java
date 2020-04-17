package com.picc.riskctrl.riskins.po;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RiskReportPictureId implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	/**风控档案编号*/
	private String riskFileNo;
	/** 菜单路径 */
	private String childPath;
	/** 照片名称 */
	private String imageName;
	
	@Column(name = "RISKFILENO", nullable = false)
	public String getRiskFileNo() {
		return riskFileNo;
	}
	public void setRiskFileNo(String riskFileNo) {
		this.riskFileNo = riskFileNo;
	}

	@Column(name = "IMAGENAME")
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Column(name = "CHILDPATH")
	public String getChildPath() {
		return childPath;
	}

	public void setChildPath(String childPath) {
		this.childPath = childPath;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childPath == null) ? 0 : childPath.hashCode());
		result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
		result = prime * result + ((riskFileNo == null) ? 0 : riskFileNo.hashCode());
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
		RiskReportPictureId other = (RiskReportPictureId) obj;
		if (childPath == null) {
			if (other.childPath != null) {
                return false;
            }
		} else if (!childPath.equals(other.childPath)) {
            return false;
        }
		if (imageName == null) {
			if (other.imageName != null) {
                return false;
            }
		} else if (!imageName.equals(other.imageName)) {
            return false;
        }
		if (riskFileNo == null) {
			if (other.riskFileNo != null) {
                return false;
            }
		} else if (!riskFileNo.equals(other.riskFileNo)) {
            return false;
        }
		return true;
	}
	
}
