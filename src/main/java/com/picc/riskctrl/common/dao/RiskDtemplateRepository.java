package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.po.RiskDtemplate;
import com.picc.riskctrl.common.po.RiskDtemplateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: databaseDao
 * @Author: liqiankun
 * @Date: 20200114
 **/
public interface RiskDtemplateRepository extends JpaRepository<RiskDtemplate, RiskDtemplateId>, JpaSpecificationExecutor<RiskDtemplate> {
	
}
