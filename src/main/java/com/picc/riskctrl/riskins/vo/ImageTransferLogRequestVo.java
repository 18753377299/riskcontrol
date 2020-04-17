package com.picc.riskctrl.riskins.vo;

import java.util.List;

public class ImageTransferLogRequestVo {

    /**
     * 影像上传的异常队列信息的查询
     */
    private ImageTransferLogQueryVo imageTransferLogQueryVo;
    /**
     * 归属机构
     */
    private List<Object> list;
    /**
     * 风控报告销售员版基本信息
     */
    private RiskReportSaleMainQueryVo riskReportSaleMainVo;
    /**
     * 页号
     */
    private int pageNo;
    /**
     * 每页的记录条数
     */
    private int pageSize;


    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public ImageTransferLogQueryVo getImageTransferLogQueryVo() {
        return imageTransferLogQueryVo;
    }

    public void setImageTransferLogQueryVo(ImageTransferLogQueryVo imageTransferLogQueryVo) {
        this.imageTransferLogQueryVo = imageTransferLogQueryVo;
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

    public RiskReportSaleMainQueryVo getRiskReportSaleMainVo() {
        return riskReportSaleMainVo;
    }

    public void setRiskReportSaleMainVo(RiskReportSaleMainQueryVo riskReportSaleMainVo) {
        this.riskReportSaleMainVo = riskReportSaleMainVo;
    }
}
