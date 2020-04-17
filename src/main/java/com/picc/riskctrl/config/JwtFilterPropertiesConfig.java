package com.picc.riskctrl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * jwtfilter 配置文件读取
 *
 * @author wangwenjie
 * @date 2020-03-03
 */
@Component
@PropertySource("classpath:config/sys.properties")
public class JwtFilterPropertiesConfig {
    @Value("${jwt_key}")
    private String jwtKey;

    @Value("${system_code}")
    private String systemCode;

    @Value("${cryptokey}")
    private String cryptokey;

    @Value("${cryptoiv}")
    private String cryptoiv;

    @Value("ignoreKey")
    private String ignoreKey;

    @Value("outSystemPermissionUrl")
    private String outSystemPermissionUrl;


    public String getJwtKey() {
        return jwtKey;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public String getCryptokey() {
        return cryptokey;
    }

    public String getCryptoiv() {
        return cryptoiv;
    }

    public String getIgnoreKey() {
        return ignoreKey;
    }

    public String getOutSystemPermissionUrl() {
        return outSystemPermissionUrl;
    }
}
