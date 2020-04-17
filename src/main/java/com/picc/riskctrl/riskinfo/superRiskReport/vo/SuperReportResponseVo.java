package com.picc.riskctrl.riskinfo.superRiskReport.vo;

import java.util.List;

public class SuperReportResponseVo {
    /**数据信息 */
    private List dataList;
    /**数据总条数 */
    private long totalCount;
    /**数据总页数*/
    private long totalPage;
    /**已选条件*/
    private List conditionList;
    /**返回信息*/
    private String status;
    /**返回信息*/
    private String message;
    /**异常信息*/
    private String qCexception;

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

    public List getConditionList() {
        return conditionList;
    }

    public void setConditionList(List conditionList) {
        this.conditionList = conditionList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQCexception() {
        return qCexception;
    }

    public void setQCexception(String qCexception) {
        this.qCexception = qCexception;
    }
}
