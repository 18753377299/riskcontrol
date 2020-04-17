package com.picc.riskctrl.riskcheck.dao;

import com.picc.riskctrl.riskcheck.po.RiskCheckImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface RiskCheckImageDao extends JpaRepository<RiskCheckImage,String>,Repository<RiskCheckImage, String>, JpaSpecificationExecutor<RiskCheckImage>{

}
