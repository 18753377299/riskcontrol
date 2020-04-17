package com.picc.riskctrl.riskins.service;


import com.picc.riskctrl.common.dao.RiskDcodeRepository;
import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.common.utils.FTPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@Transactional
public class RiskInsImageService {

	@Autowired
	private RiskDcodeRepository riskDcodeRepository;
	
	/**
	 * @author anqingsen
	 * @功能：根据riskModel获取所有riskDcode
	 * @param riskModel 风控报告类型
	 * @return List<RiskDcode>
	 * @日期：2020-01-14
	 */
	public List<RiskDcode> queryRiskDcodeByRiskModel(String riskModel) {
		List<RiskDcode> riskDcodeList = null;
		if("006".equals(riskModel) || "007".equals(riskModel)){
			// 火灾风险排查照片信息查询
			String riskModelFlag = "006".equals(riskModel)?"001":"002";
			riskDcodeList = this.queryRiskDcodeByCodeType("riskReportFireTree"+riskModelFlag);
		}else {
			riskDcodeList = this.queryRiskDcodeByCodeType("riskReportTree"+riskModel);	
		}
		return riskDcodeList;
	}

	/**
	 * @author anqingsen
	 * @功能：根据codeType获取所有的riskDcode
	 * @param string
	 * @return List<RiskDcode>
	 * @日期：2020-01-14
	 */
	private List<RiskDcode> queryRiskDcodeByCodeType(String codeType) {
		List<RiskDcode> riskDcodeList = null;
		try {
			riskDcodeList = riskDcodeRepository.findBycodeType(codeType);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询异常：" + e);
		}
		return riskDcodeList;
	}
	/**
	 * @功能：删除影像资料
	 * @author liqiankun
	 * @param riskFileNoList 风控报告编号集合
	 * @return boolean
	 * @日期：201200120
	 */
	public String deleteImageList(List<String> riskFileNoList,UserInfo userInfo) {
		String message = "success";
		if(riskFileNoList!=null && riskFileNoList.size()!=0){
			try {
				for(String riskFileNo : riskFileNoList){
					//调用影像删除接口
//					imageService.deleteImageByRiskFileNo(riskFileNo, userInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				message = "error";
				throw new RuntimeException(e);
			}
		}else{
			message = "none";
		}
		return message;
	}
	/**
	 * @功能：删除本地的风控报告文件
	 * @author liqiankun
	 * @param riskFileNoList
	 * @param path
	 * @日期：20200120
	 */
	public void deleteRiskFile(List<String> riskFileNoList) {
		FTPUtil ftp =new FTPUtil();
		try {
			for(String riskFileNo : riskFileNoList){
				ftp.removeDirectoryALLFile(riskFileNo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("删除失败:"+e);
		}finally {
           if(ftp!=null) {
	            try {
					ftp.close();
				} catch (IOException e) {
					System.out.println("关闭ftp异常：" + e.getMessage());
				}
           }
		}
		
	}
	
	/**
	 * @功能：保存图片
	 * @author 李博儒
	 * @param file 图片文件流
	 * @param pathString 要存储该图片的路径
	 * @param riskFileNo 风控报告编号
	 * @param childPath 子目录名称
	 * @return String 图片名(带后缀)
	 * @日期：modeifyby liqiankun 20200121
	 */
	public String saveImage(CommonsMultipartFile file, String pathString, String riskFileNo, String childPath, String maxFileName, FTPUtil ftp) {
		
		String fileNameNewAndSuffix = null;
		
		try {
			StringBuffer stringBuf = new StringBuffer();
			if(childPath!=null) {
				stringBuf.append(childPath).append('.').append(maxFileName).append('.').append("jpg");
			}else {
				stringBuf.append(maxFileName).append('.').append("jpg");
			}
			try {
				ftp.removeFile(pathString+"/"+childPath+"."+maxFileName);
				ftp.uploadFile(file.getInputStream(), pathString+"/"+stringBuf.toString());
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
			stringBuf.delete(0, stringBuf.length());
			
			
			
			if(childPath!=null) {
				fileNameNewAndSuffix = stringBuf.append(childPath).append('.').append(maxFileName).append('.').append("jpg").toString();
			}else{
				fileNameNewAndSuffix = stringBuf.append(maxFileName).append('.').append("jpg").toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("保存图片失败:"+e);
		}
		
		return fileNameNewAndSuffix;
		
	}
	

}
