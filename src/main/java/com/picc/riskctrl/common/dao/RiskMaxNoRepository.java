package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.schema.RiskMaxNo;
import com.picc.riskctrl.common.vo.RiskMaxNoResp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskMaxNoRepository extends JpaRepository<RiskMaxNo, Integer>,JpaSpecificationExecutor<RiskMaxNo> {

	@Query(value="SELECT MAX(maxNo), MIN(maxNo), COUNT(*) FROM RiskMaxNo  WHERE groupNo = ?1  AND tableName = ?2",nativeQuery=true)
	public RiskMaxNoResp getMaxMinNo(String groupNo,String tableName);
	
	
}
