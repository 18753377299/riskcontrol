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
@Table(name = "riskreport_protection")
public class RiskReportProtection implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 风控档案编号 */
    private String riskFileNo;
    /**
     * 消防器材类型 A 自动报警 B 自动灭火系统 C 消防栓 D 灭火器
     */
    private String fireEquipType;
    /**
     * 灭火器布置区域 A 全部覆盖 B 主要区域覆盖 C 较少覆盖 D 无
     */
    private String extinguisherArea;
    /**
     * 灭火器气压 A 正常 B 部分正常 C 不正常
     */
    private String extinguisherPre;
    /** 灭火器摆放位置 */
    private String extinguisherPos;
    /**
     * 室内消防栓布置区域 A 醒目 B 部分隐蔽 C 隐蔽
     */
    private String insHydrantArea;
    /**
     * 室内消防栓部件是否齐全完好 A 全部是 B 部分是 C 完全不是
     */
    private String insHydrantPos;
    /**
     * 室内消防栓周围遮挡情况 A 未堆放 B 部分有堆放 C 全部都有堆放
     */
    private String insHydrantHade;
    /**
     * 室外消防栓布置区域 A 全部覆盖 B 主要区域覆盖 C 较少覆盖 D 无
     */
    private String outHydrantArea;
    /**
     * 室外消防栓是否配备扳手水袋 A 全部是 B 部分是 C 完全不是
     */
    private String outHydrantBag;
    /**
     * 自动灭火覆盖情况 A 全覆盖 B 部分覆盖 C 无
     */
    private String selfExtCase;
    /**
     * 自动灭火覆盖区域 A 生产区 B 仓储区 C 办公区 D 生活区
     */
    private String selfExtArea;
    /**
     * 喷淋主控电源灯 A 正常 B 不正常
     */
    private String powerLed;
    /**
     * 喷淋栓 A 有 B 无
     */
    private String sprayBolt;
    /**
     * 自动灭火系统启动方式 A 手动启动 B 自动启动
     */
    private String selfExtWay;
    /**
     * 消防水来源 A 天然水源 B 消防水池 C 市政公共消防水源 D 无
     */
    private String waterSource;
    /**
     * 消防水池蓄水情况 A 蓄水充足 B 蓄水半充足 C 无
     */
    private String impoundment;
    /**
     * 自动报警系统类型 A 火灾手动警钟 B 火灾自动报警系统 C 无
     */
    private String alarmType;
    /**
     * 自动报警系统工作状况 A 正常 B 异常
     */
    private String alarmWork;
    /** 通往消防器材通道情况 */
    private String equipmentChannel;
    /** 公共消防队到达最短时间（分钟） */
    private Integer shortTime;
    /**
     * 消防通道通畅情况 A 宽阔，便于车辆进出 B 较狭窄，消防车进入困难
     */
    private String channelClear;
    /**
     * 消防验收合格证 A 取得标的所有建筑的消防验收合格证明 B 取得部分建筑的消防验收合格证明 C 有消防报备证明文件 D 不知道是否有验收合格证明 E 没有
     */
    private String firePreCertification;
    /**
     * 消防安全机构与人员 A 有专职安全管理人员 B 有兼职安全管理人员 C 无专兼职安全管理人员
     */
    private String firePrePeople;
    /**
     * 动火管理作业制度 A 有审批制度 B 无审批制度
     */
    private String hotWorkSystem;
    /**
     * 吸烟管理 A 全部区域禁烟，执行良好 B 全部区域禁烟，执行不足 C 指定吸烟区域，执行良好 D 指定吸烟区域，执行不足 E 无吸烟管制
     */
    private String smokingControl;
    /**
     * 消防应急预案 A 有 B 无
     */
    private String firePlan;
    /**
     * 演练频率 A 每月一次 B 每季一次 C 每半年一次 D 每年一次 E 每两年一次 F 无演练
     */
    private String drillFrequency;
    /**
     * 消防设施维护检查频率 A 每月一次 B 每季一次 C 每年一次 D 无检查
     */
    private String checkFrequency;
    /**
     * 消防自动系统测试频率 A 每月一次 B 每季一次 C 每年一次 D 每两年一次 E 无测试频率
     */
    private String testFrequency;
    /**
     * 员工培训 A 每月一次 B 每季一次 C 每半年一次 D 每年一次 E 无培训
     */
    private String staffTraining;
    /**
     * 电气线路老化检查情况 A 有老化情况 B 无老化情况
     */
    private String lineCheck;
    /**
     * 是否有安保巡逻 A 有 B 无
     */
    private String patrolFlag;
    /**
     * 巡逻区域 A 全部区域 B 部分区域
     */
    private String patrolArea;
    /**
     * 巡逻频次（h/次） A 1 B 2 C 3 D 4
     */
    private String patrolFrequency;
    /**
     * 近五年历史火灾次数 A 0 B 1 C 2 D 3 E 4次及以上
     */
    private String fireTime;
    /**
     * 企业防水灾设施 A 防洪墙 B 挡水板 C 沙袋 D 水泵 E 其他
     */
    private String waterFacility;
    /** 防水灾设施补充 */
    private String addWaterFacility;
    /**
     * 标的总排水方式 A 排入市政管道 B 直接排入江河湖海 C 自排
     */
    private String drainageMethod;
    /**
     * 排水系统是否有堵塞现象 A 是 B 否
     */
    private String drainageBlock;
    /**
     * 地下低洼设备厂房排水设备情况 A 有 B 无
     */
    private String lowEquipment;
    /**
     * 企业汛期抢险救灾应急预案 A 已建立 B 未建立
     */
    private String waterPlan;
    /**
     * 厂区历史进水损失记录（人民币） A 无 B <1万 C 1-10万 D 10-50万 E ＞50万
     */
    private String waterLossHistory;
    /**
     * 企业风灾应急预案 A 已建立 B 未建立
     */
    private String windPlan;
    /**
     * 轻钢结构房屋屋面铆钉情况 A 汛期前已检修 B 汛期前未检修
     */
    private String rivetCondition;
    /**
     * 企业遭风灾损失记录（人民币） A 无 B <1万 C 1-10万 D ＞10万
     */
    private String windLossHistory;
    /**
     * 是否定期检测防雷设施 A 是 B 否
     */
    private String thunderCheck;
    /**
     * 企业遭雷击损失记录（人民币） A 无 B <1万 C 1-10万 D 10-50万 E ＞50万
     */
    private String thunderLossHistory;
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

    @Column(name = "FIREEQUIPTYPE")
    public String getFireEquipType() {
        return fireEquipType;
    }

    public void setFireEquipType(String fireEquipType) {
        this.fireEquipType = fireEquipType;
    }

    @Column(name = "EXTINGUISHERAREA")
    public String getExtinguisherArea() {
        return extinguisherArea;
    }

    public void setExtinguisherArea(String extinguisherArea) {
        this.extinguisherArea = extinguisherArea;
    }

    @Column(name = "EXTINGUISHERPRE")
    public String getExtinguisherPre() {
        return extinguisherPre;
    }

    public void setExtinguisherPre(String extinguisherPre) {
        this.extinguisherPre = extinguisherPre;
    }

    @Column(name = "EXTINGUISHERPOS")
    public String getExtinguisherPos() {
        return extinguisherPos;
    }

    public void setExtinguisherPos(String extinguisherPos) {
        this.extinguisherPos = extinguisherPos;
    }

    @Column(name = "INSHYDRANTAREA")
    public String getInsHydrantArea() {
        return insHydrantArea;
    }

    public void setInsHydrantArea(String insHydrantArea) {
        this.insHydrantArea = insHydrantArea;
    }

    @Column(name = "INSHYDRANTPOS")
    public String getInsHydrantPos() {
        return insHydrantPos;
    }

    public void setInsHydrantPos(String insHydrantPos) {
        this.insHydrantPos = insHydrantPos;
    }

    @Column(name = "INSHYDRANTHADE")
    public String getInsHydrantHade() {
        return insHydrantHade;
    }

    public void setInsHydrantHade(String insHydrantHade) {
        this.insHydrantHade = insHydrantHade;
    }

    @Column(name = "OUTHYDRANTAREA")
    public String getOutHydrantArea() {
        return outHydrantArea;
    }

    public void setOutHydrantArea(String outHydrantArea) {
        this.outHydrantArea = outHydrantArea;
    }

    @Column(name = "OUTHYDRANTBAG")
    public String getOutHydrantBag() {
        return outHydrantBag;
    }

    public void setOutHydrantBag(String outHydrantBag) {
        this.outHydrantBag = outHydrantBag;
    }

    @Column(name = "SELFEXTCASE")
    public String getSelfExtCase() {
        return selfExtCase;
    }

    public void setSelfExtCase(String selfExtCase) {
        this.selfExtCase = selfExtCase;
    }

    @Column(name = "SELFEXTAREA")
    public String getSelfExtArea() {
        return selfExtArea;
    }

    public void setSelfExtArea(String selfExtArea) {
        this.selfExtArea = selfExtArea;
    }

    @Column(name = "POWERLED")
    public String getPowerLed() {
        return powerLed;
    }

    public void setPowerLed(String powerLed) {
        this.powerLed = powerLed;
    }

    @Column(name = "SPRAYBOLT")
    public String getSprayBolt() {
        return sprayBolt;
    }

    public void setSprayBolt(String sprayBolt) {
        this.sprayBolt = sprayBolt;
    }

    @Column(name = "SELFEXTWAY")
    public String getSelfExtWay() {
        return selfExtWay;
    }

    public void setSelfExtWay(String selfExtWay) {
        this.selfExtWay = selfExtWay;
    }

    @Column(name = "WATERSOURCE")
    public String getWaterSource() {
        return waterSource;
    }

    public void setWaterSource(String waterSource) {
        this.waterSource = waterSource;
    }

    @Column(name = "IMPOUNDMENT")
    public String getImpoundment() {
        return impoundment;
    }

    public void setImpoundment(String impoundment) {
        this.impoundment = impoundment;
    }

    @Column(name = "ALARMTYPE")
    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    @Column(name = "ALARMWORK")
    public String getAlarmWork() {
        return alarmWork;
    }

    public void setAlarmWork(String alarmWork) {
        this.alarmWork = alarmWork;
    }

    @Column(name = "EQUIPMENTCHANNEL")
    public String getEquipmentChannel() {
        return equipmentChannel;
    }

    public void setEquipmentChannel(String equipmentChannel) {
        this.equipmentChannel = equipmentChannel;
    }

    @Column(name = "SHORTTIME")
    public Integer getShortTime() {
        return shortTime;
    }

    public void setShortTime(Integer shortTime) {
        this.shortTime = shortTime;
    }

    @Column(name = "CHANNELCLEAR")
    public String getChannelClear() {
        return channelClear;
    }

    public void setChannelClear(String channelClear) {
        this.channelClear = channelClear;
    }

    @Column(name = "FIREPRECERTIFICATION")
    public String getFirePreCertification() {
        return firePreCertification;
    }

    public void setFirePreCertification(String firePreCertification) {
        this.firePreCertification = firePreCertification;
    }

    @Column(name = "FIREPREPEOPLE")
    public String getFirePrePeople() {
        return firePrePeople;
    }

    public void setFirePrePeople(String firePrePeople) {
        this.firePrePeople = firePrePeople;
    }

    @Column(name = "HOTWORKSYSTEM")
    public String getHotWorkSystem() {
        return hotWorkSystem;
    }

    public void setHotWorkSystem(String hotWorkSystem) {
        this.hotWorkSystem = hotWorkSystem;
    }

    @Column(name = "SMOKINGCONTROL")
    public String getSmokingControl() {
        return smokingControl;
    }

    public void setSmokingControl(String smokingControl) {
        this.smokingControl = smokingControl;
    }

    @Column(name = "FIREPLAN")
    public String getFirePlan() {
        return firePlan;
    }

    public void setFirePlan(String firePlan) {
        this.firePlan = firePlan;
    }

    @Column(name = "DRILLFREQUENCY")
    public String getDrillFrequency() {
        return drillFrequency;
    }

    public void setDrillFrequency(String drillFrequency) {
        this.drillFrequency = drillFrequency;
    }

    @Column(name = "CHECKFREQUENCY")
    public String getCheckFrequency() {
        return checkFrequency;
    }

    public void setCheckFrequency(String checkFrequency) {
        this.checkFrequency = checkFrequency;
    }

    @Column(name = "TESTFREQUENCY")
    public String getTestFrequency() {
        return testFrequency;
    }

    public void setTestFrequency(String testFrequency) {
        this.testFrequency = testFrequency;
    }

    @Column(name = "STAFFTRAINING")
    public String getStaffTraining() {
        return staffTraining;
    }

    public void setStaffTraining(String staffTraining) {
        this.staffTraining = staffTraining;
    }

    @Column(name = "LINECHECK")
    public String getLineCheck() {
        return lineCheck;
    }

    public void setLineCheck(String lineCheck) {
        this.lineCheck = lineCheck;
    }

    @Column(name = "PATROLFLAG")
    public String getPatrolFlag() {
        return patrolFlag;
    }

    public void setPatrolFlag(String patrolFlag) {
        this.patrolFlag = patrolFlag;
    }

    @Column(name = "PATROLAREA")
    public String getPatrolArea() {
        return patrolArea;
    }

    public void setPatrolArea(String patrolArea) {
        this.patrolArea = patrolArea;
    }

    @Column(name = "PATROLFREQUENCY")
    public String getPatrolFrequency() {
        return patrolFrequency;
    }

    public void setPatrolFrequency(String patrolFrequency) {
        this.patrolFrequency = patrolFrequency;
    }

    @Column(name = "FIRETIME")
    public String getFireTime() {
        return fireTime;
    }

    public void setFireTime(String fireTime) {
        this.fireTime = fireTime;
    }

    @Column(name = "WATERFACILITY")
    public String getWaterFacility() {
        return waterFacility;
    }

    public void setWaterFacility(String waterFacility) {
        this.waterFacility = waterFacility;
    }

    @Column(name = "ADDWATERFACILITY")

    public String getAddWaterFacility() {
        return addWaterFacility;
    }

    public void setAddWaterFacility(String addWaterFacility) {
        this.addWaterFacility = addWaterFacility;
    }

    @Column(name = "DRAINAGEMETHOD")
    public String getDrainageMethod() {
        return drainageMethod;
    }

    public void setDrainageMethod(String drainageMethod) {
        this.drainageMethod = drainageMethod;
    }

    @Column(name = "DRAINAGEBLOCK")
    public String getDrainageBlock() {
        return drainageBlock;
    }

    public void setDrainageBlock(String drainageBlock) {
        this.drainageBlock = drainageBlock;
    }

    @Column(name = "LOWEQUIPMENT")
    public String getLowEquipment() {
        return lowEquipment;
    }

    public void setLowEquipment(String lowEquipment) {
        this.lowEquipment = lowEquipment;
    }

    @Column(name = "WATERPLAN")
    public String getWaterPlan() {
        return waterPlan;
    }

    public void setWaterPlan(String waterPlan) {
        this.waterPlan = waterPlan;
    }

    @Column(name = "WATERLOSSHISTORY")
    public String getWaterLossHistory() {
        return waterLossHistory;
    }

    public void setWaterLossHistory(String waterLossHistory) {
        this.waterLossHistory = waterLossHistory;
    }

    @Column(name = "WINDPLAN")
    public String getWindPlan() {
        return windPlan;
    }

    public void setWindPlan(String windPlan) {
        this.windPlan = windPlan;
    }

    @Column(name = "RIVETCONDITION")
    public String getRivetCondition() {
        return rivetCondition;
    }

    public void setRivetCondition(String rivetCondition) {
        this.rivetCondition = rivetCondition;
    }

    @Column(name = "WINDLOSSHISTORY")
    public String getWindLossHistory() {
        return windLossHistory;
    }

    public void setWindLossHistory(String windLossHistory) {
        this.windLossHistory = windLossHistory;
    }

    @Column(name = "THUNDERCHECK")
    public String getThunderCheck() {
        return thunderCheck;
    }

    public void setThunderCheck(String thunderCheck) {
        this.thunderCheck = thunderCheck;
    }

    @Column(name = "THUNDERLOSSHISTORY")
    public String getThunderLossHistory() {
        return thunderLossHistory;
    }

    public void setThunderLossHistory(String thunderLossHistory) {
        this.thunderLossHistory = thunderLossHistory;
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