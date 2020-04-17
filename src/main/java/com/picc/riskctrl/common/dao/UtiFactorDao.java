package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.jpa.base.JpaBaseRepository;
import com.picc.riskctrl.common.po.UtiFactor;
import com.picc.riskctrl.common.po.UtiFactorId;
import org.springframework.stereotype.Repository;

@Repository
public interface UtiFactorDao extends JpaBaseRepository<UtiFactor, UtiFactorId>{
	
}
