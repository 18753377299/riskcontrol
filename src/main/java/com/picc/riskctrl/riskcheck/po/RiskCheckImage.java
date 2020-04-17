package com.picc.riskctrl.riskcheck.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RISKCHECK_Image")
public class RiskCheckImage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String riskCheckNo;
    private String imagType;
    private String imagTypeCName;
    private String imageId;
    private String imageName;
    private String imageUrl;
    private String remark;
    private Date insertTimeForHis;
    private Date operateTimeForHis;
	private RiskCheckMain riskCheckMain;
	
	/** 压缩照片路径 */
	private String thumUrl;
	/** 扩展字段一 */
	private String ext1;
	/** 扩展字段二 */
	private String ext2;
	/** 扩展字段三 */
	private String ext3;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "riskcheckno", nullable = false, length = 50)
    public String getRiskCheckNo() {
        return riskCheckNo;
    }

    public void setRiskCheckNo(String riskCheckNo) {
        this.riskCheckNo = riskCheckNo;
    }

    @Basic
    @Column(name = "imagtype", nullable = false, length = 10)
    public String getImagType() {
        return imagType;
    }

    public void setImagType(String imagType) {
        this.imagType = imagType;
    }

    @Basic
    @Column(name = "imagtypecname", nullable = false, length = 50)
    public String getImagTypeCName() {
        return imagTypeCName;
    }

    public void setImagTypeCName(String imagTypeCName) {
        this.imagTypeCName = imagTypeCName;
    }

    @Basic
    @Column(name = "imageid", nullable = true, length = 100)
    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "imagename", nullable = false, length = 100)
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Basic
    @Column(name = "imageurl", nullable = false, length = 1000)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "inserttimeforhis", insertable = false, updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    @Basic
    @Column(name = "operatetimeforhis", insertable = false)
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }
    
	@Column(name = "thumurl")
	public String getThumUrl() {
		return thumUrl;
	}

	public void setThumUrl(String thumUrl) {
		this.thumUrl = thumUrl;
	}
    
    
    public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

    @Column(name = "ext1")
	public String getExt1() {
		return ext1;
	}

	@Column(name = "ext2")
	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	@Column(name = "ext3")
	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "riskcheckno", nullable = false, insertable = false, updatable = false)
    public RiskCheckMain getRiskCheckMain() {
		return riskCheckMain;
	}

	public void setRiskCheckMain(RiskCheckMain riskCheckMain) {
		this.riskCheckMain = riskCheckMain;
	}
}
