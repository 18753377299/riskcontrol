package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.jpa.base.JpaBaseRepository;
import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.common.po.RiskDcodeId;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName: databaseDao
 * @Author: 张日炜
 * @Date: 2020-01-08 10:26
 **/
public interface RiskDcodeRepository extends JpaBaseRepository<RiskDcode, RiskDcodeId> {

    @Query(value="select r from RiskDcode r where codeType =?1 order by codeEname asc")
    List<RiskDcode> findBycodeType(String codeType);
    @Query(value="select r from RiskDcode r where codeType = 'unitNature' and validStatus = '1'",nativeQuery=true)
    List<RiskDcode> findUnitNature();


}
