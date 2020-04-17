package com.picc.riskctrl.common.po;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "RISKDNATURAL")
public class RiskDnatural  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**顺序号*/
	private Integer serialNo;
	/**数据采集年度*/
	private String naturalYear;
	/**机构*/
	private String comCode;
	/**归属机构名称*/
	private String comCodeCName;
	/**所处地区代码*/
	private String addressCode;
	/**所处地区名称*/
	private String addressName;
	/**所处地区省级代码*/
	private String addressProvince;
	/**年平均降水量*/
	private String aveRainfal;
	/**有无积水*/
	private String havePonding;
	/**历史最高积水水位*/
	private BigDecimal ponding;
	/**区域降雪量暴雪记录*/
	private String haveBlizzard;
	/**区域受热带气旋影响程度*/
	private String haveCyclone;
	/**热带气旋影响月份*/
	private String cycloneMonth;
	/**热带气旋登录个数*/
	private Integer entryNum;
	/**热带气旋登录最大等级*/
	private Integer entryLev;
	/**热带气旋影响个数*/
	private Integer impactNum;
	/**热带气旋影响最大等级*/
	private Integer impactLev;
	/**区域雷暴情况*/
	private String haveThunder;
	/**是否处于地震带 */
	private String haveEarthquake;
	/**历史最高地震等级*/
	private String earthquakeHis;
	/**10年地震次数*/
	private String earthquakeNum;
	/**是否处于泥石流、滑坡、崩塌多发区*/
	private String haveCollapse;
	/**否发生过重大泥石流、滑坡、崩塌灾害*/
	private String collapseHis;
	/**报告制作人代码*/
	private String operatorCode;
	/**报告制作人名称*/
	private String operatorName;
	/**报告制作日期*/
	private Date madeDate;
	/**有效标志位*/
	private String validStatus;
	/**有效标志位中文*/
	private String validStatusName;
	/**插入时间*/
	private Date insertTimeForHis;
	/**更新时间*/
	private Date operateTimeForHis;

	@Id
	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	
	@Column(name = "NATURALYEAR")
	public String getNaturalYear() {
		return naturalYear;
	}
	public void setNaturalYear(String naturalYear) {
		this.naturalYear = naturalYear;
	}
	@Column(name = "COMCODE")
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	@Column(name = "ADDRESSCODE")
	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	@Column(name = "AVERAINFAL")
	public String getAveRainfal() {
		return aveRainfal;
	}
	public void setAveRainfal(String aveRainfal) {
		this.aveRainfal = aveRainfal;
	}
	@Column(name = "HAVEPONDING")
	public String getHavePonding() {
		return havePonding;
	}
	public void setHavePonding(String havePonding) {
		this.havePonding = havePonding;
	}
	@Column(name = "PONDING")
	public BigDecimal getPonding() {
		return ponding;
	}
	public void setPonding(BigDecimal ponding) {
		this.ponding = ponding;
	}
	@Column(name = "HAVEBLIZZARD")
	public String getHaveBlizzard() {
		return haveBlizzard;
	}
	public void setHaveBlizzard(String haveBlizzard) {
		this.haveBlizzard = haveBlizzard;
	}
	@Column(name = "HAVECYCLONE")
	public String getHaveCyclone() {
		return haveCyclone;
	}
	public void setHaveCyclone(String haveCyclone) {
		this.haveCyclone = haveCyclone;
	}
	@Column(name = "CYCLONEMONTH")
	public String getCycloneMonth() {
		return cycloneMonth;
	}
	public void setCycloneMonth(String cycloneMonth) {
		this.cycloneMonth = cycloneMonth;
	}
	@Column(name = "ENTRYNUM")
	public Integer getEntryNum() {
		return entryNum;
	}

	public void setEntryNum(Integer entryNum) {
		this.entryNum = entryNum;
	}
	@Column(name = "ENTRYLEV")
	public Integer getEntryLev() {
		return entryLev;
	}
	public void setEntryLev(Integer entryLev) {
		this.entryLev = entryLev;
	}
	@Column(name = "IMPACTNUM")
	public Integer getImpactNum() {
		return impactNum;
	}
	public void setImpactNum(Integer impactNum) {
		this.impactNum = impactNum;
	}
	@Column(name = "IMPACTLEV")
	public Integer getImpactLev() {
		return impactLev;
	}
	public void setImpactLev(Integer impactLev) {
		this.impactLev = impactLev;
	}
	@Column(name = "HAVETHUNDER")
	public String getHaveThunder() {
		return haveThunder;
	}
	public void setHaveThunder(String haveThunder) {
		this.haveThunder = haveThunder;
	}
	@Column(name = "HAVEEARTHQUAKE")
	public String getHaveEarthquake() {
		return haveEarthquake;
	}
	public void setHaveEarthquake(String haveEarthquake) {
		this.haveEarthquake = haveEarthquake;
	}
	@Column(name = "EARTHQUAKEHIS")
	public String getEarthquakeHis() {
		return earthquakeHis;
	}
	public void setEarthquakeHis(String earthquakeHis) {
		this.earthquakeHis = earthquakeHis;
	}
	@Column(name = "EARTHQUAKENUM")
	public String getEarthquakeNum() {
		return earthquakeNum;
	}
	public void setEarthquakeNum(String earthquakeNum) {
		this.earthquakeNum = earthquakeNum;
	}
	@Column(name = "HAVECOLLAPSE")
	public String getHaveCollapse() {
		return haveCollapse;
	}
	public void setHaveCollapse(String haveCollapse) {
		this.haveCollapse = haveCollapse;
	}
	@Column(name = "COLLAPSEHIS")
	public String getCollapseHis() {
		return collapseHis;
	}
	public void setCollapseHis(String collapseHis) {
		this.collapseHis = collapseHis;
	}
	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	@Column(name = "OPERATORNAME")
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	@Column(name = "MADEDATE")
	public Date getMadeDate() {
		return madeDate;
	}
	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}
	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	@Column(name = "INSERTTIMEFORHIS",insertable=false,updatable=false)
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}
	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	@Column(name = "OPERATETIMEFORHIS",insertable=false)
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}
	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
	@Transient
	public String getValidStatusName() {
		return validStatusName;
	}
	public void setValidStatusName(String validStatusName) {
		this.validStatusName = validStatusName;
	}
	@Transient
	public String getComCodeCName() {
		return comCodeCName;
	}
	public void setComCodeCName(String comCodeCName) {
		this.comCodeCName = comCodeCName;
	}
	@Transient
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	@Transient
	public String getAddressProvince() {
		return addressProvince;
	}
	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}
	
}
