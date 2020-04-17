package com.picc.riskctrl.riskcheck.model;

/**
 * @Description: word文件字段翻译枚举类 RiskCheck_venture
 * @Author: wangwenjie
 * @Create: 2019-03-19
 */
public enum WordTranslateEnmu {

    /*企业基本*/
    //被保险人类型
    insuredType_1("个人"),
    insuredType_2("团体"),
    //被保险人性质
    unitNature_A("行政事业单位及人民团体"),
    unitNature_B("国有企业"),
    unitNature_C("民营企业"),
    unitNature_D("外商投资企业（除台资"),
    unitNature_E("台资企业"),
    /*企业基本*/

    /*简易版*/
    //风险特征
    characteristics_A("0级"),
    characteristics_B("0-1级"),
    characteristics_C("1-2级"),
    characteristics_D("2-3级"),
    characteristics_E("3-4级"),
    characteristics_F("4-5级"),
    characteristics_G("5-6级"),
    characteristics_H("6-7级"),
    characteristics_I("7-8级"),
    characteristics_J("8-9级"),
    characteristics_K("9-10级"),
    //区域历史积水记录
    rainRecord_A("三年内0次"),
    rainRecord_B("三年内1次"),
    rainRecord_C("三年内2次"),
    rainRecord_D("三年内超过（含）3次"),
    //与周边水体距离
    itemDistance_A("周边无水体（江河湖海）"),
    itemDistance_B("大于1公里（8）"),
    itemDistance_C("小于1公里大于500米"),
    itemDistance_D("小于500米"),
    //厂区地面相比周边地势高差
    comparedDegree_A("高于周边地势"),
    comparedDegree_B("与周边地势基本持平"),
    comparedDegree_C("低于周边地势"),
    //设备水敏感性
    waterSensitivity_A("不敏感（浸水损失金额10万以下）"),
    waterSensitivity_B("比较敏感(浸水损失金额占比10万至50万)"),
    waterSensitivity_C("严重敏感（浸水损失金额50万以上）"),
    //有无地下资产
    underAssetsFlag_A("无"),
    underAssetsFlag_B("有，但无浸水损失风险"),
    underAssetsFlag_C("有，浸水损失较大"),
    //历史水渍线高度
    historicWater_A("无"),
    historicWater_B("0-10cm"),
    historicWater_C("＞10cm"),
    //建筑结构
    constructBuild_A("混凝土、砖混结构"),
    constructBuild_B("砖木结构"),
    constructBuild_C("钢结构（含砖墙钢顶）"),
    constructBuild_D("其它简易建筑"),
    //门窗是够完好
    doorFlag_A("完好，能正常关闭"),
    doorFlag_B("有破损，能正常关闭"),
    doorFlag_C("破损严重或较多不能正常关闭"),
    //排水沟/井疏通状况
    dredgeCondition_A("有排水沟/井，通畅"),
    dredgeCondition_B("有排水沟/井，部分堵塞"),
    dredgeCondition_C("无排水沟/井或堵塞严重"),
    //存放形式
    stoForm_A("货架"),
    stoForm_B("货架及垫仓板"),
    stoForm_C("垫仓板"),
    stoForm_D("直接放地面"),
    //存放位置
    stoLocation_A("二楼及以上位置"),
    stoLocation_B("一楼及以上"),
    stoLocation_C("全部在一楼"),
    stoLocation_D("地下"),
    //厂区雨水排放形式
    emiForm_A("纳入市政管网"),
    emiForm_B("排入河道"),
    emiForm_C("自然流出"),
    //厂区防汛挡水物资
    conMaterials_A("有充足挡水板、沙袋等"),
    conMaterials_B("有但挡水板、沙袋不充足"),
    conMaterials_C("无"),
    //是否设置紧急排水装备
    draEquipment_A("有，且可正常使用"),
    draEquipment_B("有，但不能正常使用"),
    draEquipment_C("无"),
    /*简易版*/

    /*完整版剩余字段*/
    //所处地形
    comparedTerrain_A("平原"),
    comparedTerrain_B("丘陵"),
    comparedTerrain_C("山地"),
    comparedTerrain_D("盆地"),
    //是否临近山边、山坡
    itemEnvironment_A("否"),
    itemEnvironment_B("是"),
    //周围有无大型施工工程
    largeProjects_A("无"),
    largeProjects_B("有"),
    //大型施工工程影响
    largeProImpact_A("无"),
    largeProImpact_B("影响较小"),
    largeProImpact_C("影响较大"),
    //企业经营情况
    manSituation_A("正常"),
    manSituation_B("不正常"),
    //厂区内有无投保资产位于低洼区域
    lowEquipment_A("无"),
    lowEquipment_B("有，但该位置资产浸水损失风险较低 "),
    lowEquipment_C("有，浸水损失风险可能较高"),
    //厂房所有权性质
    ownership_A("自有建筑"),
    ownership_B("租赁建筑"),
    //厂区内是否有露天堆放资产
    airStorageFlag_A("否"),
    airStorageFlag_B("是"),
    //钢结构建筑年限
    buildYears_A("无钢结构"),
    buildYears_B("10年以下"),
    buildYears_C("10-20年"),
    buildYears_D("20年以上"),
    //仓库是否有顶峰错层结构
    staggeredFlag_A("否"),
    staggeredFlag_B("是"),
    //屋顶排水方式
    drainageMethod_A("外排水"),
    drainageMethod_B("内外均有"),
    drainageMethod_C("内排水"),
    drainageMethod_D("内外均无"),
    //室内排水管道维护状况
    drainageBlock_A("畅通，正常维护"),
    drainageBlock_B("管径过小、堵塞"),
    drainageBlock_C("破损、老化"),
    drainageBlock_D("无"),
    //排水沟（管）与河道是否相连
    connectedFlag_A("不直接相连"),
    connectedFlag_B("直接相连，有设置水闸"),
    connectedFlag_C("直接相连，未设置水闸屋顶"),
    //屋顶排水是否通畅
    unobstructedFlag_A("无杂物，定期清理，排水通畅"),
    unobstructedFlag_B("有杂物，定期清理，排水通畅"),
    unobstructedFlag_C("有杂物，未定期清理，排水堵塞"),
    //建筑物内部地面是否有水井盖或管渠
    haveCanal_A("无"),
    haveCanal_B("有，但建筑物地势较高，不会倒灌"),
    haveCanal_C("有，建筑物地势低，会发生倒灌"),
    //存货水敏性
    cargoWaterSen_A("无影响"),
    cargoWaterSen_B("遇水部分损失"),
    cargoWaterSen_C("遇水全损"),
    //企业有无汛期抢险救灾应急预案
    conPlan_A("是"),
    conPlan_B("否"),
    //汛期是否实行24小时值班制度
    dutyFlag_A("是"),
    dutyFlag_B("否"),
    //汛期是否对重点区域进行监控
    monitorFlag_A("是"),
    monitorFlag_B("否"),
    //汛期是否有可行的紧急转移制度
    transferFlag_A("是"),
    transferFlag_B("否");
    /*完整版剩余字段*/


    private String value;

    WordTranslateEnmu(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}