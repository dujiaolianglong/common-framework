/**
 * 
 */
package com.lxl.common.component.base.utils;

/**
 * @author Administrator
 *
 */
public class NumberUtils {
	/**
	 * 判断是否奇数，如果是 则返回true
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isOdd(String val) {
		int tmpVal = Integer.valueOf(val);
		if ((tmpVal & 1) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否奇数，如果是 则返回true
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isOdd(int val) {
		return (val & 1) == 1;
	}

	/**
	 * 判断是否偶数，如果是 则返回true
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isEven(int val) {
		return (val & 1) != 1;
	}

	public static void main(String[] args) {
		System.out.println(isOdd("01"));
	}
}
