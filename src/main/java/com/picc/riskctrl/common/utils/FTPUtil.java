package com.picc.riskctrl.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.*;

import java.io.*;
import java.net.SocketException;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * name：FTPUtil
 * <p>
 * </p>
 * 
 * @version 1.0
 */
@Slf4j
public class FTPUtil {
    
    private FTPClient client = null;
    
    private FtpConfig config = null;

    /** 当前工作目录，每次关闭连接要回复到null，因为当前类是单例类 */
    private String workDirectory = null;
    
    /** 是否手工控制连接 */
    private boolean handSwitch = true;
    
    /** true表示已经登录到ftp服务器 */
    private boolean ready = false;
    
    static class FtpConfig {

    	ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());
    	private String server = bundle.getString("ftpHost");
    	private Integer port = Integer.valueOf(bundle.getString("ftpPort"));
    	private String username = bundle.getString("userName");
    	private String password = bundle.getString("passWord");
    	private String rootPath = bundle.getString("saveRootPath")+bundle.getString("saveTypePath");
    	private String FTPStyle;
    	private boolean passiveMode =true ;
    	private boolean binaryFileType =true;
    	private String localEncoding ="ISO-8859-1";
    	private String remoteEncoding="UTF-8";
    	public String getServer() {
    		return server;
    	}
    	public void setServer(String server) {
    		this.server = server;
    	}
    	public Integer getPort() {
    		return port;
    	}
    	public void setPort(Integer port) {
    		this.port = port;
    	}
    	public String getUsername() {
    		return username;
    	}
    	public void setUsername(String username) {
    		this.username = username;
    	}
    	public String getPassword() {
    		return password;
    	}
    	public void setPassword(String password) {
    		this.password = password;
    	}
    	public String getRootPath() {
    		return rootPath;
    	}
    	public void setRootPath(String rootPath) {
    		this.rootPath = rootPath;
    	}
    	public boolean isPassiveMode() {
    		return passiveMode;
    	}
    	public void setPassiveMode(boolean passiveMode) {
    		this.passiveMode = passiveMode;
    	}
    	public boolean isBinaryFileType() {
    		return binaryFileType;
    	}
    	public void setBinaryFileType(boolean binaryFileType) {
    		this.binaryFileType = binaryFileType;
    	}
    	public String getLocalEncoding() {
    		return localEncoding;
    	}
    	public void setLocalEncoding(String localEncoding) {
    		this.localEncoding = localEncoding;
    	}
    	public String getRemoteEncoding() {
    		return remoteEncoding;
    	}
    	public void setRemoteEncoding(String remoteEncoding) {
    		this.remoteEncoding = remoteEncoding;
    	}
    	public String getFTPStyle() {
    		return FTPStyle;
    	}
    	public void setFTPStyle(String fTPStyle) {
    		FTPStyle = fTPStyle;
    	}
    }
    
    /**
     * 初始化参数配置及创建commons.net.ftp的客户端
     */
    public FTPUtil() {
    	FtpConfig config =new FtpConfig();
        client = new FTPClient();
        this.config = config;
        client.setControlEncoding(config.getRemoteEncoding());
        client.setConnectTimeout(60000);
        client.setDataTimeout(60000); 
        // 设置当前工作目录
        workDirectory = config.getRootPath();
    }
    
    /**
     * 连接ftp
     * 
     * @return
     * @throws SocketException
     * @throws IOException
     */
    private boolean connect() throws SocketException, IOException {
        client.connect(config.getServer(), Integer.valueOf(config.getPort()));
        int reply;
        reply = client.getReplyCode();
        
        if (!FTPReply.isPositiveCompletion(reply)) {
            client.disconnect();
            log.info("FTP server refused connection.");
            return false;
        }
        return true;
    }
    
    /**
     * 登入ftp
     * 
     * @return
     * @throws IOException
     */
    private boolean login() throws IOException {
        if (!client.login(config.getUsername(), config.getPassword())) {
            client.logout();
            log.info("FTP server login fail.");
            return false;
        }
        return true;
    }
    
    /**
     * 连接然后登入统一入口
     * 
     * @return
     * @throws SocketException
     * @throws IOException
     */
    public boolean ready() throws SocketException, IOException {
    	if(client.isConnected()) {
    		return true;
    	}else if (connect() && login()) {
            setConfig();
            ready = true;
            return true;
        }
        return false;
    }
    
    /**
     * ftp运行环境参数配置
     * 
     * @throws IOException
     */
    private void setConfig() throws IOException {
        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        client.configure(conf);
        
        // 被动传输模式
        if (config.isPassiveMode()) {
            client.enterLocalPassiveMode();
        }
        
        // 二进制传输模式
        if (config.isBinaryFileType()) {
            client.setFileType(FTP.BINARY_FILE_TYPE);
        }
        // 设置当前工作目录
        client.changeWorkingDirectory(getWorkDirectory());
    }
    
    /**
     * 关闭连接
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        if (client.isConnected()) {
            client.logout();
            client.disconnect();
            // 也可设置为null
            workDirectory = config.getRootPath();
        }
        ready = false;
    }
    
    /**
     * 获取等前工作目录的文件列表
     * 
     * @return
     * @throws IOException
     */
    public String[] listFiles() throws IOException {
        if (!setReady()) {
            return null;
        }
        FTPFile[] files = client.listFiles();
        int filesLength = files.length;
        String[] fileNameArr = new String[filesLength];
        for (int i = 0; i < filesLength; i++) {
            fileNameArr[i] = files[i].getName();
        }
        setClose();
        return fileNameArr;
    }
    
    /**
     * 上传文件，文件名方式
     * 
     * @param path
     * @param name
     * @return
     * @throws Exception 
     * @throws IOException
     */
    public boolean uploadFile(String uploadFile, String remoteName) throws Exception {
        FileInputStream fis = null;
        try {
            if (!setReady()) {
                return false;
            }
            fis = new FileInputStream(uploadFile);
            if (remoteName.contains("/")) {
            	client.changeWorkingDirectory(config.getRootPath());
                String remotePath = remoteName.substring(0,remoteName.lastIndexOf("/"));
                if(this.createDirectory(remotePath)) {
	                remoteName=remoteName.substring(remoteName.lastIndexOf("/") + 1,remoteName.length());
	                if (client.storeFile(remoteName, fis)) {
	                    client.changeWorkingDirectory(workDirectory);
	                    log.info(" upload success !!! ");
	                    return true;
	                }
                }
            }
        } catch (Exception e) {
            log.error(" upload fail !!! ");
            throw e;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e2) {
                    log.error(e2.getMessage(), e2);
                }
            }
            setClose();
        }
        return false;
    }
    
    /**
     * 上传文件,流方式
     * 
     * @param path
     * @param name
     * @return
     * @throws IOException
     */
    public boolean uploadFile(InputStream stream, String remoteName) {
        try {
            if (!setReady()) {
                return false;
            }
        	client.changeWorkingDirectory(config.getRootPath());
            if(this.createDirectory(remoteName.substring(0, remoteName.lastIndexOf("/")))) {
	            if (client.storeFile(remoteName.substring(remoteName.lastIndexOf("/")+1), stream)) {
	                log.info(" upload success !!! ");
	                return true;
	            }
            }
        } catch (Exception e) {
            log.error(" upload fail !!! ");
            return false;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e2) {
                    log.error(e2.getMessage(), e2);
                }
                
            }
        }
        return false;
        
    }
    
    
    /**
     * 上传文件,流方式
     * 
     * @param path
     * @param name
     * @return
     * @throws IOException
     */
    public OutputStream uploadFile(String remoteName) throws Exception {
    	
    	OutputStream out = null;
        if (setReady()) {
        	client.changeWorkingDirectory(config.getRootPath());
            if(this.createDirectory(remoteName.substring(0, remoteName.lastIndexOf("/")))) {
               out = client.storeFileStream(remoteName.substring(remoteName.lastIndexOf("/")+1));
            }
        }
        return out;
        
    }
    /**
     * 下载文件
     * 
     * @param ftpFileName
     * @param localName
     * @return
     * @throws Exception 
     */
    public boolean downloadFile(String ftpFileName, String localName) throws Exception {
        FileOutputStream fos = null;
        try {
            File localFile=new File(localName);
            if(localFile!=null&&!localFile.exists()){
                if(localFile.getParentFile()!=null&&!localFile.getParentFile().exists()){
                    localFile.getParentFile().mkdirs();
                }
                localFile.createNewFile();
            }
            fos = new FileOutputStream(localFile);
            if (!setReady()) {
                return false;
            }
            if (client.retrieveFile(
                new String(ftpFileName.getBytes(config.getLocalEncoding()), config.getRemoteEncoding()), fos)) {
                log.info("download success !!! ");
                return true;
            }
            log.info(" download fail !!! ");
            return false;
        } catch (Exception e) {
            log.error("ftp下载文件失败ftpFileName：" + ftpFileName + ",localName" + localName, e);
            throw e;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        
    }
    
    /**
     * 删除文件
     * 
     * @param path
     * @param name
     * @return
     * @throws IOException
     */
    public boolean removeFile(String name) throws Exception {
        if (!setReady()) {
            return false;
        }
        client.changeWorkingDirectory(config.getRootPath());
        if (client.deleteFile(name)) {
            log.info("remove file success !!! ");
            return true;
        }
        client.doCommandAsStrings("RMDA", new String(name.getBytes(), "iso-8859-1"));
        log.info(" remove file fail !!! ");
        return false;
    }

    
    
    /**

     * 删除Ftp上的文件夹 包括其中的文件 <功能详细描述>

     * 

     * @param client Ftp对象

     * @param pathName 文件夹路径

     * @return SUCCESS:成功 其他:失败


     */

    public boolean removeDirectoryALLFile(String pathName) throws Exception {
        if (!setReady()) {
            return false;
        }
        client.changeWorkingDirectory(config.getRootPath());
        FTPFile[] files = client.listFiles(pathName);
        if (null != files && files.length > 0)
        {
            for (FTPFile file : files)

            {
                if (file.isDirectory())
                {
                    removeDirectoryALLFile(pathName + "/" + file.getName());
                    // 切换到父目录，不然删不掉文件夹
                    if(pathName.indexOf("/")>-1) {
	                    client.changeWorkingDirectory(pathName.substring(0, pathName.lastIndexOf("/")));
                    }else {
	                    client.changeWorkingDirectory(pathName);
                    }
                } else{
                    if (!client.deleteFile(pathName + "/" + file.getName()))
                    {
                        return false;
                    }
                }
            }
        }
        // 切换到父目录，不然删不掉文件夹
//            client.changeWorkingDirectory(pathName.substring(0, pathName.lastIndexOf("/")));
        client.changeWorkingDirectory(config.getRootPath());
        client.removeDirectory(pathName);
        return true;

    }
    /**
     * 改变工作目录
     * 
     * @param path
     * @throws IOException
     */
    public void setWorkDirectory(String path) throws IOException {
        workDirectory = (config.getRootPath() + path);
        
        // 如果是手动控制可以设置改变工作目录
        if (handSwitch) {
            client.changeWorkingDirectory(workDirectory);
        }
    }
    
    /**
     * 
     * 复制文件
     * @param @param src
     * @param @param dst
     * @param @return
     * @param @throws Exception     
     * @return boolean
     */
    public boolean copyFile(String src,String dst) throws Exception {
        try {
            setReady();
            if (dst.contains("/")) {
                String remotePath = dst.substring(0,dst.lastIndexOf("/"));
                client.makeDirectory(remotePath);
            }
            InputStream inputStream=client.retrieveFileStream(src);
            if (!client.completePendingCommand()) {
                return false;
            }
            // 如果读取的文件流不为空则复制文件
            if (inputStream != null) {
                boolean tt= client.storeFile(dst, inputStream);
                // 关闭文件流
                inputStream.close();
                return tt;
            }
            return false;
        } catch (Exception e) {
            log.error("ftp复制文件出错,[src:" + src+",dst:"+dst+"]", e);
            throw e;
        }
    }
    
    /**
     * 创建目录
     * 
     * @param pathname
     * @return
     * @throws IOException
     */
    public boolean createDirectory(String pathname) throws IOException {
    	boolean okFlag  =false;
        StringBuffer dire = new StringBuffer().append(workDirectory);
        if(StringUtils.isNotBlank(pathname)) {
        	String[] pathList = pathname.split("/");
        	for(String path:pathList) {
        		if(StringUtils.isNotBlank(path)) {
        			dire.append("/"+path);
        			client.makeDirectory(path);
        			okFlag = client.changeWorkingDirectory(dire.toString());
        		}
        	}
        }
        return okFlag;
    }
    
    /**
     * 获取当前工作目录
     * 
     * @return
     */
    public String getWorkDirectory() {
        return workDirectory;
    }
    
    /**
     * 准备FTP连接环境
     * 
     * @return
     * @throws SocketException
     * @throws IOException
     */
    private boolean setReady() throws SocketException, IOException {
        if (!ready) {
            if (!ready()) {
                log.error("Ftp ready fail.");
                if (client.isConnected()) {
                    client.disconnect();
                }
                return false;
            }
        }
        ready = true;
        return true;
    }
    
    /**
     * 设置是否ftp连接
     * 
     * @throws IOException
     */
    private void setClose() {
        try {
            if (!handSwitch) {
                close();
            }
        } catch (Exception e) {
            log.error("关闭ftp连接出错", e);
        }
        
    }
    
    /**
     * 打开手动连接
     */
    public void openHandSwitch() {
        handSwitch = true;
    }
    
    /**
     * 关闭手动连接
     */
    public void closeHandSwitch() {
        handSwitch = false;
    }

    /**
     * @Description 通过文件路径获取输入流
     * @Author wangwenjie
     * @param path
     * @return java.io.InputStream
     * @Date 2019/4/29
     */
    public InputStream getInputStream(String path) throws IOException {
        InputStream in = null;
        if (setReady()) {
            client.changeWorkingDirectory(config.getRootPath());
            in = client.retrieveFileStream(path);
        }
        return in;
    }
    
    

    /**
     * 判断文件是否存在
     * 
     * @throws IOException
     */
    public boolean isExsits(String filePath) {
        try {
        	// 判断是否连接状态
			if (setReady()) {
				//切换路径
				client.changeWorkingDirectory(config.getRootPath());
				FTPFile[] ftpFiles = client.listFiles(filePath);
			    if(ftpFiles.length>0) {
			    	return true;
			    } else {
			    	return false;
			    }
			} else {
			    return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("判断文件是否存在出错", e);
			return false;
		} 
        
    }
}
