package com.picc.riskctrl.riskins.vo;

import java.util.List;

@SuppressWarnings("rawtypes")
public class ImageTransferLogResponseVo {
    /**
     * 数据信息
     */
    private List dataList;
    /**
     * 数据信息
     */
    private List unauditedDataList;
    /**
     * 数据信息
     */
    private List feedbackDataList;
    /**
     * 数据总条数
     */
    private long totalCount;
    /**
     * 数据总页数
     */
    private long totalPage;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 状态标志
     */
    private long status;
    /**
     * 当前页
     */
    private int pageNo;
    /**
     * 页面尺寸
     */
    private int pageSize;
    /**
     * 异常信息
     */
    private String QCexception;

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

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public List getUnauditedDataList() {
        return unauditedDataList;
    }

    public void setUnauditedDataList(List unauditedDataList) {
        this.unauditedDataList = unauditedDataList;
    }

    public List getFeedbackDataList() {
        return feedbackDataList;
    }

    public void setFeedbackDataList(List feedbackDataList) {
        this.feedbackDataList = feedbackDataList;
    }

    public String getQCexception() {
        return QCexception;
    }

    public void setQCexception(String qCexception) {
        QCexception = qCexception;
    }

}
