package com.picc.riskctrl.riskinfo.superRiskReport.dao;

import com.picc.riskctrl.base.BaseRepository;
import com.picc.riskctrl.riskinfo.superRiskReport.po.SuperRiskReport;
import org.springframework.data.jpa.repository.Query;

/**
 * 优秀风控报告dao
 *
 * @author wangwenjie
 * @date 2020-01-09
 */
public interface SuperRiskReportDao extends BaseRepository<SuperRiskReport, Integer> {
    @Query(value="select max(SERIALNO)  from  RISKINFO_RISKFILE ",nativeQuery=true)
    public Integer getMaxNo();
}
