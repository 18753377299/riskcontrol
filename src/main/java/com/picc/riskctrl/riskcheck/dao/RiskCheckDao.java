package com.picc.riskctrl.riskcheck.dao;

import com.picc.riskctrl.riskcheck.po.RiskCheckMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface RiskCheckDao extends JpaRepository<RiskCheckMain,String>,Repository<RiskCheckMain, String>, JpaSpecificationExecutor<RiskCheckMain>{

}
