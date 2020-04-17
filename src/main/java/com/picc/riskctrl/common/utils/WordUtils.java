package com.picc.riskctrl.common.utils;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
//import com.aspose.words.Document;
//import com.aspose.words.License;
//import com.aspose.words.SaveFormat;
import com.deepoove.poi.XWPFTemplate;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.*;

/**
 * @Description: 生成word相关工具类, word2pdf
 * @Author: wangwenjie
 * @Create: 2019-03-15
 */
public class WordUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordUtils.class);

    //服务器路径前缀
    private static final String PATH_PREFIX;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());
        StringBuffer path = new StringBuffer();
        path.append(bundle.getString("saveRootPath")).append(bundle.getString("saveTypePath")).append("/downloadFile/");
        PATH_PREFIX = path.toString();
    }

    /**
     * @Description 通过java反射生成word模板中插值数据
     * @Author wangwenjie
     * @param obj
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Date 2019/3/15
     */
    public static Map<String, Object> getWordResultByReflect(Object obj) {

        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("serialVersionUID")
                    || field.getType().getName().equals("java.util.List")) {
                continue;
            }
            String fieldName = field.getName();
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try {
                Method method = obj.getClass().getMethod("get" + fieldName, new Class[]{});
                Object invokeResult = method.invoke(obj);
                map.put(field.getName(), invokeResult);
            } catch (Exception e) {
                LOGGER.info("构建word插值数据异常" + e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException("构建word插值数据异常");
            }
        }
        return map;
    }

    /**
     * @Description 验证License 若不验证则转化出的pdf文档会有水印产生
     * @Author wangwenjie
     * @param
     * @return boolean
     * @Date 2019/3/22
     */
    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = WordUtils.class.getClassLoader().getResourceAsStream("license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
//            License aposeLic = new License();
//            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description 转换服务器word文件 -> pdf | aspose方式，部署linux报错NoClassDefFoundError todo
     * @Author wangwenjie
     * @param inPath 服务器下载word路径
     * @param ftpOutPath ftp上传路径 download/xxx.pdf
     * @return void
     * @Date 2019/3/22
     */
    public static void word2pdf(String inPath, String ftpOutPath) throws IOException {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        FTPUtil ftp = null;
        try {
            long old = System.currentTimeMillis();
            ftp = new FTPUtil();
            OutputStream out = ftp.uploadFile(ftpOutPath);
//            Document doc = new Document(inPath); // Address是将要被转化的word文档
//            doc.save(out, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
            out.flush();
            out.close();
            ftp.close();
        } catch (Exception e) {
            LOGGER.info("word转化pdf失败" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("word转化pdf失败");
        } finally {
            if (ftp != null) {
                ftp.close();
            }
        }
    }

    /**
     * @Description XWPFTemplate根据word模板添加数据并通过ftp输出
     * @Author wangwenjie
     * @param uploadPath 上传路径
     * @param templatePath /template 文件夹下模板路径
     * @param params template插值参数
     * @return void
     * @Date 2019/4/1
     */
    public static void uploadFileByFtp(String uploadPath, String templatePath, Map<String, Object> params) throws IOException {
        XWPFTemplate template = XWPFTemplate.compile(templatePath).render(params);
        //这里需要重新new一个ftp对象，不然会在切换路径卡住，报错
        FTPUtil ftp = new FTPUtil();
        OutputStream out = null;
        try {
            out = ftp.uploadFile(uploadPath);
            if (template != null) {
                template.write(out);
            }
            out.flush();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.close();
            }
            if (ftp != null) {
                ftp.close();
            }
        }
    }


    /**
     * @Description openOffice转换word -> pdf
     * @Author wangwenjie
     * @param sourceFile 源文件 传入xxx.doc即可 文件名+后缀名
     * @param destFile 生成pdf文件 传入 xxx.pdf
     * @return int
     * @Date 2019/3/27
     */
    /*public static void openOfficeConvertPDF(String sourceFile,String destFile){
        try {
            sourceFile = PATH_PREFIX + sourceFile;
            destFile = PATH_PREFIX +destFile;

            File inputFile = new File(sourceFile);
            if(!inputFile.exists()){
                System.out.println("源文件不存在");
                throw new RuntimeException("源文件不存在");
            }
            File outputFile = new File(destFile);

            SocketOpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8100);

            //尝试连接
            while(!connection.isConnected()){
                connection.connect();
            }

            //covert
            OpenOfficeDocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
            //关闭连接
            connection.disconnect();
            //关闭openoffice进程
            System.out.println("=== 转换完成 ===");
        } catch(Exception e){
            LOGGER.info(e.getMessage()+"转换pdf失败", e);
            e.printStackTrace();
            throw new RuntimeException("转换pdf失败");
        }
    }
*/

    /**
     * @Description 238服务器转pdf方法
     * @Author wangwenjie
     * @param sourceFile
     * @param destFile
     * @return void
     * @Date 2019/4/29
     */
    public static void openOfficeConvertPDF2(String sourceFile, String destFile) throws IOException {
        FTPUtil inFtp = new FTPUtil();
        FTPUtil outFtp = new FTPUtil();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            sourceFile = "downloadFile/" + sourceFile;
            destFile = "downloadFile/" + destFile;

            inputStream = inFtp.getInputStream(sourceFile);
            outputStream = outFtp.uploadFile(destFile);

            DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
            DocumentFormat inputFormat = formatReg.getFormatByFileExtension("doc");
            DocumentFormat outputFormat = formatReg.getFormatByFileExtension("pdf");

            //操作系统为windows 采用本地openoffice
            String operatroSystemName = System.getProperty("os.name");
            if (operatroSystemName.toLowerCase().startsWith("windows")) {
                startOpenOfficeByCmd();
            }

            //127.0.0.1
            SocketOpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);

            //尝试连接
            while (!connection.isConnected()) {
                connection.connect();
            }

            //covert
            OpenOfficeDocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputStream, inputFormat, outputStream, outputFormat);
            //关闭连接
            connection.disconnect();
            //关闭openoffice进程
            System.out.println("=== 转换完成 ===");
        } catch (Exception e) {
            LOGGER.info(e.getMessage() + "转换pdf失败", e);
            e.printStackTrace();
            throw new RuntimeException("转换pdf失败");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outFtp != null) {
                outFtp.close();
            }
            if (inFtp != null) {
                inFtp.close();
            }
        }
    }

    /**
     * @Description 合并多个pdf文件为一个
     * @Author wangwenjie
     * @param inPath
     * @param outPath
     * @return void
     * @Date 2019/3/28
     */
    /*public static void mergePdf(String[] inPath,String outPath){
        try{
            PDFMergerUtility mergePdf = new PDFMergerUtility();
            for(int i=0,len=inPath.length;i<len;i++){
                mergePdf.addSource(PATH_PREFIX + inPath[i]);
            }
            mergePdf.setDestinationFileName(PATH_PREFIX + outPath);
            mergePdf.mergeDocuments();
            System.out.println("合并完成");
        }catch (Exception e){
            LOGGER.info(e.getMessage()+"pdf合并失败", e);
            e.printStackTrace();
            throw new RuntimeException("pdf合并失败");
        }
    }*/

    /**
     * @Description 通过输入输出流读取输出文件
     * @Author wangwenjie
     * @param inPath
     * @param outPath
     * @return void
     * @Date 2019/4/30
     */
    public static void mergePdf2(String[] inPath, String outPath) throws IOException {
        FTPUtil ftpUtil = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            PDFMergerUtility mergePdf = new PDFMergerUtility();
            for (int i = 0, len = inPath.length; i < len; i++) {
                ftpUtil = new FTPUtil();
                in = ftpUtil.getInputStream("downloadFile/" + inPath[i]);
                mergePdf.addSource(in);
                ftpUtil.close();
            }
            ftpUtil = new FTPUtil();
            out = ftpUtil.uploadFile("downloadFile/" + outPath);
            mergePdf.setDestinationStream(out);
            mergePdf.mergeDocuments();
            System.out.println("合并完成");
            out.close();
            in.close();
            ftpUtil.close();
        } catch (Exception e) {
            LOGGER.info(e.getMessage() + "pdf合并失败", e);
            e.printStackTrace();
            throw new RuntimeException("pdf合并失败");
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (ftpUtil != null) {
                ftpUtil.close();
            }
        }
    }

    /**
     * @Description 删除生成pdf的辅助文件
     * @Author wangwenjie
     * @param paths
     * @return void
     * @Date 2019/3/29
     */
    public static void deleteFile(String[] paths) {
        File file = null;
        for (int i = 0, len = paths.length; i < len; i++) {
            file = new File(PATH_PREFIX + paths[i]);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * @Description 通过ftp方法删除辅助文件
     * @Author wangwenjie
     * @param paths
     * @return void
     * @Date 2019/5/25
     */
    public static void deleteFilesByFTP(String[] paths) throws IOException {
        FTPUtil ftp = new FTPUtil();
        try {
            for (String path : paths) {
                ftp.removeFile("downloadFile/" + path);
            }
        } catch (Exception e) {
            LOGGER.info("删除文件失败", e.getMessage());
        } finally {
            ftp.close();
        }
    }

    /**
     * @Description windows本地自测 openoffice启动方法
     * @Author wangwenjie
     * @param
     * @return void
     * @Date 2019/4/30
     */
    public static void startOpenOfficeByCmd() throws IOException {
        String OpenOffice_HOME = "C:\\Program Files (x86)\\OpenOffice 4";
        if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
            OpenOffice_HOME = OpenOffice_HOME + "\\";
        }
        //启动openOffice服务
        String command = OpenOffice_HOME + "program\\soffice.exe -headless -accept" +
                "=\"socket,host=127.0.0.1,port=8100;urp;\"";
        Process pro = Runtime.getRuntime().exec(command);
    }

    /**
     * @Description: 在word文档中组装表格数据，key为查询表格索引，path为文件路径,map为表格数据
     * @Author: wangwenjie
     * @Params: [key, path, map]
     * @Return: void
     * @Date: 2019/9/16
     */
    public static void insertTable(String key, String path, Map<String, String> map) throws IOException {
        FTPUtil ftpUtil = null;
        try {
            ftpUtil = new FTPUtil();
            XWPFDocument document = new XWPFDocument(ftpUtil.getInputStream(path));
            ftpUtil.close();
            List<XWPFTable> tables = document.getTables();
            for (XWPFTable table : tables) {
                if (table.getText().indexOf(key) != -1) {
                    Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
                    if (map.size() == 0) {
                        XWPFTableRow row = table.createRow();
                        row.getCell(0).setText("无");
                        row.getCell(1).setText("无");
                    } else {
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> dataInfo = iterator.next();
                            XWPFTableRow row = table.createRow();
                            row.getCell(0).setText(dataInfo.getKey());
                            row.getCell(1).setText(dataInfo.getValue());
                        }
                    }
                }
            }
            ftpUtil = new FTPUtil();
            document.write(ftpUtil.uploadFile(path));
            ftpUtil.close();
        } catch (Exception e) {
            LOGGER.info("插入表格失败！");
            e.printStackTrace();
            throw new RuntimeException("插入表格失败！" + e.getMessage());
        } finally {
            if (ftpUtil != null) {
                ftpUtil.close();
            }
        }
    }

    /**
     * @Description: 需要对表格进行合并以及设置表格宽度
     * @Author: wangwenjie
     * @Params: [key, path, map]
     * @Return: void
     * @Date: 2019/10/18
     */
    public static void insertTableHasMerge(String key, String path, Map<String, String> map) throws IOException {
        FTPUtil ftpUtil = null;
        try {
            ftpUtil = new FTPUtil();
            XWPFDocument document = new XWPFDocument(ftpUtil.getInputStream(path));
            ftpUtil.close();
            List<XWPFTable> tables = document.getTables();
            for (XWPFTable table : tables) {
                if (table.getText().indexOf(key) != -1) {
                    if (map == null) {
                        table.getRow(0).getCell(1).setText("无");
                    } else {
                        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> dataInfo = iterator.next();
                            XWPFTableRow row = table.createRow();
                            CTTcPr cellCTTcPr = getCellCTTcPr(row.getCell(1));
                            CTTblWidth tcw = cellCTTcPr.isSetTcW() ? cellCTTcPr.getTcW() : cellCTTcPr.addNewTcW();
                            //设置宽度
                            tcw.setW(new BigInteger("6000"));
                            row.getCell(0).setText(dataInfo.getKey());
                            row.getCell(1).setText(dataInfo.getValue());
                        }
                        mergeCellsHorizontal(table, 0, 0, 1);
                    }
                }
            }
            ftpUtil = new FTPUtil();
            document.write(ftpUtil.uploadFile(path));
            ftpUtil.close();
        } catch (Exception e) {
            LOGGER.info("插入表格失败！");
            e.printStackTrace();
            throw new RuntimeException("插入表格失败！" + e.getMessage());
        } finally {
            if (ftpUtil != null) {
                ftpUtil.close();
            }
        }
    }

    /**
     * @Description: 获取cell的CTTcPr
     * @Author: wangwenjie
     * @Params: [cell]
     * @Return: org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr
     * @Date: 2019/10/18
     */
    public static CTTcPr getCellCTTcPr(XWPFTableCell cell) {
        CTTc cttc = cell.getCTTc();
        CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
        return tcPr;
    }

    /**
     * @Description: 合并单元格
     * @Author: wangwenjie
     * @Params: [table, row, fromCell, toCell]
     * @Return: void
     * @Date: 2019/10/18
     */
    public static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.RESTART);
            } else {
                getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
}
