package com.picc.riskctrl.riskinfo.expert.dao;

import com.picc.riskctrl.riskinfo.expert.po.RiskInfoExpert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

/**
 * @功能：
 * @author liqiankun
 * @throws Exception 
 * @日期 20200109
 */
public interface RiskInfoExpertRepository extends JpaRepository<RiskInfoExpert,Integer>, JpaSpecificationExecutor<RiskInfoExpert>, Repository<RiskInfoExpert, Integer> {

	@Query("from RiskInfoExpert where expertNo = ?1")
	RiskInfoExpert queryExpertByexpertNoHQL(Integer expertNo);
	
}

