package com.picc.riskctrl.common.schema;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "prpcmain")
public class PrpCmain implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

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
     * 总保额
     */
    private BigDecimal sumAmount;
    /**
     * 归属机构
     */
    private String comCode;
    /**
     * 出单机构
     */
    private String makeCom;
    /**
     * 联共保标志
     */
    private String coinsFlag;

    /**
     * 核保标志
     */
    private String underWriteFlag;

    /**
     * 插入时间
     */
    private Date insertTimeForHis;
    /**
     * 更新时间
     */
    private Date operateTimeForHis;


    public PrpCmain() {
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

}

