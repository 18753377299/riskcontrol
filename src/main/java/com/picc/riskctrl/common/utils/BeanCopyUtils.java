package com.picc.riskctrl.common.utils;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 对象拷贝
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
public class BeanCopyUtils {

    /**
     * list 对象拷贝
     *
     * @author wangwenjie
     * @param sourceList 源数据 list
     * @param targetList 目标copy list
     * @return void
     */
    public static <T> void copyPropertiesList(List<T> sourceList, List<T> targetList) {
        for (T source : sourceList) {
            Class<?> classT = source.getClass();
            T target = null;
            try {
                target = (T) classT.newInstance();
                BeanUtils.copyProperties(source, target);
                targetList.add(target);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * list 对象拷贝 po -> vo
     *
     * @author wangwenjie
     * @param sourceList 源数据 list
     * @param targetList 目标copy list
     * @param classt 目标类型
     * @return void
     */
    public static  void copyPropertiesList(List sourceList, List targetList, Class<?> classt) {
        for (Object source : sourceList) {
            try {
                Object target = classt.newInstance();
                BeanUtils.copyProperties(source, target);
                targetList.add(target);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * list 对象拷贝,上面方法的重载方法
     *
     * @author wangwenjie
     * @param sourceList 源数据 list
     * @return void
     */
    public static <T> List<T> copyPropertiesList(List<T> sourceList) {
        List<T> targetList = Lists.newArrayList();
        copyPropertiesList(sourceList, targetList);
        return targetList;
    }
}
