package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.schema.PrpDcompanyFk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName: PrpDcompanyFkRepository
 * @Author: 张日炜
 * @Date: 2020-01-14 14:21
 **/
public interface PrpDcompanyFkRepository extends JpaRepository<PrpDcompanyFk, String>, JpaSpecificationExecutor<PrpDcompanyFk> {
	 @Query("select p from PrpDcompanyFk p where comCode =?1")
	 PrpDcompanyFk findByComCode(String comCode);
}
