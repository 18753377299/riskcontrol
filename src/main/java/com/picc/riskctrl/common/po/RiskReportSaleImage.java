package com.picc.riskctrl.common.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "RiskReport_saleImage")
public class RiskReportSaleImage  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**复合主键*/
	private RiskReportSaleImageId id;
	/**影像状态标志位*/
	private String stateFlag;
	/**打回原因*/
	private String repulseReason;
	/**整改意见*/
	private String riskSuggest;
	/**风险类型*/
	private String riskType;
	/**插入时间*/
	private Date insertTimeForHis;
	/**更新时间*/
	private Date operateTimeForHis;
	/**照片备注*/
	private String remark;
	/** 风控照片目录表 */
	@JSONField(serialize = false)
	private RiskReportSaleImaType riskReportSaleImaType;
	/** 风控销售员版整改意见*/
	private List<RiskReportSaleCorrection> riskReportSaleCorrectionList = new ArrayList<RiskReportSaleCorrection>(0);
	/**储存照片类别名称*/
	private String title;
	/**影像系统的图片地址*/
	private String urlName;
	/**整改后的照片*/
	private String urlAfter;
	
	private String modifyFlag;//是否完成整改的标志，1：已整改，0：未整改
	
	/** 原照片路径 */
	private String imageUrl;
	/** 压缩照片路径 */
	private String thumUrl;
	/**影像照片的pageId*/
	private String pageId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns ({  
	      @JoinColumn(name="ARCHIVESNO",referencedColumnName = "ARCHIVESNO",insertable = false, updatable = false),  
	      @JoinColumn(name="IMAGETYPE",referencedColumnName = "IMAGETYPE",insertable = false, updatable = false)
	  }) 
	public RiskReportSaleImaType getRiskReportSaleImaType() {
		return riskReportSaleImaType;
	}
	public void setRiskReportSaleImaType(RiskReportSaleImaType riskReportSaleImaType) {
		this.riskReportSaleImaType = riskReportSaleImaType;
	}

	/**       
	 *联合主键
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "archivesNo", column = @Column(name = "ARCHIVESNO")),
			@AttributeOverride(name = "imageType", column = @Column(name = "IMAGETYPE")),
			@AttributeOverride(name = "imageName", column = @Column(name = "IMAGENAME")) })
	public RiskReportSaleImageId getId() {
		return id;
	}
	public void setId(RiskReportSaleImageId id) {
		this.id = id;
	}
	
	@Column(name = "STATEFLAG")
	public String getStateFlag() {
		return stateFlag;
	}
	public void setStateFlag(String stateFlag) {
		this.stateFlag = stateFlag;
	}
	
	@Column(name = "REPULSEREASON")
	public String getRepulseReason() {
		return repulseReason;
	}

	public void setRepulseReason(String repulseReason) {
		this.repulseReason = repulseReason;
	}


	@Column(name = "RISKSUGGEST")
	public String getRiskSuggest() {
		return riskSuggest;
	}

	public void setRiskSuggest(String riskSuggest) {
		this.riskSuggest = riskSuggest;
	}

	@Column(name = "RISKTYPE")
	public String getRiskType() {
		return riskType;
	}
	
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	@Column(name = "INSERTTIMEFORHIS", insertable=false, updatable = false)
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}

	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	
	@Column(name = "OPERATETIMEFORHIS", insertable=false)
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}

	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
	
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "IMAGEURL")
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Column(name = "THUMURL")
	public String getThumUrl() {
		return thumUrl;
	}
	public void setThumUrl(String thumUrl) {
		this.thumUrl = thumUrl;
	}
	
	@Column(name = "PAGEID")
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	
	@OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.EAGER,mappedBy = "riskReportSaleImage")
	public List<RiskReportSaleCorrection> getRiskReportSaleCorrectionList() {
		return riskReportSaleCorrectionList;
	}
	public void setRiskReportSaleCorrectionList(List<RiskReportSaleCorrection> riskReportSaleCorrectionList) {
		this.riskReportSaleCorrectionList = riskReportSaleCorrectionList;
	}
	@Transient
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Transient
	public String getModifyFlag() {
		return modifyFlag;
	}
	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
	@Transient
	public String getUrlName() {
		return urlName;
	}
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	@Transient
	public String getUrlAfter() {
		return urlAfter;
	}
	public void setUrlAfter(String urlAfter) {
		this.urlAfter = urlAfter;
	}






}