package com.picc.riskctrl.common.po;

import com.picc.riskctrl.riskins.vo.ImageConfigVo;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "RISKDCODE")
public class RiskDcode  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "codeType", column = @Column(name = "codetype")),
			@AttributeOverride(name = "codeCode", column = @Column(name = "codecode")) })
	private RiskDcodeId id;
	/**机构代码*/
	@Column(name = "comcode", nullable = true)
	private String comCode;
	/**代码*/
	@Column(name = "uppercode", nullable = true)
	private String upperCode;
	/**中文含义*/
	@Column(name = "codecname", nullable = true)
	private String codeCname;
	/**英文含义*/
	@Column(name = "codeename", nullable = true)
	private String codeEname;
	/**说明*/
	@Column(name = "introduce", nullable = true)
	private String introduce;
	/**有效标志位*/
	@Column(name = "validstatus", nullable = true)
	private String validStatus;
	@Transient
	private String validStatusName;
	
	/**插入时间*/
	@Column(name = "inserttimeforhis",insertable = false, updatable = false)
	private Date insertTimeForHis;
	/**更新时间*/
	@Column(name = "operatetimeforhis" ,insertable = false)
	private Date operateTimeForHis;
	/** 子节点 */
	@Transient
	private List<RiskDcode> childRiskDcode;
	/** 该节点下图片信息*/
	@Transient
	private ImageConfigVo configList;
	public RiskDcodeId getId() {
		return id;
	}
	public void setId(RiskDcodeId id) {
		this.id = id;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getUpperCode() {
		return upperCode;
	}
	public void setUpperCode(String upperCode) {
		this.upperCode = upperCode;
	}
	public String getCodeCname() {
		return codeCname;
	}
	public void setCodeCname(String codeCname) {
		this.codeCname = codeCname;
	}
	public String getCodeEname() {
		return codeEname;
	}
	public void setCodeEname(String codeEname) {
		this.codeEname = codeEname;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	public String getValidStatusName() {
		return validStatusName;
	}
	public void setValidStatusName(String validStatusName) {
		this.validStatusName = validStatusName;
	}
	public Date getInsertTimeForHis() {
		return insertTimeForHis;
	}
	public void setInsertTimeForHis(Date insertTimeForHis) {
		this.insertTimeForHis = insertTimeForHis;
	}
	public Date getOperateTimeForHis() {
		return operateTimeForHis;
	}
	public void setOperateTimeForHis(Date operateTimeForHis) {
		this.operateTimeForHis = operateTimeForHis;
	}
	public List<RiskDcode> getChildRiskDcode() {
		return childRiskDcode;
	}
	public void setChildRiskDcode(List<RiskDcode> childRiskDcode) {
		this.childRiskDcode = childRiskDcode;
	}
	public ImageConfigVo getConfigList() {
		return configList;
	}
	public void setConfigList(ImageConfigVo configList) {
		this.configList = configList;
	}
	
	
	
}
