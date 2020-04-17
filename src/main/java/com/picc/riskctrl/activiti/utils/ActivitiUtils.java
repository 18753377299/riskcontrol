package com.picc.riskctrl.activiti.utils;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * acitiviti工作流工具类
 *
 * @author wangwenjie
 * @date 2020-01-19
 */
@Slf4j
public class ActivitiUtils {

    //spring容器上下文
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ActivitiUtils.applicationContext = applicationContext;
    }

    /**
     * 获取流程引擎，默认的获取方式，
     * 旧版集成spring通过activiti.cfg.xml文件配置流程引擎
     * 在springboot中依旧可以通过这种方式获取
     *
     * @return org.activiti.engine.ProcessEngine
     * @author wangwenjie
     */
    public static ProcessEngine getProcessEngineByDefault() {
        return ProcessEngines.getDefaultProcessEngine();
    }

    /**
     * 通过spring容器获取流程引擎
     * <p>
     * Acitiviti在springboot启动的时候会做：
     * 1.创建并且公开一个ProcessEngine bean,
     * 2.所有的acitiviti的服务都作为acitiviti bean公开
     * 3.processes文件夹中的任何BPMN 2.0流程定义都将自动部署
     * <p>
     * 以上两种获取流程引擎的方法都可以，获取的是同一流程引擎实例
     *
     * @return org.activiti.engine.ProcessEngine
     * @author wangwenjie
     */
    public static ProcessEngine getProcessEngine() {
        return applicationContext.getBean(ProcessEngine.class);
    }

    /**
     * 通过默认流程引擎获取runtimeService
     *
     * @return org.activiti.engine.RuntimeService
     * @author wangwenjie
     */
    public static RuntimeService getRuntimeServiceByDefault() {
        return getProcessEngineByDefault().getRuntimeService();
    }

    /**
     * spring容器直接获取RuntimeService
     *
     * @return org.activiti.engine.RuntimeService
     * @author wangwenjie
     */
    public static RuntimeService getRuntimeServiceIOC() {
        return applicationContext.getBean(RuntimeService.class);
    }

    /**
     * 通过容器中的引擎获取 RuntimeService
     * 运行服务，处理所有正在运行态的流程实例、任务等
     * <p>
     * 1) act_ru_execution 运行时流程执行实例表
     * 2) act_ru_identitylink 运行时流程人员表，主要存储任务节点与参与者的相关信息
     * 3) act_ru_task 运行时任务节点表
     * 4) act_ru_variable 运行时流程变量数据表
     *
     * @return org.activiti.engine.RuntimeService
     * @author wangwenjie
     */
    public static RuntimeService getRuntimeService() {
        return getProcessEngine().getRuntimeService();
    }

    /**
     * 获取RepositoryService
     * RepositoryService 流程仓库服务，管理流程仓库，比如部署、删除、读取流程资源
     * <p>
     * 1) act_re_deployment 部署信息表
     * 2) act_re_model 流程设计模型部署表
     * 3) act_re_procdef 流程定义数据表
     */
    public static RepositoryService getRepositoryService() {
        return getProcessEngine().getRepositoryService();
    }

    /**
     * 获取taskService
     * 任务服务，管理（签收、办理、指派等）、查询任务
     *
     * @return org.activiti.engine.TaskService
     * @author wangwenjie
     */
    public static TaskService getTaskService() {
        return getProcessEngine().getTaskService();
    }

    /**
     * 获取 historyService
     * 历史服务，管理所有历史数据
     * <p>
     * 1) act_hi_actinst 历史节点表
     * 2) act_hi_attachment 历史附件表
     * 3) act_ih_comment 历史意见表
     * 4) act_hi_identitylink 历史流程人员表
     * 5) act_hi_detail 历史详情表，提供历史变量的查询
     * 6) act_hi_procinst 历史流程实例表
     * 7) act_hi_taskinst 历史任务实例表
     * 8) act_hi_varinst 历史变量表
     */
    public static HistoryService getHistoryService() {
        return getProcessEngine().getHistoryService();
    }

    /**
     * 获取表单服务
     *
     * @return org.activiti.engine.FormService
     * @author wangwenjie
     */
    public static FormService getFormService() {
        return getProcessEngine().getFormService();
    }

    /**
     * 获取 IdentityService
     * 身份服务，管理用户、组及其关系
     *
     * @return org.activiti.engine.IdentityService
     * @author wangwenjie
     */
    public static IdentityService getIdentityService() {
        return getProcessEngine().getIdentityService();
    }

    /**
     * 创建流程实例
     * 流程实例中的几个id
     * getProcessInstanceId()  表示流程实例的执行树的根的ID。
     * getProcessDefinitionId() 流程实例的流程定义的ID。
     * getId() 执行的唯一标识符。
     *
     * @param key         流程定义文件中的id
     * @param businessKey 业务号，可以用来指定单号
     * @return
     */
    public static ProcessInstance createProcessIntance(String key, String businessKey) {
        ProcessInstance processInstance = getRuntimeService().startProcessInstanceByKey(key, businessKey);
        log.info("==>创建流程成功，流程key = {}，流程名称 = {}，流程实例id = {}，业务单号 = {}", key, processInstance.getName(), processInstance.getId(), businessKey);
        return processInstance;
    }

    /**
     * 根据 业务号 获取唯一流程实例
     *
     * @param businessKey
     * @return
     */
    public static ProcessInstance getProcessInstanceByBussinessKey(String businessKey) {
        return getRuntimeService().createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .singleResult();
    }

    /**
     * 根据 流程实例id 获取唯一流程实例
     *
     * @param instanceId
     * @return
     */
    public static ProcessInstance getProcessInstanceByInstanceId(String instanceId) {
        return getRuntimeService().createProcessInstanceQuery()
                .processInstanceId(instanceId)
                .singleResult();
    }


    /**
     * 通过业务单号查询当前待办任务
     *
     * @param businessKey
     * @return
     */
    public static Task getTaskByBusinessKey(String businessKey) {
        return getTaskService().createTaskQuery().processInstanceBusinessKey(businessKey)
                .singleResult();
    }

    public static List<Task> getTasks(String key) {
        return getTaskService().createTaskQuery().processInstanceBusinessKey(key).list();
    }

    /**
     * 完成当前待办任务，进行到下一步
     *
     * @param taskId    当前任务id
     * @param variables 参数
     */
    public static void taskComplete(String taskId, Map<String, Object> variables) {
        getTaskService().complete(taskId, variables);
    }

    /**
     * 给当前任务添加处理人
     *
     * @param taskId   任务id
     * @param assignee 处理人
     */
    public static void addAssignee(String taskId, String assignee) {
        getTaskService().setAssignee(taskId, assignee);
    }

    /**
     * 设置备注信息
     *
     * @param taskId            任务id
     * @param processInstanceId 流程id
     * @param comment           备注
     */
    public static void addComment(String taskId, String processInstanceId, String comment) {
        getTaskService().addComment(taskId, processInstanceId, comment);
    }

    /**
     * 获取历史记录信息
     *
     * @param id 流程实例id
     * @return java.util.List<org.activiti.engine.history.HistoricActivityInstance>
     * @author wangwenjie
     */
    public static List<HistoricActivityInstance> getHistoricActivityInstance(String id) {
        return getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(id)
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
    }

    /**
     * 流程执行经历任务
     * @param processId
     * @return
     */
    public static List<HistoricTaskInstance> getHistoricTaskInstance(String processId) {
        return getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processId)
                .list();
    }

    /**
     * 获取流程变量
     * @param processId
     * @return
     */
    public static List<HistoricVariableInstance> getHistoricVariableInstance(String processId) {
        return getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processId)
                .list();
    }

    /**
     * 根据taskid获取备注信息
     *
     * @param taskId
     * @return org.activiti.engine.task.Comment
     * @author wangwenjie
     */
    public static Comment getCommentByTaskId(String taskId) {
        List<Comment> taskComments = getTaskService().getTaskComments(taskId);
        if (!taskComments.isEmpty()) {
            return taskComments.get(0);
        }
        return null;
    }

    /**
     * 判断类型是否是 userTask
     *
     * @param type
     * @return boolean
     * @author wangwenjie
     */
    public static boolean isUserTask(String type) {
        return ActivitiConst.ACTIVITY_TYPE_USERTASK.equals(type);
    }

    /**
     * 根据业务单号删除流程实例
     *
     * @author wangwenjie
     * @param businessKey
     * @return void
     */
    public static void deleteProcessInstance(String businessKey) {
        List<ProcessInstance> list = getRuntimeService().createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey).list();
        for (ProcessInstance processInstance : list) {
            String processInstanceId = processInstance.getId();
            //流程id，删除原因
            getRuntimeService().deleteProcessInstance(processInstanceId,
                    "delete processIntance id = [" + processInstanceId + "]");
            log.info("==>已删除流程，流程id = {}，业务单号 = {}", processInstanceId, businessKey);
        }
    }

    private ActivitiUtils() {
    }
}
