package com.picc.riskctrl.riskcheck.dao;

import com.picc.riskctrl.riskcheck.po.RiskCheckMainLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskCheckMainLogDao extends JpaRepository<RiskCheckMainLog,Integer>, JpaSpecificationExecutor<RiskCheckMainLog>{

	
}
