package com.picc.riskctrl.riskinfo.superRiskReport.dao;

import com.picc.riskctrl.riskinfo.report.po.RiskInfoFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface RiskInfoFileDao extends JpaRepository<RiskInfoFile,Integer>,Repository<RiskInfoFile, Integer>, JpaSpecificationExecutor<RiskInfoFile> {

}
