package com.picc.riskctrl.base;


import pdfc.framework.web.ApiResponse;

/**
 * controller公用方法
 *
 * @author wangwenjie
 * @date 2020-01-07
 */
public class BaseController implements CommonSupport {

    /**
     * 包装异常信息
     *
     * @author wangwenjie
     * @param data
     * @return pdfc.framework.web.ApiResponse
     */
    public static <T> ApiResponse<T> error(T data) {
        return error(data, ApiResponse.FAIL_TEXT);
    }

    /**
     * 定义数据以及自定义异常信息
     *
     * @param data 数据
     * @param message 异常信息说明
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> error(T data, String message) {
        return new ApiResponse<T>(ApiResponse.FAIL, message, data);
    }

    /**
     * 成功返回信息
     *
     * @author wangwenjie
     * @param data
     * @return pdfc.framework.web.ApiResponse<T>
     */
    public static <T> ApiResponse<T> ok(T data){
        return ApiResponse.ok(data);
    }
}
