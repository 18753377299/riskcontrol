package com.picc.riskctrl.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * 启动监听
 *
 * @author wangwenjie
 * @date 2020-01-10
 */
@Configuration
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        //获取启动端口
        String port = environment.getProperty("server.port");
        //获取访问路径
        String path = environment.getProperty("server.servlet.context-path");
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            log.info("");
            log.info("-------------------- Project Start Success ---------------------------------------- ");
            log.info("项目访问路径 http://{}:{}{} ", host, port,
                    Optional.ofNullable(path).orElse(""));
            log.info("");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
