package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.schema.RiskGroup;
import com.picc.riskctrl.common.schema.RiskGroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskGroupRepository extends JpaRepository<RiskGroup, RiskGroupId>,JpaSpecificationExecutor<RiskGroup>{
	@Query(value="SELECT count(*) FROM riskcheck_main  WHERE riskcheckno = ?1 ",nativeQuery=true)
	int getCountRiskCheck(String iBillNo);
	@Query(value="SELECT count(*) FROM riskReport_main  WHERE riskfileno = ?1 ",nativeQuery=true)
	int getCountRiskReport(String iBillNo);
	@Query(value="SELECT count(*) FROM riskReportSale_main  WHERE archivesNo = ?1 ",nativeQuery=true)
	int getCountRiskReportSale(String iBillNo);
}
