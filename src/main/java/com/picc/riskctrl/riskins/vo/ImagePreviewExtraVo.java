package com.picc.riskctrl.riskins.vo;

public class ImagePreviewExtraVo {
	
	/** 图片路径 */
	private String imagePath;
	
	/** 图片名称*/
	private String imageName;
	
	/** 图片所在子路径*/
	private String childPath;
	
	/** 图片影像唯一ID*/
	private String imagePAGEID;
	
	/** 是否是影像*/
	private boolean imageFlag;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePAGEID() {
		return imagePAGEID;
	}

	public void setImagePAGEID(String imagePAGEID) {
		this.imagePAGEID = imagePAGEID;
	}

	public String getChildPath() {
		return childPath;
	}

	public void setChildPath(String childPath) {
		this.childPath = childPath;
	}

	public boolean isImageFlag() {
		return imageFlag;
	}

	public void setImageFlag(boolean imageFlag) {
		this.imageFlag = imageFlag;
	}
	
}

