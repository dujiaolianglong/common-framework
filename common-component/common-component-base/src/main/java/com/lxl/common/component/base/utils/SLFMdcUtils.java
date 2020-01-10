package com.lxl.common.component.base.utils;

import org.slf4j.MDC;

import com.lxl.common.component.base.constant.CommonConstants;

/**
 * MDC工具
 * 
 * @author Administrator
 *
 */
public class SLFMdcUtils {

	/**
	 * 日志追踪ID
	 */
	public static void setMdc(String traceId) {
		MDC.put(CommonConstants.TRACE_ID, traceId);
	}

	public static void setMdc() {
		MDC.put(CommonConstants.TRACE_ID, getTraceId());
	}

	public static String getMdc() {
		return MDC.get(CommonConstants.TRACE_ID);
	}

	public static void removeMdc() {
		MDC.remove(CommonConstants.TRACE_ID);
	}

	private static String getTraceId() {
		String uuid = UUIDUtils.generateUUID();
		return uuid.replaceAll("-", "");
	}

}
