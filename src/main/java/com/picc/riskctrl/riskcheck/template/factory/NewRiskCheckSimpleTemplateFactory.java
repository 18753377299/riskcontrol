package com.picc.riskctrl.riskcheck.template.factory;

import com.picc.riskctrl.riskcheck.template.newBuilder.NewSimpleTemplateBuilder;
import com.picc.riskctrl.riskcheck.template.newBuilder.NewTemplateBuilder;
import com.picc.riskctrl.riskcheck.template.newBuilder.NewTemplateDirector;

/**
 * @Description: 新简化版创建工厂 pdf改为单文件直接生成
 * @Author: wangwenjie
 * @Create: 2019-04-30
 */
public class NewRiskCheckSimpleTemplateFactory implements TemplateFactory {
    @Override
    public DefaultTemplates createTemplate() {
        NewTemplateBuilder builder = new NewSimpleTemplateBuilder();
        NewTemplateDirector director = new NewTemplateDirector(builder);
        return director.constructTemplate();
    }
}