package com.picc.riskctrl.common.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RiskUtiWeightVo implements Serializable{
    private static final long serialVersionUID = 1L;
    /**id号*/
    private Integer id;
    /**归属机构*/
    private String comCode;
    /**归属机构*/
    private String comCName;
    /**维护人代码*/
    private String operatorCode;
    /**维护人名称*/
    private String operatorName;
    /**火灾风险值*/
    private BigDecimal fireWeight;
    /**水灾风险值*/
    private BigDecimal waterWeight;
    /**风灾风险值*/
    private BigDecimal windWeight;
    /**雷灾风险值*/
    private BigDecimal thunderWeight;
    /**雪灾风险值*/
    private BigDecimal snowWeight;
    /**盗抢风险值*/
    private BigDecimal theftWeight;
    /**地震风险值*/
    private BigDecimal earthquakeWeight;
    /**地质灾害风险值*/
    private BigDecimal geologyWeight;
    /**有效标志位*/
    private String validStatus;
    /**插入时间*/
    private Date insertTimeForHis;
    /**更新时间*/
    private Date operateTimeForHis;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getComCode() {
        return comCode;
    }
    public void setComCode(String comCode) {
        this.comCode = comCode;
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
    public BigDecimal getFireWeight() {
        return fireWeight;
    }
    public void setFireWeight(BigDecimal fireWeight) {
        this.fireWeight = fireWeight;
    }
    public BigDecimal getWaterWeight() {
        return waterWeight;
    }
    public void setWaterWeight(BigDecimal waterWeight) {
        this.waterWeight = waterWeight;
    }
    public BigDecimal getWindWeight() {
        return windWeight;
    }
    public void setWindWeight(BigDecimal windWeight) {
        this.windWeight = windWeight;
    }
    public BigDecimal getThunderWeight() {
        return thunderWeight;
    }
    public void setThunderWeight(BigDecimal thunderWeight) {
        this.thunderWeight = thunderWeight;
    }
    public BigDecimal getSnowWeight() {
        return snowWeight;
    }
    public void setSnowWeight(BigDecimal snowWeight) {
        this.snowWeight = snowWeight;
    }
    public BigDecimal getTheftWeight() {
        return theftWeight;
    }
    public void setTheftWeight(BigDecimal theftWeight) {
        this.theftWeight = theftWeight;
    }
    public BigDecimal getEarthquakeWeight() {
        return earthquakeWeight;
    }
    public void setEarthquakeWeight(BigDecimal earthquakeWeight) {
        this.earthquakeWeight = earthquakeWeight;
    }
    public BigDecimal getGeologyWeight() {
        return geologyWeight;
    }
    public void setGeologyWeight(BigDecimal geologyWeight) {
        this.geologyWeight = geologyWeight;
    }
    public String getValidStatus() {
        return validStatus;
    }
    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
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
    public String getComCName() {
        return comCName;
    }
    public void setComCName(String comCName) {
        this.comCName = comCName;
    }

}
