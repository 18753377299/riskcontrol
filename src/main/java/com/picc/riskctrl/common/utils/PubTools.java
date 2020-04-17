package com.picc.riskctrl.common.utils;

import org.apache.commons.lang3.RandomUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PubTools {

    private static final Map<Class, String> supportTypeMap = new HashMap<Class, String>();
    public static final String RESULT_BLANK = "";

    static {
        supportTypeMap.put(Integer.class, "");
        supportTypeMap.put(long.class, "");
        supportTypeMap.put(Long.class, "");
        supportTypeMap.put(Double.class, "");
        supportTypeMap.put(BigDecimal.class, "");
        supportTypeMap.put(String.class, "");
        supportTypeMap.put(Date.class, "");
        supportTypeMap.put(Boolean.class, "");
        supportTypeMap.put(byte[].class, "");
        supportTypeMap.put(java.util.List.class, "");
        supportTypeMap.put(java.util.Map.class, "");
    }

    /**
     * 将字符串数组转换成逗号分隔的字符串
     *
     * @param arr
     *            字符串数组
     * @return 逗号分隔的字符串
     */
    public static String arryToStr(String[] arr) {
        StringBuilder sb = new StringBuilder();
        String str = "";
        for (int index = 0; index < arr.length; index++) {
            sb.append(arr[index]).append(",");
        }
        String strTemp = sb.toString();
        if (strTemp.length() > 0) {
            str = (String) strTemp.subSequence(0, strTemp.length() - 1);
        }
        return str;
    }

    /**
     * 将对象中为null的string属性修改为"",属性类型与实例类型一致不做重构
     *
     * @param obj
     *            实例对象
     * @param superClazz
     *            父实例类型(若无传null)
     * @return 重构的对象
     * @throws NoSuchFieldException
     */
    @SuppressWarnings("unused")
    public static Object reStructObj(Class<? extends Object> superClass, Object obj) throws NoSuchFieldException {
        if (superClass == null) {
            Field[] fields = BeanUtils.getDeclaredFields(obj);
            for (Field field : fields) {
                Object val = BeanUtils.forceGetProperty(obj, field.getName());
                Class<?> fieldType = field.getType();
                if (supportTypeMap.containsKey(fieldType)) {
                    if (String.class == fieldType && val == null) {
                        BeanUtils.forceSetProperty(obj, field.getName(), RESULT_BLANK);
                    }
                    if (java.util.List.class == fieldType && val != null) {
                        List<Object> list = (List<Object>) val;
                        for (int i = 0; i < list.size(); i++) {
                            Object subObj = list.get(0);
                            reStructObj(obj.getClass(), subObj);
                        }
                    }
                } else {
                    reStructObj(obj.getClass(), val);
                }
            }
        } else {
            Class<? extends Object> subClass = obj.getClass();
            if (superClass != subClass) {
                Field[] fields = BeanUtils.getDeclaredFields(obj);
                for (Field field : fields) {
                    Object val = BeanUtils.forceGetProperty(obj, field.getName());
                    Class<?> fieldType = field.getType();
                    if (supportTypeMap.containsKey(fieldType)) {
                        if (String.class == fieldType && val == null) {
                            BeanUtils.forceSetProperty(obj, field.getName(), RESULT_BLANK);
                        }
                        // if (java.util.List.class == fieldType && val != null) {
                        // List<Object> list = (List<Object>) val;
                        // for (int i = 0; i < list.size(); i++) {
                        // Object subObj = list.get(0);
                        // reStructObj(obj.getClass(), subObj);
                        // }
                        // }
                    } else {
                        if (val != null) {
                            reStructObj(obj.getClass(), val);
                        }
                    }
                }
            } else {
                return obj;
            }
        }
        return obj;
    }
    /**
     * 生成随机文件名：当前年月日时分秒+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        int rannum = (int) (RandomUtils.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return rannum + str;// 当前时间
    }
}

