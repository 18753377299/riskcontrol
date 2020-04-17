package com.picc.riskctrl.activiti.utils;

import com.google.common.collect.Maps;
import com.picc.riskctrl.common.utils.CommonConst;
import java.util.Map;

/**
 * 工作流常量
 *
 * @author wangwenjie
 * @date 2020-01-19
 */
public class ActivitiConst extends CommonConst {

    //风控报告审核流程id，取自 underwrite_process.bpmn20.xml 文件
    public static final String RISKINS_APPROVE_KEY = "underwrite_process";

    //开关名称，xml流程文件中 排他条件
    public static final String RULESWITCH_KEY = "ruleSwitch";

    //审核标志位名称
    public static final String APPROVE_FLAG = "underwriteFlag";

    //提交审核流程名称
    public static final String SUBMIT_APPROVE = "submitApprove";

    //一级审核
    public static final String FIRST_APPROVE = "firstApprove";

    //二级审核
    public static final String SECOND_APPROVE = "secondApprove";

    //一级通过
    public static final String FIRST_APPROVE_PASSED = "firstApprovePassed";
    //二级通过
    public static final String SECOND_APPROVE_PASSED = "secondApprovePassed";
    //一级打回
    public static final String FIRST_APPROVE_BACK = "firstApproveBack";
    //二级打回
    public static final String SECOND_APPROVE_BACK = "secondApproveBack";
    //待审核
    public static final String WAIT_APPROVE = "waitApprove";

    //校验
    public static final Map<String, String> validMap = Maps.newHashMap();

    static {
        //待审核状态
        validMap.put(WAIT_APPROVE, "2,4,9");
        //待二级审核
        validMap.put(SECOND_APPROVE, "2,9");
    }

    public static final String ACTIVITY_TYPE_USERTASK = "userTask";

    //审核标志位枚举类
    public enum UnderwriteFlag {
        //待审核
        WAIT_APPROVE("4"),
        //待一级审核
        WAIT_FIRST_APPROVE("4"),
        //待二级审核
        WAIT_SECOND_APPROVE("9"),
        //审核通过
        APPROVE_PASSED("1"),
        //审核不通过
        APPROVE_NOT_PASSED("2"),
        //自动审核通过
        AUTO_APPROVE_PASSED("3");

        private String flag;

        UnderwriteFlag(String flag) {
            this.flag = flag;
        }

        public String getUnderwriteFlag() {
            return this.flag;
        }
    }
}
