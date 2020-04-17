package com.picc.riskctrl.riskins.vo;

/** 图片信息 */
public class ImagePreviewConfigVo {

	/** 图片名称 */
	private String caption;

	/** 图片大小 */
	private long size;

	/** 图片宽度 */
	private String width = "120px";

	/** 图片下载路径 */
	private String downloadUrl;

	/** 删除url */
	private String url;

	/** 上传图片预览地址 */
	private String localPath;
	
	/** 备注 */
	private String remark;

	/** 某些数据 */
	private int key;

	private ImagePreviewExtraVo extra;

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public ImagePreviewExtraVo getExtra() {
		return extra;
	}

	public void setExtra(ImagePreviewExtraVo extra) {
		this.extra = extra;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

