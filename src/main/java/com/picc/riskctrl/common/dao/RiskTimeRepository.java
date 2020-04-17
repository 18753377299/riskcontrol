package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.schema.RiskTime;
import com.picc.riskctrl.common.schema.RiskTimeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskTimeRepository extends JpaRepository<RiskTime, RiskTimeId>, JpaSpecificationExecutor<RiskTime> {

}
