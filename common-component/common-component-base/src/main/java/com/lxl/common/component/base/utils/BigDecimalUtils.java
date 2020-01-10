/**
 * 
 */
package com.lxl.common.component.base.utils;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 */
public class BigDecimalUtils {

	/**
	 * 是否大于
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean greaterThan(String data1, String data2) {
		BigDecimal b1 = new BigDecimal(data1);
		BigDecimal b2 = new BigDecimal(data2);
		int result = b1.compareTo(b2);
		if (result >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 做乘法，结果只保留两位小数，不做四舍五入, 删掉多余的小数点
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static String multiply(Object data1, Object data2) {
		BigDecimal b1 = new BigDecimal(String.valueOf(data1));
		BigDecimal b2 = new BigDecimal(String.valueOf(data2));
		BigDecimal result = b1.multiply(b2);
		result.setScale(2, BigDecimal.ROUND_DOWN);
		return result.toString();
	}

	/**
	 * 做减法，结果只保留两位小数，不做四舍五入, 删掉多余的小数点
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static String divide(Object data1, Object data2, int scale) {
		BigDecimal b1 = new BigDecimal(String.valueOf(data1));
		BigDecimal b2 = new BigDecimal(String.valueOf(data2));
		BigDecimal result = b1.divide(b2, scale, BigDecimal.ROUND_DOWN);
		return result.toString();
	}
	
	/**
	 * 做除法，结果只保留两位小数，不做四舍五入, 删掉多余的小数点
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static String subtract(Object data1, Object data2) {
		BigDecimal b1 = new BigDecimal(String.valueOf(data1));
		BigDecimal b2 = new BigDecimal(String.valueOf(data2));
		BigDecimal result = b1.subtract(b2);
		result.setScale(2, BigDecimal.ROUND_DOWN);
		return result.toString();
	}
	
	/**
	 * 做加发，结果只保留两位小数，不做四舍五入, 删掉多余的小数点
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static String add(Object data1, Object data2) {
		BigDecimal b1 = new BigDecimal(String.valueOf(data1));
		BigDecimal b2 = new BigDecimal(String.valueOf(data2));
		BigDecimal result = b1.add(b2);
		result.setScale(2, BigDecimal.ROUND_DOWN);
		return result.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(divide("5", "2.40", 2));
		System.out.println(multiply("1.2", "1.2"));
	}

}
