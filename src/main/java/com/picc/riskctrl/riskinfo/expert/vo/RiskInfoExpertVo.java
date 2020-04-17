package com.picc.riskctrl.riskinfo.expert.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RiskInfoExpertVo对象")
public class RiskInfoExpertVo  implements java.io.Serializable {
	
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
	/**评论性信息*/
	private   String discuss;
	/**有效标志位*/
	private String validStatus;
	/**插入时间*/
	private Date insertTimeForHis;
	/**更新时间*/
	private Date operateTimeForHis;
	
	public Integer getExpertNo() {
		return expertNo;
	}
	public void setExpertNo(Integer expertNo) {
		this.expertNo = expertNo;
	}
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getWorkSpecialty() {
		return workSpecialty;
	}
	public void setWorkSpecialty(String workSpecialty) {
		this.workSpecialty = workSpecialty;
	}
	public String getProjectExp() {
		return projectExp;
	}
	public void setProjectExp(String projectExp) {
		this.projectExp = projectExp;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMailAddr() {
		return mailAddr;
	}
	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}
	public String getAscName() {
		return ascName;
	}
	public void setAscName(String ascName) {
		this.ascName = ascName;
	}
	public String getAscNameCode() {
		return ascNameCode;
	}
	public void setAscNameCode(String ascNameCode) {
		this.ascNameCode = ascNameCode;
	}
	public String getAscNature() {
		return ascNature;
	}
	public void setAscNature(String ascNature) {
		this.ascNature = ascNature;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}
	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}
	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
	public String getDiscuss() {
		return discuss;
	}
	public void setDiscuss(String discuss) {
		this.discuss = discuss;
	}
	
	
	
	
}
