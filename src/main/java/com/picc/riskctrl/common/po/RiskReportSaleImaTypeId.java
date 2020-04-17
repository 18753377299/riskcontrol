package com.picc.riskctrl.common.po;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RiskReportSaleImaTypeId implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	/**照片档案号*/
	private String archivesNo;
	/**所属类别*/
	private String imageType;
	
	@Column(name = "ARCHIVESNO", nullable = false)
	public String getArchivesNo() {
		return archivesNo;
	}
	public void setArchivesNo(String archivesNo) {
		this.archivesNo = archivesNo;
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
		result = prime * result + ((imageType == null) ? 0 : imageType.hashCode());
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
		RiskReportSaleImaTypeId other = (RiskReportSaleImaTypeId) obj;
		if (archivesNo == null) {
			if (other.archivesNo != null) {
                return false;
            }
		} else if (!archivesNo.equals(other.archivesNo)) {
            return false;
        }
		if (imageType == null) {
			if (other.imageType != null) {
                return false;
            }
		} else if (!imageType.equals(other.imageType)) {
            return false;
        }
		return true;
	}
	
	
}
