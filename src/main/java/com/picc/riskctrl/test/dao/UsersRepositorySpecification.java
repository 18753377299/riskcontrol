package com.picc.riskctrl.test.dao;

import com.picc.riskctrl.test.po.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * 
 *JpaSpecificationExecutor
 *
 */
public interface UsersRepositorySpecification extends JpaRepository<Users, Integer>, JpaSpecificationExecutor<Users> {

  
}
