package com.picc.riskctrl.common.service;


import com.picc.riskctrl.common.dao.UtiKeyRepository;
import com.picc.riskctrl.common.schema.UtiKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service("UtiKeyService")
public class UtiKeyService {

	@Autowired
	UtiKeyRepository utiKeyRepository;
	
	/**
	 * @功能：查询单号流水长度和单号头
	 * @author 马军亮
	 * @param tableName 表名
	 * @param fieldName	字段代码
	 * @return UtiKey
	 * @throws
	 * @日期：2017-10-12
	 */
	public UtiKey getInfo(String tableName,String fieldName) throws Exception{
		
		List<UtiKey> list = utiKeyRepository.findAll(new Specification<UtiKey>() {

			@Override
			public Predicate toPredicate(Root<UtiKey> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate1 = null;
				Predicate predicate2 = null;
				if(StringUtils.isNotBlank(tableName)) {
					predicate1 = criteriaBuilder.like(root.get("id").get("tableName"), tableName);
				}
				if(StringUtils.isNotBlank(fieldName)) {
					predicate2 = criteriaBuilder.like(root.get("id").get("fieldName"), fieldName);
				}
				return criteriaBuilder.and(predicate1,predicate2);
			}
		});
		
		return list.get(0);
	}
}
