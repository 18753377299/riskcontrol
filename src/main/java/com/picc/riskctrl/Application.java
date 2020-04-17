package com.picc.riskctrl;

import com.tencent.tsf.monitor.annotation.EnableTsfMonitor;
import com.tencent.tsf.sleuth.annotation.EnableTsfSleuth;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.tsf.route.annotation.EnableTsfRoute;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.tsf.auth.annotation.EnableTsfAuth;
import org.springframework.tsf.ratelimit.annotation.EnableTsfRateLimit;
@ComponentScan(basePackages = {"org.easy", "com.picc.riskctrl"})
@EnableTsfAuth
@EnableTsfRoute
@EnableTsfRateLimit
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class
})
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients
@EnableTsfSleuth
@EnableTsfMonitor
@EnableJpaAuditing
@EnableJpaRepositories
@EnableConfigurationProperties
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
