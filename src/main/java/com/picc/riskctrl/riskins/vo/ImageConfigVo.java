package com.picc.riskctrl.riskins.vo;

import java.util.List;

public class ImageConfigVo {
	
	private List<String> urls;
	
	private List<ImagePreviewConfigVo> configs;

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public List<ImagePreviewConfigVo> getConfigs() {
		return configs;
	}

	public void setConfigs(List<ImagePreviewConfigVo> configs) {
		this.configs = configs;
	}
	
}
