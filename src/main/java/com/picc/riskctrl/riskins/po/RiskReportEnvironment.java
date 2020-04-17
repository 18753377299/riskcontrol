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
@Table(name = "riskreport_environment")
public class RiskReportEnvironment implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 风控档案编号 */
    private String riskFileNo;
    /**
     * 区域年平均降雨量（mm） 保留两位小数
     */
    private String aveRainfal;
    /**
     * 历史是否发生过积水 1 有 0 无
     */
    private String havePonding;
    /**
     * 最高积水水位（mm） 两位小数
     */
    private BigDecimal ponding;
    /**
     * 区域降雪量暴雪记录 1 近1年有 2 近10年有 3 近20年有 4 近50年有 0 无
     */
    private String haveBlizzard;
    /**
     * 标的所在区域类型 A 无积水记录区域 B 低洼易涝区域 C 蓄洪区 D 行洪区
     */
    private String itemAreaType;
    /**
     * 标的周围环境 A 沿江、沿河、沿湖 B 沿海 C 靠山 D 无
     */
    private String itemEnvironment;
    /** 标的周围环境补充 */
    private String addEnvironment;
    /**
     * 沿江、河、湖、海或靠山距离（m） 保留两位小数
     */
    private BigDecimal itemDistance;
    /**
     * 周围道路或建筑物地势相比 A 地势相对较高 B 地势基本持平 C 地势相对较低
     */
    private String comparedTerrain;
    /** 比较程度 */
    private String comparedDegree;
    /**
     * 企业内地势情况 A 有明显的低洼处 B 无明显的低洼处
     */
    private String insTerrain;
    /** 低洼处标的存放物为 */
    private String lowGoods;
    /**
     * 厂区遭雪灾损失记录（人民币） A 无 B <10万 C 10-50万 D ＞50万
     */
    private String snowLossHistory;
    /**
     * 热带气旋影响程度 0 所在区域无影响 1 所在区域影响较小 2 所在区域影响一般 3 所在区域影响较大
     */
    private String haveCyclone;
    /** 热带气旋影响月份 */
    private String cycloneMonth;
    /** 热带气旋登录个数 */
    private Integer entryNum;
    /** 热带气旋登录最大等级 */
    private Integer entryLev;
    /** 热带气旋影响个数 */
    private Integer impactNum;
    /** 热带气旋影响最大等级 */
    private Integer impactLev;
    /**
     * 标的所在地属于 4 强雷区（年平均雷暴数＞60天） 3 高雷区（40天＜年平均雷暴数≤60天） 2 多雷区（20天＜年平均雷暴数≤40天） 1 少雷区（年平均雷暴数≤20天）
     */
    private String haveThunder;
    /**
     * 标的是否位于地震带上 1 是 0 否
     */
    private String haveEarthquake;
    /**
     * 地区有史以来地震最高烈度 1 5度及以下 2 6度 3 7度 4 8度及以上
     */
    private String earthquakeHis;
    /**
     * 标的近10年地震损失次数 0 无 1 1-5次 2 5-10次 3 10次以上
     */
    private String earthquakeNum;
    /**
     * 是否处于泥石流、滑坡、崩塌多发区 1 是 0 否
     */
    private String haveCollapse;
    /**
     * 是否发生过重大泥石 1 是 0 否
     */
    private String collapseHis;
    /**
     * 东方周边建筑物占用性质 01. 居住建筑 02.公共建筑 03.行政办公建筑 04. 商务办公建筑 05. 商业建筑 06. 文化建筑 07. 体育建筑 08. 医疗建筑 09. 生产建筑 10. 仓储建筑 11. 科教建筑
     * 12. 科研建筑 13. 教育建筑 14. 交通建筑 15. 公用建筑 16. 特殊建筑 99. 其他 以下西南北建筑物与此相同
     */
    private String eastOccupation;
    /**
     * 东方周边建筑物距离(m) 保留两位小数 以下西南北建筑物与此相同
     */
    private BigDecimal eastDistance;
    /**
     * 东方周边建筑物建筑结构 A 钢筋混凝土结构 B 砖混结构 C 砖木结构 D 钢结构 E 简易建筑 以下西南北建筑物与此相同
     */
    private String eastConstruction;
    /**
     * 东方周边建筑物建筑高度(m) 保留两位小数 以下西南北建筑物与此相同
     */
    private BigDecimal eastHeight;
    /** 南方周边建筑物占用性质 */
    private String southOccupation;
    /** 南方周边建筑物距离(m) */
    private BigDecimal southDistance;
    /** 南方周边建筑物建筑结构 */
    private String southConstruction;
    /** 南方周边建筑物建筑高度(m) */
    private BigDecimal southHeight;
    /** 西方周边建筑物占用性质 */
    private String westOccupation;
    /** 西方周边建筑物距离(m) */
    private BigDecimal westDistance;
    /** 西方周边建筑物建筑结构 */
    private String westConstruction;
    /** 西方周边建筑物建筑高度(m) */
    private BigDecimal westHeight;
    /** 北方周边建筑物占用性质 */
    private String northOccupation;
    /** 北方周边建筑物距离(m) */
    private BigDecimal northDistance;
    /** 北方周边建筑物建筑结构 */
    private String northConstruction;
    /** 北方周边建筑物建筑高度 */
    private BigDecimal northHeight;
    /** 与周边建筑物间距 */
    private String distanceAround;
    /**
     * 建筑物间过道堆放物能否导致延烧 A 是 B 否
     */
    private String burningFlag;
    /** 信息补充 */
    private String addMessage;
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

    @Column(name = "AVERAINFAL")
    public String getAveRainfal() {
        return aveRainfal;
    }

    public void setAveRainfal(String aveRainfal) {
        this.aveRainfal = aveRainfal;
    }

    @Column(name = "HAVEPONDING")
    public String getHavePonding() {
        return havePonding;
    }

    public void setHavePonding(String havePonding) {
        this.havePonding = havePonding;
    }

    @Column(name = "PONDING")
    public BigDecimal getPonding() {
        return ponding;
    }

    public void setPonding(BigDecimal ponding) {
        this.ponding = ponding;
    }

    @Column(name = "HAVEBLIZZARD")
    public String getHaveBlizzard() {
        return haveBlizzard;
    }

    public void setHaveBlizzard(String haveBlizzard) {
        this.haveBlizzard = haveBlizzard;
    }

    @Column(name = "ITEMAREATYPE")
    public String getItemAreaType() {
        return itemAreaType;
    }

    public void setItemAreaType(String itemAreaType) {
        this.itemAreaType = itemAreaType;
    }

    @Column(name = "ITEMENVIRONMENT")
    public String getItemEnvironment() {
        return itemEnvironment;
    }

    public void setItemEnvironment(String itemEnvironment) {
        this.itemEnvironment = itemEnvironment;
    }

    @Column(name = "ADDENVIRONMENT")
    public String getAddEnvironment() {
        return addEnvironment;
    }

    public void setAddEnvironment(String addEnvironment) {
        this.addEnvironment = addEnvironment;
    }

    @Column(name = "ITEMDISTANCE")
    public BigDecimal getItemDistance() {
        return itemDistance;
    }

    public void setItemDistance(BigDecimal itemDistance) {
        this.itemDistance = itemDistance;
    }

    @Column(name = "COMPAREDTERRAIN")
    public String getComparedTerrain() {
        return comparedTerrain;
    }

    public void setComparedTerrain(String comparedTerrain) {
        this.comparedTerrain = comparedTerrain;
    }

    @Column(name = "COMPAREDDEGREE")
    public String getComparedDegree() {
        return comparedDegree;
    }

    public void setComparedDegree(String comparedDegree) {
        this.comparedDegree = comparedDegree;
    }

    @Column(name = "INSTERRAIN")
    public String getInsTerrain() {
        return insTerrain;
    }

    public void setInsTerrain(String insTerrain) {
        this.insTerrain = insTerrain;
    }

    @Column(name = "LOWGOODS")
    public String getLowGoods() {
        return lowGoods;
    }

    public void setLowGoods(String lowGoods) {
        this.lowGoods = lowGoods;
    }

    @Column(name = "SNOWLOSSHISTORY")
    public String getSnowLossHistory() {
        return snowLossHistory;
    }

    public void setSnowLossHistory(String snowLossHistory) {
        this.snowLossHistory = snowLossHistory;
    }

    @Column(name = "HAVECYCLONE")
    public String getHaveCyclone() {
        return haveCyclone;
    }

    public void setHaveCyclone(String haveCyclone) {
        this.haveCyclone = haveCyclone;
    }

    @Column(name = "CYCLONEMONTH")
    public String getCycloneMonth() {
        return cycloneMonth;
    }

    public void setCycloneMonth(String cycloneMonth) {
        this.cycloneMonth = cycloneMonth;
    }

    @Column(name = "ENTRYNUM")
    public Integer getEntryNum() {
        return entryNum;
    }

    public void setEntryNum(Integer entryNum) {
        this.entryNum = entryNum;
    }

    @Column(name = "ENTRYLEV")
    public Integer getEntryLev() {
        return entryLev;
    }

    public void setEntryLev(Integer entryLev) {
        this.entryLev = entryLev;
    }

    @Column(name = "IMPACTNUM")
    public Integer getImpactNum() {
        return impactNum;
    }

    public void setImpactNum(Integer impactNum) {
        this.impactNum = impactNum;
    }

    @Column(name = "IMPACTLEV")
    public Integer getImpactLev() {
        return impactLev;
    }

    public void setImpactLev(Integer impactLev) {
        this.impactLev = impactLev;
    }

    @Column(name = "HAVETHUNDER")
    public String getHaveThunder() {
        return haveThunder;
    }

    public void setHaveThunder(String haveThunder) {
        this.haveThunder = haveThunder;
    }

    @Column(name = "HAVEEARTHQUAKE")
    public String getHaveEarthquake() {
        return haveEarthquake;
    }

    public void setHaveEarthquake(String haveEarthquake) {
        this.haveEarthquake = haveEarthquake;
    }

    @Column(name = "EARTHQUAKEHIS")
    public String getEarthquakeHis() {
        return earthquakeHis;
    }

    public void setEarthquakeHis(String earthquakeHis) {
        this.earthquakeHis = earthquakeHis;
    }

    @Column(name = "EARTHQUAKENUM")
    public String getEarthquakeNum() {
        return earthquakeNum;
    }

    public void setEarthquakeNum(String earthquakeNum) {
        this.earthquakeNum = earthquakeNum;
    }

    @Column(name = "HAVECOLLAPSE")
    public String getHaveCollapse() {
        return haveCollapse;
    }

    public void setHaveCollapse(String haveCollapse) {
        this.haveCollapse = haveCollapse;
    }

    @Column(name = "COLLAPSEHIS")
    public String getCollapseHis() {
        return collapseHis;
    }

    public void setCollapseHis(String collapseHis) {
        this.collapseHis = collapseHis;
    }

    @Column(name = "EASTOCCUPATION")
    public String getEastOccupation() {
        return eastOccupation;
    }

    public void setEastOccupation(String eastOccupation) {
        this.eastOccupation = eastOccupation;
    }

    @Column(name = "EASTDISTANCE")
    public BigDecimal getEastDistance() {
        return eastDistance;
    }

    public void setEastDistance(BigDecimal eastDistance) {
        this.eastDistance = eastDistance;
    }

    @Column(name = "EASTCONSTRUCTION")
    public String getEastConstruction() {
        return eastConstruction;
    }

    public void setEastConstruction(String eastConstruction) {
        this.eastConstruction = eastConstruction;
    }

    @Column(name = "EASTHEIGHT")
    public BigDecimal getEastHeight() {
        return eastHeight;
    }

    public void setEastHeight(BigDecimal eastHeight) {
        this.eastHeight = eastHeight;
    }

    @Column(name = "SOUTHOCCUPATION")
    public String getSouthOccupation() {
        return southOccupation;
    }

    public void setSouthOccupation(String southOccupation) {
        this.southOccupation = southOccupation;
    }

    @Column(name = "SOUTHDISTANCE")
    public BigDecimal getSouthDistance() {
        return southDistance;
    }

    public void setSouthDistance(BigDecimal southDistance) {
        this.southDistance = southDistance;
    }

    @Column(name = "SOUTHCONSTRUCTION")
    public String getSouthConstruction() {
        return southConstruction;
    }

    public void setSouthConstruction(String southConstruction) {
        this.southConstruction = southConstruction;
    }

    @Column(name = "SOUTHHEIGHT")
    public BigDecimal getSouthHeight() {
        return southHeight;
    }

    public void setSouthHeight(BigDecimal southHeight) {
        this.southHeight = southHeight;
    }

    @Column(name = "WESTOCCUPATION")
    public String getWestOccupation() {
        return westOccupation;
    }

    public void setWestOccupation(String westOccupation) {
        this.westOccupation = westOccupation;
    }

    @Column(name = "WESTDISTANCE")
    public BigDecimal getWestDistance() {
        return westDistance;
    }

    public void setWestDistance(BigDecimal westDistance) {
        this.westDistance = westDistance;
    }

    @Column(name = "WESTCONSTRUCTION")
    public String getWestConstruction() {
        return westConstruction;
    }

    public void setWestConstruction(String westConstruction) {
        this.westConstruction = westConstruction;
    }

    @Column(name = "WESTHEIGHT")
    public BigDecimal getWestHeight() {
        return westHeight;
    }

    public void setWestHeight(BigDecimal westHeight) {
        this.westHeight = westHeight;
    }

    @Column(name = "NORTHOCCUPATION")
    public String getNorthOccupation() {
        return northOccupation;
    }

    public void setNorthOccupation(String northOccupation) {
        this.northOccupation = northOccupation;
    }

    @Column(name = "NORTHDISTANCE")
    public BigDecimal getNorthDistance() {
        return northDistance;
    }

    public void setNorthDistance(BigDecimal northDistance) {
        this.northDistance = northDistance;
    }

    @Column(name = "NORTHCONSTRUCTION")
    public String getNorthConstruction() {
        return northConstruction;
    }

    public void setNorthConstruction(String northConstruction) {
        this.northConstruction = northConstruction;
    }

    @Column(name = "NORTHHEIGHT")
    public BigDecimal getNorthHeight() {
        return northHeight;
    }

    public void setNorthHeight(BigDecimal northHeight) {
        this.northHeight = northHeight;
    }

    @Column(name = "DISTANCEAROUND")
    public String getDistanceAround() {
        return distanceAround;
    }

    public void setDistanceAround(String distanceAround) {
        this.distanceAround = distanceAround;
    }

    @Column(name = "BURNINGFLAG")
    public String getBurningFlag() {
        return burningFlag;
    }

    public void setBurningFlag(String burningFlag) {
        this.burningFlag = burningFlag;
    }

    @Column(name = "ADDMESSAGE")
    public String getAddMessage() {
        return addMessage;
    }

    public void setAddMessage(String addMessage) {
        this.addMessage = addMessage;
    }

    // @Column(name = "INSERTTIMEFORHIS",insertable=false,updatable = false)
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