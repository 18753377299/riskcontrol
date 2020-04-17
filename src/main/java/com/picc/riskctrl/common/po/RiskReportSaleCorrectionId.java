package com.picc.riskctrl.common.po;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RiskReportSaleCorrectionId implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	/**照片档案号*/
	private String archivesNo;
	/**序号*/
	private Integer serialNo;
	/**照片名字*/
	private String imageName;
	/**照片类型*/
	private String imageType;

	
	@Column(name = "ARCHIVESNO", nullable = false)
	public String getArchivesNo() {
		return archivesNo;
	}
	public void setArchivesNo(String archivesNo) {
		this.archivesNo = archivesNo;
	}

	@Column(name = "SERIALNO", nullable = false)
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	
	@Column(name = "IMAGENAME", nullable = false)
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	@Column(name = "IMAGETYPE", nullable = false)
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((archivesNo == null) ? 0 : archivesNo.hashCode());
		result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
		result = prime * result + ((imageType == null) ? 0 : imageType.hashCode());
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
		RiskReportSaleCorrectionId other = (RiskReportSaleCorrectionId) obj;
		if (archivesNo == null) {
			if (other.archivesNo != null) {
                return false;
            }
		} else if (!archivesNo.equals(other.archivesNo)) {
            return false;
        }
		if (imageName == null) {
			if (other.imageName != null) {
                return false;
            }
		} else if (!imageName.equals(other.imageName)) {
            return false;
        }
		if (imageType == null) {
			if (other.imageType != null) {
                return false;
            }
		} else if (!imageType.equals(other.imageType)) {
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
