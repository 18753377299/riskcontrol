package com.picc.riskctrl.riskcheck.template.factory;

import com.picc.riskctrl.riskcheck.template.newBuilder.NewIntactTemplateBuilder;
import com.picc.riskctrl.riskcheck.template.newBuilder.NewTemplateBuilder;
import com.picc.riskctrl.riskcheck.template.newBuilder.NewTemplateDirector;

/**
 * @Description: 新完整版创建工厂 pdf改为单文件直接生成
 * @Author: wangwenjie
 * @Create: 2019-04-30
 */
public class NewRiskCheckIntactTemplateFactory implements TemplateFactory {

    @Override
    public DefaultTemplates createTemplate() {
        NewTemplateBuilder builder = new NewIntactTemplateBuilder();
        NewTemplateDirector director = new NewTemplateDirector(builder);
        return director.constructTemplate();
    }
}

