package com.picc.riskctrl.riskinfo.expert.service;

import com.picc.riskctrl.common.RiskActionExceptionResolver;
import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.common.utils.BeanUtils;
import com.picc.riskctrl.common.utils.ResultPageUtils;
import com.picc.riskctrl.riskinfo.expert.dao.RiskInfoDiscussRepository;
import com.picc.riskctrl.riskinfo.expert.dao.RiskInfoExpertRepository;
import com.picc.riskctrl.riskinfo.expert.po.RiskInfoDiscuss;
import com.picc.riskctrl.riskinfo.expert.po.RiskInfoDiscussId;
import com.picc.riskctrl.riskinfo.expert.po.RiskInfoExpert;
import com.picc.riskctrl.riskinfo.expert.vo.RiskInfoExpertDetailVo;
import com.picc.riskctrl.riskinfo.expert.vo.RiskInfoExpertRequestVo;
import com.picc.riskctrl.riskinfo.expert.vo.RiskInfoResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pdfc.framework.common.ResultPage;
import pdfc.framework.web.ApiResponse;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RiskInfoExpertService {

    public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

    @Autowired
    private RiskInfoDiscussRepository riskInfoDiscussRepository;

    @Autowired
    private RiskInfoExpertRepository riskInfoExpertRepository;

    @Autowired
    private DataSourcesService dataSourcesService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param expertNo    专家编号
     * @param userDiscuss 评论
     * @param score       评分
     * @return RiskInfoDiscuss
     * @throws Exception
     * @功能：保存专家评论信息
     * @author 马军亮
     * @修改记录: modifyby liqiankun 20200108
     * @日期 2017-9-26
     */
    public RiskInfoDiscuss saveRiskInfoDiscuss(String userDiscuss, Integer expertNo, BigDecimal score, String userCode, String userName) throws Exception {
        RiskInfoDiscuss riskInfoDiscuss = new RiskInfoDiscuss();
        RiskInfoDiscussId id = new RiskInfoDiscussId();
        try {
            riskInfoDiscuss.setUserCode(userCode);
            riskInfoDiscuss.setUserName(userName);
            //保存当前插入时间
            riskInfoDiscuss.setInsertTimeForHis(new Date());
            riskInfoDiscuss.setOperateTimeForHis(new Date());
            riskInfoDiscuss.setExpertNo(expertNo);

//			id.setExpertNo(expertNo);
//			riskInfoDiscuss.setId(id);

            riskInfoDiscuss.setDiscuss(userDiscuss);
            riskInfoDiscuss.setScore(score);
//			databaseDao.save(RiskInfoDiscuss.class, riskInfoDiscuss);
            riskInfoDiscussRepository.save(riskInfoDiscuss);
            Integer discussSequence = riskInfoDiscussRepository.queryDiscussSequence();
    		riskInfoDiscuss.setSerialNo(discussSequence-1);
            //通过专家编号获取专家评论信息
            List<RiskInfoDiscuss> riskInfoDiscusslist = riskInfoDiscussRepository.queryDiscussByexpertNoHQL(expertNo);

            BigDecimal sumscore = BigDecimal.ZERO;
            //计算总评分
            for (RiskInfoDiscuss riskInfoDiscusslistTemp : riskInfoDiscusslist) {
                sumscore = sumscore.add(riskInfoDiscusslistTemp.getScore());
            }
            if (riskInfoDiscusslist.size() > 0) {
                //计算平均得分
                BigDecimal avgScore = sumscore.divide(new BigDecimal(riskInfoDiscusslist.size()), 1, BigDecimal.ROUND_HALF_UP);
                RiskInfoExpert riskinfoExpert = riskInfoExpertRepository.queryExpertByexpertNoHQL(expertNo);

                riskinfoExpert.setScore(avgScore);
                //更新专家信息表
                riskInfoExpertRepository.save(riskinfoExpert);
            }
        } catch (Exception e) {
            LOGGER.error("保存专家评论信息异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("保存专家评论信息异常:" + e);
        }

        return riskInfoDiscuss;
    }

    /**
     * @param riskInfoExpertRequestVo 风控专家请求Vo类
     * @param operateType             操作类型
     * @return RiskInfoResponseVo RiskInfo公共返回Vo类
     * @功能:风控专家查询
     * @author 安青森
     * @日期:2017-10-13
     * @修改记录：李博儒 2017-10-30
     * @修改记录：张日炜 2020-01-09
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RiskInfoResponseVo queryRiskExpert(RiskInfoExpertRequestVo riskInfoExpertRequestVo, String operateType) {
        ApiResponse<RiskInfoResponseVo> apiResponse = new ApiResponse<>();
        RiskInfoResponseVo riskInfoResponseVo = new RiskInfoResponseVo();
        ResultPage resultPage;
        try {
            List<RiskDcode> conditionlist = new ArrayList<>();
            Specification<RiskInfoExpert> spec = new Specification<RiskInfoExpert>() {

                @Override
                public Predicate toPredicate(Root<RiskInfoExpert> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicateList = new ArrayList<>();
                    if (riskInfoExpertRequestVo.getRiskExpertVo() != null) {

                        //获取页面的-专家姓名
                        String expertName = riskInfoExpertRequestVo.getRiskExpertVo().getExpertName().trim();
                        if (StringUtils.isNotBlank(expertName)) {
                            predicateList.add(criteriaBuilder.like(root.get("expertName"), expertName + "%"));
                        }
                        //行业
                        String professions = riskInfoExpertRequestVo.getRiskExpertVo().getProfessions();
                        if (!("[]".equals(professions))) {
                            professions = professions.replace("[", "");
                            professions = professions.replace("]", "");
                            professions = professions.replace("\"", "");
                            String[] professionType = professions.split(",");
                            List<Predicate> list = new ArrayList<>();

                            for (String profession : professionType) {

                                Predicate predicate = criteriaBuilder.like(root.get("profession"), "%" + profession.trim() + "%");
                                list.add(predicate);
                                RiskDcode riskDcode = dataSourcesService.queryRiskDcode("profession", "", profession, "1");
                                conditionlist.add(riskDcode);
                            }
                            Predicate[] arr = new Predicate[list.size()];
                            predicateList.add(criteriaBuilder.or(list.toArray(arr)));
                        }
                        //最高学历
                        String educations = riskInfoExpertRequestVo.getRiskExpertVo().getEducations();
                        if (!("[]".equals(educations))) {
                            educations = educations.replace("[", "");
                            educations = educations.replace("]", "");
                            educations = educations.replace("\"", "");
                            String[] educationType = educations.split(",");
                            List<Predicate> list = new ArrayList<>();
                            for (String education : educationType) {
                                Predicate predicate = criteriaBuilder.like(root.get("education"), "%" + education.trim() + "%");
                                list.add(predicate);
                                RiskDcode riskDcode = dataSourcesService.queryRiskDcode("education", "", education, "1");
                                conditionlist.add(riskDcode);
                            }
                            Predicate[] arr = new Predicate[list.size()];
                            predicateList.add(criteriaBuilder.or(list.toArray(arr)));
                        }
                        //所选机构
                        String ascNatures = riskInfoExpertRequestVo.getRiskExpertVo().getAscNatures();
                        if (!("[]".equals(ascNatures))) {
                            ascNatures = ascNatures.replace("[", "");
                            ascNatures = ascNatures.replace("]", "");
                            ascNatures = ascNatures.replace("\"", "");
                            String[] ascNatureType = ascNatures.split(",");

                            List<Predicate> list = new ArrayList<>();
                            for (String ascNature : ascNatureType) {
                                Predicate predicate = criteriaBuilder.like(root.get("ascNature"), "%" + ascNature.trim() + "%");
                                list.add(predicate);
                                RiskDcode riskDcode = dataSourcesService.queryRiskDcode("ascType", "", ascNature, "1");
                                conditionlist.add(riskDcode);
                            }
                            Predicate[] arr = new Predicate[list.size()];
                            predicateList.add(criteriaBuilder.or(list.toArray(arr)));
                        }
                        //推荐公司
                        String senders = riskInfoExpertRequestVo.getRiskExpertVo().getSenders();
                        if (!("[]".equals(senders))) {
                            senders = senders.replace("[", "");
                            senders = senders.replace("]", "");
                            senders = senders.replace("\"", "");
                            String[] senderType = senders.split(",");
                            List<Predicate> list = new ArrayList<>();
                            for (String sender : senderType) {
                                Predicate predicate = criteriaBuilder.like(root.get("sender"), "%" + sender.trim() + "%");
                                list.add(predicate);

                                RiskDcode riskDcode = dataSourcesService.queryRiskDcode("expertSender", "", sender, "1");
                                conditionlist.add(riskDcode);
                            }
                            Predicate[] arr = new Predicate[list.size()];
                            predicateList.add(criteriaBuilder.or(list.toArray(arr)));
                        }
                    }
                    Predicate[] arr = new Predicate[predicateList.size()];
                    return criteriaBuilder.and(predicateList.toArray(arr));
                }


            };

            int pageNo = riskInfoExpertRequestVo.getPageNo();
            int pageSize = riskInfoExpertRequestVo.getPageSize();
            //获取是默认、查看险种、学历--排序标志位
            String orderColum = riskInfoExpertRequestVo.getOrderColumn().trim();
            String orderType = riskInfoExpertRequestVo.getOrderType().trim();
            Sort.Direction direction = Sort.Direction.ASC;
            if ("true".equals(orderType)) {
                direction = Sort.Direction.DESC;
            }
            Page<RiskInfoExpert> page = this.riskInfoExpertRepository.findAll(spec, PageRequest.of(pageNo - 1, pageSize, Sort.by(direction, orderColum)));
            resultPage = ResultPageUtils.returnPage(page);
            riskInfoResponseVo.setConditionList(conditionlist);
            riskInfoResponseVo.setDataList(resultPage.getData());
            riskInfoResponseVo.setTotalPage(page.getTotalPages());
            riskInfoResponseVo.setTotalCount(resultPage.getTotalCount());
            //翻译
            List datalist = riskInfoResponseVo.getDataList();
            if(datalist!=null) {
                for (Object object : datalist) {
                    RiskInfoExpert riskinfoExpert = (RiskInfoExpert) object;
                    riskinfoExpert.setRiskName(dataSourcesService.getRiskCName(riskinfoExpert.getRiskName()));

                    if (StringUtils.isNotBlank(riskinfoExpert.getProfession())) {
                        String[] codeCname = (riskinfoExpert.getProfession()).split(",");
                        StringBuilder codeCnameType = new StringBuilder();
                        for (String s : codeCname) {
                            RiskDcode riskDcode = dataSourcesService.queryRiskDcode("profession", "", s, "1");
                           if(riskDcode!=null){
                               if(StringUtils.isNotBlank(riskDcode.getCodeCname())){
                                   codeCnameType.append(riskDcode.getCodeCname()).append("，");
                                   codeCnameType = new StringBuilder(codeCnameType.substring(0, codeCnameType.length() - 1));
                                   riskinfoExpert.setProfession(codeCnameType.toString());
                               }else{
                                   riskinfoExpert.setProfession("");
                               }
                           }
                        }

                    }
                    if (StringUtils.isNotBlank(riskinfoExpert.getEducation())) {
                        String[] codeCname = (riskinfoExpert.getEducation()).split(",");
                        for (String s : codeCname) {
                            riskinfoExpert.setEducation(
                                    dataSourcesService.queryRiskDcode("education", "", s, "1").getCodeCname());
                        }
                    }

                }
            }
        } catch (Exception e) {
            LOGGER.error("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            String m = RiskActionExceptionResolver.getStackTrace(e);
            riskInfoResponseVo.setQCexception(m);
        }
        return riskInfoResponseVo;
    }
	/**
	 * @author anqingsen
	 * @param expertNo 专家编号
	 * @param pageNo 评论当前页
	 * @param pageSize 评论每页行数
	 * @return riskinfoVo
	 * @日期：2020-01-09
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public RiskInfoExpertDetailVo queryRiskInfoExpert(Integer expertNo, Integer pageNo, Integer pageSize) {

		RiskInfoExpert riskinfoExpert = new RiskInfoExpert();
		RiskInfoExpert riskinfoExpertVo = new RiskInfoExpert();
		RiskInfoExpertDetailVo riskinfoVo = new RiskInfoExpertDetailVo();

		try {
			// 专家信息查询
			if (expertNo != null) {
				riskinfoExpertVo = riskInfoExpertRepository.findById(expertNo).isPresent()?riskInfoExpertRepository.findById(expertNo).get():null;
			}
			if(null!=riskinfoExpertVo) {
				//进行对象的复制，防止将翻译字段更新到数据库中
			 BeanUtils.copyProperties(riskinfoExpert, riskinfoExpertVo);
			//学历翻译
			RiskDcode riskDcode = null;
			if(StringUtils.isNotBlank(riskinfoExpert.getEducation())) {
				riskDcode = dataSourcesService.queryRiskDcode("education", "", riskinfoExpert.getEducation(), "1");
				if(riskDcode !=null){
					riskinfoExpert.setEducation(riskDcode.getCodeCname());
				}
			}
			riskinfoExpert.setAscNameCode(riskinfoExpert.getAscName());
			if(StringUtils.isNotBlank(riskinfoExpert.getAscName())) {
				//所属机构代码翻译中文
				riskDcode = dataSourcesService.queryRiskDcode("expertIntroduce", "", riskinfoExpert.getAscName(), "1");
				if(riskDcode !=null){
					riskinfoExpert.setAscName(riskDcode.getCodeCname());
				}
			}
			if(StringUtils.isNotBlank(riskinfoExpert.getAscNature())) {
				//所属机构代码翻译中文
				riskDcode = dataSourcesService.queryRiskDcode("ascType", "", riskinfoExpert.getAscNature(), "1");
				if(riskDcode !=null){
					riskinfoExpert.setAscNature(riskDcode.getCodeCname());
				}
			}
			if(StringUtils.isNotBlank(riskinfoExpert.getSender())) {
				//推荐机构代码翻译中文
				riskDcode = dataSourcesService.queryRiskDcode("expertSender", "",riskinfoExpert.getSender(), "1");
				if(riskDcode !=null){
					riskinfoExpert.setSender(riskDcode.getCodeCname());
				}
			}
			
			//险种名代码翻译中文
			riskinfoExpert.setRiskName(dataSourcesService.getRiskCName(riskinfoExpert.getRiskName()));
			//查勘行业代码翻译中文
			if(StringUtils.isNotBlank(riskinfoExpert.getProfession())){
				String [] professions = riskinfoExpert.getProfession().split(",");
				String professionTemp = "";
				for (int i = 0;i<professions.length; i++){
					riskDcode= dataSourcesService.queryRiskDcode("profession", "", professions[i], "1");
					if(riskDcode !=null){
						String profession = riskDcode.getCodeCname();
						if(StringUtils.isNotBlank(profession)){
                            professionTemp += profession.trim() +"，";
                        }
					}
				}
				if(StringUtils.isNotBlank(professionTemp)){
					professionTemp = professionTemp.substring(0, professionTemp.length()-1);
				}
				riskinfoExpert.setProfession(professionTemp);
			}
			}
			
			// 评论信息分页查询
			Specification<RiskInfoDiscuss> spec = new Specification<RiskInfoDiscuss>() {

				/**
				 * Root<Users> root:查询对象的属性的封装。
				 * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
				 * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
				 */
				@Override
				public Predicate toPredicate(Root<RiskInfoDiscuss> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate pre = cb.equal(root.get("expertNo"), expertNo);
					return pre;
				}
			};
			Page page =  this.riskInfoDiscussRepository.findAll(spec, PageRequest.of(pageNo - 1, pageSize,Sort.by(Sort.Direction.DESC, "insertTimeForHis")));
			List<RiskInfoDiscuss> riskInfoDiscussList = page.getContent();
			for (RiskInfoDiscuss riskInfoDiscuss : riskInfoDiscussList) {
				if (StringUtils.isBlank(riskInfoDiscuss.getDiscuss())) {
					riskInfoDiscuss.setDiscuss(" ");
				}
			}
			riskinfoVo.setRiskInfoDiscussList(page.getContent());
			riskinfoVo.setRiskinfoExpert(riskinfoExpert);
			riskinfoVo.setTotalCount(page.getTotalElements());
			riskinfoVo.setTotalPage(page.getTotalPages());
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("获取专家浏览信息失败" + e.getMessage() ,e);
			throw new RuntimeException("获取专家浏览信息" + e);
		}

		return riskinfoVo;
	}

	/**
	 * @功能：删除专家信息及被评论信息
	 * @param expertNo	专家编号
	 * @return String
	 * @throws Exception
	 */
	public ApiResponse<String> delRiskInfoExpert(Integer expertNo) {
		RiskInfoExpert riskinfoExpert = new RiskInfoExpert();
		ApiResponse<String> resp = new ApiResponse<>();
		String message = "failed";
		try {
			if(expertNo !=null){
				//根据专家编号获取专家信息
				Optional<RiskInfoExpert> optional = riskInfoExpertRepository.findById(expertNo);
				riskinfoExpert = optional.get();
			}
			
			List<RiskInfoDiscuss> riskInfoDiscussList = riskInfoDiscussRepository.findAll(new Specification<RiskInfoDiscuss>() {

				@Override
				public Predicate toPredicate(Root<RiskInfoDiscuss> root, CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {
					
					return criteriaBuilder.equal(root.get("expertNo"), expertNo);
				}
			});
			
			for(int i = 0; i<riskInfoDiscussList.size(); i++)
			{
				riskInfoDiscussRepository.delete(riskInfoDiscussList.get(i));
			}
			
			riskInfoExpertRepository.delete(riskinfoExpert);
			
			message = "success";
			
		} catch (Exception e) {
			
			resp.setStatus(1);
			LOGGER.error("删除专家信息及被评论信息异常：" + e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException("删除专家信息及被评论信息异常:"+e);
		}
		
		resp.setData(message);
		return resp;
	}

//	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//	public  List<RiskInfoDiscuss> queryDiscussByexpertNoHQL(Integer expertNo){
//		List<RiskInfoDiscuss> riskInfoDiscusslist =  riskInfoDiscussRepository.queryDiscussByexpertNoHQL(expertNo);
//		return riskInfoDiscusslist;
//	}
//	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//	public  RiskInfoExpert queryExpertByexpertNoHQL(Integer expertNo){
//		RiskInfoExpert riskinfoExpert =  riskInfoExpertRepository.queryExpertByexpertNoHQL(expertNo);
//		return riskinfoExpert;
//	}

}
