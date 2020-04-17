package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.po.UtiScore;
import com.picc.riskctrl.common.po.UtiScoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilScoreRepository extends JpaRepository<UtiScore, UtiScoreId>, JpaSpecificationExecutor<UtiScore>  {

}
