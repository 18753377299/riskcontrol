package com.picc.riskctrl.common.service;

import com.picc.riskctrl.common.dao.RiskTimeRepository;
import com.picc.riskctrl.common.schema.RiskTime;
import com.picc.riskctrl.common.schema.RiskTimeId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service("RiskTimeService")
public class RiskTimeService {
	
	@Autowired
	private RiskTimeRepository riskTimeRepository;

	/**
	 * @功能：向prptime表插入数据
	 * @param prpTime --**操作轨迹表对象
	 * @param prpTimeId
	 * @return void
	 * @throws Exception 
	 * @日期：2016-07-06
	 * @修改记录： 
	 */
	public void insertRiskTime(String certiNo,String operatorCode,String updateType) {
		Long size = returnRiskTimeCount(certiNo, updateType);
		int count = Integer.parseInt(String.valueOf(size));
		RiskTime time = new RiskTime();
		RiskTimeId id = new RiskTimeId();
		
		id.setCertiNo(certiNo);
		id.setSerialNo((short) (count+1));
		id.setUpdateType(updateType);
		
		time.setId(id);
		time.setOperatorCode(operatorCode);
		time.setFlag("");
       
		riskTimeRepository.save(time);
		
	}
	
	
	/**
	 * @功能：获取对应业务单号下的prptime的serialNo
	 * @param certiNo --**业务单号
	 * @param updateType --**业务类型
	 * @return List<PrpTime> -**返回PrpTimeList实例
	 * @throws Exception 
	 * @日期：2010-01-10
	 * @修改记录： 
	 */
	public Long returnRiskTimeCount (String certiNo ,String updateType){   
		Long size = null;
	
		try {
			size = riskTimeRepository.count(new Specification<RiskTime>() {

				@Override
				public Predicate toPredicate(Root<RiskTime> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
					Predicate Predicate1 = null;
					Predicate Predicate2 = null;
					if(StringUtils.isNotBlank(certiNo)) {
						Predicate1 = criteriaBuilder.equal(root.get("id").get("certiNo"), certiNo);
					}
					if(StringUtils.isNotBlank(updateType)) {
						Predicate2 = criteriaBuilder.equal(root.get("id").get("updateType"), updateType);
					}
					return criteriaBuilder.and(Predicate1,Predicate2);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}
	
}
