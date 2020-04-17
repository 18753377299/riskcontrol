package com.picc.riskctrl.riskcheck.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RiskCheckImageVoo对象")
public class RiskCheckImageVo {

	/**顺序号*/
	private Integer id;
	/**业务号*/
	private String riskCheckNo;
	/**照片类别代码*/
	private String imagType;
	/**照片类别名称*/
	private String imagTypeCName;
	/**图片ID(影像系统)*/
	private String imageId;
	/**图片名称*/
	private String imageName;
	/**图片地址*/
	private String imageUrl;
	/**备注信息*/
	private String remark;
	
	/** 压缩照片路径 */
	private String thumUrl;
	/** 扩展字段一 */
	private String ext1;
	/** 扩展字段二 */
	private String ext2;
	/** 扩展字段三 */
	private String ext3;
	
	
	public String getThumUrl() {
		return thumUrl;
	}
	public void setThumUrl(String thumUrl) {
		this.thumUrl = thumUrl;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRiskCheckNo() {
		return riskCheckNo;
	}
	public void setRiskCheckNo(String riskCheckNo) {
		this.riskCheckNo = riskCheckNo;
	}
	public String getImagType() {
		return imagType;
	}
	public void setImagType(String imagType) {
		this.imagType = imagType;
	}
	public String getImagTypeCName() {
		return imagTypeCName;
	}
	public void setImagTypeCName(String imagTypeCName) {
		this.imagTypeCName = imagTypeCName;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
