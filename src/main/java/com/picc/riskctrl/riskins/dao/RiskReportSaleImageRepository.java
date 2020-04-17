package com.picc.riskctrl.riskins.dao;

import com.picc.riskctrl.common.jpa.base.JpaBaseRepository;
import com.picc.riskctrl.common.po.RiskReportSaleImage;
import com.picc.riskctrl.common.po.RiskReportSaleImageId;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author anqingsen
 */
public interface RiskReportSaleImageRepository extends JpaBaseRepository<RiskReportSaleImage, RiskReportSaleImageId>{

//    @Query(value= "select * from RiskReport_SaleImage where riskFileNo = ?1",nativeQuery = true)
//    List<RiskReportSaleImage> findByArchivesNo(String riskFileNo);

    @Query(
        value = "select archivesno,imagetype,imagename,remark,imageurl,thumurl from RiskReport_SaleImage where archivesNo=?1",
        nativeQuery = true)
    List<Object[]> findAllByArchivesno(String archivesNo);
}
