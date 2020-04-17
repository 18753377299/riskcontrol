package com.picc.riskctrl.riskins.vo;

import java.util.Date;

public class RiskReportSaleMainVo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**照片档案号*/
    private String archivesNo;
    /**企业名称*/
    private String companyName;
    /**查勘机构*/
    private String exploreComcode;
    /**查勘人*/
    private String explorer;
    /**审核人*/
    private String checkUpCode;
    /**查勘日期*/
    private Date exploreDate;
    /**审核状态*/
    private String checkUpFlag;

    public String getArchivesNo() {
        return archivesNo;
    }
    public void setArchivesNo(String archivesNo) {
        this.archivesNo = archivesNo;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getExploreComcode() {
        return exploreComcode;
    }
    public void setExploreComcode(String exploreComcode) {
        this.exploreComcode = exploreComcode;
    }
    public String getExplorer() {
        return explorer;
    }
    public void setExplorer(String explorer) {
        this.explorer = explorer;
    }
    public String getCheckUpCode() {
        return checkUpCode;
    }
    public void setCheckUpCode(String checkUpCode) {
        this.checkUpCode = checkUpCode;
    }
    public Date getExploreDate() {
        return exploreDate;
    }
    public void setExploreDate(Date exploreDate) {
        this.exploreDate = exploreDate;
    }
    public String getCheckUpFlag() {
        return checkUpFlag;
    }
    public void setCheckUpFlag(String checkUpFlag) {
        this.checkUpFlag = checkUpFlag;
    }

    //============================================





}