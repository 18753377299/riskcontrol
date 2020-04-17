package com.picc.riskctrl.common.service;

import com.google.common.collect.Lists;
import com.picc.riskctrl.base.BaseService;
import com.picc.riskctrl.common.RiskActionExceptionResolver;
import com.picc.riskctrl.common.dao.RiskDcodeRepository;
import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.common.po.RiskDcodeId;
import com.picc.riskctrl.common.po.RiskDnatural;
import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.common.utils.BeanCopyUtils;
import com.picc.riskctrl.common.utils.QueryRule;
import com.picc.riskctrl.common.vo.*;
import com.picc.riskctrl.exception.NullResultSetByConditionsException;
import com.picc.riskctrl.exception.RiskDnaturalServiceException;
import com.picc.riskctrl.riskins.dao.RiskDaddressRepository;
import com.picc.riskctrl.riskins.dao.RiskDnaturalRepository;
import com.picc.riskctrl.riskins.po.RiskDaddress;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * 基本信息维护service
 *
 * @author wangwenjie
 * @date 2020-02-06
 */
@Service
@Transactional
@Slf4j
public class RiskDnaturalService extends BaseService {

    @Autowired
    private RiskDcodeRepository riskDcodeRepository;

    @Autowired
    private RiskDaddressRepository riskDaddressRepository;
    @Autowired
    private DataSourcesService dataSourcesService;

    @Autowired
    private RiskDnaturalRepository riskDnaturalRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * @Description:环境信息查询
     * @author: QLJ
     * @data: 2020/1/21
     * @param riskDnaturalRequestVo
     * @return:com.picc.riskctrl.common.vo.RiskDnatural.RiskDnaturalResponseVo
     * @throws
     */
    public RiskDnaturalResponseVo queryRiskDnatural(RiskDnaturalRequestVo riskDnaturalRequestVo) {
        UserInfo userInfo = getUserInfo();

        RiskDnaturalResponseVo riskDnaturalResponseVo = new RiskDnaturalResponseVo();

        try {
            RiskDnaturalVo riskDnaturalVo = riskDnaturalRequestVo.getRiskDnaturalVo();

            Specification<RiskDnatural> spec = new Specification<RiskDnatural>() {

                @Override
                public Predicate toPredicate(Root<RiskDnatural> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicateList = new ArrayList<>();
                    if (riskDnaturalVo != null) {
                        //业务年度
                        String naturalYear = riskDnaturalVo.getNaturalYear();
                        if (StringUtils.isNotBlank(naturalYear)) {
                            predicateList.add(criteriaBuilder.equal(root.get("naturalYear"), naturalYear.trim()));
                        }
                        //归属机构
                        String comCode = riskDnaturalVo.getComCode();
                        if (StringUtils.isNotBlank(comCode)) {
                            predicateList.add(criteriaBuilder.equal(root.get("comCode"), comCode.trim()));
                        }

                        //报告制作人
                        String operatorCode = riskDnaturalVo.getOperatorCode();
                        if (StringUtils.isNotBlank(operatorCode)) {
                            predicateList.add(criteriaBuilder.equal(root.get("operatorCode"), operatorCode.trim()));
                        }

                        //所属地区
                        String addressCode = riskDnaturalVo.getAddressCode();
                        String addressProvince = riskDnaturalVo.getAddressProvince();
                        if (StringUtils.isNotBlank(addressCode)) {
                            predicateList.add(criteriaBuilder.equal(root.get("addressCode"), addressCode.trim()));
                        } else {
                            if (StringUtils.isBlank(addressProvince) && !"00000000".equals(userInfo.getComCode())) {
                                addressProvince = userInfo.getComCode().substring(0, 6);
                            }
                            if (StringUtils.isNotBlank(addressProvince)) {
                                List<RiskDaddress> riskDaddressList = queryRiskDaddressByUpperCode(addressProvince);
                                List<String> addressCodeList = new ArrayList<String>();
                                if (riskDaddressList != null && riskDaddressList.size() > 0) {
                                    for (int i = 0; i < riskDaddressList.size(); i++) {
                                        addressCodeList.add(riskDaddressList.get(i).getAddressCode());
                                    }
                                    addressCodeList.add(addressProvince);
                                }
                                //
                                Path<Object> path = root.get("addressCode");
                                CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                                for (String address : addressCodeList) {
                                    in.value(address);
                                }
                                predicateList.add(in);
                                predicateList.add(criteriaBuilder.equal(root.get("addressCode"), addressCodeList));

                            }
                        }
                        //有效标志位
                        String validStatus = riskDnaturalVo.getValidStatus();
                        List<String> validStatusList = new ArrayList<>();
                        if (!("-1".equals(validStatus.indexOf('0') + ""))) {
                            validStatusList.add("0");
                        }
                        if (!("-1".equals(validStatus.indexOf('1') + ""))) {
                            validStatusList.add("1");
                        }
                        if (validStatusList.size() > 0) {
                            Path<Object> path = root.get("validStatus");
                            CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                            for (String status : validStatusList) {
                                in.value(status);
                            }
                            predicateList.add(in);
                        }
                    }

                    Predicate[] arr = new Predicate[predicateList.size()];
                    return criteriaBuilder.and(predicateList.toArray(arr));
                }
            };
            //分页
            int pageNo = riskDnaturalRequestVo.getPageNo() - 1;
            int pageSize = riskDnaturalRequestVo.getPageSize();
            //排序
            PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "serialNo"));

            Page<RiskDnatural> page = riskDnaturalRepository.findAll(spec, pageRequest);

            List<RiskDnatural> sourceContent = page.getContent();

            //List深度拷贝
            List<RiskDnatural> targetContent = BeanCopyUtils.copyPropertiesList(sourceContent);

            for (RiskDnatural riskDnatural : targetContent) {
                //有效标志位翻译
                if (StringUtils.isNotBlank(riskDnatural.getValidStatus())) {
                    if ("1".equals(riskDnatural.getValidStatus())) {
                        riskDnatural.setValidStatusName("有效");
                    } else if ("0".equals(riskDnatural.getValidStatus())) {
                        riskDnatural.setValidStatusName("无效");
                    }
                }
                //归属机构和所属地区中文翻译
                if (StringUtils.isNotBlank(riskDnatural.getComCode())) {
                    riskDnatural.setComCodeCName(riskDnatural.getComCode());
                }
                RiskDaddress riskDaddress = dataSourcesService.queryRiskDaddressByAddressCode(riskDnatural.getAddressCode());
                if (StringUtils.isNotBlank(riskDaddress.getAddress())) {
                    riskDnatural.setAddressName(riskDaddress.getAddress());
                }
            }

            riskDnaturalResponseVo.setDataList(targetContent);
            //设置数据总条数
            riskDnaturalResponseVo.setTotalCount(page.getTotalElements());
            //设置总页数
            riskDnaturalResponseVo.setTotalPage(page.getTotalPages());
        } catch (Exception e) {
            log.error("环境信息查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            String m = RiskActionExceptionResolver.getStackTrace(e);
            riskDnaturalResponseVo.setQCexception(m);
        }
        return riskDnaturalResponseVo;
    }


    /**
     * @Description:通过上级地址代码获取环境信息
     * @author: QuLingjie
     * @data: 2020/1/22
     * @param upperCode
     * @return:java.util.List<com.picc.riskctrl.riskins.po.RiskDaddress>
     * @throws
     */
    public List<RiskDaddress> queryRiskDaddressByUpperCode(String upperCode) {
        List<RiskDaddress> riskDaddressList = new ArrayList<RiskDaddress>();
        try {

            Specification<RiskDaddress> spec = new Specification<RiskDaddress>() {
                @Override
                public Predicate toPredicate(Root<RiskDaddress> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicateList = new ArrayList<>();
                    if (StringUtils.isNotBlank(upperCode)) {
                        predicateList.add(criteriaBuilder.equal(root.get("upperCode"), upperCode.trim()));
                    }
                    Predicate[] arr = new Predicate[predicateList.size()];
                    return criteriaBuilder.and(predicateList.toArray(arr));
                }
            };
            riskDaddressList = riskDaddressRepository.findAll(spec);
        } catch (Exception e) {
            log.error("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        }
        return riskDaddressList;
    }


    /**
     * @Description: 查询环境信息是否有效
     * @author: QuLingjie
     * @data: 2020/1/22
     * @param serialNo
     * @return:com.picc.riskctrl.common.vo.RiskDnatural.RiskDnaturalResponseVo
     * @throws
     */
    public RiskDnaturalResponseVo queryRiskDnaturalState(String serialNo) {
        RiskDnatural riskDnatural = new RiskDnatural();
        RiskDnaturalResponseVo riskDnaturalResponseVo = new RiskDnaturalResponseVo();
        try {
            if (StringUtils.isNotBlank(serialNo)) {
                riskDnatural = queryBySerialNo(Integer.parseInt(serialNo));
                if (riskDnatural != null) {
                    riskDnaturalResponseVo.setMessage(riskDnatural.getValidStatus());
                } else {
                    riskDnaturalResponseVo.setMessage("数据异常，请您稍后再试...");
                }
            } else {
                riskDnaturalResponseVo.setMessage("环境信息序号不能为空！");
            }
        } catch (Exception e) {
            log.error("查询环境信息是否有效异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询环境信息是否有效异常:" + e);
        }

        return riskDnaturalResponseVo;
    }

    /**
     * 基本信息维护查询
     *
     * @author wangwenjie
     * @param riskDnaturalRequestVo
     * @return com.picc.riskctrl.common.vo.RiskDnaturalResponseVo
     */
    public RiskDnaturalResponseVo queryRiskDcodeVo(RiskDnaturalRequestVo riskDnaturalRequestVo) {
        RiskDnaturalResponseVo riskDnaturalResponseVo = new RiskDnaturalResponseVo();
        RiskActionExceptionResolver raer = new RiskActionExceptionResolver();
        try {
            QueryRule<RiskDcode> queryRule = QueryRule.getInstance();

            String codeType = riskDnaturalRequestVo.getRiskDcodeVo().getCodeType();
            if (StringUtils.isNotBlank(codeType)) {
                queryRule.addEqual("id.codeType", codeType.trim());
            }
            String codeCode = riskDnaturalRequestVo.getRiskDcodeVo().getCodeCode();
            if (StringUtils.isNotBlank(codeCode)) {
                queryRule.addEqual("id.codeCode", codeCode.trim());
            }
            String codeCname = riskDnaturalRequestVo.getRiskDcodeVo().getCodeCname();
            if (StringUtils.isNotBlank(codeCname)) {
                queryRule.addLike("codeCname", "%" + codeCname.trim() + "%");
            }
            String[] validStatus = riskDnaturalRequestVo.getRiskDcodeVo().getValidStatus();
            List<String> validStatusList = new ArrayList<>();
            if (null != validStatus && validStatus.length > 0) {
                validStatusList.addAll(Arrays.asList(validStatus));
            }
            if (!validStatusList.isEmpty()) {
                queryRule.addIn("validStatus", validStatusList);
            }
            Page<RiskDcode> page = riskDcodeRepository.findAll(queryRule.getSpecification(),
                    PageRequest.of(riskDnaturalRequestVo.getPageNo() - 1, riskDnaturalRequestVo.getPageSize()));

            List<RiskDcode> sourceList = page.getContent();
            List<RiskDcode> targetList = Lists.newArrayList();
            BeanCopyUtils.copyPropertiesList(sourceList, targetList);

            riskDnaturalResponseVo.setDataList(targetList);
            riskDnaturalResponseVo.setTotalCount(page.getTotalElements());
            riskDnaturalResponseVo.setTotalPage(page.getTotalPages());

            for (int i = 0; i < riskDnaturalResponseVo.getDataList().size(); i++) {
                RiskDcode riskDcode = (RiskDcode) riskDnaturalResponseVo.getDataList().get(i);
                if ("riskFileSender".equals(riskDcode.getId().getCodeType())) {
                    riskDcode.setCodeEname("出具报告机构");
                } else if ("profession".equals(riskDcode.getId().getCodeType())) {
                    riskDcode.setCodeEname("查勘行业");
                } else if ("claimReason".equals(riskDcode.getId().getCodeType())) {
                    riskDcode.setCodeEname("出险原因");
                }
                riskDcode.setIntroduce(riskDcode.getId().getCodeCode());
                if ("1".equals(riskDcode.getValidStatus())) {
                    riskDcode.setValidStatusName("有效");
                } else if ("0".equals(riskDcode.getValidStatus())) {
                    riskDcode.setValidStatusName("无效");
                }
            }
        } catch (Exception e) {
            log.error("基本信息维护查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            String m = RiskActionExceptionResolver.getStackTrace(e);
            riskDnaturalResponseVo.setQCexception(m);
        }
        return riskDnaturalResponseVo;
    }

    /**
     * 查询riskdcode
     *
     * @author wangwenjie
     * @param riskDcodeVo
     * @return com.picc.riskctrl.common.po.RiskDcode
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public RiskDcode queryRiskDcode(RiskDcodeVo riskDcodeVo) {
        String codeType = riskDcodeVo.getCodeType().trim();
        String codeCode = riskDcodeVo.getCodeCode().trim();

        Assert.notNull(codeCode, "codeCode 参数无效");
        Assert.notNull(codeType, "codeType 参数无效");

        QueryRule<RiskDcode> queryRule = QueryRule.getInstance();
        queryRule.addEqual("id.codeType", codeType);
        queryRule.addEqual("id.codeCode", codeCode);
        RiskDcode riskDcode = riskDcodeRepository.findOne(queryRule.getSpecification())
                .orElseThrow(NullResultSetByConditionsException::new);
        return riskDcode;
    }

    /**
     * 基本信息维护信息修改
     *
     * @author wangwenjie
     * @param riskDnaturalRequestVo
     * @return java.lang.String
     */
    public String updateRiskBasicInfoTendDetail(RiskDnaturalRequestVo riskDnaturalRequestVo) {
        String message = "";
        try {
            RiskDcode riskDcode = this.queryRiskDcode(riskDnaturalRequestVo.getRiskDcodeVo());
            if (null != riskDcode) {
                riskDcode.setCodeCname(riskDnaturalRequestVo.getRiskDcodeVo().getCodeCname());
            }
            riskDcodeRepository.save(riskDcode);
            message = "success";
        } catch (Exception e) {
            log.error("更新异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RiskDnaturalServiceException("更新异常", e);
        }
        return message;
    }

    /**
     * 基本信息维护信息保存
     *
     * @author wangwenjie
     * @param riskDnaturalRequestVo
     * @return java.lang.String
     */
    public String saveRiskBasicInfoTendDetail(RiskDnaturalRequestVo riskDnaturalRequestVo) {
        String message = "";
        try {
            RiskDcodeVo riskDcodeVo = riskDnaturalRequestVo.getRiskDcodeVo();
            RiskDcode riskDcode = new RiskDcode();
            RiskDcodeId id = new RiskDcodeId();
            if (StringUtils.isNotBlank(riskDcodeVo.getCodeType()) &&
                    StringUtils.isNotBlank(riskDcodeVo.getCodeCode())) {
                id.setCodeCode(riskDcodeVo.getCodeCode());
                id.setCodeType(riskDcodeVo.getCodeType());
                riskDcode.setId(id);
                riskDcode.setCodeCname(riskDcodeVo.getCodeCname());
                riskDcode.setValidStatus("1");
            } else {
                return "nullError";
            }
            riskDcodeRepository.save(riskDcode);
            message = "success";
        } catch (Exception e) {
            log.error("保存异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RiskDnaturalServiceException("保存异常");
        }
        return message;
    }

    /**
     * 基本信息维护信息标志位置为无效
     *
     * @author wangwenjie
     * @param riskDcodeVo
     * @return java.lang.String
     */
    public String updateValidStatus(RiskDcodeVo riskDcodeVo) {
        String message = "";
        try {
            RiskDcode riskDcode = this.queryRiskDcode(riskDcodeVo);
            riskDcode.setValidStatus("0");
            riskDcodeRepository.save(riskDcode);
            message = "success";
        } catch (Exception e) {
            log.error("更新异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RiskDnaturalServiceException("更新异常", e);
        }
        return message;
    }

    /**
     * 获取最新环境信息序号
     *
     * @author wangwenjie
     * @return java.lang.String
     */
    public Integer getNewSerialNo() {
        try {
            return riskDnaturalRepository.queryMaxId();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(SAVE_ERROR, e);
            throw new RiskDnaturalServiceException(SAVE_ERROR, e);
        }
    }

    /**
     * 保存环境信息
     *
     * @author wangwenjie
     * @param riskDnaturalVo
     * @return java.lang.String
     */
    public String saveRiskDnatural(RiskDnaturalVo riskDnaturalVo) {
        String message = "";
        try {
            Date date = new Date();
            RiskDnatural riskDnaturalNew = new RiskDnatural();
            riskDnaturalVo.setMadeDate(date);
            riskDnaturalVo.setValidStatus("1");
            riskDnaturalVo.setInsertTimeForHis(date);
            riskDnaturalVo.setOperateTimeForHis(date);
            if (!isRiskDnaturalExist(riskDnaturalVo)) {
                message = "no";
            } else {
                BeanUtils.copyProperties(riskDnaturalVo, riskDnaturalNew);
                riskDnaturalRepository.save(riskDnaturalNew);
                message = "success";
            }
        } catch (Exception e) {
            log.error(SAVE_ERROR, e);
            e.printStackTrace();
            throw new RiskDnaturalServiceException(SAVE_ERROR, e);
        }
        return message;
    }

    /**
     * 更新环境信息
     *
     * @author wangwenjie
     * @param riskDnaturalVo
     * @return java.lang.String
     */
    public String updateRiskDnatural(RiskDnaturalVo riskDnaturalVo) {
        String message = "";
        try {
            Date date = new Date();
            RiskDnatural riskDnaturalNew = new RiskDnatural();
            riskDnaturalVo.setMadeDate(date);
            riskDnaturalVo.setValidStatus("1");
            riskDnaturalVo.setInsertTimeForHis(date);
            riskDnaturalVo.setOperateTimeForHis(date);
            BeanUtils.copyProperties(riskDnaturalVo, riskDnaturalNew);
            riskDnaturalRepository.save(riskDnaturalNew);
            message = "success";
        } catch (Exception e) {
            log.error(UPDATE_ERROR, e);
            e.printStackTrace();
            throw new RiskDnaturalServiceException(UPDATE_ERROR, e);
        }
        return message;
    }

    /**
     * 根据指定id查询
     *
     * @author wangwenjie
     * @param serialNo
     * @return com.picc.riskctrl.common.po.RiskDnatural
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public RiskDnatural queryBySerialNo(Integer serialNo) {
        return riskDnaturalRepository.findById(serialNo)
                .orElseThrow(RiskDnaturalServiceException::new);
    }

    /**
     * 判断环境信息中是否已存在该条信息
     *
     * @author wangwenjie
     * @param riskDnaturalVo
     * @return boolean
     */
    public boolean isRiskDnaturalExist(RiskDnaturalVo riskDnaturalVo) {
        boolean flag = false;
        //业务年度和所属地区代码都相等时，为已存在环境信息
        try {
            QueryRule<RiskDnatural> queryRule = QueryRule.getInstance();
            String naturalYear = riskDnaturalVo.getNaturalYear();
            if (StringUtils.isNotBlank(naturalYear)) {
                queryRule.addEqual("naturalYear", naturalYear.trim());
            }
            String addressCode = riskDnaturalVo.getAddressCode();
            if (StringUtils.isNotBlank(addressCode)) {
                queryRule.addEqual("addressCode", addressCode.trim());
            }
            queryRule.addEqual("validStatus", "1");
            List<RiskDnatural> riskDnaturalList = riskDnaturalRepository.findAll(queryRule);

            if (riskDnaturalList == null || riskDnaturalList.size() == 0) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            log.error("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        }
        return flag;
    }

    /**
     * 环境信息注销
     *
     * @author wangwenjie
     * @param serialNo
     * @return java.lang.String
     */
    public String cancelBySerialNo(String serialNo) {
        String message = "";
        try {
            RiskDnatural riskDnatural = queryBySerialNo(Integer.parseInt(serialNo));
            riskDnatural.setValidStatus("0");
            riskDnaturalRepository.save(riskDnatural);
            message = "success";
        } catch (Exception e) {
            log.error(CANCEL_ERROR, e);
            e.printStackTrace();
            throw new RiskDnaturalServiceException(CANCEL_ERROR, e);
        }
        return message;
    }

    public List<RiskDcode> getRiskDcodeList(RiskDcodeVo riskDcodeVo) {
        String codeType = riskDcodeVo.getCodeType();
        List<RiskDcode> riskDcodes = null;
        try {
            if (StringUtils.isNotBlank(codeType)) {
                try {
                    riskDcodes = riskDcodeRepository.findBycodeType(codeType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != riskDcodes && riskDcodes.size() > 0) {
                for (int i = 0; i < riskDcodes.size(); i++) {
                    RiskDcode riskDcode = riskDcodes.get(i);
                    riskDcode.setIntroduce(riskDcode.getId().getCodeCode());
                    if (riskDcode.getId().getCodeCode().trim().length() == 2 &&
                            riskDcode.getId().getCodeCode().trim().equals("99")) {
                        riskDcode.setCodeCname("其他");
                    } else if (riskDcode.getId().getCodeCode().trim().length() == 4 &&
                            riskDcode.getId().getCodeCode().trim().equals("9999")) {
                        riskDcode.setCodeCname("其他");
                    }
                }
            }
        } catch (Exception e) {
            log.error(QUERY_ERROR, e);
            e.printStackTrace();
            throw new RiskDnaturalServiceException(QUERY_ERROR, e);
        }
        return riskDcodes;
    }

    public RiskBasicResponseVo getNewBasicSerialNo(RiskDcodeVo riskDcodeVo) {
        RiskBasicResponseVo riskBasicResponseVo = new RiskBasicResponseVo();
        try {
            String codeType = riskDcodeVo.getCodeType();
            String sqlBuf = "";
            List<Map<String, Object>> maxSerialNo = null;
            if (StringUtils.isNotBlank(codeType)) {
                sqlBuf += "select  max(codecode)+1 codecode from riskdcode where codetype = '" + codeType.trim() + "' and  codecname != ''";
                try {
                    maxSerialNo = jdbcTemplate.queryForList(sqlBuf);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null != maxSerialNo && maxSerialNo.size() > 0 && null != maxSerialNo.get(0).get("codecode")) {
                    String numNo = String.valueOf(maxSerialNo.get(0).get("codecode"));
                    if ("riskFileSender".equals(codeType.trim())) {
                        if (numNo.length() < 4) {
                            int sLength = 4 - numNo.length();
                            for (int i = 0; i < sLength; i++) {
                                numNo = "0" + numNo;
                            }
                            riskBasicResponseVo.setNumNo(numNo);
                        }
                    } else {
                        if (numNo.length() < 2) {
                            int sLength = 2 - numNo.length();
                            for (int i = 0; i < sLength; i++) {
                                numNo = "0" + numNo;
                            }
                        }
                        riskBasicResponseVo.setNumNo(numNo);
                    }
                }
            }
        } catch (Exception e) {
            log.error("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        }
        return riskBasicResponseVo;
    }
}
