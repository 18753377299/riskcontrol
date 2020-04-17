package com.picc.riskctrl.riskcheck.template.newBuilder;

/**
 * @Description: 模板构建者,设置各个模板路径所在
 * @Author: wangwenjie
 * @Create: 2019-04-29
 */
public abstract class NewTemplateBuilder {
    protected NewTemplateInfo templateInfo = new NewTemplateInfo();

    protected NewTemplateBuilder buildWordNotice(){
        templateInfo.setWordNoticeTemplate("/riskCheckNoticeReport.docx");
        return this;
    }

    protected abstract NewTemplateBuilder buildWordReport();

    protected abstract NewTemplateBuilder buildPdfReport();

    public NewTemplateInfo getTemplateInfo(){
        return templateInfo;
    }
}
