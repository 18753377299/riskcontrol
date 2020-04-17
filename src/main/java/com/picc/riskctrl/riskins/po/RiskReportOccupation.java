package com.picc.riskctrl.riskins.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "riskreport_occupation")
public class RiskReportOccupation implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 风控档案编号 */
    private String riskFileNo;
    /** 生产工艺流程 */
    private String productProcess;
    /** 材料名称 */
    private String materialName;
    /**
     * 物质燃烧性质 A 不燃 B 难燃 C 可燃 D 易燃
     */
    private String ignitionQuality;
    /**
     * 物质对水敏感度 A 浸水全损 B 部分致损 C 不受影响
     */
    private String waterSensitivity;
    /**
     * 可燃空间占比 A 0-30% B 30%-60% C 60%-100%
     */
    private String combustibleRatio;
    /**
     * 是否包含易燃体 A 是 B 否
     */
    private String flaLiquidFlag;
    /** 易燃液体使用区域及情况 */
    private String flaLiquidArea;
    /** 易燃液体保护措施 */
    private String flaLiquidPro;
    /**
     * 是否包含易燃气体 A 是 B 否
     */
    private String flaGasFlag;
    /** 易燃气体使用区域及情况 */
    private String flaGasArea;
    /** 易燃气体保护措施 */
    private String flaGasPro;
    /**
     * 是否包含爆炸性粉尘 A 是 B 否
     */
    private String expDustFlag;
    /** 爆炸性粉尘使用区域及情况 */
    private String expDustArea;
    /** 爆炸性粉尘保护措施 */
    private String expDustPro;
    /**
     * 是否包含动火作业 A 是 B 否
     */
    private String hotWorkFlag;
    /** 动火作业使用区域及情况 */
    private String hotWorkArea;
    /** 动火作业保护措施 */
    private String hotWorkPro;
    /**
     * 是否包含无尘车间 A 是 B 否
     */
    private String cleanRoomFlag;
    /**
     * 无尘车间使用区域及情况 A 十万级 B 万级 C 千级 D 百级
     */
    private String cleanRoomArea;
    /** 无尘车间保护措施 */
    private String cleanRoomPro;
    /**
     * 是否包含电镀工艺 A 是 B 否
     */
    private String eleProcessFlag;
    /** 电镀工艺使用区域及情况 */
    private String eleProcessArea;
    /** 电镀工艺保护措施 */
    private String eleProcessPro;
    /**
     * 是否包含高温高压 A 是 B 否
     */
    private String highTemPreFlag;
    /**
     * 高温高压设备保修记录 A 有 B 无
     */
    private String highTemPreHistory;
    /**
     * 高温高压设备使用年限 须为正整数
     */
    private Integer highTemPreYear;
    /** 高温高压设备保护措施 */
    private String highTemPrePro;
    /**
     * 现场环境 A 全部整洁有序 B 部分整洁有序 C 杂乱无序
     */
    private String siteEnvironment;
    /**
     * 生产设备泄漏情况 A 有跑、冒、滴、漏的情况 B 无跑、冒、滴、漏的情况
     */
    private String equipmentLeak;
    /**
     * 线路防护 A 明线 B 塑胶管 C 金属管 D 钢槽 E 其他
     */
    private String linePro;
    /**
     * 线路布置 A 布置整齐 B 混乱有交错 C 有搭接线路
     */
    private String lineLayout;
    /**
     * 线路使用年限 A 5年以下 B 5-10年 C 10-15年 D 15年及以上
     */
    private String lineYear;
    /**
     * 是否按要求使用防爆设备 A 是 B 否
     */
    private String riotGearFlag;
    /**
     * 仓储物摆放形式 A 垛堆 B 垫板 C 架存 D 其他
     */
    private String layingMode;
    /**
     * 作业车辆充电位置 A 仓库内 B 仓库外 C 无车辆充电
     */
    private String chargePosition;
    /**
     * 仓库内是否有生活用电 A 是 B 否
     */
    private String lifeEleFlag;
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

    @Id
    @Column(name = "RISKFILENO", nullable = false)
    public String getRiskFileNo() {
        return riskFileNo;
    }

    public void setRiskFileNo(String riskFileNo) {
        this.riskFileNo = riskFileNo;
    }

    @Column(name = "PRODUCTPROCESS")
    public String getProductProcess() {
        return productProcess;
    }

    public void setProductProcess(String productProcess) {
        this.productProcess = productProcess;
    }

    @Column(name = "MATERIALNAME")
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Column(name = "IGNITIONQUALITY")
    public String getIgnitionQuality() {
        return ignitionQuality;
    }

    public void setIgnitionQuality(String ignitionQuality) {
        this.ignitionQuality = ignitionQuality;
    }

    @Column(name = "WATERSENSITIVITY")
    public String getWaterSensitivity() {
        return waterSensitivity;
    }

    public void setWaterSensitivity(String waterSensitivity) {
        this.waterSensitivity = waterSensitivity;
    }

    @Column(name = "COMBUSTIBLERATIO")
    public String getCombustibleRatio() {
        return combustibleRatio;
    }

    public void setCombustibleRatio(String combustibleRatio) {
        this.combustibleRatio = combustibleRatio;
    }

    @Column(name = "FLALIQUIDFLAG")
    public String getFlaLiquidFlag() {
        return flaLiquidFlag;
    }

    public void setFlaLiquidFlag(String flaLiquidFlag) {
        this.flaLiquidFlag = flaLiquidFlag;
    }

    @Column(name = "FLALIQUIDAREA")
    public String getFlaLiquidArea() {
        return flaLiquidArea;
    }

    public void setFlaLiquidArea(String flaLiquidArea) {
        this.flaLiquidArea = flaLiquidArea;
    }

    @Column(name = "FLALIQUIDPRO")
    public String getFlaLiquidPro() {
        return flaLiquidPro;
    }

    public void setFlaLiquidPro(String flaLiquidPro) {
        this.flaLiquidPro = flaLiquidPro;
    }

    @Column(name = "FLAGASFLAG")
    public String getFlaGasFlag() {
        return flaGasFlag;
    }

    public void setFlaGasFlag(String flaGasFlag) {
        this.flaGasFlag = flaGasFlag;
    }

    @Column(name = "FLAGASAREA")
    public String getFlaGasArea() {
        return flaGasArea;
    }

    public void setFlaGasArea(String flaGasArea) {
        this.flaGasArea = flaGasArea;
    }

    @Column(name = "FLAGASPRO")
    public String getFlaGasPro() {
        return flaGasPro;
    }

    public void setFlaGasPro(String flaGasPro) {
        this.flaGasPro = flaGasPro;
    }

    @Column(name = "EXPDUSTFLAG")
    public String getExpDustFlag() {
        return expDustFlag;
    }

    public void setExpDustFlag(String expDustFlag) {
        this.expDustFlag = expDustFlag;
    }

    @Column(name = "EXPDUSTAREA")
    public String getExpDustArea() {
        return expDustArea;
    }

    public void setExpDustArea(String expDustArea) {
        this.expDustArea = expDustArea;
    }

    @Column(name = "EXPDUSTPRO")
    public String getExpDustPro() {
        return expDustPro;
    }

    public void setExpDustPro(String expDustPro) {
        this.expDustPro = expDustPro;
    }

    @Column(name = "HOTWORKFLAG")
    public String getHotWorkFlag() {
        return hotWorkFlag;
    }

    public void setHotWorkFlag(String hotWorkFlag) {
        this.hotWorkFlag = hotWorkFlag;
    }

    @Column(name = "HOTWORKAREA")
    public String getHotWorkArea() {
        return hotWorkArea;
    }

    public void setHotWorkArea(String hotWorkArea) {
        this.hotWorkArea = hotWorkArea;
    }

    @Column(name = "HOTWORKPRO")
    public String getHotWorkPro() {
        return hotWorkPro;
    }

    public void setHotWorkPro(String hotWorkPro) {
        this.hotWorkPro = hotWorkPro;
    }

    @Column(name = "CLEANROOMFLAG")
    public String getCleanRoomFlag() {
        return cleanRoomFlag;
    }

    public void setCleanRoomFlag(String cleanRoomFlag) {
        this.cleanRoomFlag = cleanRoomFlag;
    }

    @Column(name = "CLEANROOMAREA")
    public String getCleanRoomArea() {
        return cleanRoomArea;
    }

    public void setCleanRoomArea(String cleanRoomArea) {
        this.cleanRoomArea = cleanRoomArea;
    }

    @Column(name = "CLEANROOMPRO")
    public String getCleanRoomPro() {
        return cleanRoomPro;
    }

    public void setCleanRoomPro(String cleanRoomPro) {
        this.cleanRoomPro = cleanRoomPro;
    }

    @Column(name = "ELEPROCESSFLAG")
    public String getEleProcessFlag() {
        return eleProcessFlag;
    }

    public void setEleProcessFlag(String eleProcessFlag) {
        this.eleProcessFlag = eleProcessFlag;
    }

    @Column(name = "ELEPROCESSAREA")
    public String getEleProcessArea() {
        return eleProcessArea;
    }

    public void setEleProcessArea(String eleProcessArea) {
        this.eleProcessArea = eleProcessArea;
    }

    @Column(name = "ELEPROCESSPRO")
    public String getEleProcessPro() {
        return eleProcessPro;
    }

    public void setEleProcessPro(String eleProcessPro) {
        this.eleProcessPro = eleProcessPro;
    }

    @Column(name = "HIGHTEMPREFLAG")
    public String getHighTemPreFlag() {
        return highTemPreFlag;
    }

    public void setHighTemPreFlag(String highTemPreFlag) {
        this.highTemPreFlag = highTemPreFlag;
    }

    @Column(name = "HIGHTEMPREHISTORY")
    public String getHighTemPreHistory() {
        return highTemPreHistory;
    }

    public void setHighTemPreHistory(String highTemPreHistory) {
        this.highTemPreHistory = highTemPreHistory;
    }

    @Column(name = "HIGHTEMPREYEAR")
    public Integer getHighTemPreYear() {
        return highTemPreYear;
    }

    public void setHighTemPreYear(Integer highTemPreYear) {
        this.highTemPreYear = highTemPreYear;
    }

    @Column(name = "HIGHTEMPREPRO")
    public String getHighTemPrePro() {
        return highTemPrePro;
    }

    public void setHighTemPrePro(String highTemPrePro) {
        this.highTemPrePro = highTemPrePro;
    }

    @Column(name = "SITEENVIRONMENT")
    public String getSiteEnvironment() {
        return siteEnvironment;
    }

    public void setSiteEnvironment(String siteEnvironment) {
        this.siteEnvironment = siteEnvironment;
    }

    @Column(name = "EQUIPMENTLEAK")
    public String getEquipmentLeak() {
        return equipmentLeak;
    }

    public void setEquipmentLeak(String equipmentLeak) {
        this.equipmentLeak = equipmentLeak;
    }

    @Column(name = "LINEPRO")
    public String getLinePro() {
        return linePro;
    }

    public void setLinePro(String linePro) {
        this.linePro = linePro;
    }

    @Column(name = "LINELAYOUT")
    public String getLineLayout() {
        return lineLayout;
    }

    public void setLineLayout(String lineLayout) {
        this.lineLayout = lineLayout;
    }

    @Column(name = "LINEYEAR")
    public String getLineYear() {
        return lineYear;
    }

    public void setLineYear(String lineYear) {
        this.lineYear = lineYear;
    }

    @Column(name = "RIOTGEARFLAG")
    public String getRiotGearFlag() {
        return riotGearFlag;
    }

    public void setRiotGearFlag(String riotGearFlag) {
        this.riotGearFlag = riotGearFlag;
    }

    @Column(name = "LAYINGMODE")
    public String getLayingMode() {
        return layingMode;
    }

    public void setLayingMode(String layingMode) {
        this.layingMode = layingMode;
    }

    @Column(name = "CHARGEPOSITION")
    public String getChargePosition() {
        return chargePosition;
    }

    public void setChargePosition(String chargePosition) {
        this.chargePosition = chargePosition;
    }

    @Column(name = "LIFEELEFLAG")
    public String getLifeEleFlag() {
        return lifeEleFlag;
    }

    public void setLifeEleFlag(String lifeEleFlag) {
        this.lifeEleFlag = lifeEleFlag;
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