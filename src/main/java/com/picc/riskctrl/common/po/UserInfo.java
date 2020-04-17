package com.picc.riskctrl.common.po;

import java.util.List;

/**
 * @用于存放险种相关内容的对象，如险种代码、机构代码、用户代码
 *
 */
public class UserInfo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 用户名称 */
	private String userName;
	/** 险种代码 */
	private String riskCode;
	/** 用户登录的机构代码 */
	private String comCode;
	/** 用户代码 */
	private String userCode;
	/** 产品对应的机构代码 */
	private String riskComCode;
	/** 险类代码 */
	private String classCode;
	/** 邮件 */
	private String email;
	/** 版本号 */
	private String version;
	/** 机构名称 */
	private String comCName;
	/** sessionId */
	private String sessionID;

	/** 总公司用户登录系统的机构 */
	private String loginComCode;
	/** 统一集团工号 */
	private String groupUserCode;
	/** 功能代码 */
	private List<String> taskCodeList;
	/** 岗位代码 */
	private List<String> postList;
	/** 是否移动端 */
	private Boolean isPC = true;
	/** 是否外部系统 */
	private Boolean isOuterSystem = true;

	public Boolean getIsPC() {
		return isPC;
	}

	public void setIsPC(Boolean isPC) {
		this.isPC = isPC;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRiskComCode() {
		return riskComCode;
	}

	public void setRiskComCode(String riskComCode) {
		this.riskComCode = riskComCode;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getLoginComCode() {
		return loginComCode;
	}

	public void setLoginComCode(String loginComCode) {
		this.loginComCode = loginComCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getComCName() {
		return comCName;
	}

	public void setComCName(String comCName) {
		this.comCName = comCName;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getGroupUserCode() {
		return groupUserCode;
	}

	public void setGroupUserCode(String groupUserCode) {
		this.groupUserCode = groupUserCode;
	}

	public List<String> getTaskCodeList() {
		return taskCodeList;
	}

	public void setTaskCodeList(List<String> taskCodeList) {
		this.taskCodeList = taskCodeList;
	}


	public List<String> getPostList() {
		return postList;
	}

	public void setPostList(List<String> postList) {
		this.postList = postList;
	}

	public Boolean getIsOuterSystem() {
		return isOuterSystem;
	}

	public void setIsOuterSystem(Boolean isOuterSystem) {
		this.isOuterSystem = isOuterSystem;
	}

}