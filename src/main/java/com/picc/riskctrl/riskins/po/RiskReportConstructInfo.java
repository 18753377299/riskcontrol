package com.picc.riskctrl.riskins.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "riskreport_constructinfo")
public class RiskReportConstructInfo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 复合主键 */
    private RiskReportConstructInfoId id;
    /** 建筑名称 */
    private String constructName;
    /**
     * 建筑结构 A 钢筋混凝土结构 B 砖混结构 C 砖木结构 D 钢结构 E 简易建筑
     */
    private String constructBuild;
    /**
     * 建筑年份 A ＞20年 B ≤20年
     */
    private String constructYear;
    /** 地下可水损资产额 */
    private BigDecimal underWaterAssets;
    /**
     * 占用性质 01. 居住建筑 02.公共建筑 03.行政办公建筑 04. 商务办公建筑 05. 商业建筑 06. 文化建筑 07. 体育建筑 08. 医疗建筑 09. 生产建筑 10. 仓储建筑 11. 科教建筑 12.
     * 科研建筑 13. 教育建筑 14. 交通建筑 15. 公用建筑 16. 特殊建筑 99. 其他
     */
    private String occupancy;
    /**
     * 资产占比 保留两位小数 如54.55%，则传54.55
     */
    private BigDecimal assetsRatio;
    /** 插入时间 */
    private Date insertTimeForHis;
    /** 更新时间 */
    private Date operateTimeForHis;
    /** 风控主表 */
    private RiskReportMain riskReportMain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RISKFILENO", nullable = false, insertable = false, updatable = false)
    public RiskReportMain getRiskReportMain() {
        return riskReportMain;
    }

    public void setRiskReportMain(RiskReportMain riskReportMain) {
        this.riskReportMain = riskReportMain;
    }

    /**
     * 联合主键
     */
    @EmbeddedId
    @AttributeOverrides({@AttributeOverride(name = "riskFileNo", column = @Column(name = "RISKFILENO")),
        @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO"))})

    public RiskReportConstructInfoId getId() {
        return id;
    }

    public void setId(RiskReportConstructInfoId id) {
        this.id = id;
    }

    @Column(name = "CONSTRUCTNAME")
    public String getConstructName() {
        return constructName;
    }

    public void setConstructName(String constructName) {
        this.constructName = constructName;
    }

    @Column(name = "CONSTRUCTBUILD")
    public String getConstructBuild() {
        return constructBuild;
    }

    public void setConstructBuild(String constructBuild) {
        this.constructBuild = constructBuild;
    }

    @Column(name = "CONSTRUCTYEAR")
    public String getConstructYear() {
        return constructYear;
    }

    public void setConstructYear(String constructYear) {
        this.constructYear = constructYear;
    }

    @Column(name = "UNDERWATERASSETS")
    public BigDecimal getUnderWaterAssets() {
        return underWaterAssets;
    }

    public void setUnderWaterAssets(BigDecimal underWaterAssets) {
        this.underWaterAssets = underWaterAssets;
    }

    @Column(name = "OCCUPANCY")
    public String getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }

    @Column(name = "ASSETSRATIO")
    public BigDecimal getAssetsRatio() {
        return assetsRatio;
    }

    public void setAssetsRatio(BigDecimal assetsRatio) {
        this.assetsRatio = assetsRatio;
    }

    // @Column(name = "INSERTTIMEFORHIS",insertable=false, updatable = false)
    @CreatedDate
    @Column(name = "INSERTTIMEFORHIS", updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    // @Column(name = "OPERATETIMEFORHIS",insertable=false)
    @LastModifiedDate
    @Column(name = "OPERATETIMEFORHIS")
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }

}