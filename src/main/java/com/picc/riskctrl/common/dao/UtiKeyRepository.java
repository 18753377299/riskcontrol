package com.picc.riskctrl.common.dao;

import com.picc.riskctrl.common.jpa.base.JpaBaseRepository;
import com.picc.riskctrl.common.schema.UtiKey;
import com.picc.riskctrl.common.schema.UtiKeyId;
import org.springframework.stereotype.Repository;

@Repository
public interface UtiKeyRepository extends JpaBaseRepository<UtiKey, UtiKeyId>{
	
}
