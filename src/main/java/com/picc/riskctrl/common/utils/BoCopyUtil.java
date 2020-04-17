package com.picc.riskctrl.common.utils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

public class BoCopyUtil {
	@SuppressWarnings("unchecked")
	private static final Map<Class, String> supportTypeMap = new HashMap<Class, String>();
	// 避免在方法内多次新建数组而设立的两个基础数组 add by wangzhifu 2010-12-06
	private static final Object[] EMPTY_ARRAY = new Object[0];
	// private static Object[] SINGLE_ELEM_ARRAY = new Object[1];

	static {
		supportTypeMap.put(Integer.class, "");
		supportTypeMap.put(Long.class, "");
		supportTypeMap.put(Double.class, "");
		supportTypeMap.put(BigDecimal.class, "");
		supportTypeMap.put(String.class, "");
		supportTypeMap.put(Date.class, "");
		supportTypeMap.put(Boolean.class, "");
		supportTypeMap.put(byte[].class, "");
	}

	/**
	 * @author gjc
	 * @category 大对象转换方法
	 * @param source
	 *            源对象
	 * @param dest
	 *            目标对象
	 * @param caller
	 *            调用者
	 * @param mappingRule
	 *            源对象与目标对象子对象的名称映射方法
	 * @param intercepteMethod
	 *            字段特殊处理
	 * @exception Exception
	 * 
	 */
	public static void convert(Object source, Object dest, Object caller,
			Method mappingRule, Method intercepteMethod) throws Exception {
		Object[] SINGLE_ELEM_ARRAY = new Object[1];
		// 获取所有getter,setter 方法
		Class sourceClass = source.getClass();
		Class destClass = dest.getClass();
		// 获取所有getter,setter 方法
//		List<Method> srcGetMethods = getGetter(sourceClass);
//		List<Method> destSetMethods = getSetter(destClass);
		// 获取所有getter,setter 方法
		List<Method> srcGetMethods = null;
		List<Method> destSetMethods = null;


		srcGetMethods = getGetter(sourceClass);
		destSetMethods = getSetter(destClass);

		Map<String, Method> srcMethodMap = new HashMap<String, Method>();
		for (Method method : srcGetMethods) {
			srcMethodMap.put(method.getName().toUpperCase(), method);
		}
		String currentFieldName = null;
		Method targetGetter = null;
		int outerListSize = destSetMethods.size();
		for (int index = 0; index < outerListSize; index++) {
			Method target = destSetMethods.get(index);
			// 处理思路延用原SuperBeantools的simpleCopy方法
			currentFieldName = target.getName().substring(3);
			//begin libaofeng 20120522批改保险期间时跳过prpcinsuredidvlist表的数据copy操作
			// 暂时不能取消，取消后做批改被保险人信息后无法保存，提交核批也会校验不过去，如果要取消需要修改涉及到此拷贝的方法caoxiaohui
//			if("PrpCinsuredIdvLists".equals(currentFieldName)){
//				continue;
//			}
			//end libaofeng 20120522批改保险期间时跳过prpcinsuredidvlist表的数据copy操作
			targetGetter = destClass.getMethod("get" + currentFieldName);
			// 如果返回类型为List，则说明此字段为子表信息
			if (targetGetter.getReturnType() == List.class) {
				// 默认方法名称均为get,set.调用相应方法对子表名称转换
				String srcMethodName = "get" + currentFieldName;
				if (mappingRule != null) {
					SINGLE_ELEM_ARRAY[0] = currentFieldName;
					srcMethodName = (String) mappingRule.invoke(caller,
							SINGLE_ELEM_ARRAY);
					// System.out.println(target.getName() + " <--- " +
					// srcMethodName);
				}
				Method srcMethod = null;
				// 没有此方法退出本次循环
				try {
					srcMethod = sourceClass.getMethod(srcMethodName);
				} catch (NoSuchMethodException e) {
					// System.out.println("----Copy ERROR (没有此方法): " +
					// sourceClass.getSimpleName() + "." + srcMethodName);
					continue;
				}
				// 获取源List对象
				List<Object> fieldsListSrc = (List<Object>) srcMethod.invoke(
						source, EMPTY_ARRAY);
				// 有可能源代List对象为空，此时继续循环即可
				if (fieldsListSrc == null) {
					continue;
				}
				
				// 获目标List对象
				List<Object> fieldsListDest = (List<Object>) targetGetter
						.invoke(dest, EMPTY_ARRAY);
				// 如果目标LIST对象为空，则创建所LIST
				if (fieldsListDest == null) {
					// System.out.println("----Copy ERROR (列表为空): " +
					// sourceClass.getSimpleName() + "." + srcMethod.getName() +
					// " ---> " +
					// destClass.getSimpleName() + "." + target.getName());
					fieldsListDest = new ArrayList<Object>();
				}
				// 获取泛型
				Type genericType = targetGetter.getGenericReturnType();
				ParameterizedType paramType = (ParameterizedType) genericType;
				// 创建实例
				Class genericClazz = (Class) paramType.getActualTypeArguments()[0];
				int innerListSize = fieldsListSrc.size();
				// 循环遍历LIST
				for (int i = 0; i < innerListSize; i++) {
					Object destObject = genericClazz.newInstance();
					// 对单个对象进行拷贝
					convert(fieldsListSrc.get(i), destObject, caller,
							mappingRule, intercepteMethod);
					// 对拷后将对象存储
					fieldsListDest.add(destObject);
				}
				SINGLE_ELEM_ARRAY[0] = fieldsListDest;
				// 目标LIST赋值
				target.invoke(dest, SINGLE_ELEM_ARRAY);
				// 联合主键处理
			} else if ("Id".equals(currentFieldName)) {
				// "get" + "Id";
				String strIDname = "get" + target.getName().substring(3);
				// 获取源ID的getter方法
				Method srcIDMethod = sourceClass.getMethod(strIDname);
				// 获取目标ID的getter方法
				Method destIDMethod = destClass.getMethod(strIDname);
				// 获取源ID对象
				Object srcID = srcIDMethod.invoke(source, EMPTY_ARRAY);
				// 获取目标ID对象
				Object destID = destIDMethod.invoke(dest, EMPTY_ARRAY);
				// 目标对象可能存在为空情况
				if (destID == null) {
					// 获取ID对象类型，并实例化
					Class clazz = destIDMethod.getReturnType();
					// 若源对象id为空，则不进行处理
					if(srcID == null) {
						return;
					// 若id类型为Integer，则手动赋值（因无构造器，无法进行newInstance（））
					}else if ("java.lang.Integer".equals(clazz.getName()) && srcID != null){
						destID = 0;
					}else {
						destID = clazz.newInstance();
					}
					// 进行对象复制
					convert(srcID, destID, caller, mappingRule,
							intercepteMethod);
					SINGLE_ELEM_ARRAY[0] = destID;
					// 对目标对象进行赋值
					target.invoke(dest, SINGLE_ELEM_ARRAY);
				}
			}
			// 普通字段值进行值拷贝
			Method srcMethod = (Method) srcMethodMap.get("GET"
					+ currentFieldName.toUpperCase());
			if (srcMethod == null) {
				srcMethod = (Method) srcMethodMap.get("IS"
						+ currentFieldName.toUpperCase());
			}
			// 找到相应方法调用
			if (srcMethod != null
					&& supportTypeMap.containsKey(srcMethod.getReturnType())) {
				try {
					Object value = srcMethod.invoke(source, EMPTY_ARRAY);
					SINGLE_ELEM_ARRAY[0] = value;
					target.invoke(dest, SINGLE_ELEM_ARRAY);
				} catch (IllegalArgumentException e) {
					// 如果是类型不匹配，则打印不匹配的属性
					// System.out.println("----Copy ERROR (类型不匹配): " +
					// source.getClass().getSimpleName() + "." +
					// srcMethod.getName() + " ---> " +
					// dest.getClass().getSimpleName() + "." +
					// target.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 字段特殊处理
			if (intercepteMethod != null) {
				intercepteMethod.invoke(caller, new Object[] { dest, target,
						source });
			}
		}
		//效率点优化，主动释放map资源 add by sunlei 2013-11-16
		srcMethodMap.clear();
	}
	/**
	 * @author jingaoqiang   问题 1900095: 湖北内存溢出--不拷贝prpcinsuredidvlist对象 
	 * @category 大对象转换方法
	 * @param source
	 *            源对象
	 * @param dest
	 *            目标对象
	 * @param caller
	 *            调用者
	 * @param mappingRule
	 *            源对象与目标对象子对象的名称映射方法
	 * @param intercepteMethod
	 *            字段特殊处理
	 * @param NoCopyFieldName     
	 *            防止大对象溢出，指定对象不复制，支持对象名拼接（PrpCinsuredIdvLists,PrpCmainCars）      
	 * @exception Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void convert(Object source, Object dest, Object caller,
			Method mappingRule, Method intercepteMethod,String NoCopyFieldName) throws Exception {
		Object[] SINGLE_ELEM_ARRAY = new Object[1];
		// 获取所有getter,setter 方法
		Class sourceClass = source.getClass();
		Class destClass = dest.getClass();
		// 获取所有getter,setter 方法
//		List<Method> srcGetMethods = getGetter(sourceClass);
//		List<Method> destSetMethods = getSetter(destClass);
		// 获取所有getter,setter 方法
		List<Method> srcGetMethods = null;
		
		List<Method> destSetMethods = null;

		srcGetMethods = getGetter(sourceClass);


		destSetMethods = getSetter(destClass);

		Map<String, Method> srcMethodMap = new HashMap<String, Method>();
		for (Method method : srcGetMethods) {
			srcMethodMap.put(method.getName().toUpperCase(), method);
		}
		String currentFieldName = null;
		Method targetGetter = null;
		int outerListSize = destSetMethods.size();
		for (int index = 0; index < outerListSize; index++) {
			Method target = destSetMethods.get(index);
			// 处理思路延用原SuperBeantools的simpleCopy方法
			currentFieldName = target.getName().substring(3);
			//begin libaofeng 20120522批改保险期间时跳过prpcinsuredidvlist表的数据copy操作
			if(NoCopyFieldName.indexOf(currentFieldName)>-1){
				continue;
			}
			targetGetter = destClass.getMethod("get" + currentFieldName);
			// 如果返回类型为List，则说明此字段为子表信息
			if (targetGetter.getReturnType() == List.class) {
				// 默认方法名称均为get,set.调用相应方法对子表名称转换
				String srcMethodName = "get" + currentFieldName;
				if (mappingRule != null) {
					SINGLE_ELEM_ARRAY[0] = currentFieldName;
					srcMethodName = (String) mappingRule.invoke(caller,
							SINGLE_ELEM_ARRAY);
					// System.out.println(target.getName() + " <--- " +
					// srcMethodName);
				}
				Method srcMethod = null;
				// 没有此方法退出本次循环
				try {
					srcMethod = sourceClass.getMethod(srcMethodName);
				} catch (NoSuchMethodException e) {
					// System.out.println("----Copy ERROR (没有此方法): " +
					// sourceClass.getSimpleName() + "." + srcMethodName);
					continue;
				}
				// 获取源List对象
				List<Object> fieldsListSrc = (List<Object>) srcMethod.invoke(
						source, EMPTY_ARRAY);
				// 有可能源代List对象为空，此时继续循环即可
				if (fieldsListSrc == null) {
					continue;
				}
				
				// 获目标List对象
				List<Object> fieldsListDest = (List<Object>) targetGetter
						.invoke(dest, EMPTY_ARRAY);
				// 如果目标LIST对象为空，则创建所LIST
				if (fieldsListDest == null) {
					// System.out.println("----Copy ERROR (列表为空): " +
					// sourceClass.getSimpleName() + "." + srcMethod.getName() +
					// " ---> " +
					// destClass.getSimpleName() + "." + target.getName());
					fieldsListDest = new ArrayList<Object>();
				}
				// 获取泛型
				Type genericType = targetGetter.getGenericReturnType();
				ParameterizedType paramType = (ParameterizedType) genericType;
				// 创建实例
				Class genericClazz = (Class) paramType.getActualTypeArguments()[0];
				int innerListSize = fieldsListSrc.size();
				// 循环遍历LIST
				for (int i = 0; i < innerListSize; i++) {
					Object destObject = genericClazz.newInstance();
					// 对单个对象进行拷贝
					convert(fieldsListSrc.get(i), destObject, caller,
							mappingRule, intercepteMethod);
					// 对拷后将对象存储
					fieldsListDest.add(destObject);
				}
				SINGLE_ELEM_ARRAY[0] = fieldsListDest;
				// 目标LIST赋值
				target.invoke(dest, SINGLE_ELEM_ARRAY);
				// 联合主键处理
			} else if ("Id".equals(currentFieldName)) {
				// "get" + "Id";
				String strIDname = "get" + target.getName().substring(3);
				// 获取源ID的getter方法
				Method srcIDMethod = sourceClass.getMethod(strIDname);
				// 获取目标ID的getter方法
				Method destIDMethod = destClass.getMethod(strIDname);
				// 获取源ID对象
				Object srcID = srcIDMethod.invoke(source, EMPTY_ARRAY);
				// 获取目标ID对象
				Object destID = destIDMethod.invoke(dest, EMPTY_ARRAY);
				// 目标对象可能存在为空情况
				if (destID == null) {
					// 获取ID对象类型，并实例化
					Class clazz = destIDMethod.getReturnType();
					destID = clazz.newInstance();
					// 进行对象复制
					convert(srcID, destID, caller, mappingRule,
							intercepteMethod);
					SINGLE_ELEM_ARRAY[0] = destID;
					// 对目标对象进行赋值
					target.invoke(dest, SINGLE_ELEM_ARRAY);
				}
			}
			// 普通字段值进行值拷贝
			Method srcMethod = (Method) srcMethodMap.get("GET"
					+ currentFieldName.toUpperCase());
			if (srcMethod == null) {
				srcMethod = (Method) srcMethodMap.get("IS"
						+ currentFieldName.toUpperCase());
			}
			// 找到相应方法调用
			if (srcMethod != null
					&& supportTypeMap.containsKey(srcMethod.getReturnType())) {
				try {
					Object value = srcMethod.invoke(source, EMPTY_ARRAY);
					SINGLE_ELEM_ARRAY[0] = value;
					target.invoke(dest, SINGLE_ELEM_ARRAY);
				} catch (IllegalArgumentException e) {
					// 如果是类型不匹配，则打印不匹配的属性
					// System.out.println("----Copy ERROR (类型不匹配): " +
					// source.getClass().getSimpleName() + "." +
					// srcMethod.getName() + " ---> " +
					// dest.getClass().getSimpleName() + "." +
					// target.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 字段特殊处理
			if (intercepteMethod != null) {
				intercepteMethod.invoke(caller, new Object[] { dest, target,
						source });
			}
		}
		//效率点优化，主动释放map资源 add by sunlei 2013-11-16
		srcMethodMap.clear();
	}

	/**
	 * @功能 如果大对象的某特定字段是null 才能够为该属性赋值
	 * <p>
	 * 		201817238-003 非车承保系统非车服务化功能优化-关于非车险服务化平台问题ZDN出单问题plan表的R70数据的flag[2]，导致领款人没生成
	 * </p>
	 * @param object
	 *            大对象
	 * @param fieldName
	 *            字段名称（以大写字母开头）
	 * @param value
	 *            要设置的值
	 * @作者 曲岩
	 * @时间 2018-09-10
	 * @修改记录
	 */
	@SuppressWarnings("unchecked")
	public static void setValueforNullSpecificField(Object object,
			String fieldName, Object value) throws Exception {
		Object[] SINGLE_ELEM_ARRAY = new Object[1];
		Class objectClass = object.getClass();
		// 获取所有getter方法
		// 获取所有getter方法
		//性能调优 20110401 liyu mod start by 缓存getGetter；
//		List<Method> getterMethods = getGetter(objectClass);
		List<Method> getterMethods=new ArrayList();

		getterMethods = getGetter(objectClass);

		//性能调优 20110401 liyu mod end by 缓存getGetter；
		// 获取方法实例
		Class paramType = null;
		String currentFieldName;
		// 获取所有字表
		for (Method method : getterMethods) {
			currentFieldName = method.getName().substring(3);
			// 一对多子表处理
			if (method.getReturnType() == List.class) {
				List<Object> subObjects = (List<Object>) method.invoke(object,
						EMPTY_ARRAY);
				if (subObjects == null) {
					continue;
				}
				// 循环遍历子对象
				for (Object obj : subObjects) {
					setValueforNullSpecificField(obj, fieldName, value);
				}
				// 联合主键处理
			} else if ("Id".equals(currentFieldName)) {
				Object objectID = method.invoke(object, EMPTY_ARRAY);
				setValueforNullSpecificField(objectID, fieldName, value);
			} else if (currentFieldName.equals(fieldName)) {
				// 201817238-003 非车承保系统非车服务化功能优化-关于非车险服务化平台问题ZDN出单问题plan表的R70数据的flag[2]，导致领款人没生成 mod by Nick.quyan 20180919 begin
				//新增 对getter方法的值的获取
				//判断如果该属性的值是null 则对该属性进行赋值
				//如果该属性的值不是null 则不进行重新赋值。
				Method getterMethod = objectClass.getMethod("get" + fieldName);
				if(getterMethod.invoke(object)==null){
					//进行赋值
					// 找到了要赋值的属性，执行赋值（1、加if是为了不重复获取，2、在这里获取是避免NoSuchMethodException）
					if (paramType == null) {
						// 获取其返回值的类型
						paramType = getterMethod.getReturnType();
					}
					// 得到其setter方法
					Method setterMethod = objectClass.getMethod("set" + fieldName,
							paramType);
					
					SINGLE_ELEM_ARRAY[0] = value;
					setterMethod.invoke(object, SINGLE_ELEM_ARRAY);
				}
				//201817238-003 非车承保系统非车服务化功能优化-关于非车险服务化平台问题ZDN出单问题plan表的R70数据的flag[2]，导致领款人没生成 mod by Nick.quyan 20180919 end
			}
		}
	}
	/**
	 * 为大对象的某特定字段赋值
	 * 
	 * @param object
	 *            大对象
	 * @param fieldName
	 *            字段名称（以大写字母开头）
	 * @param value
	 *            要设置的值
	 */
	@SuppressWarnings("unchecked")
	public static void setValueforSpecificField(Object object,
			String fieldName, Object value) throws Exception {
		Object[] SINGLE_ELEM_ARRAY = new Object[1];
		Class objectClass = object.getClass();
		// 获取所有getter方法
		// 获取所有getter方法
		//性能调优 20110401 liyu mod start by 缓存getGetter；
//		List<Method> getterMethods = getGetter(objectClass);
		List<Method> getterMethods=new ArrayList();
		getterMethods = getGetter(objectClass);

		//性能调优 20110401 liyu mod end by 缓存getGetter；
		// 获取方法实例
		Class paramType = null;
		String currentFieldName;
		// 获取所有字表
		for (Method method : getterMethods) {
			currentFieldName = method.getName().substring(3);
			// 一对多子表处理
			if (method.getReturnType() == List.class) {
				List<Object> subObjects = (List<Object>) method.invoke(object,
						EMPTY_ARRAY);
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
					Method getterMethod = objectClass.getMethod("get"
							+ fieldName);
					// 获取其返回值的类型
					paramType = getterMethod.getReturnType();
				}
				// 得到其setter方法
				Method setterMethod = objectClass.getMethod("set" + fieldName,
						paramType);

				SINGLE_ELEM_ARRAY[0] = value;
				setterMethod.invoke(object, SINGLE_ELEM_ARRAY);
			}
		}
	}

	/**
	 * 获取类及其父类的所有set方法（jar包中方法的效率提升的版本）
	 * 
	 * @param cl
	 *            待获取的类
	 * @return 所有set方法
	 * 
	 *         作者：王致富 日期：2010-12-06
	 */
	private static List<Method> getSetter(Class cl) {
		List list = new ArrayList();
		Method[] methods = cl.getDeclaredMethods();
		for (int i = 0; i < methods.length; ++i) {
			Method method = methods[i];
			String methodName = method.getName();
			if (methodName.startsWith("set")) {
				list.add(method);
			}
		}
		cl = cl.getSuperclass();
		// 递归获取父类的set方法
		if (cl != Object.class) {
			list.addAll(getSetter(cl));
		}
		return list;
	}

	/**
	 * 获取类及其父类的所有get方法（jar包中方法的效率提升的版本）
	 * 
	 * @param cl
	 *            待获取的类
	 * @return 所有get方法
	 * 
	 *         作者：王致富 日期：2010-12-06
	 */
	private static List<Method> getGetter(Class cl) {
		List list = new ArrayList();
		Method[] methods = cl.getDeclaredMethods();
		int lgn=methods.length;
		for (int i = 0; i < lgn; ++i) {
			Method method = methods[i];
			String methodName = method.getName();
			// 以set或is开头的方法
			if (methodName.startsWith("get") || methodName.startsWith("is")) {
				list.add(method);
			}
		}
		cl = cl.getSuperclass();
		// 递归获取父类的get方法
		if (cl != Object.class) {
			list.addAll(getGetter(cl));
		}
		return list;
	}
}
