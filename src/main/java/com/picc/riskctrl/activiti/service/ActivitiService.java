package com.picc.riskctrl.activiti.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.picc.riskctrl.activiti.utils.ActivitiConst;
import com.picc.riskctrl.activiti.utils.ActivitiUtils;
import com.picc.riskctrl.base.BaseService;
import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.common.service.RiskTimeService;
import com.picc.riskctrl.common.utils.CommonConst;
import com.picc.riskctrl.exception.ProcessInstanceControlException;
import com.picc.riskctrl.exception.RiskNoCanNotFoundException;
import com.picc.riskctrl.exception.ServiceException;
import com.picc.riskctrl.riskins.dao.RiskReportMainRepository;
import com.picc.riskctrl.riskins.po.RiskReportMain;
import com.picc.riskctrl.riskins.service.RiskInsService;
import com.picc.riskctrl.riskins.vo.RiskInsUnderwriteVo;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作流业务层
 *
 * @author wangwenjie
 * @date 2020-01-19
 */
@Service
@Transactional
public class ActivitiService extends BaseService {

    @Autowired
    private RiskReportMainRepository riskReportMainRepository;

    @Autowired
    private RiskTimeService riskTimeService;

    @Autowired
    private RiskInsService riskInsService;

    @Autowired
    private DataSourcesService dataSourcesService;

    /**
     * 提交审核
     *
     * @author wangwenjie
     * @param riskFileNo
     * @return Map<String, Object>
     */
    public Map<String, Object> submitUnderwrite(String riskFileNo) {
        UserInfo userInfo = getUserInfo();

        RiskReportMain riskReportMain =
                riskReportMainRepository.findById(riskFileNo)
                        .orElseThrow(() -> new RiskNoCanNotFoundException().setErrorData(riskFileNo));
        try {
            //获取风控主表中保存的流程实例id
            String executionId = riskReportMain.getExecutionId();

            //没有流程 新建工作流
            if (StringUtils.isBlank(executionId)) {
                //删除已经存在的不完整流程
                ActivitiUtils.deleteProcessInstance(riskFileNo);

                //创建新的流程实例
                ProcessInstance processIntance = ActivitiUtils.createProcessIntance(ActivitiConst.RISKINS_APPROVE_KEY, riskFileNo);
                //流程实例id
                String processId = processIntance.getId();
                riskReportMain.setExecutionId(processId);
                executionId = processId;
            } else {
                ProcessInstance processInstance = ActivitiUtils.getProcessInstanceByBussinessKey(riskFileNo);
                if (processInstance == null) {
                    throw new ProcessInstanceControlException("单号为[" + riskFileNo + "]，流程实例id" +
                            "为[" + executionId + "]的审核流程已经结束，不能再次提交审核");
                } else if (!processInstance.isEnded()) {
                    Task task = ActivitiUtils.getTaskByBusinessKey(riskFileNo);
                    //当前审核状态为2且为提交审核才能再次提交
                    if (!(ActivitiConst.UnderwriteFlag.APPROVE_NOT_PASSED.getUnderwriteFlag()
                            .equals(riskReportMain.getUnderwriteFlag())
                            && ActivitiConst.SUBMIT_APPROVE.equals(task.getTaskDefinitionKey()))) {
                        throw new ProcessInstanceControlException("单号为[" + riskFileNo + "]，流程实例id" +
                                "为[" + executionId + "]的审核流程已经提交，当前待办节点为[" + task.getName() + "]");
                    }
                }
            }

            // 设置返回map
            Map<String, Object> map = Maps.newHashMap();
            map.put("executionId", executionId);
            map.put("comCode", riskReportMain.getComCode());
            map.put("riskFileNo", riskFileNo);
            map.put("userCode", userInfo.getUserCode());

            //判断人工审核或者自动审核通过，通过判断ruleSwitch true为自动审核
            boolean ruleSwitch = dataSourcesService.getRuleSwitch(ActivitiConst.APPROVE_FLAG, map.get("comCode").toString());
            Map<String, Object> variables = Maps.newHashMap();
            variables.put(ActivitiConst.RULESWITCH_KEY, ruleSwitch);
            if (ruleSwitch) {
                //设置自动审核通过标志位
                riskReportMain.setUnderwriteFlag("3");
                variables.put(ActivitiConst.APPROVE_FLAG,
                        ActivitiConst.UnderwriteFlag.AUTO_APPROVE_PASSED.getUnderwriteFlag());
                riskReportMain.setUnderwriteDate(new Date());
                riskTimeService.insertRiskTime(riskFileNo, userInfo.getUserCode(), CommonConst.RC_UNDERWRITE);
                //todo mq发送消息
            } else {
                //设置待审核状态
                riskReportMain.setUnderwriteFlag("4");
                variables.put(ActivitiConst.APPROVE_FLAG, ActivitiConst.UnderwriteFlag.WAIT_APPROVE.getUnderwriteFlag());
            }

            //任务进行到下一步
            Task task = ActivitiUtils.getTaskByBusinessKey(riskFileNo);

            //任务节点只有是提交审核才向下执行，已经提交过的流程不改变
            if (ActivitiConst.SUBMIT_APPROVE.equals(task.getTaskDefinitionKey())) {
                //添加提交审核人
                ActivitiUtils.addAssignee(task.getId(), userInfo.getUserName());
                ActivitiUtils.taskComplete(task.getId(), variables);
            }

            return map;
        } catch (Exception e) {
            throw new ServiceException("提交审核异常", e);
        }
    }

    /**
     * 审核
     *
     * @author wangwenjie
     * @param riskFileNo 单号
     * @param executionId 流程实例id
     * @param underwriteFlag 审核标志
     * @param repulsesugggest 备注
     * @return void
     */
    public void underwrite(String riskFileNo, String executionId, String underwriteFlag, String repulsesugggest) {
        UserInfo userInfo = getUserInfo();

        RiskReportMain riskReportMain = riskReportMainRepository.findById(riskFileNo)
                .orElseThrow(() -> new RiskNoCanNotFoundException().setErrorData(riskFileNo));
        String oldFlag = riskReportMain.getUnderwriteFlag();
        try {
            ProcessInstance processInstance = ActivitiUtils.getProcessInstanceByBussinessKey(riskFileNo);
            if (processInstance == null) {
                String processInstanceId = riskReportMain.getExecutionId();
                if (StringUtils.isBlank(processInstanceId)) {
                    throw new ProcessInstanceControlException("单号为[" + riskFileNo + "]，流程实例id" +
                            "为空，请先进行提交审核操作");
                } else {
                    throw new ProcessInstanceControlException("单号为[" + riskFileNo + "]，流程实例id" +
                            "为[" + processInstanceId + "]的审核流程已经结束，不能再次审核处理");
                }
            }

            //任务流程校验
            Task taskValid = ActivitiUtils.getTaskByBusinessKey(riskFileNo);
            String validValue = ActivitiConst.validMap.get(taskValid.getTaskDefinitionKey());
            if (ActivitiConst.SUBMIT_APPROVE.equals(taskValid.getTaskDefinitionKey())) {
                throw new ProcessInstanceControlException("当前待办节点为[提交审核]，请重新提交审核");
            } else if (!validValue.contains(underwriteFlag)) {
                throw new ProcessInstanceControlException("当前待办节点[" + taskValid.getName() + "]支持的审核状态underwriteFlag为[" + validValue + "]");
            }

            //201926127-003 关于完善商业非车险风控服务平台部分使用功能的请示：报告添加审核中状态
            riskReportMain.setUnderwriteFlag("A");
            riskReportMainRepository.save(riskReportMain);

            //任务处理参数
            Map<String, Object> variables = Maps.newHashMap();
            //获取相关标志位
            switch (underwriteFlag) {
                //审核不通过,打回
                case "2":
                    riskReportMain.setUnderwriteFlag(ActivitiConst.UnderwriteFlag.APPROVE_NOT_PASSED.getUnderwriteFlag());
                    riskReportMain.setRepulsesugggest(repulsesugggest);

                    //打回
                    variables.put(ActivitiConst.APPROVE_FLAG, ActivitiConst.UnderwriteFlag.APPROVE_NOT_PASSED.getUnderwriteFlag());
                    //to打回节点
                    Task toBack = ActivitiUtils.getTaskByBusinessKey(riskFileNo);
                    ActivitiUtils.taskComplete(toBack.getId(), variables);

                    //打回任务执行
                    Task backTask = ActivitiUtils.getTaskByBusinessKey(riskFileNo);
                    ActivitiUtils.addAssignee(backTask.getId(), userInfo.getUserName());
                    ActivitiUtils.addComment(backTask.getId(), executionId, repulsesugggest);
                    ActivitiUtils.taskComplete(backTask.getId(), variables);

                    riskInsService.insertRiskUnderwriteback(executionId, userInfo.getUserCode(), CommonConst.RC_UNDERWRITEFIR, repulsesugggest);
                    riskTimeService.insertRiskTime(riskFileNo, userInfo.getUserCode(), CommonConst.RC_REPULSE);
                    break;
                //进入一级审核
                case "4":
                    variables.put(ActivitiConst.APPROVE_FLAG, ActivitiConst.UnderwriteFlag.WAIT_FIRST_APPROVE.getUnderwriteFlag());
                    //待审核to一级审核通过
                    Task waitToFirst = ActivitiUtils.getTaskByBusinessKey(riskFileNo);
                    ActivitiUtils.taskComplete(waitToFirst.getId(), variables);

                    variables.put(ActivitiConst.APPROVE_FLAG, ActivitiConst.UnderwriteFlag.WAIT_SECOND_APPROVE.getUnderwriteFlag());
                    //一级审核通过执行，流转二级
                    Task firstToSecond = ActivitiUtils.getTaskByBusinessKey(riskFileNo);
                    ActivitiUtils.addAssignee(firstToSecond.getId(), userInfo.getUserName());
                    ActivitiUtils.addComment(firstToSecond.getId(), executionId, repulsesugggest);
                    ActivitiUtils.taskComplete(firstToSecond.getId(), variables);

                    //状态为待二级审核
                    riskReportMain.setUnderwriteFlag(ActivitiConst.UnderwriteFlag.WAIT_SECOND_APPROVE.getUnderwriteFlag());
                    //RC05审核通过
                    riskInsService.insertRiskUnderwriteback(executionId, userInfo.getUserCode(), CommonConst.RC_UNDERWRITEFIR, repulsesugggest);
                    riskTimeService.insertRiskTime(riskFileNo, userInfo.getUserCode(), CommonConst.RC_UNDERWRITEFIR);
                    break;
                //进入二级审核
                case "9":
                    //流转二级
                    Task waitToSecond = ActivitiUtils.getTaskByBusinessKey(riskFileNo);
                    if (!ActivitiConst.SECOND_APPROVE.equals(waitToSecond.getTaskDefinitionKey())) {
                        variables.put(ActivitiConst.APPROVE_FLAG, ActivitiConst.UnderwriteFlag.WAIT_SECOND_APPROVE.getUnderwriteFlag());
                        ActivitiUtils.taskComplete(waitToSecond.getId(), variables);
                    }

                    //审核通过参数设置
                    variables.put(ActivitiConst.APPROVE_FLAG, ActivitiConst.UnderwriteFlag.APPROVE_PASSED.getUnderwriteFlag());
                    //流转二级审核通过
                    Task toPassed = ActivitiUtils.getTaskByBusinessKey(riskFileNo);
                    ActivitiUtils.taskComplete(toPassed.getId(), variables);

                    //审核通过
                    Task secondTaskApprove = ActivitiUtils.getTaskByBusinessKey(riskFileNo);
                    ActivitiUtils.addAssignee(secondTaskApprove.getId(), userInfo.getUserName());
                    ActivitiUtils.addComment(secondTaskApprove.getId(), executionId, repulsesugggest);
                    ActivitiUtils.taskComplete(secondTaskApprove.getId(), variables);

                    //审核通过
                    riskReportMain.setUnderwriteFlag(ActivitiConst.UnderwriteFlag.APPROVE_PASSED.getUnderwriteFlag());
                    riskReportMain.setUnderwriteDate(new Date());
                    riskReportMain.setUnderwriteCode(userInfo.getUserCode());
                    riskReportMain.setUnderwriteName(userInfo.getUserName());

                    //RC05审核通过
                    riskInsService.insertRiskUnderwriteback(executionId, userInfo.getUserCode(), CommonConst.RC_UNDERWRITE, repulsesugggest);
                    riskTimeService.insertRiskTime(riskFileNo, userInfo.getUserCode(), CommonConst.RC_UNDERWRITE);
                    break;
            }

            if ("1".equals(riskReportMain.getUnderwriteFlag())) {
                //todo 发送mq
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            //重置标志位
            riskReportMain.setUnderwriteFlag(oldFlag);
            throw new ServiceException("审核异常", e);
        }
    }

    /**
     * 组织审核历史记录
     *
     * @author wangwenjie
     * @param executionId
     * @return java.util.List<com.picc.riskctrl.riskins.vo.RiskInsUnderwriteVo>
     */
    public List<RiskInsUnderwriteVo> getUnderwriteProcess(String executionId) {
        try {
            List<RiskInsUnderwriteVo> riskInsUnderwriteVoList = Lists.newArrayList();

            List<HistoricActivityInstance> historicActivityInstance = ActivitiUtils.getHistoricActivityInstance(executionId);

            //组织记录
            String[] logs = {ActivitiConst.SUBMIT_APPROVE, ActivitiConst.FIRST_APPROVE_PASSED,
                    ActivitiConst.FIRST_APPROVE_BACK, ActivitiConst.SECOND_APPROVE_PASSED,
                    ActivitiConst.SECOND_APPROVE_BACK};
            List<String> logList = Arrays.asList(logs);

            for (HistoricActivityInstance activityInstance : historicActivityInstance) {
                //获取已经结束局的流程，类型为task并且过滤需要的task类型
                if (activityInstance.getEndTime() != null
                        && ActivitiUtils.isUserTask(activityInstance.getActivityType())
                        && logList.contains(activityInstance.getActivityId())) {
                    String taskId = activityInstance.getTaskId();
                    RiskInsUnderwriteVo vo = new RiskInsUnderwriteVo();
                    vo.setNodeName(activityInstance.getActivityName());
                    vo.setOperateName(activityInstance.getAssignee());
                    vo.setOperateTime(activityInstance.getEndTime());
                    //获取备注对象
                    Comment comment = ActivitiUtils.getCommentByTaskId(activityInstance.getTaskId());
                    if (comment != null) {
                        vo.setRepulsesugggest(comment.getFullMessage());
                    }
                    riskInsUnderwriteVoList.add(vo);
                }
            }
            return riskInsUnderwriteVoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("组织审核历史记录异常", e);
        }
    }
}
