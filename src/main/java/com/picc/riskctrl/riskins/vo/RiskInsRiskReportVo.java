package com.picc.riskctrl.riskins.vo;

import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.riskins.po.*;

import java.util.List;


public class RiskInsRiskReportVo {

	

	/**风控信息主表*/

	private RiskReportMain riskReportMainVo;

	/**风控机器损坏险风险评估**/

	private List<RiskReportMachine> riskReportMachineList;

	/**风控营业中断险风险评估**/

	private List<RiskReportCloBusiness> riskReportCloBusinessList;

	/** 风控建筑 */

	private List<RiskReportConstruct> riskReportConstructList;

	/** 风控保护措施 */

	private List<RiskReportProtection> riskReportProtectionList;

	/** 风控环境*/

	private List<RiskReportEnvironment> riskReportEnvironmentList;

	/** 风控占用性质*/

	private List<RiskReportOccupation> riskReportOccupationList;

	/** 风控附加扩展盗窃、抢劫责任 */

	private List<RiskReportTheft> riskReportTheftList;

	/** 风控附加扩展供应中断责任 */

	private List<RiskReportInterrupt> riskReportInterruptList;

	/** 风控风险值 */

	private List<RiskReportAssess> riskReportAssessList;

	/** 风控附加扩展露天堆放 */

	private List<RiskReportAirStorage> riskReportAirStorageList;

	/** 风控建筑详细信息 */

	private List<RiskReportConstructInfo> riskReportConstructInfoList;

	/**风控标的要素*/

	private List<RiskReportClaim> riskReportClaimList;

	/**风控地址信息*/

	private List<RiskReportAddress> riskReportAddressList;

	/** 风控报告影像菜单信息 */

	private List<RiskDcode> riskDcodeList;

	/** 影像资料 */

	private List<RiskReportPicture> riskReportPictureList;

//	/** 要删除的图片信息 */

//	private List<RiskDcode> riskDcodeListDel;

//	/** 移动端图片信息*/

//	private Map<String,List<CommonsMultipartFile>> mapFiles;

	/**风控火灾风险排查*/

	private List<RiskReportFireDanger> riskReportFireDangerList;

	

	/** 雷达图图片的echars编码*/

	private String picBase64Info;

	/**异常信息*/

	private String QCexception;

	

	

	

	public List<RiskReportMachine> getRiskReportMachineList() {

		return riskReportMachineList;

	}

	public void setRiskReportMachineList(List<RiskReportMachine> riskReportMachineList) {

		this.riskReportMachineList = riskReportMachineList;

	}

	public String getQQexception() {

		return QCexception;

	}

	public void setQCexception(String stackTraceElements) {

		this.QCexception = stackTraceElements;

	}

	public RiskReportMain getRiskReportMainVo() {

		return riskReportMainVo;

	}

	public void setRiskReportMainVo(RiskReportMain riskReportMainVo) {

		this.riskReportMainVo = riskReportMainVo;

	}

	public List<RiskReportConstruct> getRiskReportConstructList() {

		return riskReportConstructList;

	}

	public void setRiskReportConstructList(

			List<RiskReportConstruct> riskReportConstructList) {

		this.riskReportConstructList = riskReportConstructList;

	}

	public List<RiskReportProtection> getRiskReportProtectionList() {

		return riskReportProtectionList;

	}

	public void setRiskReportProtectionList(

			List<RiskReportProtection> riskReportProtectionList) {

		this.riskReportProtectionList = riskReportProtectionList;

	}

	public List<RiskReportEnvironment> getRiskReportEnvironmentList() {

		return riskReportEnvironmentList;

	}

	public void setRiskReportEnvironmentList(

			List<RiskReportEnvironment> riskReportEnvironmentList) {

		this.riskReportEnvironmentList = riskReportEnvironmentList;

	}

	public List<RiskReportOccupation> getRiskReportOccupationList() {

		return riskReportOccupationList;

	}

	public void setRiskReportOccupationList(

			List<RiskReportOccupation> riskReportOccupationList) {

		this.riskReportOccupationList = riskReportOccupationList;

	}

	public List<RiskReportTheft> getRiskReportTheftList() {

		return riskReportTheftList;

	}

	public void setRiskReportTheftList(List<RiskReportTheft> riskReportTheftList) {

		this.riskReportTheftList = riskReportTheftList;

	}

	public List<RiskReportInterrupt> getRiskReportInterruptList() {

		return riskReportInterruptList;

	}

	public void setRiskReportInterruptList(

			List<RiskReportInterrupt> riskReportInterruptList) {

		this.riskReportInterruptList = riskReportInterruptList;

	}

	public List<RiskReportAssess> getRiskReportAssessList() {

		return riskReportAssessList;

	}

	public void setRiskReportAssessList(List<RiskReportAssess> riskReportAssessList) {

		this.riskReportAssessList = riskReportAssessList;

	}

	public List<RiskReportAirStorage> getRiskReportAirStorageList() {

		return riskReportAirStorageList;

	}

	public void setRiskReportAirStorageList(

			List<RiskReportAirStorage> riskReportAirStorageList) {

		this.riskReportAirStorageList = riskReportAirStorageList;

	}

	public List<RiskReportConstructInfo> getRiskReportConstructInfoList() {

		return riskReportConstructInfoList;

	}

	public void setRiskReportConstructInfoList(

			List<RiskReportConstructInfo> riskReportConstructInfoList) {

		this.riskReportConstructInfoList = riskReportConstructInfoList;

	}

	public List<RiskReportClaim> getRiskReportClaimList() {

		return riskReportClaimList;

	}

	public void setRiskReportClaimList(List<RiskReportClaim> riskReportClaimList) {

		this.riskReportClaimList = riskReportClaimList;

	}

	public List<RiskReportAddress> getRiskReportAddressList() {

		return riskReportAddressList;

	}

	public void setRiskReportAddressList(

			List<RiskReportAddress> riskReportAddressList) {

		this.riskReportAddressList = riskReportAddressList;

	}

	public List<RiskDcode> getRiskDcodeList() {

		return riskDcodeList;

	}

	public void setRiskDcodeList(List<RiskDcode> riskDcodeList) {

		this.riskDcodeList = riskDcodeList;

	}

//	public List<RiskDcode> getRiskDcodeListDel() {

//		return riskDcodeListDel;

//	}

//	public void setRiskDcodeListDel(List<RiskDcode> riskDcodeListDel) {

//		this.riskDcodeListDel = riskDcodeListDel;

//	}

//	public Map<String, List<CommonsMultipartFile>> getMapFiles() {

//		return mapFiles;

//	}

//	public void setMapFiles(Map<String, List<CommonsMultipartFile>> mapFiles) {

//		this.mapFiles = mapFiles;

//	}

	public String getPicBase64Info() {

		return picBase64Info;

	}

	public void setPicBase64Info(String picBase64Info) {

		this.picBase64Info = picBase64Info;

	}	

	public List<RiskReportPicture> getRiskReportPictureList() {

		return riskReportPictureList;

	}

	public void setRiskReportPictureList(List<RiskReportPicture> riskReportPictureList) {

		this.riskReportPictureList = riskReportPictureList;

	}

	public List<RiskReportCloBusiness> getRiskReportCloBusinessList() {

		return riskReportCloBusinessList;

	}

	public void setRiskReportCloBusinessList(List<RiskReportCloBusiness> riskReportCloBusinessList) {

		this.riskReportCloBusinessList = riskReportCloBusinessList;

	}

	public List<RiskReportFireDanger> getRiskReportFireDangerList() {

		return riskReportFireDangerList;

	}

	public void setRiskReportFireDangerList(

			List<RiskReportFireDanger> riskReportFireDangerList) {

		this.riskReportFireDangerList = riskReportFireDangerList;

	}

	

}


