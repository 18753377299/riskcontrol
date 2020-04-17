package com.picc.riskctrl.common.schema;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "IMAGETRANSFERLOG")
public class ImageTransferLog implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 影像上传的异常队列信息编号*
     */
    private String businessNo;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 业务状态
     */
    private String businessStatus;
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
     * comCode
     */
    private String comCode;

    @Id
    @Column(name = "BUSINESSNO")
    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    @Column(name = "BUSINESSTYPE")
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Column(name = "BUSINESSSTATUS")
    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    @Column(name = "ERRMESSAGE")
    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    @Column(name = "LASTTRANSFERTIME")
    public Date getLastTransferTime() {
        return lastTransferTime;
    }

    public void setLastTransferTime(Date lastTransferTime) {
        this.lastTransferTime = lastTransferTime;
    }

    @Column(name = "INSERTTIMEFORHIS", insertable = false, updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    @Column(name = "OPERATETIMEFORHIS", insertable = false)
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }

    @Column(name = "MQMESSAGE")
    public String getMqMessage() {
        return mqMessage;
    }

    public void setMqMessage(String mqMessage) {
        this.mqMessage = mqMessage;
    }

    @Column(name = "COMCODE")
    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }
	
	/*drop table imagetransferlog;
	CREATE TABLE
	    imagetransferlog
	    (
	        businessno CHAR(22) NOT NULL,
	        businesstype VARCHAR(10) NOT NULL,
	        errmessage LVARCHAR(255),
	        businessstatus CHAR(1) NOT NULL,
	        lasttransfertime DATETIME YEAR TO SECOND  DEFAULT CURRENT YEAR TO SECOND,
	        inserttimeforhis DATETIME YEAR TO SECOND  DEFAULT CURRENT YEAR TO SECOND,
	        operatetimeforhis DATETIME YEAR TO SECOND  DEFAULT CURRENT YEAR TO SECOND,
	        PRIMARY KEY (businessno)
	    );*/

}
