package com.picc.riskctrl.common.vo;

import java.util.List;

public class RiskUtiWeightResponseVo {
    /**权重基本信息*/
    private RiskUtiWeightVo riskutiweightvo;
    /**数据信息 */
    private List dataList;
    /**数据总条数 */
    private long totalCount;
    /**数据总页数*/
    private long totalPage;
    /**错误信息*/
    private String message;
    /** 页号 */
    private int pageNo;
    /** 每页的记录条数 */
    private int pageSize;
    /**异常信息*/
    private String QCexception;
    public RiskUtiWeightVo getRiskutiweightvo() {
        return riskutiweightvo;
    }
    public void setRiskutiweightvo(RiskUtiWeightVo riskutiweightvo) {
        this.riskutiweightvo = riskutiweightvo;
    }
    public List getDataList() {
        return dataList;
    }
    public void setDataList(List dataList) {
        this.dataList = dataList;
    }
    public long getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
    public long getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
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
    public String getQCexception() {
        return QCexception;
    }
    public void setQCexception(String qCexception) {
        QCexception = qCexception;
    }

}
