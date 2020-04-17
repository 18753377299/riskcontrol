package com.picc.riskctrl.riskins.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "riskreport_clobusiness")
public class RiskReportCloBusiness implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 风控档案编号 */
    private String riskFileNo;
    /**
     * 营业利润 正数，小数点后四位
     */
    private BigDecimal operatProfit;
    /**
     * 约定维持费用 正数，小数点后四位
     */
    private BigDecimal convenCost;
    /**
     * 审计师费用 正数，小数点后四位
     */
    private BigDecimal auditorFee;
    /**
     * 维持费用是否列明项目及金额 A 列明 B 不详
     */
    private String itemsAmountFlag;
    /** 项目及金额 */
    private String itemsAmount;
    /**
     * 是否列明保额确定方式及依据 A 列明 B 不详
     */
    private String certainWayFlag;
    /** 保额确定方式及依据 */
    private String certainWay;
    /** 三年营利和维持费用情况 */
    private String maintainCost;
    /** 最大赔偿期 */
    private String maxPayoutDate;
    /** 免赔期 */
    private String franchiseDate;
    /**
     * 保险合同主险条款 A 财产综合险/一切险 B 财产基本险 C 机器损坏险
     */
    private String mainClause;
    /**
     * 是否属于限制或禁止承保范围 A 是 B 否
     */
    private String noCoverage;
    /**
     * 近三年营业中断赔付记录 A 有 B 无
     */
    private String paymentRecords;
    /**
     * 是否有完整财务记录及会计账册 A 有 B 无
     */
    private String finaRecords;
    /**
     * 会计资料是否存放良好且有备份 A 是 B 否
     */
    private String accountData;
    /**
     * 生产线类型 A 单一 B 多样但集中 C 多样且独立
     */
    private String proLineType;
    /**
     * 是否有关键设备 A 有 B 无
     */
    private String keyEquipFlag;
    /**
     * 关键设备名称 详细解释（对正常生产有重大影响的设备）
     */
    private String keyEquip;
    /**
     * 设备类型 A 标准 B 定制
     */
    private String equipType;
    /**
     * 是否有备件 A 有 B 无
     */
    private String spareParts;
    /**
     * 制造商 A 国内 B 国外
     */
    private String manufacturer;
    /**
     * 维修商 A 国内 B 国外
     */
    private String repairman;
    /**
     * 设备供应商数量 A 单一 B 非单一
     */
    private String supplierNum;
    /**
     * 供应商供货维修水平 A 良好 B 较差
     */
    private String supplierLevel;
    /** 关键设备预计最长恢复时间 */
    private String maxRecovTime;
    /**
     * 是否有产品及市场情况 A 有 B 无
     */
    private String marketSitu;
    /**
     * 库存能保证供货需要时间 正整数校验，单位（月）
     */
    private Integer sureTime;
    /**
     * 产品销售是否存在季节性 A 是 B 否
     */
    private String seasonal;
    /**
     * 销售旺季起期 正整数校验，12月份之一
     */
    private Integer seasonStart;
    /**
     * 销售旺季止期 正整数校验，12月份之一
     */
    private Integer seasonEnd;
    /**
     * 被保险人产品竞争力 A 较强 B 一般 C 较差
     */
    private String competition;
    /**
     * 被保险人所处行业情况 A 景气 B 一般 C 不景气
     */
    private String boomCondition;
    /**
     * 是否建立有效应急预案 A 是 B 否
     */
    private String concyPlan;
    /**
     * 扩展类附加险 正整数校验,单位（个）
     */
    private Integer additionRisk;
    /**
     * 属于CBI范围条款 正整数校验,单位（个）
     */
    private Integer scopItem;
    /** 预计最长恢复时间 */
    private String maxBusiTime;
    /** 预计最大可能损失 */
    private String maxLoss;
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

    @Column(name = "OPERATPROFIT")
    public BigDecimal getOperatProfit() {
        return operatProfit;
    }

    public void setOperatProfit(BigDecimal operatProfit) {
        this.operatProfit = operatProfit;
    }

    @Column(name = "CONVENCOST")
    public BigDecimal getConvenCost() {
        return convenCost;
    }

    public void setConvenCost(BigDecimal convenCost) {
        this.convenCost = convenCost;
    }

    @Column(name = "AUDITORFEE")
    public BigDecimal getAuditorFee() {
        return auditorFee;
    }

    public void setAuditorFee(BigDecimal auditorFee) {
        this.auditorFee = auditorFee;
    }

    @Column(name = "ITEMSAMOUNTFLAG")
    public String getItemsAmountFlag() {
        return itemsAmountFlag;
    }

    public void setItemsAmountFlag(String itemsAmountFlag) {
        this.itemsAmountFlag = itemsAmountFlag;
    }

    @Column(name = "ITEMSAMOUNT")
    public String getItemsAmount() {
        return itemsAmount;
    }

    public void setItemsAmount(String itemsAmount) {
        this.itemsAmount = itemsAmount;
    }

    @Column(name = "CERTAINWAYFLAG")
    public String getCertainWayFlag() {
        return certainWayFlag;
    }

    public void setCertainWayFlag(String certainWayFlag) {
        this.certainWayFlag = certainWayFlag;
    }

    @Column(name = "CERTAINWAY")
    public String getCertainWay() {
        return certainWay;
    }

    public void setCertainWay(String certainWay) {
        this.certainWay = certainWay;
    }

    @Column(name = "MAINTAINCOST")
    public String getMaintainCost() {
        return maintainCost;
    }

    public void setMaintainCost(String maintainCost) {
        this.maintainCost = maintainCost;
    }

    @Column(name = "MAXPAYOUTDATE")
    public String getMaxPayoutDate() {
        return maxPayoutDate;
    }

    public void setMaxPayoutDate(String maxPayoutDate) {
        this.maxPayoutDate = maxPayoutDate;
    }

    @Column(name = "FRANCHISEDATE")
    public String getFranchiseDate() {
        return franchiseDate;
    }

    public void setFranchiseDate(String franchiseDate) {
        this.franchiseDate = franchiseDate;
    }

    @Column(name = "MAINCLAUSE")
    public String getMainClause() {
        return mainClause;
    }

    public void setMainClause(String mainClause) {
        this.mainClause = mainClause;
    }

    @Column(name = "NOCOVERAGE")
    public String getNoCoverage() {
        return noCoverage;
    }

    public void setNoCoverage(String noCoverage) {
        this.noCoverage = noCoverage;
    }

    @Column(name = "PAYMENTRECORDS")
    public String getPaymentRecords() {
        return paymentRecords;
    }

    public void setPaymentRecords(String paymentRecords) {
        this.paymentRecords = paymentRecords;
    }

    @Column(name = "FINARECORDS")
    public String getFinaRecords() {
        return finaRecords;
    }

    public void setFinaRecords(String finaRecords) {
        this.finaRecords = finaRecords;
    }

    @Column(name = "ACCOUNTDATA")
    public String getAccountData() {
        return accountData;
    }

    public void setAccountData(String accountData) {
        this.accountData = accountData;
    }

    @Column(name = "PROLINETYPE")
    public String getProLineType() {
        return proLineType;
    }

    public void setProLineType(String proLineType) {
        this.proLineType = proLineType;
    }

    @Column(name = "KEYEQUIPFLAG")
    public String getKeyEquipFlag() {
        return keyEquipFlag;
    }

    public void setKeyEquipFlag(String keyEquipFlag) {
        this.keyEquipFlag = keyEquipFlag;
    }

    @Column(name = "KEYEQUIP")
    public String getKeyEquip() {
        return keyEquip;
    }

    public void setKeyEquip(String keyEquip) {
        this.keyEquip = keyEquip;
    }

    @Column(name = "EQUIPTYPE")
    public String getEquipType() {
        return equipType;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    @Column(name = "SPAREPARTS")
    public String getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(String spareParts) {
        this.spareParts = spareParts;
    }

    @Column(name = "MANUFACTURER")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Column(name = "REPAIRMAN")
    public String getRepairman() {
        return repairman;
    }

    public void setRepairman(String repairman) {
        this.repairman = repairman;
    }

    @Column(name = "SUPPLIERNUM")
    public String getSupplierNum() {
        return supplierNum;
    }

    public void setSupplierNum(String supplierNum) {
        this.supplierNum = supplierNum;
    }

    @Column(name = "SUPPLIERLEVEL")
    public String getSupplierLevel() {
        return supplierLevel;
    }

    public void setSupplierLevel(String supplierLevel) {
        this.supplierLevel = supplierLevel;
    }

    @Column(name = "MAXRECOVTIME")
    public String getMaxRecovTime() {
        return maxRecovTime;
    }

    public void setMaxRecovTime(String maxRecovTime) {
        this.maxRecovTime = maxRecovTime;
    }

    @Column(name = "MARKETSITU")
    public String getMarketSitu() {
        return marketSitu;
    }

    public void setMarketSitu(String marketSitu) {
        this.marketSitu = marketSitu;
    }

    @Column(name = "SURETIME")
    public Integer getSureTime() {
        return sureTime;
    }

    public void setSureTime(Integer sureTime) {
        this.sureTime = sureTime;
    }

    @Column(name = "SEASONAL")
    public String getSeasonal() {
        return seasonal;
    }

    public void setSeasonal(String seasonal) {
        this.seasonal = seasonal;
    }

    @Column(name = "SEASONSTART")
    public Integer getSeasonStart() {
        return seasonStart;
    }

    public void setSeasonStart(Integer seasonStart) {
        this.seasonStart = seasonStart;
    }

    @Column(name = "SEASONEND")
    public Integer getSeasonEnd() {
        return seasonEnd;
    }

    public void setSeasonEnd(Integer seasonEnd) {
        this.seasonEnd = seasonEnd;
    }

    @Column(name = "COMPETITION")
    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    @Column(name = "BOOMCONDITION")
    public String getBoomCondition() {
        return boomCondition;
    }

    public void setBoomCondition(String boomCondition) {
        this.boomCondition = boomCondition;
    }

    @Column(name = "CONCYPLAN")
    public String getConcyPlan() {
        return concyPlan;
    }

    public void setConcyPlan(String concyPlan) {
        this.concyPlan = concyPlan;
    }

    @Column(name = "ADDITIONRISK")
    public Integer getAdditionRisk() {
        return additionRisk;
    }

    public void setAdditionRisk(Integer additionRisk) {
        this.additionRisk = additionRisk;
    }

    @Column(name = "SCOPITEM")
    public Integer getScopItem() {
        return scopItem;
    }

    public void setScopItem(Integer scopItem) {
        this.scopItem = scopItem;
    }

    @Column(name = "MAXBUSITIME")
    public String getMaxBusiTime() {
        return maxBusiTime;
    }

    public void setMaxBusiTime(String maxBusiTime) {
        this.maxBusiTime = maxBusiTime;
    }

    @Column(name = "MAXLOSS")
    public String getMaxLoss() {
        return maxLoss;
    }

    public void setMaxLoss(String maxLoss) {
        this.maxLoss = maxLoss;
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
