package com.picc.riskctrl.common.service;

import com.picc.riskctrl.common.RiskControlConst;
import com.picc.riskctrl.common.dao.RiskDcodeRepository;
import com.picc.riskctrl.common.dao.RiskDtemplateRepository;
import com.picc.riskctrl.common.dao.UtiBackRuleConfigRepository;
import com.picc.riskctrl.common.jpa.condition.Restrictions;
import com.picc.riskctrl.common.jpa.vo.Criteria;
import com.picc.riskctrl.common.po.*;
import com.picc.riskctrl.riskins.dao.RiskDaddressRepository;
import com.picc.riskctrl.riskins.po.RiskDaddress;
import com.picc.riskctrl.riskins.service.RiskInsConfigService;
import com.picc.riskctrl.riskins.vo.RiskInsResponseVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @ClassName: dataSourcesService
 * @Author: 张日炜
 * @Date: 2020-01-07 17:15
 **/
@Slf4j
@Service
public class DataSourcesService {

    @Autowired
    private RiskDcodeRepository riskDcodeRepository;

    @Autowired
    private UtiBackRuleConfigRepository utiBackRuleConfigRepository;

    @Autowired
    private RiskInsConfigService riskInsConfigService;
    @Autowired
    private RiskDtemplateRepository riskDtemplateRepository;
    @Autowired
    private RiskDaddressRepository riskDaddressRepository;

    /**
     * @param codeType            代码类型
     * @param comCode             机构代码
     * @param codeCodeOrCodeCname 代码或中文翻译
     * @param queryType           "0"是表示模糊查询，为"1"时表示精确查询
     * @return RiskDcode
     * @throws
     * @功能：代码与中文含义的相互翻译
     * @author 马军亮---张日炜
     * @日期 2017-9-26
     */
    @Cacheable(cacheNames = {"RiskDcode"})
    public RiskDcode queryRiskDcode(String codeType, String comCode, String codeCodeOrCodeCname, String queryType) {
        RiskDcode riskDcodeNew = null;
        try {
            if (StringUtils.isNotBlank(codeCodeOrCodeCname)) {
                codeCodeOrCodeCname = codeCodeOrCodeCname.replace("\"", "");
                String finalCodeCodeOrCodeCname = codeCodeOrCodeCname;
                Specification<RiskDcode> spec = (Specification<RiskDcode>) (root, criteriaQuery, criteriaBuilder) -> {
                    List<Predicate> predicateList = new ArrayList<>();
                    predicateList.add(criteriaBuilder.equal(root.get("id").get("codeType"), codeType));
                    if (StringUtils.isNotBlank(comCode)) {
                        predicateList.add(criteriaBuilder.equal(root.get("comCode"), comCode));
                    }
                    if ("1".equals(queryType)) {
                        Predicate codeCode = criteriaBuilder.equal(root.get("id").get("codeCode"), finalCodeCodeOrCodeCname);
                        Predicate codeCname = criteriaBuilder.equal(root.get("codeCname"), finalCodeCodeOrCodeCname);
                        predicateList.add(criteriaBuilder.or(codeCode, codeCname));

                    } else {
                        Predicate codeCode = criteriaBuilder.like(root.get("codeCode"), finalCodeCodeOrCodeCname);
                        Predicate codeCname = criteriaBuilder.like(root.get("codeCname"), finalCodeCodeOrCodeCname);
                        predicateList.add(criteriaBuilder.or(codeCode, codeCname));
                    }

                    Predicate[] arr = new Predicate[predicateList.size()];
                    return criteriaBuilder.and(predicateList.toArray(arr));
                };
                Optional<RiskDcode> one = riskDcodeRepository.findOne(spec);
                if (one.isPresent()) {
                    riskDcodeNew = one.get();
                    if (StringUtils.isBlank(riskDcodeNew.getIntroduce())) {
                        riskDcodeNew.setIntroduce("暂未说明");
                    }
                }
            }
        } catch (Exception e) {
            log.error("查询出现异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询出现异常:" + e);
        }
        return riskDcodeNew;
    }

    /**
     * @param riskName 险种代码
     * @return String
     * @throws @日期 2017-9-26
     * @功能：险种代码翻译为中文含义
     * @author 马军亮-张日炜
     */
    public String getRiskCName(String riskName) {
        StringBuilder risknameTemp = new StringBuilder();
        if (StringUtils.isNotBlank(riskName)) {
            String[] risknames = riskName.split(",");

            for (String riskname : risknames) {
                String addRiskName = "";
                switch (riskname.trim()) {
                    case "Q":
                        addRiskName = "企财险";
                        break;
                    case "G":
                        addRiskName = "工程险";
                        break;
                    case "JS":
                        addRiskName = "机损险";
                        break;
                    case "C":
                        addRiskName = "船舶险";
                        break;
                    case "Z":
                        addRiskName = "责任险";
                        break;
                    case "J":
                        addRiskName = "家财险";
                        break;
                    case "H":
                        addRiskName = "货运险";
                        break;
                    case "9":
                        addRiskName = "未说明";
                        break;
                    default:
                        addRiskName = "未说明";
                        break;
                }
                risknameTemp.append(addRiskName.trim()).append("，");
            }
            risknameTemp = new StringBuilder(risknameTemp.substring(0, risknameTemp.length() - 1));
        }
        return risknameTemp.toString();
    }


    /**
     * @param codeType
     * @throws
     * @Description:查询出RiskDcode表中的数据
     * @author: liqiankun
     * @data: 2018/4/3
     * @return:java.util.List<com.picc.riskctrl.common.po.RiskDcode>
     * @修改记录:modifyby QuLingjie 2020/1/10
     */
    public List<RiskDcode> riskDcodeQuery(String codeType) {
        List<RiskDcode> riskDcodes = null;
        try {
            riskDcodes = riskDcodeRepository.findBycodeType(codeType);
            for (int i = 0; i < riskDcodes.size(); i++) {
                RiskDcode riskDcode = riskDcodes.get(i);
                if (StringUtils.isNotBlank(riskDcode.getId().getCodeCode()) &&
                        StringUtils.isBlank(riskDcode.getCodeCname())) {
                    riskDcode.setCodeCname("未说明");
                }
            }
        } catch (Exception e) {
            log.error("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);

        }
        return riskDcodes;
    }

    /**
     * @param codeType 代码类型
     * @return Map<String                               ,                               RiskDcode>
     * @throws
     * @功能：代码与中文含义的相互翻译
     * @author 马军亮
     * @日期 2017-10-9
     * @modify 张日炜 2020-01-15
     */
    public Map<String, RiskDcode> queryRiskDcode(String codeType) {
        Map<String, RiskDcode> map = new HashMap<String, RiskDcode>();
        try {
            if (StringUtils.isNotBlank(codeType)) {
                Specification<RiskDcode> spec = (Specification<RiskDcode>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("id").get("codeType"), codeType);
                List<RiskDcode> riskDcodeList = riskDcodeRepository.findAll(spec);
                for (RiskDcode riskDcodeVo : riskDcodeList) {
                    map.put(riskDcodeVo.getId().getCodeCode(), riskDcodeVo);
                }
            }
        } catch (Exception e) {
            log.info("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        }
        return map;
    }

    /**
     * @return Map
     * @throws Exception
     * @功能：获取所有投保险种
     * @author 李博儒
     * @日期：2018-01-17
     * @modify 张日炜 2020-01-15
     */
    public Map<String, Code> queryAllRiskCode(String comCode) throws Exception {

        Map<String, Code> map = new HashMap<String, Code>();

        List<Code> classCodeList = riskInsConfigService.queryClassCode();

        if (classCodeList != null && classCodeList.size() > 0) {

            for (Code code : classCodeList) {
                List<Code> riskCodeList = riskInsConfigService.queryRiskCode(code.getCode(), comCode);
                if (riskCodeList != null && riskCodeList.size() > 0) {
                    for (Code codeTem : riskCodeList) {
                        map.put(codeTem.getCode(), codeTem);
                    }
                }
            }
        }

        return map;
    }

    /**
     * 后台规则对外开关接口
     *
     * @author wangwenjie
     * @param ruleCode 规则代码
     * @param comCode 机构代码
     * @return boolean
     */
    public boolean getRuleSwitch(String ruleCode, String comCode) {
        String ruleValue = "";
        try {
            ruleValue = this.getBackRuleValueCommon(ruleCode, comCode);
        } catch (Exception e) {
            log.info("与数据库交互异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("与数据库交互异常:" + e);
        }
        if ("1".equals(ruleCode.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 后台规则配置的值公用方法
     *
     * @author wangwenjie
     * @param ruleCode
     * @param comCode
     * @return java.lang.String
     */
    public String getBackRuleValueCommon(String ruleCode, String comCode) throws Exception {

        /** 性能调优 start 2011-03-16 my mod by 循环调用DB，现改成缓存中获取数据 **/
        String ruleValue = "";
        UtiBackRuleConfig utiBackRuleConfig = this.getUtiBackRuleConfig(ruleCode, comCode);
        if (utiBackRuleConfig != null) {
            ruleValue = utiBackRuleConfig.getRuleValue();
        }
        /** 性能调优 end 2011-03-16 my mod by 循环调用DB，现改成缓存中获取数据 **/
        return ruleValue;
    }

    /**
     * 后台规则配置的值公用方法
     *
     * @author wangwenjie
     * @param ruleCode
     * @param comCode
     * @return com.picc.riskctrl.common.po.UtiBackRuleConfig
     */
    public UtiBackRuleConfig getUtiBackRuleConfig(String ruleCode, String comCode) throws Exception {
        UtiBackRuleConfig utiBackRuleConfig = null;
        /*String key = cacheManager.generateCacheKey(ruleCode, comCode);
        Object cacheObj = cacheManager.getCache(key);*/
        Object cacheObj = null;
        if (cacheObj != null) {
            utiBackRuleConfig = (UtiBackRuleConfig) cacheObj;
        } else {
            String utiRuleSql = "SELECT a FROM UtiBackRuleConfig a WHERE a.id.ruleCode=? AND a.validStatus = ? ";
            List<UtiBackRuleConfig> utiBackRuleConfigList =
                    utiBackRuleConfigRepository.findAllByRuleCodeAndValidStatus(ruleCode, "1");

            String upperComCode = "";
            int pageCountNumber = 0;
            //获取上级机构的Service
            if (!utiBackRuleConfigList.isEmpty()) {
                while (utiBackRuleConfig == null) {
                    if (pageCountNumber++ > 5) {
                        break;
                    }
                    if ("00000000".equals(comCode)) {
                        utiBackRuleConfig = this.ruleCheck_Switch(utiBackRuleConfigList, comCode);
                        break;
                    } else {
                        utiBackRuleConfig = this.ruleCheck_Switch(utiBackRuleConfigList, comCode);
                        if (utiBackRuleConfig != null) {
                            break;
                        } else {
                            upperComCode = getUpperComCode(comCode);
                            comCode = upperComCode;
                        }
                    }
                }
            }
//            cacheManager.putCache(key, utiBackRuleConfig);
        }
        return utiBackRuleConfig;
    }

    /**
     * 后台规则配置的值公用方法
     *
     * @author wangwenjie
     * @param utiBackRuleConfigList
     * @param comCode
     * @return com.picc.riskctrl.common.po.UtiBackRuleConfig
     */
    public UtiBackRuleConfig ruleCheck_Switch(List<UtiBackRuleConfig> utiBackRuleConfigList ,String comCode){

        UtiBackRuleConfig ruleConfig = null;
        try {
            for(UtiBackRuleConfig utiBackRuleConfig : utiBackRuleConfigList){
                UtiBackRuleConfigId ruleId = utiBackRuleConfig.getId();
                if(comCode.equals(ruleId.getComCode())){
                    ruleConfig = utiBackRuleConfig ;
                }
            }
        } catch (Exception e) {
            log.info("后台规则配置方法异常：" + e.getMessage() ,e);
            e.printStackTrace();
            throw new RuntimeException("后台规则配置方法异常:"+e);
        }
        return ruleConfig ;
    }

    /**
     * @功能：根据机构代码获取上级机构代码
     * @param comCode 机构代码
     * @return upperComCode 上级机构代码
     * @throws Exception
     */
    public String getUpperComCode(String comCode){
        String upperPath = "";
        String upperComCode = "";
        //获取当前机构的缓存
        /*String keyComCode = cacheManager.generateCacheKey("getUpperComCode",comCode);
        Object cacheObj = cacheManager.getCache(keyComCode);
        if (cacheObj != null) {
            upperPath = (String) cacheObj;
            upperComCode = this.getUpperComCodeByUpperPath(upperPath, comCode);
            return upperComCode;
        }
*/
        //todo
        /*PrpDcompany prpDcompany = dictService.getPrpDcompany(comCode);
        if(prpDcompany == null || prpDcompany.getUpperPath() == null){
            upperPath = "00000000";
            //throw new BusinessException("找不到机构代码为" + comCode + "的机构", false);
        }else{
            upperPath = prpDcompany.getUpperPath();
        }*/
        upperComCode = this.getUpperComCodeByUpperPath(upperPath, comCode);
//        cacheManager.putCache(keyComCode, upperPath);

        return upperComCode;
    }

    private String getUpperComCodeByUpperPath(String upperPath,String comCode){
        /** 性能调优 start 2011-4-11 my mod by 修改异常问题 **/
        String upperComCode = "";
//		if(upperPath.indexOf(",") == -1){
        if(upperPath.indexOf(',') == -1){
            return upperPath;
        }
        String[] comCodeList = upperPath.split(",");
        int lgn = comCodeList.length;
        for(int i = 0; i < lgn;i++){
            if(comCode.equals(comCodeList[i])){
                if(i > 0){
                    upperComCode = comCodeList[i-1];
                }else{
                    upperComCode = comCode;
                }
                break;
            }
        }
        /** 性能调优 start 2011-4-11 my mod by 修改异常问题 **/
        return upperComCode;
    }

    /**
     * @功能：投保险种代码翻译
     * @author 李博儒--liqiankun
     * @param riskCode  风险档案编号
     * @return String 描述删除信息
     * @日期：20200120
     */
    public String queryRiskCodeName(String riskCode, String comCode) {

        String riskName = riskCode;

//			QueryRule queryRule = QueryRule.getInstance();
//
//			queryRule.addEqual("id.riskCode", riskCode);
//			queryRule.addEqual("validstatus", "1");

        String classCode = null;

//			List<RiskDtemplate> result = databaseDao.findAll(RiskDtemplate.class, queryRule);

        Criteria<RiskDtemplate> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("id.riskCode", riskCode));
        criteria.add(Restrictions.eq("validstatus", "1"));
        List<RiskDtemplate> result = riskDtemplateRepository.findAll(criteria);

        if (result.size() != 0) {
            classCode = result.get(0).getId().getClassCode();
        }

        if (RiskControlConst.JBX.equals(riskCode.trim())) {

            riskName = RiskControlConst.JBX_CNAME;

        } else if (RiskControlConst.ZHX.equals(riskCode.trim())) {

            riskName = RiskControlConst.ZHX_CNAME;

        } else if (RiskControlConst.YQX.equals(riskCode.trim())) {

            riskName = RiskControlConst.YQX_CNAME;

        } else if ("PUB".equals(riskCode.trim())) {
            if (classCode != null) {
                // 调用数据字典
//					List<PrpDrisk> prpDrisks = dictService.getrisk(classCode, "", comCode, "1", 0, 0).getData();
//					for (PrpDrisk prpdRisk : prpDrisks) {
//						riskName = prpdRisk.getRiskCName();
//					}
            }
        } else {
            // 调用数据字典
//				List<PrpDrisk> prpDrisks = dictService.getrisk("", riskCode, 0, 0).getData();
//				for (PrpDrisk prpdRisk : prpDrisks) {
//					riskName = prpdRisk.getRiskCName();
//				}
        }

        return riskName;
    }

    /**
     * @功能：获取所有照片目录
     * @author 李博儒
     * @param codeType 代码类别
     * @return Map<String       ,       RiskDcode>
     * @日期
     */
    @ApiOperation(value = "获取所有照片目录", notes = "codeType 代码类别  modifyby liqiankun 20200121")
    public Map<String, RiskDcode> queryAllImageCategory(String codeType) {
        Map<String, RiskDcode> map = new HashMap<String, RiskDcode>();
        try {
            if (StringUtils.isNotBlank(codeType)) {
                Criteria<RiskDcode> criteria = new Criteria<>();
                criteria.add(Restrictions.eq("id.codeType", codeType));
                List<RiskDcode> riskDcodeList = riskDcodeRepository.findAll(criteria);

                for (RiskDcode riskDcodeVo : riskDcodeList) {
                    map.put(riskDcodeVo.getCodeEname(), riskDcodeVo);
                }
            }
        } catch (Exception e) {
            log.info("与数据库交互异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("与数据库交互异常:" + e);
        }
        return map;
    }


    /**
     * @Description:归属机构查询
     * @author: Qulingjie
     * @data: 2020/1/21
     * @param keyWord
     * @param pageNo
     * @param pageSize
     * @param comCode
     * @return:com.picc.riskctrl.riskins.vo.RiskInsResponseVo
     * @throws
     */
    public RiskInsResponseVo queryComCode(String keyWord, int pageNo, int pageSize, String comCode) {

        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();

        return riskInsResponseVo;
    }

    /**
     * @Description:通过地址代码获取环境信息
     * @author: QuLingjie
     * @data: 2020/1/22
     * @param addressCode
     * @return:com.picc.riskctrl.riskins.po.RiskDaddress
     * @throws
     */

    public RiskDaddress queryRiskDaddressByAddressCode(String addressCode) {

        RiskDaddress riskDaddress = null;


        try {
            Specification<RiskDaddress> spec = new Specification<RiskDaddress>() {
                @Override
                public Predicate toPredicate(Root<RiskDaddress> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicateList = new ArrayList<>();
                    if (StringUtils.isNotBlank(addressCode)) {
                        predicateList.add(criteriaBuilder.equal(root.get("addressCode"), addressCode.trim()));
                    }

                    predicateList.add(criteriaBuilder.equal(root.get("validStatus"), "1"));

                    Predicate[] arr = new Predicate[predicateList.size()];
                    return criteriaBuilder.and(predicateList.toArray(arr));
                }
            };
            //查询方法需要完善
            //riskDaddress = databaseDao.findUnique(RiskDaddress.class, queryRule);

            Optional<RiskDaddress> riskDaddresslist = riskDaddressRepository.findOne(spec);
            //List结果转换成对象
            riskDaddress = riskDaddresslist.orElse(null);

        } catch (Exception e) {
            log.info("与数据库交互异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("与数据库交互异常:" + e);
        }

        return riskDaddress;
    }

    public String queryComCodeCName(String comCode) {
        //todo 缓存及数据字典
        return "";
    }

	              
	@Cacheable(cacheNames = {"RiskDcode"})
    public RiskDcode queryRiskDcodeForImage(String codeType, String comCode, String codeCodeOrCodeCname, String queryType) {
        RiskDcode riskDcode = new RiskDcode();
        try {
            if (StringUtils.isNotBlank(codeCodeOrCodeCname)) {
                codeCodeOrCodeCname = codeCodeOrCodeCname.replace("\"", "");
                String finalCodeCodeOrCodeCname = codeCodeOrCodeCname;
                Specification<RiskDcode> spec = (Specification<RiskDcode>) (root, criteriaQuery, criteriaBuilder) -> {
                    List<Predicate> predicateList = new ArrayList<>();
                    predicateList.add(criteriaBuilder.equal(root.get("id").get("codeType"), codeType));
                    if (StringUtils.isNotBlank(comCode)) {
                        predicateList.add(criteriaBuilder.equal(root.get("comCode"), comCode));
                    }
                    if ("1".equals(queryType)) {
                        Predicate codeCode = criteriaBuilder.equal(root.get("id").get("codeEname"), finalCodeCodeOrCodeCname);
                        Predicate codeCname = criteriaBuilder.equal(root.get("codeCname"), finalCodeCodeOrCodeCname);
                        predicateList.add(criteriaBuilder.or(codeCode, codeCname));

                    } else {
                        Predicate codeCode = criteriaBuilder.like(root.get("codeEname"), finalCodeCodeOrCodeCname);
                        Predicate codeCname = criteriaBuilder.like(root.get("codeCname"), finalCodeCodeOrCodeCname);
                        predicateList.add(criteriaBuilder.or(codeCode, codeCname));
                    }

                    Predicate[] arr = new Predicate[predicateList.size()];
                    return criteriaBuilder.and(predicateList.toArray(arr));
                };
                Optional<RiskDcode> one = riskDcodeRepository.findOne(spec);
                if (one.isPresent()) {
                    riskDcode = one.get();
                }
            }
        } catch (Exception e) {
            log.info("查询出现异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询出现异常:" + e);
        }
        return riskDcode;
    }
    /**
	 * @功能：代码与中文含义的相互翻译
	 * @author 马军亮
	 * @param codeType
	 *            代码类型
	 * @return List<RiskDcode>
	 * @throws @日期
	 *             2017-12-29
	 */	
	public List<RiskDcode> queryRiskDcodeList(String codeType) {
		List<RiskDcode> riskDcodeList = new ArrayList<RiskDcode>();
		try {
			if (StringUtils.isNotBlank(codeType)) {
				try {
					Criteria<RiskDcode> criteria = new Criteria<>();
	                criteria.add(Restrictions.eq("id.codeType", codeType));
	                riskDcodeList = riskDcodeRepository.findAll(criteria);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			log.info("查询异常：" + e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException("查询异常:"+e);
		}
		return riskDcodeList;
	}
}
