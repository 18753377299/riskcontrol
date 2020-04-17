package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.jpa.base.JpaBaseRepository;
import com.picc.riskctrl.common.po.UtiHighlightRisk;
import com.picc.riskctrl.common.po.UtiHighlightRiskId;
import org.springframework.stereotype.Repository;

@Repository
public interface UtiHighlightRiskDao extends  JpaBaseRepository<UtiHighlightRisk, UtiHighlightRiskId>{

}
