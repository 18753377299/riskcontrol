package com.picc.riskctrl.riskins.dao;

import com.picc.riskctrl.common.jpa.base.JpaBaseRepository;
import com.picc.riskctrl.riskins.po.RiskReportMain;
import org.springframework.data.jpa.repository.Query;

/**
 * @author anqingsen
 * @功能：根据报告编号查询报告详细信息
 *
 */
public interface RiskReportMainRepository extends JpaBaseRepository<RiskReportMain,String> {

	@Query("from RiskReportMain where riskFileNo = ?1")
	RiskReportMain queryExpertByexpertNoHQL(String riskFileNo);

}
