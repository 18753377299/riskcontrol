package com.picc.riskctrl.riskinfo.expert.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: RiskExpertVo
 * @Author: 张日炜
 * @Date: 2020-01-07 15:52
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RiskExpertVo对象")
public class RiskExpertVo {
    //专家姓名
    private String expertName;
    //行业
    private String professions;
    //最高学历
    private String educations;
    //机构性质
    private String ascNatures;
    //推荐公司
    private String senders;
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	public String getProfessions() {
		return professions;
	}
	public void setProfessions(String professions) {
		this.professions = professions;
	}
	public String getEducations() {
		return educations;
	}
	public void setEducations(String educations) {
		this.educations = educations;
	}
	public String getAscNatures() {
		return ascNatures;
	}
	public void setAscNatures(String ascNatures) {
		this.ascNatures = ascNatures;
	}
	public String getSenders() {
		return senders;
	}
	public void setSenders(String senders) {
		this.senders = senders;
	}
    
    
}
