package com.picc.riskctrl.riskins.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "riskreport_main")
public class RiskReportMain implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 风险档案编号 清分为null
     */
    private String riskFileNo;
    /** 险类代码 */
    private String classCode;
    /** 产品代码 */
    private String riskCode;
    /** 险类代码 */
    private String classCName;
    /** 产品代码 */
    private String riskCName;
    /** 归属机构 */
    private String comCode;
    /** 归属机构中文 */
    private String comCodeCName;
    /**
     * 存货比例 decimal(5,2) 如存货比例为50.12%，则传50.12
     */
    private BigDecimal stockRate;
    /**
     * 风控报告模板 001:专职版 002:兼职版 004:机器损失险 005:营业中断险 006: 火灾风险排查专业版 007： 火灾风险排查简化版
     */
    private String riskModel;
    /**
     * 被保险人类型 1 个人 2 团体
     */
    private String insuredType;
    /** 被保险人代码 */
    private String insuredCode;
    /** 被保险人姓名/企业名称 */
    private String insuredName;
    /** 保险财产地址(省) */
    private String addressProvince;
    /** 保险财产地址(省)代码 */
    private String addressProvinceCode;
    /** 保险财产地址(市) */
    private String addressCity;
    /** 保险财产地址(市)代码 */
    private String addressCityCode;
    /** 保险财产地址(区或县) */
    private String addressCounty;
    /** 保险财产地址(区或县)代码 */
    private String addressCountyCode;
    /** 省市县组合地址 */
    private String address;
    /** 保险财产地址(详细) */
    private String addressDetail;
    /** 邮政编码 */
    private String postCode;
    /** 国民经济行业代码 */
    private String businessSource;
    /** 国民经济行业代码 */
    private String businessSourceCName;
    /** 行业类型代码 */
    private String businessClass;
    /**
     * 被保险人/企业性质 1 行政事业单位及人民团体 2 国有企业 3 民营企业 4 外商投资企业（除台资） 5 台资企业
     */
    private String unitNature;
    /**
     * 以往承保情况 1 客户首次投保 2 续保 3 由其他公司转保
     */
    private String underwriteStatus;
    /** 操作人员代码 */
    private String operatorCode;
    /** 操作人员中文 */
    private String operator;
    /** 操作人员集团统一工号 */
    private String operatorCodeUni;
    /**
     * 三年内历史赔付损失记录 1 有 0 无
     */
    private String historyLoseFlag;
    /** 总保险金额 */
    private BigDecimal sumAmount;
    /**
     * 提交核保时间 清分为null
     */
    private Date undwrtSubmitDate;
    /**
     * 核保标志 清分传0
     */
    private String underwriteFlag;
    /**
     * 核保通过时间 清分为null
     */
    private Date underwriteDate;
    /**
     * 核保人员代码 清分为null
     */
    private String underwriteCode;
    /**
     * 核保人员姓名 清分为null
     */
    private String underwriteName;
    /** 查勘机构 */
    private String explorecomCode;
    /** 查勘机构中文 */
    private String explorecomCodeCName;
    /** 查勘人 */
    private String explorer;
    /** 查勘人中文 */
    private String explorerCName;
    /** 查勘人集团统一工号 */
    private String explorerUni;
    /** 查勘日期 */
    private Date exploreDate;
    /** 制作日期 */
    private Date madeDate;
    /**
     * 查勘类别 1 初勘 2 复勘
     */
    private String exploreType;
    /** 初勘风控报告编号 */
    private String lastRiskFileNo;
    /**
     * 投保附加险情况 A 扩展盗窃、抢劫责任 B 扩展供应中断责任 C 扩展露天堆放责任
     */
    private String addRisk;
    /** 信息补充 */
    private String explain;
    /** 单次事故可能最大损失 */
    private BigDecimal onceAccidentLosest;
    /** 危险单位数量 */
    private Integer riskUnitNumber;
    /** 危险单位划分说明 */
    private String riskUnitExplain;
    /** 损失率 */
    private BigDecimal loseRate;
    /**
     * 突出风险 清分为null
     */
    private String highlightRisk;
    /**
     * 综合得分 清分为null
     */
    private BigDecimal score;
    /*** 现场评估补充说明 *or***风险隐患说明 */
    private String supplementAppraisal;
    /**
     * 风控建议 清分为null
     */
    private String riskSuggest;
    /** 其他意见 */
    private String othSuggest;
    /** 补充说明 * or **防灾防损建议 */
    private String addMessage;
    /** 照片编号 */
    private String archivesNo;
    /** 插入时间 */
    private Date insertTimeForHis;
    /** 更新时间 */
    private Date operateTimeForHis;
    /** 请求端标志 0-web 1-Android 2-IOS */
    private String mobileFlag;
    /**
     * 所使用的权重表id 清分为null
     */
    private Integer utiWeightId;
    /**
     * 所使用的流程实例id 清分为null
     */
    private String executionId;
    /** 2000坐标系精度 */
    private BigDecimal pointx2000;
    /** 2000坐标系纬度 */
    private BigDecimal pointy2000;
    /** 02坐标系精度 */
    private BigDecimal pointx02;
    /** 02坐标系纬度 */
    private BigDecimal pointy02;
    /** 打回原因 */
    private String repulsesugggest;
    /** 风险评估等级 */
    private String valuation;
    /** 保单号 */
    private String policyNo;
    /** 投保单号 */
    private String proposalNo;
    /** 风控地址信息 */
    private List<RiskReportAddress> riskReportAddressList = new ArrayList<RiskReportAddress>(0);
    /** 风控机器损失险风险评估 */
    private List<RiskReportMachine> riskReportMachineList = new ArrayList<RiskReportMachine>(0);
    /** 风控营业中断险风险评估 */
    private List<RiskReportCloBusiness> riskReportCloBusinessList = new ArrayList<RiskReportCloBusiness>(0);
    /** 风控历史损失 */
    private List<RiskReportClaim> riskReportClaimList = new ArrayList<RiskReportClaim>(0);
    /** 风控建筑 */
    private List<RiskReportConstruct> riskReportConstructList = new ArrayList<RiskReportConstruct>(0);
    /** 风控占用性质 */
    private List<RiskReportOccupation> riskReportOccupationList = new ArrayList<RiskReportOccupation>(0);
    /** 风控保护措施 */
    private List<RiskReportProtection> riskReportProtectionList = new ArrayList<RiskReportProtection>(0);
    /** 风控环境 */
    private List<RiskReportEnvironment> riskReportEnvironmentList = new ArrayList<RiskReportEnvironment>(0);
    /** 风控附加扩展盗窃、抢劫责任 */
    private List<RiskReportTheft> riskReportTheftList = new ArrayList<RiskReportTheft>(0);
    /** 风控附加扩展供应中断责任 */
    private List<RiskReportInterrupt> riskReportInterruptList = new ArrayList<RiskReportInterrupt>(0);
    /** 风控风险值 */
    private List<RiskReportAssess> riskReportAssessList = new ArrayList<RiskReportAssess>(0);
    /** 风控建筑详细信息 */
    private List<RiskReportConstructInfo> riskReportConstructInfoList = new ArrayList<RiskReportConstructInfo>(0);
    /** 风控附加扩展露天堆放 */
    private List<RiskReportAirStorage> riskReportAirStorageList = new ArrayList<RiskReportAirStorage>(0);
    /** 影像资料 */
    private List<RiskReportPicture> riskReportPictureList = new ArrayList<RiskReportPicture>(0);
    /** 风控火灾风险排查 */
    private List<RiskReportFireDanger> riskReportFireDangerList = new ArrayList<RiskReportFireDanger>(0);

    @Id
    @Column(name = "RISKFILENO")
    public String getRiskFileNo() {
        return riskFileNo;
    }

    public void setRiskFileNo(String riskFileNo) {
        this.riskFileNo = riskFileNo;
    }

    @Column(name = "CLASSCODE")
    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    @Column(name = "RISKCODE")
    public String getRiskCode() {
        return riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    @Column(name = "COMCODE")
    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    @Column(name = "STOCKRATE")
    public BigDecimal getStockRate() {
        return stockRate;
    }

    public void setStockRate(BigDecimal stockRate) {
        this.stockRate = stockRate;
    }

    @Column(name = "INSUREDNAME")
    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    @Column(name = "BUSINESSSOURCE")
    public String getBusinessSource() {
        return businessSource;
    }

    public void setBusinessSource(String businessSource) {
        this.businessSource = businessSource;
    }

    @Column(name = "BUSINESSCLASS")
    public String getBusinessClass() {
        return businessClass;
    }

    public void setBusinessClass(String businessClass) {
        this.businessClass = businessClass;
    }

    @Column(name = "UNITNATURE")
    public String getUnitNature() {
        return unitNature;
    }

    public void setUnitNature(String unitNature) {
        this.unitNature = unitNature;
    }

    @Column(name = "UNDERWRITESTATUS")
    public String getUnderwriteStatus() {
        return underwriteStatus;
    }

    public void setUnderwriteStatus(String underwriteStatus) {
        this.underwriteStatus = underwriteStatus;
    }

    @Column(name = "OPERATORCODE")
    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    @Transient
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "OPERATORCODEUNI")
    public String getOperatorCodeUni() {
        return operatorCodeUni;
    }

    public void setOperatorCodeUni(String operatorCodeUni) {
        this.operatorCodeUni = operatorCodeUni;
    }

    @Column(name = "HISTORYLOSEFLAG")
    public String getHistoryLoseFlag() {
        return historyLoseFlag;
    }

    public void setHistoryLoseFlag(String historyLoseFlag) {
        this.historyLoseFlag = historyLoseFlag;
    }

    @Column(name = "SUMAMOUNT")
    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    @Column(name = "UNDWRTSUBMITDATE")
    public Date getUndwrtSubmitDate() {
        return undwrtSubmitDate;
    }

    public void setUndwrtSubmitDate(Date undwrtSubmitDate) {
        this.undwrtSubmitDate = undwrtSubmitDate;
    }

    @Column(name = "UNDERWRITEFLAG")
    public String getUnderwriteFlag() {
        return underwriteFlag;
    }

    public void setUnderwriteFlag(String underwriteFlag) {
        this.underwriteFlag = underwriteFlag;
    }

    @Column(name = "UNDERWRITEDATE")
    public Date getUnderwriteDate() {
        return underwriteDate;
    }

    public void setUnderwriteDate(Date underwriteDate) {
        this.underwriteDate = underwriteDate;
    }

    @Column(name = "UNDERWRITECODE")
    public String getUnderwriteCode() {
        return underwriteCode;
    }

    public void setUnderwriteCode(String underwriteCode) {
        this.underwriteCode = underwriteCode;
    }

    @Column(name = "UNDERWRITENAME")
    public String getUnderwriteName() {
        return underwriteName;
    }

    public void setUnderwriteName(String underwriteName) {
        this.underwriteName = underwriteName;
    }

    @Column(name = "EXPLORECOMCODE")
    public String getExplorecomCode() {
        return explorecomCode;
    }

    public void setExplorecomCode(String explorecomCode) {
        this.explorecomCode = explorecomCode;
    }

    @Column(name = "EXPLORER")
    public String getExplorer() {
        return explorer;
    }

    public void setExplorer(String explorer) {
        this.explorer = explorer;
    }

    @Column(name = "EXPLORERUNI")
    public String getExplorerUni() {
        return explorerUni;
    }

    public void setExplorerUni(String explorerUni) {
        this.explorerUni = explorerUni;
    }

    @Column(name = "EXPLOREDATE")
    @Temporal(TemporalType.DATE)
    public Date getExploreDate() {
        return exploreDate;
    }

    public void setExploreDate(Date exploreDate) {
        this.exploreDate = exploreDate;
    }

    @Column(name = "MADEDATE")
    public Date getMadeDate() {
        return madeDate;
    }

    public void setMadeDate(Date madeDate) {
        this.madeDate = madeDate;
    }

    @Column(name = "EXPLORETYPE")
    public String getExploreType() {
        return exploreType;
    }

    public void setExploreType(String exploreType) {
        this.exploreType = exploreType;
    }

    @Column(name = "LASTRISKFILENO")
    public String getLastRiskFileNo() {
        return lastRiskFileNo;
    }

    public void setLastRiskFileNo(String lastRiskFileNo) {
        this.lastRiskFileNo = lastRiskFileNo;
    }

    @Column(name = "ADDRISK")
    public String getAddRisk() {
        return addRisk;
    }

    public void setAddRisk(String addRisk) {
        this.addRisk = addRisk;
    }

    @Column(name = "CUSTOMEREXPLAIN")
    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Column(name = "ONCEACCIDENTLOSEST")
    public BigDecimal getOnceAccidentLosest() {
        return onceAccidentLosest;
    }

    public void setOnceAccidentLosest(BigDecimal onceAccidentLosest) {
        this.onceAccidentLosest = onceAccidentLosest;
    }

    @Column(name = "RISKUNITNUMBER")
    public Integer getRiskUnitNumber() {
        return riskUnitNumber;
    }

    public void setRiskUnitNumber(Integer riskUnitNumber) {
        this.riskUnitNumber = riskUnitNumber;
    }

    @Column(name = "RISKUNITEXPLAIN")
    public String getRiskUnitExplain() {
        return riskUnitExplain;
    }

    public void setRiskUnitExplain(String riskUnitExplain) {
        this.riskUnitExplain = riskUnitExplain;
    }

    @Column(name = "LOSERATE")
    public BigDecimal getLoseRate() {
        return loseRate;
    }

    public void setLoseRate(BigDecimal loseRate) {
        this.loseRate = loseRate;
    }

    @Column(name = "HIGHLIGHTRISK")
    public String getHighlightRisk() {
        return highlightRisk;
    }

    public void setHighlightRisk(String highlightRisk) {
        this.highlightRisk = highlightRisk;
    }

    @Column(name = "SCORE")
    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    @Column(name = "SUPPLEMENTAPPRAISAL")
    public String getSupplementAppraisal() {
        return supplementAppraisal;
    }

    public void setSupplementAppraisal(String supplementAppraisal) {
        this.supplementAppraisal = supplementAppraisal;
    }

    @Column(name = "RISKSUGGEST")
    public String getRiskSuggest() {
        return riskSuggest;
    }

    public void setRiskSuggest(String riskSuggest) {
        this.riskSuggest = riskSuggest;
    }

    @Column(name = "OTHSUGGEST")
    public String getOthSuggest() {
        return othSuggest;
    }

    public void setOthSuggest(String othSuggest) {
        this.othSuggest = othSuggest;
    }

    // @Column(name = "INSERTTIMEFORHIS", insertable = false, updatable = false)
    @CreatedDate
    @Column(name = "INSERTTIMEFORHIS", updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    // @Column(name = "OPERATETIMEFORHIS", insertable = false)
    @LastModifiedDate
    @Column(name = "OPERATETIMEFORHIS")
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }

    @Column(name = "RISKMODEL")
    public String getRiskModel() {
        return riskModel;
    }

    public void setRiskModel(String riskModel) {
        this.riskModel = riskModel;
    }

    @Column(name = "INSUREDTYPE")
    public String getInsuredType() {
        return insuredType;
    }

    public void setInsuredType(String insuredType) {
        this.insuredType = insuredType;
    }

    @Column(name = "INSUREDCODE")
    public String getInsuredCode() {
        return insuredCode;
    }

    public void setInsuredCode(String insuredCode) {
        this.insuredCode = insuredCode;
    }

    @Column(name = "ADDRESSPROVINCE")
    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    @Column(name = "ADDRESSCITY")
    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    @Column(name = "ADDRESSCOUNTY")
    public String getAddressCounty() {
        return addressCounty;
    }

    public void setAddressCounty(String addressCounty) {
        this.addressCounty = addressCounty;
    }

    @Column(name = "ADDRESSDETAIL")
    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    @Column(name = "POSTCODE")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Column(name = "ARCHIVESNO")
    public String getArchivesNo() {
        return archivesNo;
    }

    public void setArchivesNo(String archivesNo) {
        this.archivesNo = archivesNo;
    }

    @Column(name = "ADDMESSAGE")
    public String getAddMessage() {
        return addMessage;
    }

    public void setAddMessage(String addMessage) {
        this.addMessage = addMessage;
    }

    @Column(name = "MOBILEFLAG")
    public String getMobileFlag() {
        return mobileFlag;
    }

    public void setMobileFlag(String mobileFlag) {
        this.mobileFlag = mobileFlag;
    }

    @Column(name = "UTIWEIGHTID")
    public Integer getUtiWeightId() {
        return utiWeightId;
    }

    public void setUtiWeightId(Integer utiWeightId) {
        this.utiWeightId = utiWeightId;
    }

    @Column(name = "EXECUTIONID")
    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportAddress> getRiskReportAddressList() {
        return riskReportAddressList;
    }

    public void setRiskReportAddressList(List<RiskReportAddress> riskReportAddressList) {
        this.riskReportAddressList = riskReportAddressList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportMachine> getRiskReportMachineList() {
        return riskReportMachineList;
    }

    public void setRiskReportMachineList(List<RiskReportMachine> riskReportMachineList) {
        this.riskReportMachineList = riskReportMachineList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportCloBusiness> getRiskReportCloBusinessList() {
        return riskReportCloBusinessList;
    }

    public void setRiskReportCloBusinessList(List<RiskReportCloBusiness> riskReportCloBusinessList) {
        this.riskReportCloBusinessList = riskReportCloBusinessList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportClaim> getRiskReportClaimList() {
        return riskReportClaimList;
    }

    public void setRiskReportClaimList(List<RiskReportClaim> riskReportClaimList) {
        this.riskReportClaimList = riskReportClaimList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportConstruct> getRiskReportConstructList() {
        return riskReportConstructList;
    }

    public void setRiskReportConstructList(List<RiskReportConstruct> riskReportConstructList) {
        this.riskReportConstructList = riskReportConstructList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportOccupation> getRiskReportOccupationList() {
        return riskReportOccupationList;
    }

    public void setRiskReportOccupationList(List<RiskReportOccupation> riskReportOccupationList) {
        this.riskReportOccupationList = riskReportOccupationList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportProtection> getRiskReportProtectionList() {
        return riskReportProtectionList;
    }

    public void setRiskReportProtectionList(List<RiskReportProtection> riskReportProtectionList) {
        this.riskReportProtectionList = riskReportProtectionList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportEnvironment> getRiskReportEnvironmentList() {
        return riskReportEnvironmentList;
    }

    public void setRiskReportEnvironmentList(List<RiskReportEnvironment> riskReportEnvironmentList) {
        this.riskReportEnvironmentList = riskReportEnvironmentList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportTheft> getRiskReportTheftList() {
        return riskReportTheftList;
    }

    public void setRiskReportTheftList(List<RiskReportTheft> riskReportTheftList) {
        this.riskReportTheftList = riskReportTheftList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportInterrupt> getRiskReportInterruptList() {
        return riskReportInterruptList;
    }

    public void setRiskReportInterruptList(List<RiskReportInterrupt> riskReportInterruptList) {
        this.riskReportInterruptList = riskReportInterruptList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportAssess> getRiskReportAssessList() {
        return riskReportAssessList;
    }

    public void setRiskReportAssessList(List<RiskReportAssess> riskReportAssessList) {
        this.riskReportAssessList = riskReportAssessList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportConstructInfo> getRiskReportConstructInfoList() {
        return riskReportConstructInfoList;
    }

    public void setRiskReportConstructInfoList(List<RiskReportConstructInfo> riskReportConstructInfoList) {
        this.riskReportConstructInfoList = riskReportConstructInfoList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportAirStorage> getRiskReportAirStorageList() {
        return riskReportAirStorageList;
    }

    public void setRiskReportAirStorageList(List<RiskReportAirStorage> riskReportAirStorageList) {
        this.riskReportAirStorageList = riskReportAirStorageList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportPicture> getRiskReportPictureList() {
        return riskReportPictureList;
    }

    public void setRiskReportPictureList(List<RiskReportPicture> riskReportPictureList) {
        this.riskReportPictureList = riskReportPictureList;
    }

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportMain")
    public List<RiskReportFireDanger> getRiskReportFireDangerList() {
        return riskReportFireDangerList;
    }

    public void setRiskReportFireDangerList(List<RiskReportFireDanger> riskReportFireDangerList) {
        this.riskReportFireDangerList = riskReportFireDangerList;
    }

    @Transient
    public String getAddressProvinceCode() {
        return addressProvinceCode;
    }

    public void setAddressProvinceCode(String addressProvinceCode) {
        this.addressProvinceCode = addressProvinceCode;
    }

    @Transient
    public String getAddressCityCode() {
        return addressCityCode;
    }

    public void setAddressCityCode(String addressCityCode) {
        this.addressCityCode = addressCityCode;
    }

    @Transient
    public String getClassCName() {
        return classCName;
    }

    public void setClassCName(String classCName) {
        this.classCName = classCName;
    }

    @Transient
    public String getRiskCName() {
        return riskCName;
    }

    public void setRiskCName(String riskCName) {
        this.riskCName = riskCName;
    }

    @Transient
    public String getComCodeCName() {
        return comCodeCName;
    }

    public void setComCodeCName(String comCodeCName) {
        this.comCodeCName = comCodeCName;
    }

    @Transient
    public String getExplorerCName() {
        return explorerCName;
    }

    public void setExplorerCName(String explorerCName) {
        this.explorerCName = explorerCName;
    }

    @Transient
    public String getExplorecomCodeCName() {
        return explorecomCodeCName;
    }

    public void setExplorecomCodeCName(String explorecomCodeCName) {
        this.explorecomCodeCName = explorecomCodeCName;
    }

    @Transient
    public String getBusinessSourceCName() {
        return businessSourceCName;
    }

    public void setBusinessSourceCName(String businessSourceCName) {
        this.businessSourceCName = businessSourceCName;
    }

    @Transient
    public String getAddressCountyCode() {
        return addressCountyCode;
    }

    public void setAddressCountyCode(String addressCountyCode) {
        this.addressCountyCode = addressCountyCode;
    }

    @Transient
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "REPULSESUGGGEST")
    public String getRepulsesugggest() {
        return repulsesugggest;
    }

    public void setRepulsesugggest(String repulsesugggest) {
        this.repulsesugggest = repulsesugggest;
    }

    @Column(name = "pointx2000")
    public BigDecimal getPointx2000() {
        return pointx2000;
    }

    public void setPointx2000(BigDecimal pointx2000) {
        this.pointx2000 = pointx2000;
    }

    @Column(name = "pointy2000")
    public BigDecimal getPointy2000() {
        return pointy2000;
    }

    public void setPointy2000(BigDecimal pointy2000) {
        this.pointy2000 = pointy2000;
    }

    @Column(name = "pointx02")
    public BigDecimal getPointx02() {
        return pointx02;
    }

    public void setPointx02(BigDecimal pointx02) {
        this.pointx02 = pointx02;
    }

    @Column(name = "pointy02")
    public BigDecimal getPointy02() {
        return pointy02;
    }

    public void setPointy02(BigDecimal pointy02) {
        this.pointy02 = pointy02;
    }

    @Column(name = "VALUATION")
    public String getValuation() {
        return valuation;
    }

    public void setValuation(String valuation) {
        this.valuation = valuation;
    }

    @Column(name = "policyno")
    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    @Column(name = "proposalno")
    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

}
