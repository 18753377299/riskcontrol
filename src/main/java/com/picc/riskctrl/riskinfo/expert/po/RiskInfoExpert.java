package com.picc.riskctrl.riskinfo.expert.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @功能：专家表
 * @author liqiankun
 * @throws Exception 
 * @日期 20200109
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "RISKINFO_EXPERT")
@EntityListeners(AuditingEntityListener.class)
public class RiskInfoExpert  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**专家编号*/
	private Integer expertNo;
	/**姓名*/
	private String expertName;
	/**学历*/
	private String education;
	/**专业方向*/
	private String specialty;
	/**个人资质*/
	private String qualification;
	/**工作经验*/
	private String experience;
	/**查勘险种*/
	private String riskName;
	/**查勘行业*/
	private String profession;
	/**工作专长*/
	private String workSpecialty;
	/**保险行业相关风控项目经验*/
	private String projectExp;
	/**联系电话*/
	private String phoneNumber;
	/**邮箱*/
	private String mailAddr;
	/**所属机构*/
	private String ascName;
	/**所属机构代码*/
	private String ascNameCode;
	/**机构性质*/
	private String ascNature;
	/**推荐机构*/
	private String sender;
	/**照片路径*/
	private String url;
	/**评分*/
	private BigDecimal score;
	/**有效标志位*/
	private String validStatus;
	/**插入时间*/
	private Date insertTimeForHis;
	/**更新时间*/
	private Date operateTimeForHis;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EXPERTNO")
	public Integer getExpertNo() {
		return expertNo;
	}
	public void setExpertNo(Integer expertNo) {
		this.expertNo = expertNo;
	}
	
	@Column(name = "EXPERTNAME")
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	
	@Column(name = "EDUCATION")
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	
	@Column(name = "SPECIALTY")
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	@Column(name = "QUALIFICATION")
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	
	@Column(name = "EXPERIENCE")
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	
	@Column(name = "RISKNAME")
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	
	@Column(name = "PROFESSION")
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	@Column(name = "WORKSPECIALTY")
	public String getWorkSpecialty() {
		return workSpecialty;
	}
	public void setWorkSpecialty(String workSpecialty) {
		this.workSpecialty = workSpecialty;
	}
	
	@Column(name = "PROJECTEXP")
	public String getProjectExp() {
		return projectExp;
	}
	public void setProjectExp(String projectExp) {
		this.projectExp = projectExp;
	}
	
	@Column(name = "PHONENUMBER")
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Column(name = "MAILADDR")
	public String getMailAddr() {
		return mailAddr;
	}
	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}
	
	@Column(name = "ASCNAME")
	public String getAscName() {
		return ascName;
	}
	public void setAscName(String ascName) {
		this.ascName = ascName;
	}
	
	@Transient
	public String getAscNameCode() {
		return ascNameCode;
	}
	public void setAscNameCode(String ascNameCode) {
		this.ascNameCode = ascNameCode;
	}
	@Column(name = "ASCNATURE")
	public String getAscNature() {
		return ascNature;
	}
	public void setAscNature(String ascNature) {
		this.ascNature = ascNature;
	}
	
	@Column(name = "SENDER")
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	@Column(name = "URL")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "SCORE")
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	
	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	@CreatedDate
//	@Column(name = "INSERTTIMEFORHIS",insertable=false,updatable=false)
	@Column(name = "INSERTTIMEFORHIS",updatable=false)
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}
	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	@LastModifiedDate
//	@Column(name = "OPERATETIMEFORHIS",insertable=false)
	@Column(name = "OPERATETIMEFORHIS")
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}
	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
	
	
}
