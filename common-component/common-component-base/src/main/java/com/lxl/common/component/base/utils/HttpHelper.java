/**
 * 
 */
package com.lxl.common.component.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http 请求帮助类
 * 
 * @author Administrator
 *
 */
public class HttpHelper {

	private static final Logger logger = LoggerFactory.getLogger(HttpHelper.class);

	/**
	 * 获取body内容
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getBodyString(HttpServletRequest request) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = request.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

			char[] bodyCharBuffer = new char[1024];
			int len = 0;
			while ((len = reader.read(bodyCharBuffer)) != -1) {
				sb.append(new String(bodyCharBuffer, 0, len));
			}
		} catch (IOException e) {
			logger.error("getBodyString error", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("getBodyString inputStream close error", e);
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("getBodyString reader close error", e);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 获取url参数
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	public static Map<String, String> getParams(HttpServletRequest httpServletRequest) {
		Map<String, String[]> params = httpServletRequest.getParameterMap();
		params.entrySet();
		Map<String, String> tmpParams = new HashMap<>();
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			String mapKey = entry.getKey();
			String mapValue = entry.getValue()[0];
			tmpParams.put(mapKey, mapValue);
		}
		return tmpParams;
	}

	/**
	 * 判断是否是上传文件请求
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	public static boolean isMultipart(HttpServletRequest httpServletRequest) {
		// 判断是否是上传文件
		boolean multipart = httpServletRequest.getHeader("Content-type") != null
				&& httpServletRequest.getHeader("Content-type").startsWith("multipart/form-data");
		return multipart;
	}
}
