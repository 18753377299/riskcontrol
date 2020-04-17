package com.picc.riskctrl.riskcheck.template.newBuilder;

import com.picc.riskctrl.riskcheck.template.factory.DefaultTemplates;

/**
 * @Description: 模板构造
 * @Author: wangwenjie
 * @Create: 2019-04-30
 */
public class NewTemplateDirector {
    private NewTemplateBuilder newBuilder;

    public NewTemplateDirector(NewTemplateBuilder newBuilder) {
        this.newBuilder = newBuilder;
    }

    public void setTemplateBuilder(NewTemplateBuilder newBuilder) {
        this.newBuilder = newBuilder;
    }

    public DefaultTemplates constructTemplate() {
        newBuilder.buildWordNotice()
                .buildWordReport()
                .buildPdfReport();
        return newBuilder.getTemplateInfo();
    }
}

