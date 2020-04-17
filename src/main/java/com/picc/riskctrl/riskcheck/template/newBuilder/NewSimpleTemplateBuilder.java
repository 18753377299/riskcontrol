package com.picc.riskctrl.riskcheck.template.newBuilder;

/**
 * @Description: 简化版构造者
 * @Author: wangwenjie
 * @Create: 2019-04-30
 */
public class NewSimpleTemplateBuilder extends NewTemplateBuilder {

    @Override
    protected NewTemplateBuilder buildWordReport() {
        templateInfo.setWordReportTemplate("/riskCheckReportSimple.docx");
        return this;
    }

    @Override
    protected NewTemplateBuilder buildPdfReport() {
        templateInfo.setDefaultPdfTemplate("/riskCheckReportSimple_pdf.docx");
        return this;
    }
}

