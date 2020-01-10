/**
 * 
 */
package com.lxl.common.component.base.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.lxl.common.component.base.constant.CommonConstants;

/**
 * 反射工具类
 * 
 * @author Administrator
 *
 */
public class ReflectUtils {

	/** asm反射方法缓存, 频繁访问性能消耗,用空间换时间 */
	private static ConcurrentHashMap<String, Map<String, Object>> methodAccessCache = new ConcurrentHashMap<>();

	/**
	 * 反射调用
	 * 
	 * @param obj
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invoke(Object obj, String method, Object... args) {
		Map<String, Object> map = getMethodCache(obj);
		MethodAccess methodAccess = (MethodAccess) map.get(CommonConstants.METHODACCESS);
		int methodInx = getMethodIndx(map, method, methodAccess, args);
		return methodAccess.invoke(obj, methodInx, args);
	}

	/**
	 * 获取参数Class类型
	 * 
	 * @param args
	 * @return
	 */
	private static Class<?>[] getParameterTypes(Object... args) {
		int len = args.length;
		Class<?>[] paramTypes = new Class[len];
		for (int i = 0; i < args.length; i++) {
			paramTypes[i] = args[i].getClass();
		}
		return paramTypes;
	}

	/**
	 * 从JVM缓存获取
	 * 
	 * @param obj
	 * @return
	 */
	private static Map<String, Object> getMethodCache(Object obj) {
		Class<?> clazz = obj.getClass();
		String key = clazz.getName();
		Map<String, Object> map = methodAccessCache.get(key);
		if (null == map) {
			map = new HashMap<>();
			MethodAccess methodAccess = MethodAccess.get(clazz);
			map.put(CommonConstants.METHODACCESS, methodAccess);
			methodAccessCache.put(key, map);
		}
		return map;
	}

	/**
	 * 获取index
	 * 
	 * @param map
	 * @param method
	 * @param methodAccess
	 * @param args
	 * @return
	 */
	private static int getMethodIndx(Map<String, Object> map, String method, MethodAccess methodAccess, Object... args) {
		Class<?>[] paramTypes = getParameterTypes(args);
		Object methodInx = map.get(method);
		if (null == methodInx) {
			methodInx = methodAccess.getIndex(method, paramTypes);
			map.put(method, methodInx);
		}
		return (int) methodInx;
	}

	/**
	 * 获取field
	 * 
	 * @param object
	 * @return
	 */
	public static List<Field> getField(Object object) {
		Class<?> clazz = object.getClass();
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null && clazz != Object.class) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		return fieldList;
	}

}
