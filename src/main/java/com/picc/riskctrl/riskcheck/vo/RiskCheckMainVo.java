package com.picc.riskctrl.riskcheck.vo;

import com.picc.riskctrl.common.po.RiskDcode;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RiskCheckMainVoo对象")
public class RiskCheckMainVo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/**巡检编号*/
	private String riskCheckNo;
	/**归属机构*/
	private String comCode;
	private String comCodeCName;
	/**巡检报告类型*/
	private String checkModel;
	/**被保险人类型*/
	private String insuredType;
	/**被保险人代码*/
	private String insuredCode;
	/**被保险人姓名/企业名称*/
	private String insuredName;
	private String addressProvince;
	public String getAddressProvince() {
		return addressProvince;
	}
	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}
	public String getAddressCity() {
		return addressCity;
	}
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	public String getAddressCounty() {
		return addressCounty;
	}
	public void setAddressCounty(String addressCounty) {
		this.addressCounty = addressCounty;
	}
	private String addressCity;
	private String addressCounty;
	/**保险财产地址(详细地址)*/
	private String addressDetail;
	/**地址编码*/
	private String addressCode;
	/**国民经济行业代码*/
	private String businessSource;
	private String businessSourceCName;
	private String checkerName;
	/**行业类型代码*/
	private String businessClass;
	/**被保险人/企业性质*/
	private String unitNature;
	/**操作人员代码*/
	private String operatorCode;
	/**操作人员集团统一工号*/
	private String operatorCode_uni;
	/**提交核保时间*/
	private Date undwrtSubmitDate;
	/**核保标志
	 * T：未处理
	 * 1：已完成
	 */
	private String underwriteFlag;
	/**核保通过时间*/
	private Date underwriteDate;
	/**核保人员代码*/
	private String underwriteCode;
	/**核保人员姓名*/
	private String underwriteName;
	/**申请查勘机构*/
	private String checkComCode;
	private String checkComCodeCName;
	/**巡检人*/
	private String checker;
	/**巡检日期*/
	private Date checkDate;
	/**制作日期*/
	private Date madeDate;
	/**操作系统
	 * 0:web端
	 * 1：安卓
	 * 2：苹果
	 */
	private String mobileFlag;
	/**突出风险*/
	private String highlightRisk;
	/**综合得分*/
	private BigDecimal score;
	/**程实例id*/
	private String executionId;
	/**打回原因*/
	private String repulsesugggest;
	/**地球坐标系经度*/
	private BigDecimal pointx_2000;
	/**地球坐标系纬度*/
	private BigDecimal pointy_2000;
	/**火星坐标系经度*/
	private BigDecimal pointx_02;
	/**火星坐标系纬度*/
	private BigDecimal pointy_02;
	
	
	private List<RiskCheckAssessVo> riskCheckAssessList = new ArrayList<RiskCheckAssessVo>(0);
	private List<RiskCheckImageVo> riskCheckImageList = new ArrayList<RiskCheckImageVo>(0);
	private List<RiskCheckVentureVo> riskCheckVentureList = new ArrayList<RiskCheckVentureVo>(0);
	private List<RiskDcode> riskDcodeList = new ArrayList<RiskDcode>(0);
	
	public String getRiskCheckNo() {
		return riskCheckNo;
	}
	public void setRiskCheckNo(String riskCheckNo) {
		this.riskCheckNo = riskCheckNo;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getInsuredType() {
		return insuredType;
	}
	public void setInsuredType(String insuredType) {
		this.insuredType = insuredType;
	}
	public String getInsuredCode() {
		return insuredCode;
	}
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	public String getBusinessSource() {
		return businessSource;
	}
	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}
	public String getBusinessClass() {
		return businessClass;
	}
	public void setBusinessClass(String businessClass) {
		this.businessClass = businessClass;
	}
	public String getUnitNature() {
		return unitNature;
	}
	public void setUnitNature(String unitNature) {
		this.unitNature = unitNature;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOperatorCode_uni() {
		return operatorCode_uni;
	}
	public void setOperatorCode_uni(String operatorCode_uni) {
		this.operatorCode_uni = operatorCode_uni;
	}
	public Date getUndwrtSubmitDate() {
		return undwrtSubmitDate;
	}
	public void setUndwrtSubmitDate(Date undwrtSubmitDate) {
		this.undwrtSubmitDate = undwrtSubmitDate;
	}
	public String getUnderwriteFlag() {
		return underwriteFlag;
	}
	public void setUnderwriteFlag(String underwriteFlag) {
		this.underwriteFlag = underwriteFlag;
	}
	public Date getUnderwriteDate() {
		return underwriteDate;
	}
	public void setUnderwriteDate(Date underwriteDate) {
		this.underwriteDate = underwriteDate;
	}
	public String getUnderwriteCode() {
		return underwriteCode;
	}
	public void setUnderwriteCode(String underwriteCode) {
		this.underwriteCode = underwriteCode;
	}
	public String getUnderwriteName() {
		return underwriteName;
	}
	public void setUnderwriteName(String underwriteName) {
		this.underwriteName = underwriteName;
	}

	public Date getMadeDate() {
		return madeDate;
	}
	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}
	public String getMobileFlag() {
		return mobileFlag;
	}
	public void setMobileFlag(String mobileFlag) {
		this.mobileFlag = mobileFlag;
	}
	public String getHighlightRisk() {
		return highlightRisk;
	}
	public void setHighlightRisk(String highlightRisk) {
		this.highlightRisk = highlightRisk;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getRepulsesugggest() {
		return repulsesugggest;
	}
	public void setRepulsesugggest(String repulsesugggest) {
		this.repulsesugggest = repulsesugggest;
	}
	
	public String getCheckModel() {
		return checkModel;
	}
	public void setCheckModel(String checkModel) {
		this.checkModel = checkModel;
	}
	public String getCheckComCode() {
		return checkComCode;
	}
	public void setCheckComCode(String checkComCode) {
		this.checkComCode = checkComCode;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public BigDecimal getPointx_2000() {
		return pointx_2000;
	}
	public void setPointx_2000(BigDecimal pointx_2000) {
		this.pointx_2000 = pointx_2000;
	}
	public BigDecimal getPointy_2000() {
		return pointy_2000;
	}
	public void setPointy_2000(BigDecimal pointy_2000) {
		this.pointy_2000 = pointy_2000;
	}
	public BigDecimal getPointx_02() {
		return pointx_02;
	}
	public void setPointx_02(BigDecimal pointx_02) {
		this.pointx_02 = pointx_02;
	}
	public BigDecimal getPointy_02() {
		return pointy_02;
	}
	public void setPointy_02(BigDecimal pointy_02) {
		this.pointy_02 = pointy_02;
	}
	public List<RiskCheckAssessVo> getRiskCheckAssessList() {
		return riskCheckAssessList;
	}
	public void setRiskCheckAssessList(List<RiskCheckAssessVo> riskCheckAssessList) {
		this.riskCheckAssessList = riskCheckAssessList;
	}
	public List<RiskCheckImageVo> getRiskCheckImageList() {
		return riskCheckImageList;
	}
	public void setRiskCheckImageList(List<RiskCheckImageVo> riskCheckImageList) {
		this.riskCheckImageList = riskCheckImageList;
	}
	public List<RiskCheckVentureVo> getRiskCheckVentureList() {
		return riskCheckVentureList;
	}
	public void setRiskCheckVentureList(List<RiskCheckVentureVo> riskCheckVentureList) {
		this.riskCheckVentureList = riskCheckVentureList;
	}
	public List<RiskDcode> getRiskDcodeList() {
		return riskDcodeList;
	}
	public void setRiskDcodeList(List<RiskDcode> riskDcodeList) {
		this.riskDcodeList = riskDcodeList;
	}
	public String getBusinessSourceCName() {
		return businessSourceCName;
	}
	public void setBusinessSourceCName(String businessSourceCName) {
		this.businessSourceCName = businessSourceCName;
	}
	public String getCheckerName() {
		return checkerName;
	}
	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}
	public String getComCodeCName() {
		return comCodeCName;
	}
	public void setComCodeCName(String comCodeCName) {
		this.comCodeCName = comCodeCName;
	}
	public String getCheckComCodeCName() {
		return checkComCodeCName;
	}
	public void setCheckComCodeCName(String checkComCodeCName) {
		this.checkComCodeCName = checkComCodeCName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
