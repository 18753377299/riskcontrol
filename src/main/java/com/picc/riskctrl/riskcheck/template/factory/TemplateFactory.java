package com.picc.riskctrl.riskcheck.template.factory;

/**
 * @Description: 创建模板工厂，所有创建模板类实现该工厂
 */
public interface TemplateFactory {
    DefaultTemplates createTemplate();
}
