package com.picc.riskctrl.riskinfo.claim.dao;

import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.common.po.RiskDcodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RiskDcodeDao extends JpaRepository<RiskDcode,RiskDcodeId>, JpaSpecificationExecutor<RiskDcode> {

	List<RiskDcode> findByIdCodeType(String codeType);
}
