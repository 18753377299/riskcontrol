package com.picc.riskctrl.riskcheck.po;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "RISKCheck_MAIN")
public class RiskCheckMain {
	private String riskCheckNo;
    private String comCode;  
    private String checkModel;
    private String insuredType;
    private String insuredCode;
    private String insuredName;
    private String addressProvince;
	private String addressCity;
	private String addressCounty;
    private String addressDetail;
    private String addressCode;
    private String businessSource;
    private String businessClass;
    private String unitNature;
    private String operatorCode;
    private String operatorCodeUni;
    private Date undwrtsubmitDate;
    private String underwriteFlag;
    private Date underwriteDate;
    private String underwriteCode;
    private String underwriteName;
    private String checkComCode;
    private String checker;
    private Date checkDate;
    private Timestamp madeDate;
    private String mobileFlag;
    private String highlightRisk;
    private BigDecimal score;
    private String executionId;
    private String repulseSugggest;
    private BigDecimal pointx_2000;
    private BigDecimal pointy_2000;
    private BigDecimal pointx_02;
    private BigDecimal pointy_02;
    private Date insertTimeForHis;
    private Date operateTimeForHis;
    
	private List<RiskCheckAssess> riskCheckAssessList = new ArrayList<RiskCheckAssess>(0);
	private List<RiskCheckImage> riskCheckImageList = new ArrayList<RiskCheckImage>(0);
	private List<RiskCheckVenture> riskCheckVentureList = new ArrayList<RiskCheckVenture>(0);


    @Id
    @Column(name = "riskcheckno", nullable = false, length = 22)
    public String getRiskCheckNo() {
        return riskCheckNo;
    }

    public void setRiskCheckNo(String riskCheckNo) {
        this.riskCheckNo = riskCheckNo;
    }

    @Basic
    @Column(name = "comcode", nullable = false, length = 8)
    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    @Basic
    @Column(name = "checkmodel", nullable = true, length = 3)
    public String getCheckModel() {
        return checkModel;
    }

    public void setCheckModel(String checkModel) {
        this.checkModel = checkModel;
    }

    @Basic
    @Column(name = "insuredtype", nullable = true, length = 1)
    public String getInsuredType() {
        return insuredType;
    }

    public void setInsuredType(String insuredType) {
        this.insuredType = insuredType;
    }

    @Basic
    @Column(name = "insuredcode", nullable = true, length = 30)
    public String getInsuredCode() {
        return insuredCode;
    }

    public void setInsuredCode(String insuredCode) {
        this.insuredCode = insuredCode;
    }

    @Basic
    @Column(name = "insuredname", nullable = true, length = 120)
    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    @Basic
    @Column(name = "addressdetail", nullable = true, length = 255)
    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    @Basic
    @Column(name = "addresscode", nullable = true, length = 6)
    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    @Basic
    @Column(name = "businesssource", nullable = true, length = 6)
    public String getBusinessSource() {
        return businessSource;
    }

    public void setBusinessSource(String businessSource) {
        this.businessSource = businessSource;
    }

    @Basic
    @Column(name = "businessclass", nullable = true, length = 2)
    public String getBusinessClass() {
        return businessClass;
    }

    public void setBusinessClass(String businessClass) {
        this.businessClass = businessClass;
    }

    @Basic
    @Column(name = "unitnature", nullable = true, length = 1)
    public String getUnitNature() {
        return unitNature;
    }

    public void setUnitNature(String unitNature) {
        this.unitNature = unitNature;
    }

    @Basic
    @Column(name = "operatorcode", nullable = true, length = 10)
    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    @Basic
    @Column(name = "operatorcodeuni", nullable = true, length = 10)
    public String getOperatorCodeUni() {
        return operatorCodeUni;
    }

    public void setOperatorCodeUni(String operatorCodeUni) {
        this.operatorCodeUni = operatorCodeUni;
    }

    @Basic
    @Column(name = "undwrtsubmitdate", nullable = true)
    public Date getUndwrtsubmitDate() {
        return undwrtsubmitDate;
    }

    public void setUndwrtsubmitDate(Date undwrtsubmitDate) {
        this.undwrtsubmitDate = undwrtsubmitDate;
    }

    @Basic
    @Column(name = "underwriteflag", nullable = true, length = 1)
    public String getUnderwriteFlag() {
        return underwriteFlag;
    }

    public void setUnderwriteFlag(String underwriteFlag) {
        this.underwriteFlag = underwriteFlag;
    }

    @Basic
    @Column(name = "underwritedate", nullable = true)
    public Date getUnderwriteDate() {
        return underwriteDate;
    }

    public void setUnderwriteDate(Date underwriteDate) {
        this.underwriteDate = underwriteDate;
    }

    @Basic
    @Column(name = "underwritecode", nullable = true, length = 10)
    public String getUnderwriteCode() {
        return underwriteCode;
    }

    public void setUnderwriteCode(String underwriteCode) {
        this.underwriteCode = underwriteCode;
    }

    @Basic
    @Column(name = "underwritename", nullable = true, length = 120)
    public String getUnderwriteName() {
        return underwriteName;
    }

    public void setUnderwriteName(String underwriteName) {
        this.underwriteName = underwriteName;
    }

    @Basic
    @Column(name = "checkcomcode", nullable = true, length = 8)
    public String getCheckComCode() {
        return checkComCode;
    }

    public void setCheckComCode(String checkComCode) {
        this.checkComCode = checkComCode;
    }

    @Basic
    @Column(name = "checker", nullable = true, length = 10)
    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    @Basic
    @Column(name = "checkdate", nullable = true)
    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    @Basic
    @Column(name = "madedate", nullable = true)
    public Timestamp getMadeDate() {
        return madeDate;
    }

    public void setMadeDate(Timestamp madeDate) {
        this.madeDate = madeDate;
    }

    @Basic
    @Column(name = "mobileflag", nullable = false, length = 1)
    public String getMobileFlag() {
        return mobileFlag;
    }

    public void setMobileFlag(String mobileFlag) {
        this.mobileFlag = mobileFlag;
    }

    @Basic
    @Column(name = "highlightrisk", nullable = true, length = 2147483647)
    public String getHighlightRisk() {
        return highlightRisk;
    }

    public void setHighlightRisk(String highlightRisk) {
        this.highlightRisk = highlightRisk;
    }

    @Basic
    @Column(name = "score", nullable = true, precision = 2)
    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    @Basic
    @Column(name = "executionid", nullable = true, length = 255)
    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    @Basic
    @Column(name = "repulsesugggest", nullable = true, length = 1000)
    public String getRepulseSugggest() {
        return repulseSugggest;
    }

    public void setRepulseSugggest(String repulseSugggest) {
        this.repulseSugggest = repulseSugggest;
    }

    @Basic
    @Column(name = "pointx_2000", nullable = true, precision = 15)
    public BigDecimal getPointx_2000() {
        return pointx_2000;
    }
    public void setPointx_2000(BigDecimal pointx_2000) {
		this.pointx_2000 = pointx_2000;
	}

    @Basic
    @Column(name = "pointy_2000", nullable = true, precision = 15)
    public BigDecimal getPointy_2000() {
        return pointy_2000;
    }

	public void setPointy_2000(BigDecimal pointy_2000) {
		this.pointy_2000 = pointy_2000;
	}
    
    @Basic
    @Column(name = "pointx_02", nullable = true, precision = 15)
    public BigDecimal getPointx_02() {
		return pointx_02;
	}

	public void setPointx_02(BigDecimal pointx_02) {
		this.pointx_02 = pointx_02;
	}
	@Basic
    @Column(name = "pointy_02", nullable = true, precision = 15)
	public BigDecimal getPointy_02() {
		return pointy_02;
	}

	public void setPointy_02(BigDecimal pointy_02) {
		this.pointy_02 = pointy_02;
	}
	

	@Basic
    @Column(name = "inserttimeforhis", insertable = false, updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    @Basic
    @Column(name = "operatetimeforhis", insertable = false)
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }
    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskCheckMain")
    public List<RiskCheckAssess> getRiskCheckAssessList() {
		return riskCheckAssessList;
	}

	public void setRiskCheckAssessList(List<RiskCheckAssess> riskCheckAssessList) {
		this.riskCheckAssessList = riskCheckAssessList;
	}

	@OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskCheckMain")
	public List<RiskCheckImage> getRiskCheckImageList() {
		return riskCheckImageList;
	}

	public void setRiskCheckImageList(List<RiskCheckImage> riskCheckImageList) {
		this.riskCheckImageList = riskCheckImageList;
	}

	@OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskCheckMain")
	public List<RiskCheckVenture> getRiskCheckVentureList() {
		return riskCheckVentureList;
	}

	public void setRiskCheckVentureList(List<RiskCheckVenture> riskCheckVentureList) {
		this.riskCheckVentureList = riskCheckVentureList;
	}
	@Basic
    @Column(name = "addressprovince", nullable = true, length = 30)
	public String getAddressProvince() {
		return addressProvince;
	}

	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}
	@Basic
    @Column(name = "addresscity", nullable = true, length = 40)
	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	@Basic
    @Column(name = "addresscounty", nullable = true, length = 225)
	public String getAddressCounty() {
		return addressCounty;
	}

	public void setAddressCounty(String addressCounty) {
		this.addressCounty = addressCounty;
	}
}
