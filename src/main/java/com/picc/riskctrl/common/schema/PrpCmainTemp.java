package com.picc.riskctrl.common.schema;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "prpcmaintemp")
public class PrpCmainTemp implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 投保单号码
     */
    private String proposalNo;
    /**
     * 保单号
     */
    private String policyNo;
    /**
     * 险类代码
     */
    private String classCode;
    /**
     * 险种代码
     */
    private String riskCode;
    /**
     * 项目代码
     */
    private String projectCode;
    /**
     * 合同号
     */
    private String contractNo;
    /**
     * 保单种类
     */
    private String policySort;
    /**
     * 业务性质
     */
    private String businessNature;
    /**
     * 语种
     */
    private String language;
    /**
     * 保单类型
     */
    private String policyType;
    /**
     * 涉农标志
     */
    private String agriFlag;
    /**
     * OperateDate
     */
    private Date operateDate;
    /**
     * 起运日期
     */
    private Date startDate;
    /**
     * 起保小时
     */
    private Integer startHour;
    /**
     * 生效终止日期
     */
    private Date endDate;
    /**
     * 终保小时
     */
    private Integer endHour;
    /**
     * 净费率
     */
    private BigDecimal pureRate;
    /**
     * 手续费率/浮动费率
     */
    private BigDecimal disRate;
    /**
     * 折扣率
     */
    private BigDecimal discount;
    /**
     * 币别
     */
    private String currency;
    /**
     * 货物价值
     */
    private BigDecimal sumValue;
    /**
     * 总保额
     */
    private BigDecimal sumAmount;
    /**
     * 总折扣金额
     */
    private BigDecimal sumDiscount;
    /**
     * 总保费
     */
    private BigDecimal sumPremium;
    /**
     * 总附加险保费
     */
    private BigDecimal sumSubPrem;
    /**
     * 被保险总数量/人数/户数
     */
    private Integer sumQuantity;
    /**
     * 投保人数
     */
    private Integer insuredCount;
    /**
     * 保单数量
     */
    private Integer policyCount;
    /**
     * 司法管辖
     */
    private String judicalScope;
    /**
     * 是否自动转帐续保标志
     */
    private String autoTransRenewFlag;
    /**
     * 争议解决方式
     */
    private String argueSolution;
    /**
     * 仲裁委员会名称
     */
    private String arbitBoardName;
    /**
     * 约定分期交费次数
     */
    private Integer payTimes;
    /**
     * 出单机构
     */
    private String makeCom;
    /**
     * 签单地点
     */
    private String operateSite;
    /**
     * 归属机构
     */
    private String comCode;
    /**
     * 经办人代码
     */
    private String handlerCode;
    /**
     * 归属人
     */
    private String handler1Code;
    /**
     * 复核人代码
     */
    private String approverCode;
    /**
     * 初审标志
     */
    private String checkFlag;
    /**
     * 初审员
     */
    private String checkUpCode;
    /**
     * 初审意见
     */
    private String checkOpinion;
    /**
     * 最终核保人代码
     */
    private String underWriteCode;
    /**
     * 最终核保人名称
     */
    private String underWriteName;
    /**
     * 操作员代码
     */
    private String operatorCode;
    /**
     * 报表数据生成日期
     */
    private Date inputTime;
    /**
     * 核保完成日期
     */
    private Date underWriteEndDate;
    /**
     * 保单统计年月
     */
    private Date statisticsYM;
    /**
     * 代理人代码
     */
    private String agentCode;
    /**
     * 联共保标志
     */
    private String coinsFlag;
    /**
     * 商业分保标志
     */
    private String reinsFlag;
    /**
     * 统保标志
     */
    private String allinsFlag;
    /**
     * 核保标志
     */
    private String underWriteFlag;
    /**
     * 见费出单标志
     */
    private String jfeeFlag;
    /**
     * 政策性业务方案代码
     */
    private String policyRelCode;
    /**
     * 政策性业务方案名称
     */
    private String policyRelName;
    /**
     * 是否迁移数据
     */
    private String dmFlag;
    /**
     * 输入标志
     */
    private String inputFlag;
    /**
     * 提交核保时间
     */
    private Date undwrtSubmitDate;
    /**
     * 其它标志字段
     */
    private String othFlag;
    /**
     * 备注
     */
    private String remark;
    /**
     * 复核员
     */
    private String checkCode;
    /**
     * 短信转存后是否删除标记
     */
    private String flag;
    /**
     * 插入时间
     */
    private Date insertTimeForHis;
    /**
     * 更新时间
     */
    private Date operateTimeForHis;
    /**
     * 保险费支付办法
     */
    private String payMode;
    /**
     * 交叉销售标志
     */
    private String crossFlag;
    /**
     * 总税额
     */
    private BigDecimal sumTaxFee;
    /**
     * 总净保费
     */
    private BigDecimal sumNetPremium;


    public PrpCmainTemp() {
    }

    /**
     * 投保单号码
     */
    @Id
    @Column(name = "proposalno")
    public String getProposalNo() {
        return this.proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

    /**
     * 保单号
     */

    @Column(name = "policyno")
    public String getPolicyNo() {
        return this.policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    /**
     * 险类代码
     */

    @Column(name = "classcode")
    public String getClassCode() {
        return this.classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    /**
     * 险种代码
     */

    @Column(name = "riskcode")
    public String getRiskCode() {
        return this.riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    /**
     * 项目代码
     */

    @Column(name = "projectcode")
    public String getProjectCode() {
        return this.projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * 合同号
     */

    @Column(name = "contractno")
    public String getContractNo() {
        return this.contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * 保单种类
     */

    @Column(name = "policysort")
    public String getPolicySort() {
        return this.policySort;
    }

    public void setPolicySort(String policySort) {
        this.policySort = policySort;
    }

    /**
     * 业务性质
     */

    @Column(name = "businessnature")
    public String getBusinessNature() {
        return this.businessNature;
    }

    public void setBusinessNature(String businessNature) {
        this.businessNature = businessNature;
    }

    /**
     * 语种
     */

    @Column(name = "language")
    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 保单类型
     */

    @Column(name = "policytype")
    public String getPolicyType() {
        return this.policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    /**
     * 涉农标志
     */

    @Column(name = "agriflag")
    public String getAgriFlag() {
        return this.agriFlag;
    }

    public void setAgriFlag(String agriFlag) {
        this.agriFlag = agriFlag;
    }

    /**
     * OperateDate
     */

    @Column(name = "operatedate")
    public Date getOperateDate() {
        return this.operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    /**
     * 起运日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "startdate")
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 起保小时
     */

    @Column(name = "starthour")
    public Integer getStartHour() {
        return this.startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    /**
     * 生效终止日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "enddate")
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 终保小时
     */

    @Column(name = "endhour")
    public Integer getEndHour() {
        return this.endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    /**
     * 净费率
     */

    @Column(name = "purerate")
    public BigDecimal getPureRate() {
        return this.pureRate;
    }

    public void setPureRate(BigDecimal pureRate) {
        this.pureRate = pureRate;
    }

    /**
     * 手续费率/浮动费率
     */

    @Column(name = "disrate")
    public BigDecimal getDisRate() {
        return this.disRate;
    }

    public void setDisRate(BigDecimal disRate) {
        this.disRate = disRate;
    }

    /**
     * 折扣率
     */

    @Column(name = "discount")
    public BigDecimal getDiscount() {
        return this.discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 币别
     */

    @Column(name = "currency")
    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 货物价值
     */

    @Column(name = "sumvalue")
    public BigDecimal getSumValue() {
        return this.sumValue;
    }

    public void setSumValue(BigDecimal sumValue) {
        this.sumValue = sumValue;
    }

    /**
     * 总保额
     */

    @Column(name = "sumamount")
    public BigDecimal getSumAmount() {
        return this.sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    /**
     * 总折扣金额
     */

    @Column(name = "sumdiscount")
    public BigDecimal getSumDiscount() {
        return this.sumDiscount;
    }

    public void setSumDiscount(BigDecimal sumDiscount) {
        this.sumDiscount = sumDiscount;
    }

    /**
     * 总保费
     */

    @Column(name = "sumpremium")
    public BigDecimal getSumPremium() {
        return this.sumPremium;
    }

    public void setSumPremium(BigDecimal sumPremium) {
        this.sumPremium = sumPremium;
    }

    /**
     * 总附加险保费
     */

    @Column(name = "sumsubprem")
    public BigDecimal getSumSubPrem() {
        return this.sumSubPrem;
    }

    public void setSumSubPrem(BigDecimal sumSubPrem) {
        this.sumSubPrem = sumSubPrem;
    }

    /**
     * 被保险总数量/人数/户数
     */

    @Column(name = "sumquantity")
    public Integer getSumQuantity() {
        return this.sumQuantity;
    }

    public void setSumQuantity(Integer sumQuantity) {
        this.sumQuantity = sumQuantity;
    }

    /**
     * 投保人数
     */

    @Column(name = "insuredcount")
    public Integer getInsuredCount() {
        return this.insuredCount;
    }

    public void setInsuredCount(Integer insuredCount) {
        this.insuredCount = insuredCount;
    }

    /**
     * 保单数量
     */

    @Column(name = "policycount")
    public Integer getPolicyCount() {
        return this.policyCount;
    }

    public void setPolicyCount(Integer policyCount) {
        this.policyCount = policyCount;
    }

    /**
     * 司法管辖
     */

    @Column(name = "judicalscope")
    public String getJudicalScope() {
        return this.judicalScope;
    }

    public void setJudicalScope(String judicalScope) {
        this.judicalScope = judicalScope;
    }

    /**
     * 是否自动转帐续保标志
     */

    @Column(name = "autotransrenewflag")
    public String getAutoTransRenewFlag() {
        return this.autoTransRenewFlag;
    }

    public void setAutoTransRenewFlag(String autoTransRenewFlag) {
        this.autoTransRenewFlag = autoTransRenewFlag;
    }

    /**
     * 争议解决方式
     */

    @Column(name = "arguesolution")
    public String getArgueSolution() {
        return this.argueSolution;
    }

    public void setArgueSolution(String argueSolution) {
        this.argueSolution = argueSolution;
    }

    /**
     * 仲裁委员会名称
     */

    @Column(name = "arbitboardname")
    public String getArbitBoardName() {
        return this.arbitBoardName;
    }

    public void setArbitBoardName(String arbitBoardName) {
        this.arbitBoardName = arbitBoardName;
    }

    /**
     * 约定分期交费次数
     */

    @Column(name = "paytimes")
    public Integer getPayTimes() {
        return this.payTimes;
    }

    public void setPayTimes(Integer payTimes) {
        this.payTimes = payTimes;
    }

    /**
     * 出单机构
     */

    @Column(name = "makecom")
    public String getMakeCom() {
        return this.makeCom;
    }

    public void setMakeCom(String makeCom) {
        this.makeCom = makeCom;
    }

    /**
     * 签单地点
     */

    @Column(name = "operatesite")
    public String getOperateSite() {
        return this.operateSite;
    }

    public void setOperateSite(String operateSite) {
        this.operateSite = operateSite;
    }

    /**
     * 归属机构
     */

    @Column(name = "comcode")
    public String getComCode() {
        return this.comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    /**
     * 经办人代码
     */

    @Column(name = "handlercode")
    public String getHandlerCode() {
        return this.handlerCode;
    }

    public void setHandlerCode(String handlerCode) {
        this.handlerCode = handlerCode;
    }

    /**
     * 归属人
     */

    @Column(name = "handler1code")
    public String getHandler1Code() {
        return this.handler1Code;
    }

    public void setHandler1Code(String handler1Code) {
        this.handler1Code = handler1Code;
    }

    /**
     * 复核人代码
     */

    @Column(name = "approvercode")
    public String getApproverCode() {
        return this.approverCode;
    }

    public void setApproverCode(String approverCode) {
        this.approverCode = approverCode;
    }

    /**
     * 初审标志
     */

    @Column(name = "checkflag")
    public String getCheckFlag() {
        return this.checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    /**
     * 初审员
     */

    @Column(name = "checkupcode")
    public String getCheckUpCode() {
        return this.checkUpCode;
    }

    public void setCheckUpCode(String checkUpCode) {
        this.checkUpCode = checkUpCode;
    }

    /**
     * 初审意见
     */

    @Column(name = "checkopinion")
    public String getCheckOpinion() {
        return this.checkOpinion;
    }

    public void setCheckOpinion(String checkOpinion) {
        this.checkOpinion = checkOpinion;
    }

    /**
     * 最终核保人代码
     */

    @Column(name = "underwritecode")
    public String getUnderWriteCode() {
        return this.underWriteCode;
    }

    public void setUnderWriteCode(String underWriteCode) {
        this.underWriteCode = underWriteCode;
    }

    /**
     * 最终核保人名称
     */

    @Column(name = "underwritename")
    public String getUnderWriteName() {
        return this.underWriteName;
    }

    public void setUnderWriteName(String underWriteName) {
        this.underWriteName = underWriteName;
    }

    /**
     * 操作员代码
     */

    @Column(name = "operatorcode")
    public String getOperatorCode() {
        return this.operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    /**
     * 报表数据生成日期
     */

    @Column(name = "inputtime")
    public Date getInputTime() {
        return this.inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    /**
     * 核保完成日期
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "underwriteenddate")
    public Date getUnderWriteEndDate() {
        return this.underWriteEndDate;
    }

    public void setUnderWriteEndDate(Date underWriteEndDate) {
        this.underWriteEndDate = underWriteEndDate;
    }

    /**
     * 保单统计年月
     */

    @Column(name = "statisticsym")
    public Date getStatisticsYM() {
        return this.statisticsYM;
    }

    public void setStatisticsYM(Date statisticsYM) {
        this.statisticsYM = statisticsYM;
    }

    /**
     * 代理人代码
     */

    @Column(name = "agentcode")
    public String getAgentCode() {
        return this.agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    /**
     * 联共保标志
     */

    @Column(name = "coinsflag")
    public String getCoinsFlag() {
        return this.coinsFlag;
    }

    public void setCoinsFlag(String coinsFlag) {
        this.coinsFlag = coinsFlag;
    }

    /**
     * 商业分保标志
     */

    @Column(name = "reinsflag")
    public String getReinsFlag() {
        return this.reinsFlag;
    }

    public void setReinsFlag(String reinsFlag) {
        this.reinsFlag = reinsFlag;
    }

    /**
     * 统保标志
     */

    @Column(name = "allinsflag")
    public String getAllinsFlag() {
        return this.allinsFlag;
    }

    public void setAllinsFlag(String allinsFlag) {
        this.allinsFlag = allinsFlag;
    }

    /**
     * 核保标志
     */

    @Column(name = "underwriteflag")
    public String getUnderWriteFlag() {
        return this.underWriteFlag;
    }

    public void setUnderWriteFlag(String underWriteFlag) {
        this.underWriteFlag = underWriteFlag;
    }

    /**
     * 见费出单标志
     */

    @Column(name = "jfeeflag")
    public String getJfeeFlag() {
        return this.jfeeFlag;
    }

    public void setJfeeFlag(String jfeeFlag) {
        this.jfeeFlag = jfeeFlag;
    }

    /**
     * 政策性业务方案代码
     */

    @Column(name = "policyrelcode")
    public String getPolicyRelCode() {
        return this.policyRelCode;
    }

    public void setPolicyRelCode(String policyRelCode) {
        this.policyRelCode = policyRelCode;
    }

    /**
     * 政策性业务方案名称
     */

    @Column(name = "policyrelname")
    public String getPolicyRelName() {
        return this.policyRelName;
    }

    public void setPolicyRelName(String policyRelName) {
        this.policyRelName = policyRelName;
    }

    /**
     * 是否迁移数据
     */

    @Column(name = "dmflag")
    public String getDmFlag() {
        return this.dmFlag;
    }

    public void setDmFlag(String dmFlag) {
        this.dmFlag = dmFlag;
    }

    /**
     * 输入标志
     */

    @Column(name = "inputflag")
    public String getInputFlag() {
        return this.inputFlag;
    }

    public void setInputFlag(String inputFlag) {
        this.inputFlag = inputFlag;
    }

    /**
     * 其它标志字段
     */

    @Column(name = "othflag")
    public String getOthFlag() {
        return this.othFlag;
    }

    public void setOthFlag(String othFlag) {
        this.othFlag = othFlag;
    }

    /**
     * 备注
     */

    @Column(name = "remark")
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 短信转存后是否删除标记
     */

    @Column(name = "flag")
    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * 复核员
     */
    @Column(name = "checkcode")
    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    /**
     * 插入时间
     */

    @Column(name = "inserttimeforhis", insertable = false, updatable = false)
    public Date getInsertTimeForHis() {
        return this.insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    /**
     * 更新时间
     */

    @Column(name = "operatetimeforhis", insertable = false)
    public Date getOperateTimeForHis() {
        return this.operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }

    /**
     * 保险费支付办法
     */

    @Column(name = "paymode")
    public String getPayMode() {
        return this.payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    /**
     * 交叉销售标志
     */

    @Column(name = "crossflag")
    public String getCrossFlag() {
        return this.crossFlag;
    }

    public void setCrossFlag(String crossFlag) {
        this.crossFlag = crossFlag;
    }

    /**
     * 总税额
     */

    @Column(name = "sumtaxfee")
    public BigDecimal getSumTaxFee() {
        return sumTaxFee;
    }

    public void setSumTaxFee(BigDecimal sumTaxFee) {
        this.sumTaxFee = sumTaxFee;
    }

    /**
     * 总净保费
     */
    @Column(name = "sumnetpremium")
    public BigDecimal getSumNetPremium() {
        return sumNetPremium;
    }

    public void setSumNetPremium(BigDecimal sumNetPremium) {
        this.sumNetPremium = sumNetPremium;
    }

    /**
     * 提交核保时间
     */

    @Column(name = "undwrtsubmitdate")

    public Date getUndwrtSubmitDate() {
        return undwrtSubmitDate;
    }

    public void setUndwrtSubmitDate(Date undwrtSubmitDate) {
        this.undwrtSubmitDate = undwrtSubmitDate;
    }

    /**
     * 数据类型
     */

    @Column(name = "datatype")
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


}

