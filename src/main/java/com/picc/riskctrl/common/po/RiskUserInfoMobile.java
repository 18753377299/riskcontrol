package com.picc.riskctrl.common.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "riskuserinfo_mobile")
public class RiskUserInfoMobile implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户 */
    private String userCode;

    /** 硬件版本信息 */
    private String hardwareVersion;

    /** 头像路径 */
    private String url;

    /** 最新录入时间*/
    private Date entryTime;

    /** 经纬度 */
    private String lngAndLat;

    /**插入时间*/
    private Date insertTimeForHis;

    /**更新时间*/
    private Date operateTimeForHis;

    @Id
    @Column(name = "USERCODE")
    public String getUserCode() {
        return userCode;
    }
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Column(name = "HARDWAREVERSION")
    public String getHardwareVersion() {
        return hardwareVersion;
    }
    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    @Column(name = "URL")
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "ENTRYTIME")
    public Date getEntryTime() {
        return entryTime;
    }
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    @Column(name = "LNGANDLAT")
    public String getLngAndLat() {
        return lngAndLat;
    }
    public void setLngAndLat(String lngAndLat) {
        this.lngAndLat = lngAndLat;
    }


    @Column(name = "INSERTTIMEFORHIS",insertable=false, updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }
    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    @Column(name = "OPERATETIMEFORHIS",insertable=false)
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }
    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }
}
