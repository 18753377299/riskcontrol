package com.picc.riskctrl.riskinfo.claim.dao;

import com.picc.riskctrl.riskinfo.claim.po.RiskInfoClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskInfoClaimDao extends JpaRepository<RiskInfoClaim, Integer>, JpaSpecificationExecutor<RiskInfoClaim>{

  @Query(value="select max(SERIALNO)  from  RISKINFO_CLAIM ",nativeQuery=true)
    public Integer getMaxNo();
}
