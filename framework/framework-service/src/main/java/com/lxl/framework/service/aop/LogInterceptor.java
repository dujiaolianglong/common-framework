package com.lxl.framework.service.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lxl.common.component.base.constant.CommonConstants;
import com.lxl.common.component.base.utils.HttpHelper;
import com.lxl.common.component.base.utils.SLFMdcUtils;
import com.lxl.common.component.base.utils.StringUtils;
import com.lxl.common.component.base.utils.UUIDUtils;

/**
 * 日志追踪拦截器
 * 
 * @author Administrator
 *
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

	private static final ThreadLocal<Long> START_TTIME_THREAD_LOCAL = new NamedThreadLocal<Long>("HttpRequestStartTime");

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		String traceId = httpServletRequest.getHeader(CommonConstants.TRACE_ID);
		if (StringUtils.isEmpty(traceId)) {
			traceId = UUIDUtils.generateUUID();
		}
		SLFMdcUtils.setMdc(traceId);
		long beginTime = System.nanoTime();
		START_TTIME_THREAD_LOCAL.set(beginTime);

		if (HttpHelper.isMultipart(httpServletRequest)) {
			return true;
		}

		String body = HttpHelper.getBodyString(httpServletRequest);
		Map<String, String> params = HttpHelper.getParams(httpServletRequest);
		logger.info("req--------- [url={}]", httpServletRequest.getRequestURI());
		logger.info("req--------- [body={}]", body);
		logger.info("req--------- [params={}]", JSONObject.toJSONString(params));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {
		long beginTime = START_TTIME_THREAD_LOCAL.get();
		long endTime = System.nanoTime();
		long time = (endTime - beginTime) / 1000000;
		if (time > 1000) {
			logger.warn(getLogMsg(httpServletRequest) + time);
		}
		SLFMdcUtils.removeMdc();
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e)
			throws Exception {

	}

	private String getLogMsg(HttpServletRequest httpServletRequest) {
		StringBuilder strBuld = new StringBuilder();
		strBuld.append("URL[");
		strBuld.append(httpServletRequest.getRequestURI());
		strBuld.append("]耗时(毫秒):");
		return strBuld.toString();
	}

}
