package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.base.BaseRepository;
import com.picc.riskctrl.common.po.UtiBackRuleConfig;
import com.picc.riskctrl.common.po.UtiBackRuleConfigId;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description:
 * @author: QuLingjie
 * @data: 2020/1/16
*/
public interface UtiBackRuleConfigRepository extends BaseRepository<UtiBackRuleConfig, UtiBackRuleConfigId> {
    @Query("select r from UtiBackRuleConfig r where ruleCode =?1 and validStatus=?2")
    List<UtiBackRuleConfig> findAllByRuleCodeAndValidStatus(String ruleCode, String validStatus);

}
