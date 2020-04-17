package com.picc.riskctrl.base;


/**
 * service公用方法
 *
 * @author wangwenjie
 * @date 2020-01-07
 */
public class BaseService implements CommonSupport {

    //去除特殊符号的正则 含有 [ ] " 的
    protected static final String REMOVE_SPE_SYMBOL_REG = "[\\[\\]\"]";

    //常用日志打印变量
    protected static final String SAVE_ERROR = "保存异常";
    protected static final String UPDATE_ERROR = "更新异常";
    protected static final String QUERY_ERROR = "查询异常";
    protected static final String CANCEL_ERROR = "注销异常";

    /**
     * 移除特殊符号的方法
     *
     * @author wangwenjie
     * @param string
     * @return java.lang.String
     */
    protected static String removeSpeSymbolsReg(String string) {
        return string.replaceAll(REMOVE_SPE_SYMBOL_REG, "");
    }
}
