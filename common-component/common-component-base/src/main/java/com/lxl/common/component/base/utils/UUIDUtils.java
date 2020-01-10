/**
 * 
 */
package com.lxl.common.component.base.utils;

import java.util.UUID;

/**
 * @author Administrator
 *
 */
public class UUIDUtils {

	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
