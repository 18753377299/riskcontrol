package com.picc.riskctrl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * 读取savepath配置文件
 *
 * @author wangwenjie
 * @date 2020-02-18
 */
@Component
@PropertySource("classpath:config/savePath.properties")
public class SavePathConfig {
    @Value("${saveRootPath}")
    private String saveRootPath;

    @Value("${saveTypePath}")
    private String saveTypePath;

    @Value("${savePort}")
    private String savePort;

    @Value("${userName}")
    private String userName;

    @Value("${passWord}")
    private String passWord;

    @Value("${ftpHost}")
    private String ftpHost;

    @Value("${ftpPort}")
    private String ftpPort;

    public String getSaveRootPath() {
        return saveRootPath;
    }

    public String getSaveTypePath() {
        return saveTypePath;
    }

    public String getSavePort() {
        return savePort;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    /**
     * 根据配置文件获取路径
     *
     * @author wangwenjie
     * @return java.lang.String
     */
    public String getHostPath(HttpServletRequest request) throws Exception {
        String protocol = request.getScheme();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String projectUrl = protocol + "://" + ip + ":" + savePort + "/";
        return projectUrl;
    }
}
