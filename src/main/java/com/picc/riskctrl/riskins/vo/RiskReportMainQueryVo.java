package com.picc.riskctrl.riskins.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zrw
 * @date 2020/01/13
 */
@Data
public class RiskReportMainQueryVo {

    /** 风险档案编号 */
    private String riskFileNo;
    /** 投保险类 */
    private String classCode;
    /** 投保险种 */
    private String riskCode;
    /** 归属机构 */
    private String comCode;
    /** 风控报告模板 */
    private String riskModel;
    /** 被保险人姓名/企业名称 */
    private String insuredName;
    /** 被保险人代码 **/
    private String insuredCode;
    /** 查勘人 */
    private String explorer;
    /** 查勘人集团统一工号 */
    private Date exploreDateBegin;
    /** 查勘日期止期 */
    private Date exploreDateEnd;
    /** 查勘类别 */
    private String exploreType;
    /** 核保状态 */
    private String[] underwriteFlag;
    /** 保险财产地址(详细) */
    private String addressDetail;
    /** 移动端查询标志位 */
    private Boolean mobFlag = false;
    /**
     * 业务类型 underwrite 审核 query 查询
     */
    private String businessType;

    /** 外系统标志位 */
    private String outerOperateFlag;
    /** 外系统操作起始时间 */
    private Date outerOperateDateBegin;
    /** 外系统操作终止时间 */
    private Date outerOperateDateEnd;

    /** 查勘人集团工号 */
    private String explorerCode;
    
    /**要删除的集合*/
	@SuppressWarnings("rawtypes")
	private List riskFileNoList;

	public String getRiskFileNo() {
		return riskFileNo;
	}

	public void setRiskFileNo(String riskFileNo) {
		this.riskFileNo = riskFileNo;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
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

	public String getRiskModel() {
		return riskModel;
	}

	public void setRiskModel(String riskModel) {
		this.riskModel = riskModel;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getInsuredCode() {
		return insuredCode;
	}

	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	public String getExplorer() {
		return explorer;
	}

	public void setExplorer(String explorer) {
		this.explorer = explorer;
	}

	public Date getExploreDateBegin() {
		return exploreDateBegin;
	}

	public void setExploreDateBegin(Date exploreDateBegin) {
		this.exploreDateBegin = exploreDateBegin;
	}

	public Date getExploreDateEnd() {
		return exploreDateEnd;
	}

	public void setExploreDateEnd(Date exploreDateEnd) {
		this.exploreDateEnd = exploreDateEnd;
	}

	public String getExploreType() {
		return exploreType;
	}

	public void setExploreType(String exploreType) {
		this.exploreType = exploreType;
	}

	public String[] getUnderwriteFlag() {
		return underwriteFlag;
	}

	public void setUnderwriteFlag(String[] underwriteFlag) {
		this.underwriteFlag = underwriteFlag;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public Boolean getMobFlag() {
		return mobFlag;
	}

	public void setMobFlag(Boolean mobFlag) {
		this.mobFlag = mobFlag;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getOuterOperateFlag() {
		return outerOperateFlag;
	}

	public void setOuterOperateFlag(String outerOperateFlag) {
		this.outerOperateFlag = outerOperateFlag;
	}

	public Date getOuterOperateDateBegin() {
		return outerOperateDateBegin;
	}

	public void setOuterOperateDateBegin(Date outerOperateDateBegin) {
		this.outerOperateDateBegin = outerOperateDateBegin;
	}

	public Date getOuterOperateDateEnd() {
		return outerOperateDateEnd;
	}

	public void setOuterOperateDateEnd(Date outerOperateDateEnd) {
		this.outerOperateDateEnd = outerOperateDateEnd;
	}

	public String getExplorerCode() {
		return explorerCode;
	}

	public void setExplorerCode(String explorerCode) {
		this.explorerCode = explorerCode;
	}

	public List getRiskFileNoList() {
		return riskFileNoList;
	}

	public void setRiskFileNoList(List riskFileNoList) {
		this.riskFileNoList = riskFileNoList;
	}
    
    

}
