package com.picc.riskctrl.riskinfo.report.dao;

import com.picc.riskctrl.riskinfo.report.po.RiskInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

/**
 * @功能：专业风控模板表
 * @author liqiankun
 * @日期 20200109
 */
public interface RiskInfoModelRepository extends JpaRepository<RiskInfoModel,Integer>,Repository<RiskInfoModel, Integer>, JpaSpecificationExecutor<RiskInfoModel>{
	
}
