package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.jpa.base.JpaBaseRepository;
import com.picc.riskctrl.common.po.UtiScore;
import com.picc.riskctrl.common.po.UtiScoreId;
import org.springframework.stereotype.Repository;

@Repository
public interface UtiScoreDao extends  JpaBaseRepository<UtiScore, UtiScoreId> {
	
}
