package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.jpa.base.JpaBaseRepository;
import com.picc.riskctrl.common.schema.UtiWeight;
import org.springframework.stereotype.Repository;

@Repository
public interface UtiWeightDao extends  JpaBaseRepository<UtiWeight, Integer>{

}
