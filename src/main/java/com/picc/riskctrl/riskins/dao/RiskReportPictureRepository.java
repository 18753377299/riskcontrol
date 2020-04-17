package com.picc.riskctrl.riskins.dao;

import com.picc.riskctrl.riskins.po.RiskReportPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.repository.Repository;

public interface RiskReportPictureRepository extends JpaRepository<RiskReportPicture,String>{

	@Query("from RiskReportPicture where riskFileNo = ?1")
	List<RiskReportPicture> findByRiskFileNo(String riskFileNo);

}
