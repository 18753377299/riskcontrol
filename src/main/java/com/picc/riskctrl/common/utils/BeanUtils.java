package com.picc.riskctrl.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

	private static final Object[] EMPTY_ARRAY = new Object[0];
	private BeanUtils() {
	}

	public static Field[] getDeclaredFields(Object object) {
		Assert.notNull(object);
		for (Class superClass = object.getClass(); superClass != java.lang.Object.class;) {
			return superClass.getDeclaredFields();
		}
		return getDeclaredFields(object.getClass());
	}

	public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class superClass = clazz; superClass != java.lang.Object.class;) {
            try {
                return superClass.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {
                superClass = superClass.getSuperclass();
            }
        }

		throw new NoSuchFieldException((new StringBuilder()).append("No such field: ").append(clazz.getName())
				.append('.').append(propertyName).toString());
	}

	public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.info("error wont' happen");
		}
		field.setAccessible(accessible);
		return result;
	}

	public static void forceSetProperty(Object object, String propertyName, Object newValue)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			logger.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}

	public static Object invokePrivateMethod(Object object, String methodName, Object[] params)
			throws NoSuchMethodException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
            types[i] = params[i].getClass();
        }

		Class clazz = object.getClass();
		Method method = null;
		Class superClass = clazz;
		do {
			if (superClass == java.lang.Object.class) {
                break;
            }
			try {
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			} catch (NoSuchMethodException e) {
				superClass = superClass.getSuperclass();
			}
		} while (true);
		if (method == null) {
            throw new NoSuchMethodException((new StringBuilder()).append("No Such Method:")
                    .append(clazz.getSimpleName()).append(methodName).toString());
        }
		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try {
			result = method.invoke(object, params);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		method.setAccessible(accessible);
		return result;
	}

	public static List getFieldsByType(Object object, Class type) {
		List list = new ArrayList();
		Field[] fields = object.getClass().getDeclaredFields();
		Field[] arr$ = fields;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++) {
			Field field = arr$[i$];
			if (field.getType().isAssignableFrom(type)) {
                list.add(field);
            }
		}

		return list;
	}

	public static Class getPropertyType(Class type, String name) throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}

	public static String getGetterName(Class type, String fieldName) {
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");
		if ("boolean".equals(type.getName())) {
            return (new StringBuilder()).append("is").append(StringUtils.capitalize(fieldName)).toString();
        } else {
            return (new StringBuilder()).append("get").append(StringUtils.capitalize(fieldName)).toString();
        }
	}

	public static Method getGetterMethod(Class type, String fieldName) {
		try {
			return type.getMethod(getGetterName(type, fieldName), new Class[0]);
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static Object invoke(String className, String methodName, Class[] argsClass, Object[] args)
			throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {
		Class cl = Class.forName(className);
		Method method = cl.getMethod(methodName, argsClass);
		return method.invoke(cl.newInstance(), args);
	}

	public static Object invoke(Object oldObject, String methodName, Class[] argsClass, Object[] args)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Class cl = oldObject.getClass();
		Method method = cl.getMethod(methodName, argsClass);
		return method.invoke(oldObject, args);
	}

	public static String[] getFieldsName(Class cl) throws Exception {
		Field[] fields = cl.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }

		return fieldNames;
	}

	public static List getAllFieldName(Class cl) {
		List list = new ArrayList();
		Field[] fields = cl.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (!"serialVersionUID".equals(field.getName())) {
                list.add(field.getName());
            }
		}

		do {
			cl = cl.getSuperclass();
			if (cl != java.lang.Object.class) {
                list.addAll(getAllFieldName(cl));
            } else {
                return list;
            }
		} while (true);
	}

	public static List getSetter(Class cl) {
		List list = new ArrayList();
		Method[] methods = cl.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String methodName = method.getName();
			if (methodName.startsWith("set")) {
                list.add(method);
            }
		}

		do {
			cl = cl.getSuperclass();
			if (cl != java.lang.Object.class) {
                list.addAll(getSetter(cl));
            } else {
                return list;
            }
		} while (true);
	}

	public static List getGetter(Class cl) {
		List list = new ArrayList();
		Method[] methods = cl.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String methodName = method.getName();
			if (methodName.startsWith("get") || methodName.startsWith("is")) {
                list.add(method);
            }
		}

		do {
			cl = cl.getSuperclass();
			if (cl != java.lang.Object.class) {
                list.addAll(getGetter(cl));
            } else {
                return list;
            }
		} while (true);
	}

	public static String getClassNameWithoutPackage(Class cl) {
		String className = cl.getName();
		int pos = className.lastIndexOf('.') + 1;
		if (pos == -1) {
            pos = 0;
        }
		return className.substring(pos);
	}

	public static String beanToString(Object obj) {
		return ToStringBuilder.reflectionToString(obj);
	}

	/**
	 * @功能：为大对象的某特定字段赋值
	 * @author 马军亮
	 * @param object
	 *            大对象
	 * @param fieldName
	 *            字段名称（以大写字母开头）
	 * @param value
	 *            要设置的值
	 * @throws @日期
	 *             2017-10-23
	 */
	@SuppressWarnings("unchecked")
	public static void setValueforSpecificField(Object object, String fieldName, Object value) throws Exception {
		// if(object == null) {
		// return;
		// }
		try {
			if (object != null) {
				Object[] SINGLE_ELEM_ARRAY = new Object[1];
				Class objectClass = object.getClass();
				// 获取所有getter方法
				List<Method> getterMethods = new ArrayList<Method>();
				getterMethods = getGetter(objectClass);
				// 获取方法实例
				Class paramType = null;
				String currentFieldName;
				// 获取所有字表
				for (Method method : getterMethods) {
					currentFieldName = method.getName().substring(3);
					// 一对多子表处理
					if (method.getReturnType() == List.class) {
						List<Object> subObjects = (List<Object>) method.invoke(object, EMPTY_ARRAY);
						if (subObjects == null) {
							continue;
						}
						// 循环遍历子对象
						for (Object obj : subObjects) {
							setValueforSpecificField(obj, fieldName, value);
						}
						// 联合主键处理
					} else if ("Id".equals(currentFieldName)) {
						Object objectID = method.invoke(object, EMPTY_ARRAY);
						setValueforSpecificField(objectID, fieldName, value);
					} else if (currentFieldName.equals(fieldName)) {
						// 找到了要赋值的属性，执行赋值（1、加if是为了不重复获取，2、在这里获取是避免NoSuchMethodException）
						if (paramType == null) {
							// 首先得到getter方法（用于获取返回值的类型）
							Method getterMethod = objectClass.getMethod("get" + fieldName);
							// 获取其返回值的类型
							paramType = getterMethod.getReturnType();
						}
						// 得到其setter方法
						Method setterMethod = objectClass.getMethod("set" + fieldName, paramType);

						SINGLE_ELEM_ARRAY[0] = value;
						setterMethod.invoke(object, SINGLE_ELEM_ARRAY);
					}
				}
			}
		} catch (Exception e) {
			logger.info("某特定字段赋值异常：" + e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException("某特定字段赋值异常:" + e);
		}
	}
    /**
 	* 批量对象转换
 	*/
	public static List<?> convert(List<?> fromList, Class<?> clazz) {
	    if (org.apache.commons.collections.CollectionUtils.isEmpty(fromList)) {
	        return null;
	    }
	    List<Object> toList = new ArrayList<Object>();
	    for (Object from : fromList) {
	        if (from == null) {
	            return null;
	        }
	        Object to = null;
	        try {
	            to = clazz.newInstance();
	        	BeanUtils.copyProperties(to, from);
	        } catch (Exception e) {
				logger.info("初始化{}对象失败：" + e.getMessage(), e);
	        }
	        toList.add(to);
	    }
	    return toList;
	}

	/**
	* @Description: 根据指定类型返回转换list
	* @Author: wangwenjie
	* @Param: [fromList, clazz]
	* @Return: java.util.List<T>
	* @Date: 2019/11/13 15:51
	*/
	public static <T> List<T> convertByType(List<?> fromList, Class<T> clazz) {
		if (fromList.isEmpty()) {
			return null;
		}
		List<T> toList = new ArrayList<T>();
		for (Object from : fromList) {
			if (from == null) {
				return null;
			}
			T to = null;
			try {
				to = clazz.newInstance();
				BeanUtils.copyProperties(to, from);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("初始化{}对象失败：" + e.getMessage(), e);
			}
			toList.add(to);
		}
		return toList;
	}


	private static final Log logger = LogFactory.getLog(BeanUtils.class);

}