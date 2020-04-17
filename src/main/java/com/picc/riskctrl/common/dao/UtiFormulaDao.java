package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.jpa.base.JpaBaseRepository;
import com.picc.riskctrl.common.po.UtiFormula;
import com.picc.riskctrl.common.po.UtiFormulaId;
import org.springframework.stereotype.Repository;

@Repository
public interface UtiFormulaDao extends JpaBaseRepository<UtiFormula, UtiFormulaId>{

}
