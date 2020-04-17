package com.picc.riskctrl.riskinfo.report.service;

import com.alibaba.fastjson.JSON;
import com.picc.riskctrl.riskinfo.report.dao.RiskInfoModelRepository;
import com.picc.riskctrl.riskinfo.report.po.RiskInfoModel;
import com.picc.riskctrl.riskinfo.superRiskReport.dao.RiskInfoFileDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RiskInfoModelService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

	@Autowired
	private RiskInfoModelRepository riskInfoModelRepository;
	@Autowired
	private RiskInfoFileDao riskInfoFileDao;
	/**
	 * @Description: 根据表信息生成风控模板菜单json数据
	 * @author: 杨号
	 * @time:2017年9月20日下午3:09:16
	 * @修改记录: modifyby liqiankun 20200108
	 * @return
	 */
	public String getData() {
		String jsonData = "";
		try {
			// 从数据库中获取原始数据
			List<RiskInfoModel> modelList =this.listRiskInfoModel();
			// 层级菜单的集合
			List<RiskInfoModel> menuList = new ArrayList<RiskInfoModel>(0);
			// 先找到所有的一级菜单
			for (int i = 0; i < modelList.size(); i++) {
				// 一级菜单没有parentId
				if (StringUtils.isBlank(modelList.get(i).getParentId()) || "".equals(modelList.get(i).getParentId())) {
					menuList.add(modelList.get(i));
				}
			}
			// 为一级菜单设置子菜单，getChil递归调用
			for (RiskInfoModel model : menuList) {
				model.setChildModel(this.getChild(model.getId().toString(),
						modelList));
			}
			// 将最末尾级包含URL的菜单取出放在上级的urls里，然后将上级菜单设置为最末级菜单
//			menuList = this.changeLast(menuList);
			jsonData = JSON.toJSONString(menuList);
		} catch (Exception e) {
			LOGGER.error( e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return jsonData;
	}
	/**
	 * 
	 * @Description: 获取菜单的子菜单项
	 * @author: 杨号
	 * @time:2017年9月20日下午2:27:44
	 * @修改记录: modifyby liqiankun 20200108
	 * @param id
	 * @param modelList
	 * @return
	 */
	private List<RiskInfoModel> getChild(String id,
			List<RiskInfoModel> modelList) {
		// 子菜单
		List<RiskInfoModel> childList = new ArrayList<RiskInfoModel>(0);
		for (RiskInfoModel model : modelList) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (StringUtils.isNotBlank(model.getParentId())) {
				if (model.getParentId().equals(id)) {
					childList.add(model);
				}
			}
		}
		// 把子菜单的子菜单再循环一遍
		for (RiskInfoModel model : childList) {
			// 非叶节点继续递归
			if ("1".equals(model.getNodeType())) {
				// 递归调用，获取子菜单
				model.setChildModel(getChild(model.getId().toString(),
						modelList));
			}
		}
		// 递归退出条件
		if (childList.size() == 0) {
			return null;
		}
		return childList;
	}
	/**
	 * 
	 * @Description: 获取RiskInfoModel表数据
	 * @author: 杨号
	 * @time:2017年9月20日上午9:46:36
	 * @修改记录: modifyby liqiankun 20200108
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RiskInfoModel> listRiskInfoModel() throws Exception {
		return riskInfoModelRepository.findAll();
	}
	/**
	 * @Description:根据id获取RiskInfoModel表中信息
	 * @author 安青森
	 * @日期：2017年12月11日
	 * @return
	 * @修改记录 modifyby liqiankun 20200108
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RiskInfoModel queryRiskInfoReport(Integer id) throws Exception{
//		QueryRule queryRule = QueryRule.getInstance();
		RiskInfoModel listRiskInfoModel = new RiskInfoModel();
		if(id != null){
//			listRiskInfoModel = databaseDao.findUnique(RiskInfoModel.class, queryRule);
			listRiskInfoModel = riskInfoModelRepository.getOne(id);
		}else{
			throw new RuntimeException("该PDF文件不存在！");
		}
		return listRiskInfoModel;
	}




}
