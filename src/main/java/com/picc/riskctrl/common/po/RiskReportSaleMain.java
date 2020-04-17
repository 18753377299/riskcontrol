package com.picc.riskctrl.common.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "riskreport_salemain")
public class RiskReportSaleMain implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 照片档案号 */
    private String archivesNo;
    /** 查勘机构 */
    private String exploreComcode;
    /** 查勘机构中文 */
    private String exploreComcodeCName;
    /** 查勘人 */
    private String explorer;
    /** 查勘人中文 */
    private String explorerCName;
    /** 归属机构 */
    private String comCode;
    /** 审核人 */
    private String checkUpCode;
    /** 审核人 */
    private String checkUpCodeCName;
    /** 企业名称 */
    private String companyName;
    /** 查勘地址 */
    private String exploreAddress;
    /** 查勘日期 */
    private Date exploreDate;
    /** 审核状态 */
    private String checkUpFlag;
    /**
     * 手机操作系统标志位 1 Anroid 安卓 2 IOS 苹果
     */
    private String mobileFlag;
    /** 错误信息 */
    private String message;
    /** 状态标志 */
    private long status;
    /** 插入时间 */
    private Date insertTimeForHis;
    /** 更新时间 */
    private Date operateTimeForHis;
    /** 风控销售员版图片目录 */
    private List<RiskReportSaleImaType> riskReportSaleImaTypeList = new ArrayList<RiskReportSaleImaType>(0);

    @Id
    @Column(name = "ARCHIVESNO")
    public String getArchivesNo() {
        return archivesNo;
    }

    public void setArchivesNo(String archivesNo) {
        this.archivesNo = archivesNo;
    }

    @Column(name = "EXPLORECOMCODE")
    public String getExploreComcode() {
        return exploreComcode;
    }

    public void setExploreComcode(String exploreComcode) {
        this.exploreComcode = exploreComcode;
    }

    @Column(name = "EXPLORER")
    public String getExplorer() {
        return explorer;
    }

    public void setExplorer(String explorer) {
        this.explorer = explorer;
    }

    @Column(name = "CHECKUPCODE")
    public String getCheckUpCode() {
        return checkUpCode;
    }

    public void setCheckUpCode(String checkUpCode) {
        this.checkUpCode = checkUpCode;
    }

    @Column(name = "COMPANYNAME")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "EXPLOREADDRESS")
    public String getExploreAddress() {
        return exploreAddress;
    }

    public void setExploreAddress(String exploreAddress) {
        this.exploreAddress = exploreAddress;
    }

    @Column(name = "EXPLOREDATE")
    public Date getExploreDate() {
        return exploreDate;
    }

    public void setExploreDate(Date exploreDate) {
        this.exploreDate = exploreDate;
    }

    @Column(name = "CHECKUPFLAG")
    public String getCheckUpFlag() {
        return checkUpFlag;
    }

    public void setCheckUpFlag(String checkUpFlag) {
        this.checkUpFlag = checkUpFlag;
    }

    @Column(name = "COMCODE")
    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    @Column(name = "MOBILEFLAG")
    public String getMobileFlag() {
        return mobileFlag;
    }

    public void setMobileFlag(String mobileFlag) {
        this.mobileFlag = mobileFlag;
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

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportSaleMain")
    public List<RiskReportSaleImaType> getRiskReportSaleImaTypeList() {
        return riskReportSaleImaTypeList;
    }

    public void setRiskReportSaleImaTypeList(List<RiskReportSaleImaType> riskReportSaleImaTypeList) {
        this.riskReportSaleImaTypeList = riskReportSaleImaTypeList;
    }

    @Transient
    public String getExplorerCName() {
        return explorerCName;
    }

    public void setExplorerCName(String explorerCName) {
        this.explorerCName = explorerCName;
    }

    @Transient
    public String getExploreComcodeCName() {
        return exploreComcodeCName;
    }

    public void setExploreComcodeCName(String exploreComcodeCName) {
        this.exploreComcodeCName = exploreComcodeCName;
    }

    @Transient
    public String getCheckUpCodeCName() {
        return checkUpCodeCName;
    }

    public void setCheckUpCodeCName(String checkUpCodeCName) {
        this.checkUpCodeCName = checkUpCodeCName;
    }

    @Transient
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Transient
    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }
}
