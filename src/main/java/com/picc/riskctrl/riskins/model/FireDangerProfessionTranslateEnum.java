package com.picc.riskctrl.riskins.model;

/**
 * @Description: [专业版]火灾生成报告字段翻译 RiskReport_firedanger
 * @Author: wangwenjie
 * @Create: 2019-07-23
 */
public enum FireDangerProfessionTranslateEnum {
    /*防火分隔*/
    fireproofSeparate_A("有完整防火墙，防火卷帘门、防火门完好，可闭合", ""),
    fireproofSeparate_B("无完整防火墙，防火卷帘门、防火门无法闭合", ""),
    /*建筑结构*/
    buildStructure_A("混凝土结构", ""),
    buildStructure_B("混合结构", ""),
    buildStructure_C("钢结构", ""),
    buildStructure_D("砖木结构或木结构", ""),
    buildStructure_E("简易建筑", ""),
    /*建筑使用年限*/
    buildUseYear_A("0-5年", ""),
    buildUseYear_B("5-10年", ""),
    buildUseYear_C("10-15年", ""),
    buildUseYear_D("15年以上", ""),
    /*建筑所有权性质*/
    buildOwnerShipNature_A("自有", ""),
    buildOwnerShipNature_B("租赁", ""),
    buildOwnerShipNature_C("混租（同一建筑）", ""),
    /*火灾荷载密度（建筑物内所容纳可燃物容积占比）*/
    fireLoadDensity_A("可燃物空间占比0%-30%", ""),
    fireLoadDensity_B("可燃物空间占比30%-60%", ""),
    fireLoadDensity_C("可燃物空间占比60%-100%", ""),
    /*主要生产或仓储材料燃烧性质*/
    mainMaterialBurnNature_A("不燃", ""),
    mainMaterialBurnNature_B("难燃", ""),
    mainMaterialBurnNature_C("可燃", ""),
    mainMaterialBurnNature_D("易燃", ""),
    /*有无爆炸生产环境*/
    explodeProductEnviron_A("有（爆炸粉尘、挥发性易燃液体、可燃气体等）", ""),
    explodeProductEnviron_B("无", ""),
    /*危险工艺部位（可多选）*/
    dangerProcessPart_A("无", ""),
    dangerProcessPart_B("无尘车间", ""),
    dangerProcessPart_C("电镀工艺", ""),
    dangerProcessPart_D("明火工艺", ""),
    dangerProcessPart_E("高压工艺", ""),
    dangerProcessPart_F("高温工段", ""),
    dangerProcessPart_G("涂装或喷漆", ""),
    dangerProcessPart_H("充放电测试", ""),
    /*作业车充电位置*/
    chargePosition_A("充电位置在建筑外", ""),
    chargePosition_B("充电位置在建筑内单独充电间", ""),
    chargePosition_C("与周边可燃物距离2米以上", ""),
    chargePosition_D("与周边可燃物距离2米以内", ""),
    /*电气线路投入使用年限*/
    lineYear_A("0-5年", ""),
    lineYear_B("5-10年", ""),
    lineYear_C("10-15年", ""),
    lineYear_D("15年以上", ""),
    /*仓储区域灯具*/
    storageAreaLight_A("防爆灯", ""),
    storageAreaLight_B("冷光灯", ""),
    storageAreaLight_C("其他灯具", ""),
    /*仓储存放情况满足“堆垛间距1m，安全通道2m，与柱0.3m，与墙与顶板0.5m”*/
    storageSituation_A("满足", ""),
    storageSituation_B("部分满足", ""),
    storageSituation_C("不满足", ""),
    /*电气线路或用电设备与可燃物周边间距*/
    lineDistance_A("大于0.5m", ""),
    lineDistance_B("0m-0.5m", ""),
    lineDistance_C("直接接触", ""),
    /*生产和仓储场所有其他无关电器设备（如生活用电等）*/
    proIrrelevantDevice_A("有", ""),
    proIrrelevantDevice_B("无", ""),
    /*消防设施（可多选）*/
    fireFacility_A("自动灭火系统", ""),
    fireFacility_B("火灾报警系统", ""),
    fireFacility_C("消火栓", ""),
    fireFacility_D("灭火器", ""),
    fireFacility_E("无", ""),
    /*室内消火栓系统*/
    indoorHydrantSystem_A("配件齐全", ""),
    indoorHydrantSystem_B("配件缺失", ""),
    /*室外消火栓系统*/
    outdoorHydrantSystem_A("扳手齐全", ""),
    outdoorHydrantSystem_B("配件缺失", ""),
    /*灭火器*/
    fireExtinguisher_A("全部有效、配备充足", ""),
    fireExtinguisher_B("配备充足，部分无效", ""),
    fireExtinguisher_C("配备不充足、部分无效", ""),
    /*消防器材通道*/
    fireEquipmentPassage_A("通畅", ""),
    fireEquipmentPassage_B("部分堵塞", ""),
    /*自动灭火系统（可多选）*/
    autoExtinguishSystem_A("末端试水压力低于0.05MPa", "正常"),
    autoExtinguishSystem_B("电源指示灯异常", "不正常"),
    autoExtinguishSystem_C("水泵控制开关手动或停止", ""),
    autoExtinguishSystem_D("水池内液位异常", ""),
    autoExtinguishSystem_E("分区控制阀异常", ""),
    autoExtinguishSystem_F("上述情况均无", ""),
    /*自动报警系统状态*/
    autoAlarmSystemStatus_A("全绿灯", ""),
    autoAlarmSystemStatus_B("有（屏蔽、故障、消音、报警等）信号指示灯亮", ""),
    autoAlarmSystemStatus_C("控制柜未通电", ""),
    /*自动报警系统监管*/
    autoAlarmSystemWatch_A("联动控制柜自动", ""),
    autoAlarmSystemWatch_B("联动控制柜手动但有人值班", ""),
    autoAlarmSystemWatch_C("联动控制柜手动或停止且无人值班", ""),
    /*消防水源供应（可多选）*/
    fireWaterSupply_A("市政水源", ""),
    fireWaterSupply_B("消防水池且水位充足", ""),
    fireWaterSupply_C("天然水源", ""),
    fireWaterSupply_D("消防水池水且位不充足", ""),
    fireWaterSupply_E("无", ""),
    /*电气线路保护*/
    electricLineProtect_A("暗敷、金属管/槽", "是"),
    electricLineProtect_B("塑胶管", "否"),
    electricLineProtect_C("明线", ""),
    electricLineProtect_D("存在无绝缘布包裹、私拉乱接等现象", ""),
    /*防火间距*/
    fireSeparation_A("大于15m", ""),
    fireSeparation_B("10m-15m", ""),
    fireSeparation_C("小于10m", ""),
    /*建筑物间存在物体*/
    bodyBetweenBuild_A("可燃物", ""),
    bodyBetweenBuild_B("钢棚", ""),
    bodyBetweenBuild_C("无", ""),
    /*厂区周边环境*/
    surroundEnvironment_A("1公里内存在化学品堆场等爆炸危险性场所", ""),
    surroundEnvironment_B("相邻火灾高风险场所", ""),
    surroundEnvironment_C("普通场所", ""),
    /*企业是否建立消防安全组织架构*/
    establishFireSafety_A("是", ""),
    establishFireSafety_B("否", ""),
    /*是否配置专兼职消防人员*/
    configureFireFighter_A("是", ""),
    configureFireFighter_B("否", ""),
    /*防火巡查、检查制度及落实情况*/
    fireCheckHappen_A("有，1月/次", ""),
    fireCheckHappen_B("有，少于1月/次", ""),
    fireCheckHappen_C("无制度", ""),
    /*动火审批制度和落实情况*/
    hotFireApproval_A("有且落实", ""),
    hotFireApproval_B("有但无落实", ""),
    hotFireApproval_C("无制度", ""),
    /*开展消防演练情况*/
    fireDrill_A("至少半年一次", ""),
    fireDrill_B("每年1次", ""),
    fireDrill_C("无", ""),
    /*定期对员工的消防培训*/
    fireTrain_A("至少半年一次", ""),
    fireTrain_B("每年1次", ""),
    fireTrain_C("无", ""),
    /*消防设施维护保养*/
    fireMaintenance_A("指定单位驻点保养", ""),
    fireMaintenance_B("非驻点定期保养", ""),
    fireMaintenance_C("自行保养", ""),
    fireMaintenance_D("无保养", ""),
    /*禁烟管理情况*/
    noSmokeManage_A("有制度落实较好", ""),
    noSmokeManage_B("有制度但未落实", ""),
    noSmokeManage_C("无制度", ""),
    /*消防控制室值班制度*/
    fireControlDuty_A("24h两人制", ""),
    fireControlDuty_B("24h一人制", ""),
    fireControlDuty_C("非24h值班", ""),
    /*一年内是否收到相关消防安全管理部门风险警示或整改建议函*/
    rectificateSuggest_A("是", ""),
    rectificateSuggest_B("否", "");

    private String professionValue;
    private String simpleValue;

    FireDangerProfessionTranslateEnum(String professionValue, String simpleValue) {
        this.professionValue = professionValue;
        this.simpleValue = simpleValue;
    }

    public String getProfessionValue(){
        return professionValue;
    }

    public String getSimpleValue(){
        if("".equals(simpleValue)){
            return professionValue;
        }
        return simpleValue;
    }
}
