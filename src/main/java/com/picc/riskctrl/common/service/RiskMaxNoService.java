package com.picc.riskctrl.common.service;

import com.picc.riskctrl.common.dao.RiskMaxNoRepository;
import com.picc.riskctrl.common.vo.RiskMaxNoResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "RiskMaxNoService")
public class RiskMaxNoService {

	public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");
	
	@Autowired
	private RiskMaxNoRepository  riskMaxNoRepository;
	
	/**
	 * @查询可用的最大号和最小号
	 * @author 马军亮
	 * @param groupNo 组号
	 * @param tableName 表名
	 * @return 查询结果记录集
	 * @throws Exception
	 */
	public String[] getMaxMinNo(String iGroupNo, String iTableName)	throws Exception {
		String[] strMaxMinNo = new String[3];
		try {
			RiskMaxNoResp resp = riskMaxNoRepository.getMaxMinNo(iGroupNo, iTableName);
			
			strMaxMinNo[0] = resp.getCount();
			strMaxMinNo[1] = resp.getMax();
			strMaxMinNo[2] = resp.getMin();
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("查询异常：" + e.getMessage() ,e);
        	throw new RuntimeException("查询异常:"+e);
		}
		return strMaxMinNo;
	}
}
