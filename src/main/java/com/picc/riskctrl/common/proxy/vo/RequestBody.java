package com.picc.riskctrl.common.proxy.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_DEFAULT )
@Data
public class RequestBody {
    //系统代码
    private String systemCode;
    //有效状态
    private String validStatus;
    //分页
    private Integer pageNo;
    //每页数据
    private Integer pageSize;
    //险种分类代码
    private String classCode;
    //反转类型
    private String reverseType;
    //销售区域代码
    private String saleAreaCode;
    //机构代码
    private String areaCode;
    //产品代码
    private String riskCode;
    //标的代码
    private String itemCode;
    //限额/免赔代码
    private String limitCode;
    //限额/免赔级别
    private String limitLevel;
    //承保是否必输
    private String isRecorded;
    //额外的限额/免赔代码
    private String extraLimitCode;
    //短期费率类型
    private String rateType;
    //短期费率代码
    private String shortRateID;
    //新的短期
    private Integer newShortTerm;
    //旧的短期
    private Integer oldShortTerm;
    //上级标的代码
    private String upperItemCode;
    private String extraItemCode;
    //方案代码
    private String planCode;
    //方案维度
    private String dimensions;
    //方案维度类型
    private String dimensionRemark;
    //
    private String agentType;
    //请求类型
    private String REQUEST_TYPE;
    /* getPrpDRCKRateLower接口新增以下三个*/
    //条款代码
    private String clauseCode;
    //责任代码
    private String kindCode;
    //机构代码
    private String comCode;
    /** getPlanInfo接口新增对象*/
    //缴费计划配置表
    @JsonProperty("cpzx_Dplan")
    private PrpDplan prpDplan;
    /** getPrpDPlanLib接口新增对象*/
    private String planLibCode;
    // 查询有效报案 新增字段 start
    private String policyNo;
    private Date endDate;
    // 查询有效报案 新增字段 end

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getREQUEST_TYPE() {
        return REQUEST_TYPE;
    }

    public void setREQUEST_TYPE(String REQUEST_TYPE) {
        this.REQUEST_TYPE = REQUEST_TYPE;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getDimensionRemark() {
        return dimensionRemark;
    }

    public void setDimensionRemark(String dimensionRemark) {
        this.dimensionRemark = dimensionRemark;
    }



    public String getSaleAreaCode() {
        return saleAreaCode;
    }

    public void setSaleAreaCode(String saleAreaCode) {
        this.saleAreaCode = saleAreaCode;
    }

    public String getRiskCode() {
        return riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getReverseType() {
        return reverseType;
    }

    public void setReverseType(String reverseType) {
        this.reverseType = reverseType;
    }

    public String getClauseCode() {
        return clauseCode;
    }

    public void setClauseCode(String clauseCode) {
        this.clauseCode = clauseCode;
    }

    public String getKindCode() {
        return kindCode;
    }

    public void setKindCode(String kindCode) {
        this.kindCode = kindCode;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }


    public PrpDplan getPrpDplan() {
        return prpDplan;
    }

    public void setPrpDplan(PrpDplan prpDplan) {
        this.prpDplan = prpDplan;
    }


    public String getPlanLibCode() {
        return planLibCode;
    }

    public void setPlanLibCode(String planLibCode) {
        this.planLibCode = planLibCode;
    }

    public void setParams_RateLower(String systemCode,String riskCode,String clauseCode,String kindCode,String comCode,Integer pageNo,Integer pageSize){
        this.systemCode = systemCode;
        this.riskCode = riskCode;
        this.clauseCode = clauseCode;
        this.kindCode = kindCode;
        this.comCode = comCode;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
    public void setParams_PlanLib(String systemCode,String riskCode,String saleAreaCode, int pageNo, int pageSize){
        this.systemCode = systemCode;
        this.riskCode = riskCode;
        this.saleAreaCode = saleAreaCode;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
