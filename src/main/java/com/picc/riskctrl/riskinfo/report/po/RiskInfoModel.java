package com.picc.riskctrl.riskinfo.report.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @功能：专业风控模板表
 * @author liqiankun
 * @日期 20200109
 */
@Entity
@Table(name = "RISKINFO_MODEL")
public class RiskInfoModel implements Serializable{

	private static final long serialVersionUID = 1L;
	/**  序号 */
	private Integer id;
	/**  父节点 */
	private String parentId;
	/**  子节点 */
	private String chilId;
	/**  节点类型 1-非叶节点  0-叶节点 */
	private String nodeType;
	/**  菜单名称  */
	private String nodeCName;
	/**  风控模板路径 */
	private String url;
	/**  有效标志位 */
	private String validStatus;
	/**  同级显示顺序  */
	private String orderNo;
	/**  子风控模板菜单项  */
	private List<RiskInfoModel> childModel;
	/**  提供父级放置将最后一级图片url的路径  */
	private List<String> urls;
	
	@Id
	@Column(name = "ID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "PARENTID")
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Column(name = "CHILID")
	public String getChilId() {
		return chilId;
	}
	public void setChilId(String chilId) {
		this.chilId = chilId;
	}
	
	@Column(name = "NODETYPE")
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	@Column(name = "NODECNAME")
	public String getNodeCName() {
		return nodeCName;
	}
	public void setNodeCName(String nodeCName) {
		this.nodeCName = nodeCName;
	}
	
	@Column(name = "URL")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	
	@Column(name = "ORDERNO")
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Transient
	public List<RiskInfoModel> getChildModel() {
		return childModel;
	}
	public void setChildModel(List<RiskInfoModel> childModel) {
		this.childModel = childModel;
	}
	
	@Transient
	public List<String> getUrls() {
		return urls;
	}
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
	
}
