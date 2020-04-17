package com.picc.riskctrl.common.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "RiskReport_saleCorrection")
public class RiskReportSaleCorrection  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**复合主键*/
	private RiskReportSaleCorrectionId id;
	/**标题*/
	private String title;
	/**内容*/
	private String content;
	/**风险类型*/
	private String riskType;
	/**有无反馈*/
	private String feedbackFlag;
	/**反馈情况确认*/
	private String feedbackConfirm;
	/**整改标记*/
	private String correctionFlag;
	/**整改前照片路径*/
	private String urlBefore;
	/**整改后照片路径*/
	private String urlBehind;
	/**提交人员*/
	private String operateCode;
	/**提交日期*/
	private Date submitDate;
	/**插入时间*/
	private Date insertTimeForHis;
	/**更新时间*/
	private Date operateTimeForHis;
	/** 风控销售员版信息表*/
	@JSONField(serialize = false)
	private RiskReportSaleImage riskReportSaleImage;
	
	/**整改前照片路径*/
	private String nameBefore;
	/**整改后照片路径*/
	private String nameBehind;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns ({  
	    @JoinColumn(name="ARCHIVESNO",referencedColumnName = "ARCHIVESNO",insertable = false, updatable = false),  
	    @JoinColumn(name="IMAGENAME",referencedColumnName = "IMAGENAME",insertable = false, updatable = false),  
	    @JoinColumn(name="IMAGETYPE",referencedColumnName = "IMAGETYPE",insertable = false, updatable = false)
	}) 
	public RiskReportSaleImage getRiskReportSaleImage() {
		return riskReportSaleImage;
	}
	public void setRiskReportSaleImage(RiskReportSaleImage riskReportSaleImage) {
		this.riskReportSaleImage = riskReportSaleImage;
	}
	
	/**       
	 *联合主键
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "archivesNo", column = @Column(name = "ARCHIVESNO")),
			@AttributeOverride(name = "imageName", column = @Column(name = "IMAGENAME")),
			@AttributeOverride(name = "imageType", column = @Column(name = "IMAGETYPE")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public RiskReportSaleCorrectionId getId() {
		return id;
	}
	public void setId(RiskReportSaleCorrectionId id) {
		this.id = id;
	}
	
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "FEEDBACKFLAG")
	public String getFeedbackFlag() {
		return feedbackFlag;
	}
	public void setFeedbackFlag(String feedbackFlag) {
		this.feedbackFlag = feedbackFlag;
	}
	
	@Column(name = "FEEDBACKCONFIRM")
	public String getFeedbackConfirm() {
		return feedbackConfirm;
	}
	public void setFeedbackConfirm(String feedbackConfirm) {
		this.feedbackConfirm = feedbackConfirm;
	}
	
	@Column(name = "CORRECTIONFLAG")
	public String getCorrectionFlag() {
		return correctionFlag;
	}
	public void setCorrectionFlag(String correctionFlag) {
		this.correctionFlag = correctionFlag;
	}
	
	@Transient
	public String getUrlBefore() {
		return urlBefore;
	}
	public void setUrlBefore(String urlBefore) {
		this.urlBefore = urlBefore;
	}
	
	@Transient
	public String getUrlBehind() {
		return urlBehind;
	}
	public void setUrlBehind(String urlBehind) {
		this.urlBehind = urlBehind;
	}
	
	@Column(name = "OPERATECODE")
	public String getOperateCode() {
		return operateCode;
	}
	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
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
	@Column(name = "NAMEBEFORE")
	public String getNameBefore() {
		return nameBefore;
	}
	public void setNameBefore(String nameBefore) {
		this.nameBefore = nameBefore;
	}
	@Column(name = "NAMEBEHIND")
	public String getNameBehind(){
		return nameBehind;
	}
	public void setNameBehind(String nameBehind) {
		this.nameBehind = nameBehind;
	}
	
	@Column(name = "RISKTYPE")
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	
	@Column(name = "SUBMITDATE")
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	
}