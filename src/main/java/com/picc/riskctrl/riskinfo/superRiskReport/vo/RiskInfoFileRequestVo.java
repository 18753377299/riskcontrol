package com.picc.riskctrl.riskinfo.superRiskReport.vo;

public class RiskInfoFileRequestVo {
    /**
     * 优秀风控报告信杯查询
     */
    private RiskFileVo riskFileVo;
    /**
     * 优秀风控报告信杯
     */
    private RiskInfoFileVo riskInfoFileVo;
    /**
     * 页坷
     */
    private int pageNo;
    /**
     * 毝页的记录条数
     */
    private int pageSize;
    /**
     * 排庝方弝
     */
    private String orderType;
    /**
     * 排庝字段
     */
    private String orderColumn;


    public RiskFileVo getRiskFileVo() {
        return riskFileVo;
    }

    public void setRiskFileVo(RiskFileVo riskFileVo) {
        this.riskFileVo = riskFileVo;
    }

    public RiskInfoFileVo getRiskInfoFileVo() {
        return riskInfoFileVo;
    }

    public void setRiskInfoFileVo(RiskInfoFileVo riskInfoFileVo) {
        this.riskInfoFileVo = riskInfoFileVo;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

}
