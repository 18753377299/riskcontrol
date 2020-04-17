package com.picc.riskctrl.riskins.api;

import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.common.utils.FTPUtil;
import com.picc.riskctrl.riskins.service.RiskInsImageService;
import com.picc.riskctrl.riskins.service.RiskInsSaleService;
import com.picc.riskctrl.riskins.vo.ImagePreviewConfigVo;
import com.picc.riskctrl.riskins.vo.ImagePreviewExtraVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import pdfc.framework.web.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/riskins/image")
public class RiskInsImageApi {

    public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

    @Autowired
    RiskInsImageService riskInsImageService;

    @Autowired
    RiskInsSaleService riskInsSaleService;

    /**
     * @功能：图片上传
     * @param request HttpServletRequest
     * @param files 图片文件
     * @param riskFileNo 风控编号
     * @param childPath 子路径
     * @returns
     * @throws Exception
     */
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ApiOperation(value="汛期报告录入---影像上传",notes="addby 崔凤志  20200220")
    public ApiResponse uploadImage(HttpServletRequest request,
                                   @RequestParam("file") CommonsMultipartFile[] files,
                                   @RequestParam(value = "riskFileNo", required = false) String riskFileNo,
                                   @RequestParam(value = "childPath", required = false) String childPath,
                                   @RequestParam(value = "fileName", required = false) String fileName,
                                   @RequestParam(value = "uploadImageFlag", required = false) String uploadImageFlag,
                                   @RequestParam(value = "serialNo", required = false) String serialNo) {

        ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());
        String saveRootPath=bundle.getString("saveRootPath");
        String  saveTypePath=bundle.getString("saveTypePath");

        ApiResponse resp = new ApiResponse();
             FTPUtil ftp = new FTPUtil();
        try {
            UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

            //UserInfo userInfo = new UserInfo();
            //userInfo.setIsPC(true);


            StringBuffer stringBuf = new StringBuffer();

            if (StringUtils.isNotBlank(fileName)) {
                if (!userInfo.getIsPC() && !("1".equals(uploadImageFlag))) {
                    childPath = fileName + "_PG";
                    fileName = riskInsSaleService.queryRiskReportSaleCorrectionCount(riskFileNo, fileName) + "";
                } else {
                    fileName = fileName.split("\\.")[fileName.split("\\.").length - 2];
                }
            } else {
                fileName = "0";
            }
            stringBuf.append(saveRootPath + saveTypePath).append('/').append(riskFileNo).append('/').append(childPath);

            String pathString = stringBuf.toString();
            int maxFileName = Integer.parseInt(fileName);

            List<String> imageUrls = new ArrayList<String>(0);
            List<ImagePreviewConfigVo> imgPreConfigs = new ArrayList<ImagePreviewConfigVo>(0);


            // 保存图片
            for (CommonsMultipartFile file : files) {

                maxFileName++;
                String imageName = "";
                if (!userInfo.getIsPC() && !("1".equals(uploadImageFlag))) {
                    resp = riskInsSaleService.setBehindName(riskFileNo, childPath.split("_")[0], serialNo, maxFileName + "");
                    imageName = riskInsImageService.saveImage(file, riskFileNo+"/"+childPath, riskFileNo, null, maxFileName + "",ftp);
                } else {
                    imageName = riskInsImageService.saveImage(file, riskFileNo+"/"+childPath, riskFileNo, childPath,
                            maxFileName + "",ftp);
                }
                String url = saveTypePath +"/"+ riskFileNo + "/" + childPath + "/" + imageName + "?uuid=" + UUID.randomUUID().toString();
                String remark = "" ;
                if (!userInfo.getIsPC() && ("1".equals(uploadImageFlag))) {
                    remark = file.getName();

                    if(remark.indexOf("\\") != -1) {
                        String[] arr=remark.split("\\\\");
                        remark = arr[arr.length-1];
                    }

                    int index = remark.indexOf(".");
                    if (index != -1) {
                        remark = remark.substring(0,index);
                    }
                }

                imageUrls.add(url);
                ImagePreviewConfigVo imageConfig = new ImagePreviewConfigVo();
                imageConfig.setCaption(imageName);
                imageConfig.setSize(file.getSize());
                imageConfig.setUrl("/riskins/image/imageRemove");
                imageConfig.setLocalPath(url);
                imageConfig.setRemark(remark);
                ImagePreviewExtraVo extra = new ImagePreviewExtraVo();

                extra.setImagePath(stringBuf.toString());
                extra.setImageName(imageName);
                extra.setChildPath(childPath);

                imageConfig.setDownloadUrl("/riskins/image/imageDownLoad;" + url + ";" + imageName);

                imageConfig.setExtra(extra);

                imgPreConfigs.add(imageConfig);
            }
            // 移动端影像保存删除及上传
            if (!userInfo.getIsPC() && !("1".equals(uploadImageFlag))) {
                if (resp.getStatus() == 1) {
                    Map<String, CommonsMultipartFile[]> fileMap = new HashMap<String, CommonsMultipartFile[]>();
                    fileMap.put(childPath, files);
                    try {
                        //保存该条 image信息
                        resp = riskInsSaleService.saveRiskReportSaleImage(riskFileNo, childPath, Integer.toString(Integer.parseInt(fileName) + 1));
                    } catch (Exception e) {
                        LOGGER.info( e.getMessage() ,e);
                        resp.setStatus(0);
                        resp.setStatusText(e.getMessage());
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }

            Map<String, Object> map = new HashMap<String, Object>();

            map.put("urls", imageUrls);
            map.put("configs", imgPreConfigs);

            resp.setData(map);
        } catch (Exception e) {
            LOGGER.info( e.getMessage() ,e);
            System.out.println("e.getMessage()----" + e.getMessage());
            System.out.println("e.getLocalizedMessage()----" + e.getLocalizedMessage());
            System.out.println("e.getCause()----" + e.getCause());
            resp.setStatusText(e.getLocalizedMessage());
            resp.setStatus(500L);
            throw new RuntimeException(e);
        }finally {
            if(ftp!=null) {
                try {
                    ftp.close();
                } catch (IOException e) {
                    LOGGER.info("关闭ftp异常：" + e.getMessage() ,e);
                }
            }
        }
        return resp;
    }
}
