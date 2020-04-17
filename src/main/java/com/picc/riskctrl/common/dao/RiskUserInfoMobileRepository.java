package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.po.RiskUserInfoMobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RiskUserInfoMobileRepository extends JpaRepository<RiskUserInfoMobile, String>, JpaSpecificationExecutor<RiskUserInfoMobile> {
}
