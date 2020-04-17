package com.picc.riskctrl.common.service;

import com.picc.riskctrl.common.dao.RiskGroupRepository;
import com.picc.riskctrl.common.schema.RiskGroup;
import com.picc.riskctrl.common.schema.RiskGroupId;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


@Service(value = "RiskGroupService")
@Transactional
public class RiskGroupService {
	public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

	@Autowired
	RiskGroupRepository riskGroupRepository;
	
	public String getGroupNo(String iTableName, String iRiskCode,String iComCode, String iYear,String iRiskModel) throws Exception{
		String strGroupNo = "";
		String strSubGroupNo = "";
		try {
			iTableName = iTableName.toLowerCase();
			iRiskCode = iRiskCode.toUpperCase();
			if("prpdquote".equals(iTableName)|| "prpdquotee".equals(iTableName)){
				strSubGroupNo = iRiskCode + iYear.trim() + iComCode.substring(0, 8);
			}
			//照片档案号生成规则为RC+003(销售员版报告模板)+8位查勘机构+4位年份+5位顺序号
			else if("riskreportsalemain".equals(iTableName)){
				strSubGroupNo = iRiskModel + iComCode.substring(0, 8) + iYear.trim();
			}
			//巡检报告编号生成规则为RX+报告模板（普通：001 ,简化：002）+8位巡检机构+4位年份+5位顺序号
			else if("riskcheckmain".equals(iTableName)){
				strSubGroupNo = iRiskModel + iComCode.substring(0, 8) + iYear.trim();
			}
			// 风险档案编号生成规则：3位产品代码+报告模板（专职：A01 兼职：A02）+4位归属机构+4位年份+6位顺序编号
			else if("riskreport_main".equals(iTableName)){
				strSubGroupNo = iRiskCode + iRiskModel + iComCode.substring(0, 4) + iYear.trim();
			}else{
				strSubGroupNo = iRiskCode + iRiskModel + iComCode.substring(0, 4) + iYear.trim();
			}
			
			RiskGroupId id = new RiskGroupId();
			id.setSubGroupNo(strSubGroupNo);
			
			RiskGroup riskGroup = null;

			Optional<RiskGroup> optional = riskGroupRepository.findById(id);
			if(optional.isPresent()) {
				riskGroup = optional.get();
			}
			
			if (riskGroup != null) {
				strGroupNo = riskGroup.getId().getGroupNo();//id的合编组
			} else {
				strGroupNo = strSubGroupNo;//id的分编组
			}
			
		} catch (Exception e) {
			LOGGER.info("获取GroupNo异常：" + e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException("获取GroupNo异常:"+e);
		}
		return strGroupNo;
	}
	
	public String getGroupNo(String subGroupNo) throws Exception{
		String strGroupNo = "";
		RiskGroupId id = new RiskGroupId();
		id.setSubGroupNo(subGroupNo);
		try {
			Optional<RiskGroup> optional = riskGroupRepository.findById(id);
			if(optional.isPresent()) {
				RiskGroup prpGroup = optional.get();
				strGroupNo = prpGroup.getId().getGroupNo();
			}else {
				strGroupNo = subGroupNo;
			}
			
		} catch (Exception e) {
			LOGGER.info("查询异常：" + e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException("查询异常:"+e);
		}
		return strGroupNo;	
	}
	
	
	public List<RiskGroup> query(String groupNo) throws Exception{
		
		List<RiskGroup> riskGroupList = riskGroupRepository.findAll(new Specification<RiskGroup>() {
			/**
			 * Predicate:封装了 单个的查询条件
			 * Root<RiskGroup> root:查询对象的属性的封装。
			 * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
			 * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
			 */
			@Override
			public Predicate toPredicate(Root<RiskGroup> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = null;
				if(StringUtils.isNotEmpty(groupNo)) {
					predicate = criteriaBuilder.equal(root.get("id").get("subGroupNo"), groupNo);
				}
				return predicate;
			}
		});
		return riskGroupList;
	}

}
