package com.picc.riskctrl.riskcheck.po;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RISKCHECK_Venture")
public class RiskCheckVenture {
	private String riskCheckNo;
//  private String compFaithFlag;
//  private String compFaithMess;
//  private String fifShow;
  private String characteristics;
  private String rainRecord;
  private String itemDistance;
  private String comparedDegree;
  private String comparedTerrain;
  private String itemEnvironment;
  private String largeProjects;
  private String largeProImpact;
  private String waterSensitivity;
  private String underAssetsFlag;
  private String historicWater;
  private String manSituation;
  private String lowEquipment;
  private String ownership;
  private String constructBuild;
  private String airStorageFlag;
  private String buildYears;
  private String doorFlag;
  private String staggeredFlag;
  private String drainageMethod;
  private String drainageBlock;
  private String dredgeCondition;
  private String connectedFlag;
  private String unobstructedFlag;
  private String haveCanal;
  private String cargoWaterSen;
  private String stoForm;
  private String stoLocation;
  private String emiForm;
  private String conMaterials;
  private String draEquipment;
  private String conPlan;
  private String dutyFlag;
  private String monitorFlag;
  private String transferFlag;
  private Date insertTimeForHis;
  private Date operateTimeForHis;
  private RiskCheckMain riskCheckMain;
	
  @Id
  @Column(name = "riskcheckno", nullable = false, length = 22)
  public String getRiskCheckNo() {
      return riskCheckNo;
  }

  public void setRiskCheckNo(String riskCheckNo) {
      this.riskCheckNo = riskCheckNo;
  }
  
//  @Basic
//  @Column(name = "compFaithFlag", nullable = true, length = 1)
//  public String getCompFaithFlag() {
//		return compFaithFlag;
//	}
//
//	public void setCompFaithFlag(String compFaithFlag) {
//		this.compFaithFlag = compFaithFlag;
//	}
//
//	@Basic
//  @Column(name = "compFaithMess", nullable = true, length = 1)
//	public String getCompFaithMess() {
//		return compFaithMess;
//	}
//
//	public void setCompFaithMess(String compFaithMess) {
//		this.compFaithMess = compFaithMess;
//	}
	
//	@Basic
//  @Column(name = "fifShow", nullable = true, length = 1)
//	public String getFifShow() {
//		return fifShow;
//	}
//
//	public void setFifShow(String fifShow) {
//		this.fifShow = fifShow;
//	}

	@Basic
  @Column(name = "rainrecord", nullable = true, length = 1)
  public String getRainRecord() {
      return rainRecord;
  }
	public void setRainRecord(String rainRecord) {
      this.rainRecord = rainRecord;
  }

  @Basic
  @Column(name = "itemdistance", nullable = true, length = 1)
  public String getItemDistance() {
      return itemDistance;
  }

  public void setItemDistance(String itemDistance) {
      this.itemDistance = itemDistance;
  }

  @Basic
  @Column(name = "compareddegree", nullable = true, length = 1)
  public String getComparedDegree() {
      return comparedDegree;
  }

  public void setComparedDegree(String comparedDegree) {
      this.comparedDegree = comparedDegree;
  }

  @Basic
  @Column(name = "comparedterrain", nullable = true, length = 1)
  public String getComparedTerrain() {
      return comparedTerrain;
  }

  public void setComparedTerrain(String comparedTerrain) {
      this.comparedTerrain = comparedTerrain;
  }

  @Basic
  @Column(name = "itemenvironment", nullable = true, length = 1)
  public String getItemEnvironment() {
      return itemEnvironment;
  }

  public void setItemEnvironment(String itemEnvironment) {
      this.itemEnvironment = itemEnvironment;
  }

  @Basic
  @Column(name = "largeprojects", nullable = true, length = 1)
  public String getLargeProjects() {
      return largeProjects;
  }

  public void setLargeProjects(String largeProjects) {
      this.largeProjects = largeProjects;
  }

  @Basic
  @Column(name = "largeproimpact", nullable = true, length = 1)
  public String getLargeProImpact() {
      return largeProImpact;
  }

  public void setLargeProImpact(String largeProImpact) {
      this.largeProImpact = largeProImpact;
  }

  @Basic
  @Column(name = "watersensitivity", nullable = true, length = 1)
  public String getWaterSensitivity() {
      return waterSensitivity;
  }

  public void setWaterSensitivity(String waterSensitivity) {
      this.waterSensitivity = waterSensitivity;
  }

  @Basic
  @Column(name = "underassetsflag", nullable = true, length = 1)
  public String getUnderAssetsFlag() {
      return underAssetsFlag;
  }

  public void setUnderAssetsFlag(String underAssetsFlag) {
      this.underAssetsFlag = underAssetsFlag;
  }

  @Basic
  @Column(name = "historicwater", nullable = true, length = 1)
  public String getHistoricWater() {
      return historicWater;
  }

  public void setHistoricWater(String historicWater) {
      this.historicWater = historicWater;
  }

  @Basic
  @Column(name = "mansituation", nullable = true, length = 1)
  public String getManSituation() {
      return manSituation;
  }

  public void setManSituation(String manSituation) {
      this.manSituation = manSituation;
  }

  @Basic
  @Column(name = "lowequipment", nullable = true, length = 1)
  public String getLowEquipment() {
      return lowEquipment;
  }

  public void setLowEquipment(String lowEquipment) {
      this.lowEquipment = lowEquipment;
  }

  @Basic
  @Column(name = "ownership", nullable = true, length = 1)
  public String getOwnership() {
      return ownership;
  }

  public void setOwnership(String ownership) {
      this.ownership = ownership;
  }

  @Basic
  @Column(name = "constructbuild", nullable = true, length = 1)
  public String getConstructBuild() {
      return constructBuild;
  }

  public void setConstructBuild(String constructBuild) {
      this.constructBuild = constructBuild;
  }

  @Basic
  @Column(name = "airstorageflag", nullable = true, length = 1)
  public String getAirStorageFlag() {
      return airStorageFlag;
  }

  public void setAirStorageFlag(String airStorageFlag) {
      this.airStorageFlag = airStorageFlag;
  }

  @Basic
  @Column(name = "buildyears", nullable = true, length = 1)
  public String getBuildYears() {
      return buildYears;
  }

  public void setBuildYears(String buildYears) {
      this.buildYears = buildYears;
  }

  @Basic
  @Column(name = "doorflag", nullable = true, length = 1)
  public String getDoorFlag() {
      return doorFlag;
  }

  public void setDoorFlag(String doorFlag) {
      this.doorFlag = doorFlag;
  }

  @Basic
  @Column(name = "staggeredflag", nullable = true, length = 1)
  public String getStaggeredFlag() {
      return staggeredFlag;
  }

  public void setStaggeredFlag(String staggeredFlag) {
      this.staggeredFlag = staggeredFlag;
  }

  @Basic
  @Column(name = "drainagemethod", nullable = true, length = 1)
  public String getDrainageMethod() {
      return drainageMethod;
  }

  public void setDrainageMethod(String drainageMethod) {
      this.drainageMethod = drainageMethod;
  }

  @Basic
  @Column(name = "drainageblock", nullable = true, length = 1)
  public String getDrainageBlock() {
      return drainageBlock;
  }

  public void setDrainageBlock(String drainageBlock) {
      this.drainageBlock = drainageBlock;
  }

  @Basic
  @Column(name = "dredgecondition", nullable = true, length = 1)
  public String getDredgeCondition() {
      return dredgeCondition;
  }

  public void setDredgeCondition(String dredgeCondition) {
      this.dredgeCondition = dredgeCondition;
  }

  @Basic
  @Column(name = "connectedflag", nullable = true, length = 1)
  public String getConnectedFlag() {
      return connectedFlag;
  }

  public void setConnectedFlag(String connectedFlag) {
      this.connectedFlag = connectedFlag;
  }

  @Basic
  @Column(name = "unobstructedflag", nullable = true, length = 1)
  public String getUnobstructedFlag() {
      return unobstructedFlag;
  }

  public void setUnobstructedFlag(String unobstructedFlag) {
      this.unobstructedFlag = unobstructedFlag;
  }

  @Basic
  @Column(name = "havecanal", nullable = true, length = 1)
  public String getHaveCanal() {
      return haveCanal;
  }

  public void setHaveCanal(String haveCanal) {
      this.haveCanal = haveCanal;
  }

  @Basic
  @Column(name = "cargowatersen", nullable = true, length = 1)
  public String getCargoWaterSen() {
      return cargoWaterSen;
  }

  public void setCargoWaterSen(String cargoWaterSen) {
      this.cargoWaterSen = cargoWaterSen;
  }

  @Basic
  @Column(name = "stoform", nullable = true, length = 1)
  public String getStoForm() {
      return stoForm;
  }

  public void setStoForm(String stoForm) {
      this.stoForm = stoForm;
  }

  @Basic
  @Column(name = "stolocation", nullable = true, length = 1)
  public String getStoLocation() {
      return stoLocation;
  }

  public void setStoLocation(String stoLocation) {
      this.stoLocation = stoLocation;
  }

  @Basic
  @Column(name = "emiform", nullable = true, length = 1)
  public String getEmiForm() {
      return emiForm;
  }

  public void setEmiForm(String emiForm) {
      this.emiForm = emiForm;
  }

  @Basic
  @Column(name = "conmaterials", nullable = true, length = 1)
  public String getConMaterials() {
      return conMaterials;
  }

  public void setConMaterials(String conMaterials) {
      this.conMaterials = conMaterials;
  }

  @Basic
  @Column(name = "draequipment", nullable = true, length = 1)
  public String getDraEquipment() {
      return draEquipment;
  }

  public void setDraEquipment(String draEquipment) {
      this.draEquipment = draEquipment;
  }

  @Basic
  @Column(name = "conplan", nullable = true, length = 1)
  public String getConPlan() {
      return conPlan;
  }

  public void setConPlan(String conPlan) {
      this.conPlan = conPlan;
  }

  @Basic
  @Column(name = "dutyflag", nullable = true, length = 1)
  public String getDutyFlag() {
      return dutyFlag;
  }

  public void setDutyFlag(String dutyFlag) {
      this.dutyFlag = dutyFlag;
  }

  @Basic
  @Column(name = "monitorflag", nullable = true, length = 1)
  public String getMonitorFlag() {
      return monitorFlag;
  }

  public void setMonitorFlag(String monitorFlag) {
      this.monitorFlag = monitorFlag;
  }

  @Basic
  @Column(name = "transferflag", nullable = true, length = 1)
  public String getTransferFlag() {
      return transferFlag;
  }

  public void setTransferFlag(String transferFlag) {
      this.transferFlag = transferFlag;
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

  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airStorageFlag == null) ? 0 : airStorageFlag.hashCode());
		result = prime * result + ((buildYears == null) ? 0 : buildYears.hashCode());
		result = prime * result + ((cargoWaterSen == null) ? 0 : cargoWaterSen.hashCode());
		result = prime * result + ((characteristics == null) ? 0 : characteristics.hashCode());
//		result = prime * result + ((compFaithFlag == null) ? 0 : compFaithFlag.hashCode());
//		result = prime * result + ((compFaithMess == null) ? 0 : compFaithMess.hashCode());
		result = prime * result + ((comparedDegree == null) ? 0 : comparedDegree.hashCode());
		result = prime * result + ((comparedTerrain == null) ? 0 : comparedTerrain.hashCode());
		result = prime * result + ((conMaterials == null) ? 0 : conMaterials.hashCode());
		result = prime * result + ((conPlan == null) ? 0 : conPlan.hashCode());
		result = prime * result + ((connectedFlag == null) ? 0 : connectedFlag.hashCode());
		result = prime * result + ((constructBuild == null) ? 0 : constructBuild.hashCode());
		result = prime * result + ((doorFlag == null) ? 0 : doorFlag.hashCode());
		result = prime * result + ((draEquipment == null) ? 0 : draEquipment.hashCode());
		result = prime * result + ((drainageBlock == null) ? 0 : drainageBlock.hashCode());
		result = prime * result + ((drainageMethod == null) ? 0 : drainageMethod.hashCode());
		result = prime * result + ((dredgeCondition == null) ? 0 : dredgeCondition.hashCode());
		result = prime * result + ((dutyFlag == null) ? 0 : dutyFlag.hashCode());
		result = prime * result + ((emiForm == null) ? 0 : emiForm.hashCode());
//		result = prime * result + ((fifShow == null) ? 0 : fifShow.hashCode());
		result = prime * result + ((haveCanal == null) ? 0 : haveCanal.hashCode());
		result = prime * result + ((historicWater == null) ? 0 : historicWater.hashCode());
		result = prime * result + ((insertTimeForHis == null) ? 0 : insertTimeForHis.hashCode());
		result = prime * result + ((itemDistance == null) ? 0 : itemDistance.hashCode());
		result = prime * result + ((itemEnvironment == null) ? 0 : itemEnvironment.hashCode());
		result = prime * result + ((largeProImpact == null) ? 0 : largeProImpact.hashCode());
		result = prime * result + ((largeProjects == null) ? 0 : largeProjects.hashCode());
		result = prime * result + ((lowEquipment == null) ? 0 : lowEquipment.hashCode());
		result = prime * result + ((manSituation == null) ? 0 : manSituation.hashCode());
		result = prime * result + ((monitorFlag == null) ? 0 : monitorFlag.hashCode());
		result = prime * result + ((operateTimeForHis == null) ? 0 : operateTimeForHis.hashCode());
		result = prime * result + ((ownership == null) ? 0 : ownership.hashCode());
		result = prime * result + ((rainRecord == null) ? 0 : rainRecord.hashCode());
		result = prime * result + ((riskCheckMain == null) ? 0 : riskCheckMain.hashCode());
		result = prime * result + ((riskCheckNo == null) ? 0 : riskCheckNo.hashCode());
		result = prime * result + ((staggeredFlag == null) ? 0 : staggeredFlag.hashCode());
		result = prime * result + ((stoForm == null) ? 0 : stoForm.hashCode());
		result = prime * result + ((stoLocation == null) ? 0 : stoLocation.hashCode());
		result = prime * result + ((transferFlag == null) ? 0 : transferFlag.hashCode());
		result = prime * result + ((underAssetsFlag == null) ? 0 : underAssetsFlag.hashCode());
		result = prime * result + ((unobstructedFlag == null) ? 0 : unobstructedFlag.hashCode());
		result = prime * result + ((waterSensitivity == null) ? 0 : waterSensitivity.hashCode());
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
		RiskCheckVenture other = (RiskCheckVenture) obj;
		if (airStorageFlag == null) {
			if (other.airStorageFlag != null) {
                return false;
            }
		} else if (!airStorageFlag.equals(other.airStorageFlag)) {
            return false;
        }
		if (buildYears == null) {
			if (other.buildYears != null) {
                return false;
            }
		} else if (!buildYears.equals(other.buildYears)) {
            return false;
        }
		if (cargoWaterSen == null) {
			if (other.cargoWaterSen != null) {
                return false;
            }
		} else if (!cargoWaterSen.equals(other.cargoWaterSen)) {
            return false;
        }
		if (characteristics == null) {
			if (other.characteristics != null) {
                return false;
            }
		} else if (!characteristics.equals(other.characteristics)) {
            return false;
        }
//		if (compFaithFlag == null) {
//			if (other.compFaithFlag != null)
//				return false;
//		} else if (!compFaithFlag.equals(other.compFaithFlag))
//			return false;
//		if (compFaithMess == null) {
//			if (other.compFaithMess != null)
//				return false;
//		} else if (!compFaithMess.equals(other.compFaithMess))
//			return false;
		if (comparedDegree == null) {
			if (other.comparedDegree != null) {
                return false;
            }
		} else if (!comparedDegree.equals(other.comparedDegree)) {
            return false;
        }
		if (comparedTerrain == null) {
			if (other.comparedTerrain != null) {
                return false;
            }
		} else if (!comparedTerrain.equals(other.comparedTerrain)) {
            return false;
        }
		if (conMaterials == null) {
			if (other.conMaterials != null) {
                return false;
            }
		} else if (!conMaterials.equals(other.conMaterials)) {
            return false;
        }
		if (conPlan == null) {
			if (other.conPlan != null) {
                return false;
            }
		} else if (!conPlan.equals(other.conPlan)) {
            return false;
        }
		if (connectedFlag == null) {
			if (other.connectedFlag != null) {
                return false;
            }
		} else if (!connectedFlag.equals(other.connectedFlag)) {
            return false;
        }
		if (constructBuild == null) {
			if (other.constructBuild != null) {
                return false;
            }
		} else if (!constructBuild.equals(other.constructBuild)) {
            return false;
        }
		if (doorFlag == null) {
			if (other.doorFlag != null) {
                return false;
            }
		} else if (!doorFlag.equals(other.doorFlag)) {
            return false;
        }
		if (draEquipment == null) {
			if (other.draEquipment != null) {
                return false;
            }
		} else if (!draEquipment.equals(other.draEquipment)) {
            return false;
        }
		if (drainageBlock == null) {
			if (other.drainageBlock != null) {
                return false;
            }
		} else if (!drainageBlock.equals(other.drainageBlock)) {
            return false;
        }
		if (drainageMethod == null) {
			if (other.drainageMethod != null) {
                return false;
            }
		} else if (!drainageMethod.equals(other.drainageMethod)) {
            return false;
        }
		if (dredgeCondition == null) {
			if (other.dredgeCondition != null) {
                return false;
            }
		} else if (!dredgeCondition.equals(other.dredgeCondition)) {
            return false;
        }
		if (dutyFlag == null) {
			if (other.dutyFlag != null) {
                return false;
            }
		} else if (!dutyFlag.equals(other.dutyFlag)) {
            return false;
        }
		if (emiForm == null) {
			if (other.emiForm != null) {
                return false;
            }
		} else if (!emiForm.equals(other.emiForm)) {
            return false;
        }
//		if (fifShow == null) {
//			if (other.fifShow != null)
//				return false;
//		} else if (!fifShow.equals(other.fifShow))
//			return false;
		if (haveCanal == null) {
			if (other.haveCanal != null) {
                return false;
            }
		} else if (!haveCanal.equals(other.haveCanal)) {
            return false;
        }
		if (historicWater == null) {
			if (other.historicWater != null) {
                return false;
            }
		} else if (!historicWater.equals(other.historicWater)) {
            return false;
        }
		if (insertTimeForHis == null) {
			if (other.insertTimeForHis != null) {
                return false;
            }
		} else if (!insertTimeForHis.equals(other.insertTimeForHis)) {
            return false;
        }
		if (itemDistance == null) {
			if (other.itemDistance != null) {
                return false;
            }
		} else if (!itemDistance.equals(other.itemDistance)) {
            return false;
        }
		if (itemEnvironment == null) {
			if (other.itemEnvironment != null) {
                return false;
            }
		} else if (!itemEnvironment.equals(other.itemEnvironment)) {
            return false;
        }
		if (largeProImpact == null) {
			if (other.largeProImpact != null) {
                return false;
            }
		} else if (!largeProImpact.equals(other.largeProImpact)) {
            return false;
        }
		if (largeProjects == null) {
			if (other.largeProjects != null) {
                return false;
            }
		} else if (!largeProjects.equals(other.largeProjects)) {
            return false;
        }
		if (lowEquipment == null) {
			if (other.lowEquipment != null) {
                return false;
            }
		} else if (!lowEquipment.equals(other.lowEquipment)) {
            return false;
        }
		if (manSituation == null) {
			if (other.manSituation != null) {
                return false;
            }
		} else if (!manSituation.equals(other.manSituation)) {
            return false;
        }
		if (monitorFlag == null) {
			if (other.monitorFlag != null) {
                return false;
            }
		} else if (!monitorFlag.equals(other.monitorFlag)) {
            return false;
        }
		if (operateTimeForHis == null) {
			if (other.operateTimeForHis != null) {
                return false;
            }
		} else if (!operateTimeForHis.equals(other.operateTimeForHis)) {
            return false;
        }
		if (ownership == null) {
			if (other.ownership != null) {
                return false;
            }
		} else if (!ownership.equals(other.ownership)) {
            return false;
        }
		if (rainRecord == null) {
			if (other.rainRecord != null) {
                return false;
            }
		} else if (!rainRecord.equals(other.rainRecord)) {
            return false;
        }
		if (riskCheckMain == null) {
			if (other.riskCheckMain != null) {
                return false;
            }
		} else if (!riskCheckMain.equals(other.riskCheckMain)) {
            return false;
        }
		if (riskCheckNo == null) {
			if (other.riskCheckNo != null) {
                return false;
            }
		} else if (!riskCheckNo.equals(other.riskCheckNo)) {
            return false;
        }
		if (staggeredFlag == null) {
			if (other.staggeredFlag != null) {
                return false;
            }
		} else if (!staggeredFlag.equals(other.staggeredFlag)) {
            return false;
        }
		if (stoForm == null) {
			if (other.stoForm != null) {
                return false;
            }
		} else if (!stoForm.equals(other.stoForm)) {
            return false;
        }
		if (stoLocation == null) {
			if (other.stoLocation != null) {
                return false;
            }
		} else if (!stoLocation.equals(other.stoLocation)) {
            return false;
        }
		if (transferFlag == null) {
			if (other.transferFlag != null) {
                return false;
            }
		} else if (!transferFlag.equals(other.transferFlag)) {
            return false;
        }
		if (underAssetsFlag == null) {
			if (other.underAssetsFlag != null) {
                return false;
            }
		} else if (!underAssetsFlag.equals(other.underAssetsFlag)) {
            return false;
        }
		if (unobstructedFlag == null) {
			if (other.unobstructedFlag != null) {
                return false;
            }
		} else if (!unobstructedFlag.equals(other.unobstructedFlag)) {
            return false;
        }
		if (waterSensitivity == null) {
			if (other.waterSensitivity != null) {
                return false;
            }
		} else if (!waterSensitivity.equals(other.waterSensitivity)) {
            return false;
        }
		return true;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "riskcheckno", nullable = false, insertable = false, updatable = false)
  public RiskCheckMain getRiskCheckMain() {
		return riskCheckMain;
	}

	public void setRiskCheckMain(RiskCheckMain riskCheckMain) {
		this.riskCheckMain = riskCheckMain;
	}
	@Basic
  @Column(name = "characteristics", nullable = true, length = 1)
	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}
}
