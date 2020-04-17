package com.picc.riskctrl.common.schema;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "UTIWEIGHT")
public class UtiWeight implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**id号*/
    private Integer id;
    /**归属机构*/
    private String comCode;
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

    /*模板号*/
    private String riskModel;

    /**插入时间*/
    private Date insertTimeForHis;
    /**更新时间*/
    private Date operateTimeForHis;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "COMCODE", nullable = false)
    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    @Column(name = "OPERATORCODE")
    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    @Column(name = "OPERATORNAME", nullable = false)
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    @Column(name = "FIREWEIGHT", nullable = false)
    public BigDecimal getFireWeight() {
        return fireWeight;
    }

    public void setFireWeight(BigDecimal fireWeight) {
        this.fireWeight = fireWeight;
    }

    @Column(name = "WATERWEIGHT", nullable = false)
    public BigDecimal getWaterWeight() {
        return waterWeight;
    }

    public void setWaterWeight(BigDecimal waterWeight) {
        this.waterWeight = waterWeight;
    }

    @Column(name = "WINDWEIGHT", nullable = false)
    public BigDecimal getWindWeight() {
        return windWeight;
    }

    public void setWindWeight(BigDecimal windWeight) {
        this.windWeight = windWeight;
    }

    @Column(name = "THUNDERWEIGHT", nullable = false)
    public BigDecimal getThunderWeight() {
        return thunderWeight;
    }

    public void setThunderWeight(BigDecimal thunderWeight) {
        this.thunderWeight = thunderWeight;
    }

    @Column(name = "SNOWWEIGHT", nullable = false)
    public BigDecimal getSnowWeight() {
        return snowWeight;
    }

    public void setSnowWeight(BigDecimal snowWeight) {
        this.snowWeight = snowWeight;
    }

    @Column(name = "THEFTWEIGHT", nullable = false)
    public BigDecimal getTheftWeight() {
        return theftWeight;
    }

    public void setTheftWeight(BigDecimal theftWeight) {
        this.theftWeight = theftWeight;
    }

    @Column(name = "EARTHQUAKEWEIGHT", nullable = false)
    public BigDecimal getEarthquakeWeight() {
        return earthquakeWeight;
    }

    public void setEarthquakeWeight(BigDecimal earthquakeWeight) {
        this.earthquakeWeight = earthquakeWeight;
    }

    @Column(name = "GEOLOGYWEIGHT", nullable = false)
    public BigDecimal getGeologyWeight() {
        return geologyWeight;
    }

    public void setGeologyWeight(BigDecimal geologyWeight) {
        this.geologyWeight = geologyWeight;
    }

    @Column(name = "VALIDSTATUS")
    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    @Column(name = "RISKMODEL")
    public String getRiskModel() {
        return riskModel;
    }

    public void setRiskModel(String riskModel) {
        this.riskModel = riskModel;
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


}

