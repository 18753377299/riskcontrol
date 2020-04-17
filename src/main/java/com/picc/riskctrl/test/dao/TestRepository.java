package com.picc.riskctrl.test.dao;

import com.picc.riskctrl.test.po.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test,Integer>{
	
}
