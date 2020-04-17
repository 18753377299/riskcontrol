package com.picc.riskctrl.common.vo;

import java.math.BigDecimal;
import java.util.Date;

public class RiskDnaturalVo {

    /**顺序号*/
    private Integer serialNo;
    /**业务年度*/
    private String naturalYear;
    /**归属机构*/
    private String comCode;
    /**归属机构名称*/
    private String comCodeCName;
    /**所处地区代码*/
    private String addressCode;
    /**所处地区名称*/
    private String addressName;
    /**所处地区省级代码*/
    private String addressProvince;
    /**年平均降水量*/
    private String aveRainfal;
    /**有无积水*/
    private String havePonding;
    /**历史最高积水水位*/
    private BigDecimal ponding;
    /**区域降雪量暴雪记录*/
    private String haveBlizzard;
    /**区域受热带气旋影响程度*/
    private String haveCyclone;
    /**热带气旋影响月份*/
    private String cycloneMonth;
    /**热带气旋登录个数*/
    private Integer entryNum;
    /**热带气旋登录最大等级*/
    private Integer entryLev;
    /**热带气旋影响个数*/
    private Integer impactNum;
    /**热带气旋影响最大等级*/
    private Integer impactLev;
    /**区域雷暴情况*/
    private String haveThunder;
    /**是否处于地震带 */
    private String haveEarthquake;
    /**历史最高地震等级*/
    private String earthquakeHis;
    /**10年地震次数*/
    private String earthquakeNum;
    /**是否处于泥石流、滑坡、崩塌多发区*/
    private String haveCollapse;
    /**否发生过重大泥石流、滑坡、崩塌灾害*/
    private String collapseHis;
    /**报告制作人代码*/
    private String operatorCode;
    /**报告制作人名称*/
    private String operatorName;
    /**报告制作日期*/
    private Date madeDate;
    /**有效标志位*/
    private String validStatus;
    /**有效标志位中文*/
    private String validStatusName;
    /**插入时间*/
    private Date insertTimeForHis;
    /**更新时间*/
    private Date operateTimeForHis;
    public Integer getSerialNo() {
        return serialNo;
    }
    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }
    public String getNaturalYear() {
        return naturalYear;
    }
    public void setNaturalYear(String naturalYear) {
        this.naturalYear = naturalYear;
    }
    public String getComCode() {
        return comCode;
    }
    public void setComCode(String comCode) {
        this.comCode = comCode;
    }
    public String getComCodeCName() {
        return comCodeCName;
    }
    public void setComCodeCName(String comCodeCName) {
        this.comCodeCName = comCodeCName;
    }

    public String getAddressCode() {
        return addressCode;
    }
    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }
    public String getAddressName() {
        return addressName;
    }
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
    public String getAveRainfal() {
        return aveRainfal;
    }
    public void setAveRainfal(String aveRainfal) {
        this.aveRainfal = aveRainfal;
    }
    public String getHavePonding() {
        return havePonding;
    }
    public void setHavePonding(String havePonding) {
        this.havePonding = havePonding;
    }
    public BigDecimal getPonding() {
        return ponding;
    }
    public void setPonding(BigDecimal ponding) {
        this.ponding = ponding;
    }
    public String getHaveBlizzard() {
        return haveBlizzard;
    }
    public void setHaveBlizzard(String haveBlizzard) {
        this.haveBlizzard = haveBlizzard;
    }
    public String getHaveCyclone() {
        return haveCyclone;
    }
    public void setHaveCyclone(String haveCyclone) {
        this.haveCyclone = haveCyclone;
    }
    public String getCycloneMonth() {
        return cycloneMonth;
    }
    public void setCycloneMonth(String cycloneMonth) {
        this.cycloneMonth = cycloneMonth;
    }
    public Integer getEntryNum() {
        return entryNum;
    }
    public void setEntryNum(Integer entryNum) {
        this.entryNum = entryNum;
    }
    public Integer getEntryLev() {
        return entryLev;
    }
    public void setEntryLev(Integer entryLev) {
        this.entryLev = entryLev;
    }
    public Integer getImpactNum() {
        return impactNum;
    }
    public void setImpactNum(Integer impactNum) {
        this.impactNum = impactNum;
    }
    public Integer getImpactLev() {
        return impactLev;
    }
    public void setImpactLev(Integer impactLev) {
        this.impactLev = impactLev;
    }
    public String getHaveThunder() {
        return haveThunder;
    }
    public void setHaveThunder(String haveThunder) {
        this.haveThunder = haveThunder;
    }
    public String getHaveEarthquake() {
        return haveEarthquake;
    }
    public void setHaveEarthquake(String haveEarthquake) {
        this.haveEarthquake = haveEarthquake;
    }
    public String getEarthquakeHis() {
        return earthquakeHis;
    }
    public void setEarthquakeHis(String earthquakeHis) {
        this.earthquakeHis = earthquakeHis;
    }
    public String getEarthquakeNum() {
        return earthquakeNum;
    }
    public void setEarthquakeNum(String earthquakeNum) {
        this.earthquakeNum = earthquakeNum;
    }
    public String getHaveCollapse() {
        return haveCollapse;
    }
    public void setHaveCollapse(String haveCollapse) {
        this.haveCollapse = haveCollapse;
    }
    public String getCollapseHis() {
        return collapseHis;
    }
    public void setCollapseHis(String collapseHis) {
        this.collapseHis = collapseHis;
    }
    public String getOperatorCode() {
        return operatorCode;
    }
    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }
    public String getOperatorName() {
        return operatorName;
    }
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    public Date getMadeDate() {
        return madeDate;
    }
    public void setMadeDate(Date madeDate) {
        this.madeDate = madeDate;
    }

    public String getValidStatus() {
        return validStatus;
    }
    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }
    public String getValidStatusName() {
        return validStatusName;
    }
    public void setValidStatusName(String validStatusName) {
        this.validStatusName = validStatusName;
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
    public String getAddressProvince() {
        return addressProvince;
    }
    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }


}
