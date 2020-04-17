package com.picc.riskctrl.riskins.vo;

import java.util.Date;

public class ImageTransferLogQueryVo {

    /**
     * 异常队列信息编号
     */
    private String businessNo;
    /**
     * 业务类型
     */
    private int[] businessType;
    /**
     * 业务状态
     */
    private int[] businessStatus;
    /**
     * 报文消息
     */
    private String mqMessage;
    /**
     * 异常信息
     */
    private String errMessage;
    /**
     * 上次推送时间
     */
    private Date lastTransferTime;
    /**
     * 插入时间
     */
    private Date insertTimeForHis;
    /**
     * 更新时间
     */
    private Date operateTimeForHis;
    /**
     * 更新时间的范围（查询更新时间的上下限）
     */
    private Date[] operateTimeForHisRange;

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public int[] getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int[] businessType) {
        this.businessType = businessType;
    }

    public int[] getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(int[] businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public Date getLastTransferTime() {
        return lastTransferTime;
    }

    public void setLastTransferTime(Date lastTransferTime) {
        this.lastTransferTime = lastTransferTime;
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

    public String getMqMessage() {
        return mqMessage;
    }

    public void setMqMessage(String mqMessage) {
        this.mqMessage = mqMessage;
    }

    public Date[] getOperateTimeForHisRange() {
        return operateTimeForHisRange;
    }

    public void setOperateTimeForHisRange(Date[] operateTimeForHisRange) {
        this.operateTimeForHisRange = operateTimeForHisRange;
    }
}
