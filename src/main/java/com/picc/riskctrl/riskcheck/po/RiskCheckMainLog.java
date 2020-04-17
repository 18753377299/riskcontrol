package com.picc.riskctrl.riskcheck.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "riskcheck_mainlog")
public class RiskCheckMainLog implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer logId;
	/**归属机构*/
	private String comCode;
	/** 投保单号码 */
	private String proposalNo;
	/** 保单号 */
	private String policyNo;
	/** 险种代码 */
	private String riskCode;
	private String insuredType;
    private String insuredCode;
    private String insuredName;
    /**巡检人代码*/
    private String checker;
    
    private String checkModel;
    /**巡检机构*/
    private String checkComCode;
    
    /** 业务状态 */
   	private String checkerStatus;
   	private String addressDetail;
    private BigDecimal pointx_2000;
    private BigDecimal pointy_2000;
    private BigDecimal pointx_02;
    private BigDecimal pointy_02;
    
    private Timestamp insertTimeForHis;
    private Timestamp operateTimeForHis;
    
    private String riskCheckNo;
    
    private Date checkDate;
    
    
    @Id
//    @Column(name = "logid", unique = true, nullable = false, precision=18, scale=0)
    @Column(name = "logid", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
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
    public Timestamp getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Timestamp insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    @Basic
    @Column(name = "operatetimeforhis",insertable = false)
    public Timestamp getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Timestamp operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }

    @Basic
    @Column(name = "proposalno", nullable = true, length = 22)
	public String getProposalNo() {
		return proposalNo;
	}


	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	@Basic
	@Column(name = "policyno", nullable = true, length = 22)
	public String getPolicyNo() {
		return policyNo;
	}


	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Basic
	@Column(name = "riskcode", nullable = true, length = 3)
	public String getRiskCode() {
		return riskCode;
	}


	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
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
	@Column(name = "checkerstatus", nullable = true, length = 1)
	public String getCheckerStatus() {
		return checkerStatus;
	}

	public void setCheckerStatus(String checkerStatus) {
		this.checkerStatus = checkerStatus;
	}
	
	@Basic
	@Column(name = "checkmodel", nullable = true)
	public String getCheckModel() {
		return checkModel;
	}

	public void setCheckModel(String checkModel) {
		this.checkModel = checkModel;
	}
	
	
	@Basic
	@Column(name = "riskcheckno", nullable = true)
	public String getRiskCheckNo() {
		return riskCheckNo;
	}

	public void setRiskCheckNo(String riskCheckNo) {
		this.riskCheckNo = riskCheckNo;
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
	@Column(name = "checkcomcode", nullable = true)
	public String getCheckComCode() {
		return checkComCode;
	}

	public void setCheckComCode(String checkComCode) {
		this.checkComCode = checkComCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((addressDetail == null) ? 0 : addressDetail.hashCode());
		result = prime * result
				+ ((checkComCode == null) ? 0 : checkComCode.hashCode());
		result = prime * result
				+ ((checkDate == null) ? 0 : checkDate.hashCode());
		result = prime * result
				+ ((checkModel == null) ? 0 : checkModel.hashCode());
		result = prime * result + ((checker == null) ? 0 : checker.hashCode());
		result = prime * result
				+ ((checkerStatus == null) ? 0 : checkerStatus.hashCode());
		result = prime * result + ((comCode == null) ? 0 : comCode.hashCode());
		result = prime
				* result
				+ ((insertTimeForHis == null) ? 0 : insertTimeForHis.hashCode());
		result = prime * result
				+ ((insuredCode == null) ? 0 : insuredCode.hashCode());
		result = prime * result
				+ ((insuredName == null) ? 0 : insuredName.hashCode());
		result = prime * result
				+ ((insuredType == null) ? 0 : insuredType.hashCode());
		result = prime * result + ((logId == null) ? 0 : logId.hashCode());
		result = prime
				* result
				+ ((operateTimeForHis == null) ? 0 : operateTimeForHis
						.hashCode());
		result = prime * result
				+ ((pointx_02 == null) ? 0 : pointx_02.hashCode());
		result = prime * result
				+ ((pointx_2000 == null) ? 0 : pointx_2000.hashCode());
		result = prime * result
				+ ((pointy_02 == null) ? 0 : pointy_02.hashCode());
		result = prime * result
				+ ((pointy_2000 == null) ? 0 : pointy_2000.hashCode());
		result = prime * result
				+ ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result
				+ ((proposalNo == null) ? 0 : proposalNo.hashCode());
		result = prime * result
				+ ((riskCheckNo == null) ? 0 : riskCheckNo.hashCode());
		result = prime * result
				+ ((riskCode == null) ? 0 : riskCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (getClass() != obj.getClass()) {
            return false;
        }
		RiskCheckMainLog other = (RiskCheckMainLog) obj;
		if (addressDetail == null) {
			if (other.addressDetail != null) {
                return false;
            }
		} else if (!addressDetail.equals(other.addressDetail)) {
            return false;
        }
		if (checkComCode == null) {
			if (other.checkComCode != null) {
                return false;
            }
		} else if (!checkComCode.equals(other.checkComCode)) {
            return false;
        }
		if (checkDate == null) {
			if (other.checkDate != null) {
                return false;
            }
		} else if (!checkDate.equals(other.checkDate)) {
            return false;
        }
		if (checkModel == null) {
			if (other.checkModel != null) {
                return false;
            }
		} else if (!checkModel.equals(other.checkModel)) {
            return false;
        }
		if (checker == null) {
			if (other.checker != null) {
                return false;
            }
		} else if (!checker.equals(other.checker)) {
            return false;
        }
		if (checkerStatus == null) {
			if (other.checkerStatus != null) {
                return false;
            }
		} else if (!checkerStatus.equals(other.checkerStatus)) {
            return false;
        }
		if (comCode == null) {
			if (other.comCode != null) {
                return false;
            }
		} else if (!comCode.equals(other.comCode)) {
            return false;
        }
		if (insertTimeForHis == null) {
			if (other.insertTimeForHis != null) {
                return false;
            }
		} else if (!insertTimeForHis.equals(other.insertTimeForHis)) {
            return false;
        }
		if (insuredCode == null) {
			if (other.insuredCode != null) {
                return false;
            }
		} else if (!insuredCode.equals(other.insuredCode)) {
            return false;
        }
		if (insuredName == null) {
			if (other.insuredName != null) {
                return false;
            }
		} else if (!insuredName.equals(other.insuredName)) {
            return false;
        }
		if (insuredType == null) {
			if (other.insuredType != null) {
                return false;
            }
		} else if (!insuredType.equals(other.insuredType)) {
            return false;
        }
		if (logId == null) {
			if (other.logId != null) {
                return false;
            }
		} else if (!logId.equals(other.logId)) {
            return false;
        }
		if (operateTimeForHis == null) {
			if (other.operateTimeForHis != null) {
                return false;
            }
		} else if (!operateTimeForHis.equals(other.operateTimeForHis)) {
            return false;
        }
		if (pointx_02 == null) {
			if (other.pointx_02 != null) {
                return false;
            }
		} else if (!pointx_02.equals(other.pointx_02)) {
            return false;
        }
		if (pointx_2000 == null) {
			if (other.pointx_2000 != null) {
                return false;
            }
		} else if (!pointx_2000.equals(other.pointx_2000)) {
            return false;
        }
		if (pointy_02 == null) {
			if (other.pointy_02 != null) {
                return false;
            }
		} else if (!pointy_02.equals(other.pointy_02)) {
            return false;
        }
		if (pointy_2000 == null) {
			if (other.pointy_2000 != null) {
                return false;
            }
		} else if (!pointy_2000.equals(other.pointy_2000)) {
            return false;
        }
		if (policyNo == null) {
			if (other.policyNo != null) {
                return false;
            }
		} else if (!policyNo.equals(other.policyNo)) {
            return false;
        }
		if (proposalNo == null) {
			if (other.proposalNo != null) {
                return false;
            }
		} else if (!proposalNo.equals(other.proposalNo)) {
            return false;
        }
		if (riskCheckNo == null) {
			if (other.riskCheckNo != null) {
                return false;
            }
		} else if (!riskCheckNo.equals(other.riskCheckNo)) {
            return false;
        }
		if (riskCode == null) {
			if (other.riskCode != null) {
                return false;
            }
		} else if (!riskCode.equals(other.riskCode)) {
            return false;
        }
		return true;
	}

	
}

