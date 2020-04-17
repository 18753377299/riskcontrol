package com.picc.riskctrl.riskinfo.claim.service;

import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.common.utils.FTPUtil;
import com.picc.riskctrl.common.utils.PubTools;
import com.picc.riskctrl.common.utils.ResultPageUtils;
import com.picc.riskctrl.riskinfo.claim.dao.RiskDcodeDao;
import com.picc.riskctrl.riskinfo.claim.dao.RiskInfoClaimDao;
import com.picc.riskctrl.riskinfo.claim.po.RiskInfoClaim;
import com.picc.riskctrl.riskinfo.claim.vo.RiskClaimVo;
import com.picc.riskctrl.riskinfo.claim.vo.RiskInfoClaimRequestVo;
import com.picc.riskctrl.riskinfo.claim.vo.RiskInfoResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import pdfc.framework.common.ResultPage;
import pdfc.framework.web.ApiResponse;

import javax.imageio.ImageIO;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RiskInfoClaimService {

	public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");
	@Autowired
	private RiskInfoClaimDao riskInfoClaimDao;

	@Autowired
	private RiskDcodeDao riskDcodeDao;

	@Autowired
	private DataSourcesService dataSourcesService;


	/**
	 * @功能:多条件分页查询典型案例分析
	 * @author 周东旭
	 * @return RiskInfoResponseVo
	 *  @日期：20200107
	 **/
	public RiskInfoResponseVo queryRiskInfoClaim(RiskInfoClaimRequestVo riskInfoClaimRequestVo) {
		int pageNo = riskInfoClaimRequestVo.getPageNo();
		int pageSize = riskInfoClaimRequestVo.getPageSize();
		RiskInfoResponseVo riskInfoResponseVo = new RiskInfoResponseVo();
		RiskClaimVo riskClaimVo = riskInfoClaimRequestVo.getRiskClaimVo();
		try {
			if(null!=riskClaimVo) {
				/**
				 * Specification<Users>:用于封装查询条件
				 */
				Specification<RiskInfoClaim> spec = new Specification<RiskInfoClaim>() {
					
					//Predicate:封装了 单个的查询条件
					/**
					 * Root<Users> root:查询对象的属性的封装。
					 * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
					 * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
					 */
					@Override
					public Predicate toPredicate(Root<RiskInfoClaim> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						List<Predicate> list1 = new ArrayList<>();
						if(StringUtils.isNotBlank(riskClaimVo.getClaimName())) {
							list1.add(cb.like(root.get("claimName"),riskClaimVo.getClaimName()+"%"));//拼加条件：案例名称
						}
						if(StringUtils.isNotBlank(riskClaimVo.getRiskCname())) {
							list1.add(cb.like(root.get("riskCname"),riskClaimVo.getRiskCname()+"%"));//拼加条件：产品名称
						}

						String claimYear = riskClaimVo.getClaimYear();
						if(claimYear!=null && claimYear.length() > 4) {
							claimYear = String.valueOf(Integer.valueOf(claimYear.substring(0, 4)) + 1);
						}
						if(StringUtils.isNotBlank(riskClaimVo.getClaimYear())) {
							list1.add(cb.equal(root.get("claimYear"),claimYear));
						}

						//获取页面的--险种
						String riskNames = riskClaimVo.getRiskNames();
						if(riskNames != null && !("[]".equals(riskNames))){
							riskNames = riskNames.replace("[", "");
							riskNames = riskNames.replace("]", "");
							riskNames = riskNames.replace("\"", "");
							String  [] riskNameType=riskNames.split(",");;
//							riskNameType[0]="Q";
							List<Predicate> list2 = new ArrayList<>();
							for (String riskName : riskNameType) {
								list2.add(cb.like(root.get("riskName"),"%"+riskName+"%"));
							}
							Predicate[] predicate = new Predicate[riskNameType.length];
							list1.add(cb.or(list2.toArray(predicate)));
						}
						//获取页面的--赔款金额
						if(riskClaimVo.getClaimAmountLow()!= null || riskClaimVo.getClaimAmountHigh()!=null ){
							BigDecimal  claimAmountLow = riskClaimVo.getClaimAmountLow();
							BigDecimal claimAmountHigh = riskClaimVo.getClaimAmountHigh();
							if(claimAmountLow!=null && claimAmountHigh!=null){
								list1.add(cb.between(root.get("claimAmount"), claimAmountLow, claimAmountHigh));
							}else if(claimAmountLow!=null){
								list1.add(cb.greaterThanOrEqualTo(root.get("claimAmount"), claimAmountLow));
							}else{
								list1.add(cb.lessThanOrEqualTo(root.get("claimAmount"), claimAmountHigh));
							}
						}
						//获取页面的--行业
						String professions = riskClaimVo.getProfessions();
						if(professions != null && !("[]".equals(professions))){
							professions = professions.replace("[", "");
							professions = professions.replace("]", "");
							professions = professions.replace("\"", "");
							String  [] professionType =professions.split(",");
							List<Predicate> list3 = new ArrayList<>();
							for (String profession : professionType) {
								list3.add(cb.like(root.get("profession"),"%"+profession+"%"));
							}
							Predicate[] predicate1 = new Predicate[professionType.length];
							list1.add(cb.or(list3.toArray(predicate1)));
						}
//						获取页面的--案例来源
						String senders = riskClaimVo.getSenders();
						if(senders != null && !("[]".equals(senders))){
							senders = senders.replace("[", "");
							senders = senders.replace("]", "");
							senders = senders.replace("\"", "");
							String  [] senderType =senders.split(",");
							List<Predicate> list4 = new ArrayList<>();
							for (String sender : senderType) {
								list4.add(cb.like(root.get("sender"),"%"+sender+"%"));
							}
							Predicate[] predicate2 = new Predicate[senderType.length];
							list1.add(cb.or(list4.toArray(predicate2)));
						}
						//出险原因
						String claimReasons = riskClaimVo.getClaimReasons();
						if(claimReasons != null && !("[]".equals(claimReasons))){
							claimReasons = claimReasons.replace("[", "");
							claimReasons = claimReasons.replace("]", "");
							claimReasons = claimReasons.replace("\"", "");
							String  [] claimReasonType =claimReasons.split(",");
							List<Predicate> list5 = new ArrayList<>();
							for (String claimReason : claimReasonType) {
								list5.add(cb.like(root.get("claimReason"),"%"+claimReason+"%"));
							}
							Predicate[] predicate3 = new Predicate[claimReasonType.length];
							list1.add(cb.or(list5.toArray(predicate3)));
						}
						//查询有效的信息
						list1.add(cb.equal(root.get("validStatus"), "1"));
						Predicate[] arr = new Predicate[list1.size()];
						return cb.and(list1.toArray(arr));
					}
				};
	            String orderColum = riskInfoClaimRequestVo.getOrderColumn();
	            String orderType = riskInfoClaimRequestVo.getOrderType();
	            Sort.Direction direction = Sort.Direction.ASC;
	            if(!"serialNo".equals(orderColum) && "true".equals(orderType)){
						direction = Sort.Direction.DESC;
	            }

				Page<RiskInfoClaim> page = this.riskInfoClaimDao.findAll(spec,
						PageRequest.of(pageNo - 1, pageSize,Sort.by(direction,orderColum)));
				List<RiskInfoClaim> list = page.getContent();
				ResultPage<RiskInfoResponseVo> resultPage = ResultPageUtils.returnPage(page);
				riskInfoResponseVo.setDataList(list);
				riskInfoResponseVo.setTotalCount(resultPage.getTotalCount());

				//中文翻译
				List datalist = riskInfoResponseVo.getDataList();
				for(int i=0;i<datalist.size();i++){
					String professionStr = "";
					String claimReasonStr = "";
					String senderStr = "";
					RiskInfoClaim riskInfoClaimInfo = (RiskInfoClaim) datalist.get(i);
					// 险种代码翻译
					riskInfoClaimInfo.setRiskName(dataSourcesService.getRiskCName(riskInfoClaimInfo.getRiskName()));
					if(org.apache.commons.lang.StringUtils.isBlank(riskInfoClaimInfo.getClaimYear())){
						riskInfoClaimInfo.setClaimYear("未说明");
					}
					if(org.apache.commons.lang.StringUtils.isBlank(riskInfoClaimInfo.getRiskCname())){
						riskInfoClaimInfo.setRiskCname("未说明");;
					}
					if(org.apache.commons.lang.StringUtils.isBlank(riskInfoClaimInfo.getRiskName())){
						riskInfoClaimInfo.setRiskName("未说明");;
					}
					//查勘行业翻译
					if(org.apache.commons.lang.StringUtils.isNotBlank(riskInfoClaimInfo.getProfession())){
						String  [] codeCname =(riskInfoClaimInfo.getProfession()).split(",");
						List<String> profession = new ArrayList<>();
						for(int j=0;j<codeCname.length;j++){
							profession.add(dataSourcesService.queryRiskDcode("profession","",codeCname[j],"1").getCodeCname());
						}
						professionStr = org.apache.commons.lang.StringUtils.join(profession, "、");
						riskInfoClaimInfo.setProfession(professionStr);
					}
					// 出险原因翻译
					if(org.apache.commons.lang.StringUtils.isNotBlank(riskInfoClaimInfo.getClaimReason())){
						String  [] codeCname = (riskInfoClaimInfo.getClaimReason()).split(",");
						List<String> claimReason = new ArrayList<>();
						for(int j=0;j<codeCname.length;j++){
							claimReason.add(dataSourcesService.queryRiskDcode("claimReason","",codeCname[j], "1").getCodeCname());
						}
						claimReasonStr = org.apache.commons.lang.StringUtils.join(claimReason, "、");
						riskInfoClaimInfo.setClaimReason(claimReasonStr);
					}
					// 案件来源翻译
					if(org.apache.commons.lang.StringUtils.isNotBlank(riskInfoClaimInfo.getSender())){
						String  [] codeCname =(riskInfoClaimInfo.getSender()).split(",");
						List<String> sender = new ArrayList<>();
						for(int j=0;j<codeCname.length;j++){
							sender.add(dataSourcesService.queryRiskDcode("claimSender","",codeCname[j].trim(),"1").getCodeCname());
						}
						senderStr = org.apache.commons.lang.StringUtils.join(sender, "、");
						riskInfoClaimInfo.setSender(senderStr);
					}
				}
			}
		}catch(Exception e) {
			LOGGER.error("典型案例分析查询异常：" + e.getMessage() ,e);
			throw new RuntimeException("典型案例分析查询异常:"+e);
		}
		return riskInfoResponseVo;
	}
	
	public ApiResponse<List<RiskDcode>> riskDcodeQuery(String codeType) {
		Assert.hasText(codeType, "请传入代码类型");
		ApiResponse resp = new ApiResponse();
		List<RiskDcode> riskDcodes = null;
		try{
			riskDcodes = riskDcodeDao.findByIdCodeType(codeType);

			if(resp.getData()!=null){
				for(int i=0;i<riskDcodes.size();i++){
					RiskDcode riskDcode=riskDcodes.get(i);
					if(StringUtils.isNotBlank(riskDcode.getId().getCodeCode())&&StringUtils.isBlank(riskDcode.getCodeCname())){
						riskDcode.setCodeCname("未说明");
					}
				}
			}
			resp.setData(riskDcodes);
		}catch(Exception e){
			LOGGER.error("标准代码查询异常：" + e.getMessage() ,e);
			e.printStackTrace();
			resp.setStatus(1);
			resp.setStatusText("标准代码查询异常");
		}
		return resp;
	}

	/**
	 * @功能：典型案例分析查询
	 * @作者：崔凤志
	 * @日期：20200205
	 */
	public RiskInfoClaim queryRiskInfoClaimBySerialNo(int serialNo) {
		Assert.isTrue(serialNo >0, "典型案例序列号应为正整数");
		return riskInfoClaimDao.findById(serialNo).orElseThrow(() -> new IllegalArgumentException("未查询到对应典型案例"));
	}

	/**
	 * @功能:保存pdf
	 * @param file pdf文件
	 * @param projectUrl 网络地址
	 * @param saveType 保存类型
	 * @param businessType 业务类型
	 * @param serialNo 序号
	 * @return AjaxResult 返回结果
	 * @author 梁尚
	 * @日期:2018-04-25
	 * @修改记录：zrw
	 */
	public ApiResponse<String[]> savePdf(MultipartFile file,String projectUrl, String saveType, String businessType,
							  String serialNo) {
		ApiResponse<String[]> apiResponse = new ApiResponse<>();
		try {
			//存储文件路径创建
			String savePath =saveType+"/"+businessType;
			String fileName = "";
			if("save".equals(saveType)) {
				fileName = serialNo;
			}else {
				fileName = PubTools.getRandomFileName();
			}
			//pdf文件保存
			//将pdf转成jpg并保存
			apiResponse = pdfParseJpg(file,savePath+"/",fileName);
			//存储pdf及jpg存储地址
			String[] urlArray = {projectUrl+"/"+saveType+"/"+businessType+"/"+fileName+".jpg",projectUrl+"/"+saveType+"/"+businessType+"/"+fileName+".pdf"};

			apiResponse.setData(urlArray);

		} catch (Exception e) {
			LOGGER.error("保存pdf异常：" + e.getMessage() ,e);
			e.printStackTrace();
			apiResponse.setStatus(1);
			apiResponse.setStatusText(e.getMessage());
			throw new RuntimeException("保存pdf异常:"+e);
		}

		return apiResponse;
	}
	/**
	 * @功能:pdf转jpg(仅首页)
	 * @param savePath pdf地址
	 * @return AjaxResult 返回结果
	 * @author 梁尚
	 * @日期:2018-04-25
	 * @修改记录：
	 */
	public ApiResponse pdfParseJpg(MultipartFile file,String savePath,String fileName){

		ApiResponse apiResponse = new ApiResponse();
		//初始化读取流为null，方便捕捉异常
		PDDocument doc = null;
		OutputStream out =null;
		FTPUtil ftp = new FTPUtil();
		try {
			doc = PDDocument.load(file.getInputStream());
			ftp.uploadFile(file.getInputStream(), savePath+fileName+".pdf");
			//获取pdf页数据
			List pages = doc.getDocumentCatalog().getAllPages();
			for(int i=0;i<pages.size();){
				try {
					PDPage page = (PDPage) pages.get(i);
					//将pdf每页转成图片保存至内存中
					BufferedImage image = page.convertToImage();
					//保存图片到服务器
					out =ftp.uploadFile(savePath+fileName+".jpg");
					ImageIO.write(image, "jpg", out);
					apiResponse.setStatus(1);
					break;
				}catch (Exception e) {
					if(i < pages.size()-1) {
						continue;
					}else {
						throw e;
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("pdf转jpg异常：" + e.getMessage() ,e);
			apiResponse.setStatus(1);
			apiResponse.setStatusText(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("pdf转jpg异常:"+e);
		}finally{
			//关闭读取流
			if(doc != null){
				try {
					doc.close();
				} catch (IOException e) {
					LOGGER.error("关闭文件流异常：" + e.getMessage() ,e);
					apiResponse.setStatus(1);
					apiResponse.setStatusText(e.getMessage());
					e.printStackTrace();
					throw new RuntimeException("关闭文件流异常:"+e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e2) {
					LOGGER.error("关闭文件流异常：" + e2.getMessage() ,e2);
				}

			}
			try {
				ftp.close();
			} catch (IOException e) {
				LOGGER.error("关闭ftp异常：" + e.getMessage() ,e);
			}
		}
		return apiResponse;

	}
}
