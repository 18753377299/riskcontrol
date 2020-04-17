package com.picc.riskctrl.common.proxy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class PrpDriskVo implements Serializable {
    //产品代码
    private String riskCode;
    //旧产品代码
    private String oldRiskCode;
    //产品中文名称
    private String riskCName;
    //产品中文简称
    private String riskSCName;
    //产品繁体中文名称（预留）
    private String riskTName;
    //产品英文名称
    private String riskEName;
    //产品英文简称
    private String riskSEName;
    //产品属性：1:单一产品；2:综合产品；3:组合产品；4:无框架组合产品
    private String riskAttribute;
    //销售区域层级：01:全国性；02:非全国性；
    private String saleAreaLevel;
    //销售区域代码：用逗号分隔
    private String businessArea;
    //销售区域名称：用逗号分隔
    private String businessName;
    //资料内容
    private String materialContxt;
    //险种分类代码
    private String classCode;
    //框架代码
    private String frameCode;
    //报备\报批号
    private String reportNo;
    //归属项目
    private String projectCode;
    //是否存在方案：1.是；2.否
    private String planInd;
    //财务核算层级： 4.条款责任
    private String accountLevel;
    //再保核算层级：1.险种分类；2.产品；3.条款；4.条款责任
    private String reinsLevel;
    //管理核算层级：1.险种分类；2.产品；3.条款；4.条款责任
    private String managementLevel;
    //统计核算层级：1.险种分类；2.产品；3.条款；4.条款责任
    private String statLevel;
    //审核标志：0、新增完成；1、待审核；2、修改完成；3、修改待审核；4、打回；5、修改打回；8、审核通过待编码；9、审核通过；
    private String auditFlag;
    //涉农标志：1、涉农；*2、非涉农；（默认2） 3、兼有
    private String agricultureFlag;
    //政策\商业性标志：0、一般；1、商业性；2、中央政策性；3、地方政策性；4、兼有，默认为0
    private String policyFlag;
    //出单政策性业务处理标志：1可录、0不录、2必录，默认为1
    private String policyProcessFlag;
    //强制保险标志：1、是；0、否；
    private String isForce;
    //出单是否必选方案：1：是；0否
    private String requiredFlag;
    //生效日期
    private String validDate;
    //失效日期
    private String invalidDate;
    //有效标志
    private String validStatus;
    //农险模板(tcol1)
    //--** H01,种植险通用模板
    //--** I01,通用
    //--** I02,育肥猪
    //--** I03,奶牛
    //--** I04,大畜生
    //--** I05,能繁母猪
    //--** M01,林木险通用模板
    //--** Z03	责任险组合产品非定额方案通用模板
    //--** Z04	责任险组合产品定额方案通用模板
    //--** E01	普通意外险产品通用模板
    //--**J01	普通家财险产品非定额方案通用模板
    //--**J02	普通家财险产品定额方案通用模板
    //--**Z01	责任险产品非定额方案通用模板
    //--**Z02	责任险产品定额方案通用模板
    //--**I06	生猪价格指数保险
    //--**W01	普通健康险产品通用模板
    private String templateCode;
    //客户群分类(tcol2) 0:个团兼有 1:个险 2:团险
    private String clientType;
    //方案配置方式(tcol3) “”：表示按现有方案配置方式 ;0：多维固定组合
    private String configType;
    //备注
    private String remark;
    //同步标志：1 已同步 0 未同步
    private String synflag;
    //费率单位 (100,1000) 默认1000
    private String rateUnit;
    //按什么取短期费率标志 1:按险别 0：按其他
    private String shortRateFlag;
    //险种分类标志 1: 货运险 2: 车辆险
    private String classFlag;
    //产品标志 1：正常产品 2：储金产品 3：投资型产品
    private String riskFlag;
    //自动送收付费
    private String autoPaymentFlag;
    //修改标志字段 0：表示未修改 1：表示已修改 2: 表示已下发过
    private String upDateFlag;
    //产品下发时间
    private String riskLaunchDate;
    //产品下发文号
    private String risklaunchNum;
    //目标客户
    private String targetCustom;
    //产品简介
    private String riskProfile;
    //创新程度 0：原创产品 1：改造产品
    private String innovationlevel;
    //还原险类
    private String restoreClassCode;
    //还原产品
    private String restoreRiskCode;
    //是否重大影响产品 0：否 1：是
    private String isImpactFlag;
    //是否是家庭保额产品 0：否 1：是
    private String isFamilyAmount;
    //是否外网披露产品 0:否 1:是
    private String isDisclosure;
    //不披露原因
    private String noDisclosureReason;
    //产品项目经理
    private String productManage;
    // 创建人
    private String creatorCode;
    //最后修改人
    private String updaterCode;
    //插入时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp insertTimeForHis;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp operateTimeForHis;

    //  private CPZX_Dclass cpzxDclass;
}