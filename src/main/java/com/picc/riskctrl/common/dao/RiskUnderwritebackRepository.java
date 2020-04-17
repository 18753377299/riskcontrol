package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.schema.RiskUnderwriteback;
import com.picc.riskctrl.common.schema.RiskUnderwritebackId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface RiskUnderwritebackRepository extends JpaRepository<RiskUnderwriteback, RiskUnderwritebackId>, JpaSpecificationExecutor<RiskUnderwriteback> {

    /**
     * @Description:
     * @author: QuLingjie
     * @data: 2020/1/20
     * @param executionId
     * @param updateType
     * @return:long
    */
    @Query(value = "select count(*) from riskunderwriteback r where executionId = ?1 and updateType = ?2",nativeQuery = true)
    long countByExecutionIdAndUpdateType(String executionId, String updateType);
}
