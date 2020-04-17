package com.picc.riskctrl.riskinfo.expert.dao;

import com.picc.riskctrl.common.jpa.base.JpaBaseRepository;
import com.picc.riskctrl.riskinfo.expert.po.RiskInfoDiscuss;
import com.picc.riskctrl.riskinfo.expert.po.RiskInfoDiscussId;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @功能：
 * @author liqiankun
 * @throws Exception 
 * @日期 20200109
 */
public interface RiskInfoDiscussRepository extends JpaBaseRepository<RiskInfoDiscuss,RiskInfoDiscussId>{

	@Query("from RiskInfoDiscuss where expertNo = ?1")
	List<RiskInfoDiscuss> queryDiscussByexpertNoHQL(Integer expertNo);
	
//	@Query(value="select * from t_users where name = ?",nativeQuery=true)
//	List<Users> findUniqueByHql(String name);
//	
//	@Query("update Users set name  = ? where id  = ?")
//	@Modifying //需要执行一个更新操作
//	void updateUsersNameById(String name,Integer id);
	
	@Query(value="select nextval ('riskinfo_discuss_sequence')",nativeQuery=true)
	Integer queryDiscussSequence();
	
}
