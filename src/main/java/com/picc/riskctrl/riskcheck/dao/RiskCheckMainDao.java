
package com.picc.riskctrl.riskcheck.dao;

import com.picc.riskctrl.common.schema.PrpDcompanyFk;
import com.picc.riskctrl.riskcheck.po.RiskCheckMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskCheckMainDao extends JpaRepository<RiskCheckMain,String>, JpaSpecificationExecutor<RiskCheckMain>{

	//查询根据主键查询PrpDcompany表
	@Query(value = "select * from PrpDcompany where comCode = '?1' ",nativeQuery=true)
	public PrpDcompanyFk viewPrpDcompanyByComCode(String comCode);
	
	//查询多条根据upperPath查询PrpDcompany表
	@Query(value = "select * from PrpDcompany where substr(this_.upperpath,0,26) = ?1 ",nativeQuery=true)
	public List<PrpDcompanyFk> queryPrpDcompanyByUpperPath(String upperPath);
}
