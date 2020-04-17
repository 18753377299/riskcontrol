package com.picc.riskctrl.activiti.config;

import com.picc.riskctrl.activiti.utils.ActivitiUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * activiti工作流配置
 *
 * @author wangwenjie
 * @date 2020-01-15
 */
@Configuration
@Slf4j
public class ActivitiConfiguration extends AbstractProcessEngineAutoConfiguration implements ApplicationContextAware {


    private final DataSource dataSource;

    public ActivitiConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * activiti流程引擎配置类
     *
     * @author wangwenjie
     * @param transactionManager
     * @param springAsyncExecutor
     * @return org.activiti.spring.SpringProcessEngineConfiguration
     */
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
            PlatformTransactionManager transactionManager,
            SpringAsyncExecutor springAsyncExecutor) throws IOException {
        SpringProcessEngineConfiguration processEngineConfiguration
                = baseSpringProcessEngineConfiguration(dataSource, transactionManager, null);
        processEngineConfiguration.setAsyncExecutorActivate(false);
        ActivitiUtils.setApplicationContext(applicationContext);
        return processEngineConfiguration;
    }

    /**
     * CommandLineRunner在springboot启动时执行
     *
     * @author wangwenjie
     * @return org.springframework.boot.CommandLineRunner
     */
    @Bean
    public CommandLineRunner init() {

        return init -> {
            log.info("activiti 流程引擎版本 [{}]", ProcessEngine.VERSION);

            RepositoryService repositoryService = ActivitiUtils.getRepositoryService();
            //获取所有流程定义最新版本
            List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                    .latestVersion().list();
            for (ProcessDefinition processDefinition : processDefinitionList) {
                log.info("activiti 已发布流程 key = [{}]，name = [{}]，latestVersion = [{}]",
                        processDefinition.getKey(), processDefinition.getName(), processDefinition.getVersion());
            }
        };
    }
}
