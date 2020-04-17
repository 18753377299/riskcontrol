package com.picc.riskctrl.common.service;

import com.picc.riskctrl.common.dao.RiskGroupRepository;
import com.picc.riskctrl.common.dao.RiskMaxNoRepository;
import com.picc.riskctrl.common.schema.RiskGroup;
import com.picc.riskctrl.common.schema.RiskMaxNo;
import com.picc.riskctrl.common.schema.RiskMaxNoId;
import com.picc.riskctrl.common.schema.UtiKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BillService")
public class BillService {
	public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");
	
	@Autowired
	UtiKeyService utiKeyService;
	
	@Autowired
	RiskGroupService riskGroupService;
	
	@Autowired
	RiskMaxNoService riskMaxNoService;
	
	@Autowired
	RiskMaxNoRepository riskMaxNoRepository;
	
	@Autowired
	RiskGroupRepository riskGroupRepository;

	
	
	/**
	 * @功能：获取一个新号
	 * @author 马军亮
	 * @param iTableName 单号数据表名
	 * @param iFieldName 字段代码
	 * @param iRiskCode 险种代码
	 * @param iComCode 出单部门
	 * @param iYear 业务年度
	 * @param iSessionID
	 * @return UtiKey
	 * @throws
	 * @日期：2017-10-12
	 */
	public String getNo(String iTableName, String iFieldName, String iRiskCode,String iComCode, int iYear, String iSessionID, String iRiskModel)throws Exception {
		String strGroupNo;
		String strMaxNo = "";
		String strMinNo = "";
		String strNewNo = "";
		String strHead = "";

		int intChgLength;
		int intCount;
		String strYear = String.valueOf(iYear);
		String[] strMaxMinNo = new String[3];

		// 拼接成组号
		strHead = getHead(iTableName, iFieldName);
		strGroupNo = riskGroupService.getGroupNo(iTableName, iRiskCode,iComCode, strYear, iRiskModel);
		
		strGroupNo = strHead + strGroupNo;
		if ((null == strGroupNo || 0 == strGroupNo.length())
				&& !"PolicyTask".equals(iTableName)) {
			return "";
		}

		// 增加计数器，如果循环20次依然取不到单号，则抛异常
		int whileCount = 0;
		WHILE_LABEL: while (true) {
			if (whileCount++ > 20) {
				throw new Exception("无法获取单号，请联系管理员！");
			}
			strMaxMinNo = riskMaxNoService.getMaxMinNo(strGroupNo, iTableName);
			strMaxNo = strMaxMinNo[1];
			strMinNo = strMaxMinNo[2];
			intCount = Integer.parseInt(strMaxMinNo[0]);
			if (0 == intCount) {
				try {
					
					RiskMaxNo riskMaxNo = new RiskMaxNo();
					RiskMaxNoId id = new RiskMaxNoId();
					riskMaxNo.setId(id);
					id.setGroupNo(strGroupNo);
					id.setTableName(iTableName);
					
					// 照片编号生成  begin  RiskReportSaleMain
					if ("PolicyTask".equals(iTableName)) {
						riskMaxNo.getId().setMaxNo("000001");
					}else if("RiskReportSaleMain".equals(iTableName)) {
						riskMaxNo.getId().setMaxNo("00001");
						
					// 汛检报告编号生成
					}else if("RiskCheckMain".equals(iTableName)) {
						riskMaxNo.getId().setMaxNo("00001");
					}
					else {
						riskMaxNo.getId().setMaxNo("000001");
					}
					riskMaxNo.setFlag("0");
					riskMaxNoRepository.save(riskMaxNo);
					
				} catch (Exception ex1) {
					
					LOGGER.info("与数据库交互异常：" + ex1.getMessage() ,ex1);
					throw new RuntimeException("与数据库交互异常:"+ex1);
				}
				strMaxMinNo = riskMaxNoService.getMaxMinNo(strGroupNo,iTableName);
			}
			strMaxNo = strMaxMinNo[1];
			strMinNo = strMaxMinNo[2];
			intCount = Integer.parseInt(strMaxMinNo[0]);
			if (strMaxNo.trim().equals(strMinNo.trim())) {
				strMaxNo = String.valueOf(Long.parseLong(strMinNo) + 1);
				if("RiskReportSaleMain".equals(iTableName) || "RiskCheckMain".equals(iTableName)) {
					intChgLength = 5 - strMaxNo.length();
				} else {
					intChgLength = 6 - strMaxNo.length();
				}
				String str = "";
				for (int i = 0; i < intChgLength; i++) {
					str += "0";
				}
				strMaxNo = str.trim() + strMaxNo;
				
				try {
					RiskMaxNo riskMaxNo = new RiskMaxNo();
					RiskMaxNoId id = new RiskMaxNoId();
					riskMaxNo.setId(id);
					id.setGroupNo(strGroupNo);
					id.setTableName(iTableName);
					id.setMaxNo(strMaxNo);
					riskMaxNo.setFlag("0");
					riskMaxNoRepository.save(riskMaxNo);
				} catch (Exception ex1) {
					LOGGER.info("查询异常：" + ex1.getMessage() ,ex1);
					ex1.printStackTrace();
					throw new RuntimeException("查询异常:"+ex1);
				}
			}

			try {
				RiskMaxNo riskMaxNo = new RiskMaxNo();
				RiskMaxNoId id = new RiskMaxNoId();
				id.setGroupNo(strGroupNo);
				id.setTableName(iTableName);
				id.setMaxNo(strMinNo);
				riskMaxNo.setId(id);
				riskMaxNoRepository.delete(riskMaxNo);
			} catch (Exception ex2) {
				ex2.printStackTrace();
				continue WHILE_LABEL;
			}
			strNewNo = pullNo(iTableName, iFieldName, strMinNo, strGroupNo);
			// 校验单号的有效性
			if (checkNo(iTableName.trim(), iFieldName.trim(), strNewNo.trim(),strGroupNo.trim(), "0")) {
				break WHILE_LABEL;
			}
		}
		// 返回新生成的单号
		return strNewNo;
	}
	
	
	/**
	 * @功能 拉长单号
	 * @author 马军亮
	 * @param iTableName 单号数据表名
	 * @param iFieldName 字段代码
	 * @param iBillNo 单号
	 * @param iGroupNo 组号
	 * @return UtiKey
	 * @throws
	 * @日期：2017-10-12
	 */
	public String pullNo(String iTableName, String iFieldName, String iBillNo,String iGroupNo) throws Exception {
		String strColLength = "";
		String strBillNo = "";
		int intNoLength = 0, intChgLength = 0;
		int intLength = 0;
		int intColLength = 0;
		UtiKey utiKey = null;

		strBillNo = iBillNo;

		utiKey = utiKeyService.getInfo(iTableName, iFieldName);
		if (utiKey == null) {
			return strBillNo;
		}
		strColLength = utiKey.getColLength() + "";
		if (null != strColLength && !"".equals(strColLength)) {
			intColLength = Integer.parseInt(strColLength);
		}
		// 单号的总长度
		intLength = iGroupNo.length() + strColLength.length();
		if (iBillNo.length() >= intLength) {
			return iBillNo;
		}
		intNoLength = iBillNo.length();
		intChgLength = intColLength - intNoLength;
		if (intChgLength >= 0) {
			
		} else {
			iBillNo = iBillNo.substring(Math.abs(intChgLength));
		}
		iBillNo = iGroupNo.trim() + iBillNo.trim();
		return iBillNo;
	}
	
	
	
	/**
	 * 单号检查
	 * @param iTableName 数据表
	 * @param iBillNo 单号
	 * @param iGroupNo 组号
	 * @param iCheckFlag 检查类型
	 */
	public boolean checkNo(String iTableName, String iFieldName,String iBillNo, String iGroupNo, String iCheckFlag) throws Exception {
		boolean blnResult = false;
		String strFieldName = "";
		
		String strGroupNo = "", strMaxNo = "", strBillNo = "";
		String[] strPickNo = new String[3];
		UtiKey utiKey = utiKeyService.getInfo(iTableName, iFieldName);
		if (utiKey == null) {
			blnResult = false;
			return blnResult;
		}
		// 将单号分离成单号头+分组+流水号
		strPickNo = pickNo(iTableName, iBillNo);
		strGroupNo = strPickNo[1];
		strMaxNo = strPickNo[2];
		// 拉长单号
		if ("2".equals(iCheckFlag)) {
			blnResult = true;
			return blnResult;
		}
		// 获取所有的分组
		List<RiskGroup> list = riskGroupService.query(strGroupNo);
		
		int intSize = list.size();
		
		strFieldName = iFieldName;
		
		for (int i = 0; i < intSize; i++) {
			strBillNo = list.get(i).getId().getSubGroupNo() + strMaxNo;
		}
		
		//int intCount = riskGroupRepository.getCount(iTableName, strFieldName, Integer.parseInt(iBillNo), strFieldName,strBillNo);
		
		int intCount = 0;
		try {
			if("RiskCheckMain".equals(iTableName)) {
				intCount = riskGroupRepository.getCountRiskCheck(iBillNo);
			}else if ("RiskReportMain".equals(iTableName)) {
				intCount = riskGroupRepository.getCountRiskReport(iBillNo);
			}else {
				intCount = riskGroupRepository.getCountRiskReportSale(iBillNo);
			}
//			intCount = riskGroupRepositoryEnity.getTotalCount(iTableName, strFieldName, iBillNo, strBillNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (intCount > 1) {
			blnResult = false;
		} else if ((intCount == 1) && ("0".equals(iCheckFlag))) {
			blnResult = false;
		} else if ((intCount == 0) && ("1".equals(iCheckFlag))) {
			blnResult = false;
		} else if ((intCount == 0) && ("2".equals(iCheckFlag))) {
			blnResult = false;
		} else {
			blnResult = true;
		}
		return blnResult;
	}
	
	
	// --函数功能:单号检查
	private String[] pickNo(String iTableName, String iBillNo) throws Exception {
		String[] strPickNo = new String[3];
		String strGroupNo = "";
		String strSerialNo = "";
		int intBillNoLgn = iBillNo.length();
		if ("fcorepolicy".equalsIgnoreCase(iTableName) || "fporeendor".equalsIgnoreCase(iTableName)
				|| "florepay".equalsIgnoreCase(iTableName) || "fzacc".equalsIgnoreCase(iTableName)) {
			if (16 == intBillNoLgn) {
				strGroupNo = iBillNo.substring(1, 10);
				strSerialNo = iBillNo.substring(10);
			}
		} else if ("fjsettle".equalsIgnoreCase(iTableName)) {
			if (8 == intBillNoLgn) {
				strGroupNo = iBillNo.substring(0, 4);
				strSerialNo = iBillNo.substring(4, 7);
			}
		} else if (!"prpphead".equalsIgnoreCase(iTableName)) {
			if (21 == intBillNoLgn) {
				strGroupNo = iBillNo.substring(1, 15);
				strSerialNo = iBillNo.substring(15);
			} else if (19 == intBillNoLgn) {
				strGroupNo = iBillNo.substring(1, 13);
				strSerialNo = iBillNo.substring(13);
			}
		}

		strGroupNo = riskGroupService.getGroupNo(strGroupNo);
		strPickNo[0] = iBillNo.substring(0, 1);
		strPickNo[1] = strGroupNo;
		strPickNo[2] = strSerialNo;
		return strPickNo;
	}
	
	/**
	 * @功能 获取单号头
	 * @author 马军亮
	 * @param iTableName 数据表名
	 * @param iBillNo 单号
	 * @param iGroupNo 组号
	 * @throws Exception
	 */
	public String getHead(String iTableName, String iFieldName)
			throws Exception {
		String strHeadID = "";
		UtiKey utiKey = null;

		utiKey = utiKeyService.getInfo(iTableName, iFieldName);
		if (utiKey == null) {
			throw new Exception("单号生成失败，请检查UtiKey表中的数据！");
		}
		strHeadID = utiKey.getHeadId();
		return strHeadID;
	}
}
