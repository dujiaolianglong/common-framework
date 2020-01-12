/**
 * 
 */
package com.lxl.common.component.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component
public class EnvProfileUtils implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(EnvProfileUtils.class);

	@Value("${app.sprofile:}")
	private String envProfile;

	@Value("${app.wrokerId:}")
	private int wrokerId;

	@Value("${spring.application.name:}")
	private String appName;

	private static String envProfileCopy;

	private static String appId;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("init envProfile : {}", envProfile);
		EnvProfileUtils.envProfileCopy = envProfile;
		EnvProfileUtils.appId = appName + ":" + wrokerId;
	}

	public static boolean isDev() {
		if ("dev".equals(envProfileCopy)) {
			return true;
		}
		return false;
	}

	public static boolean isTest() {
		if ("test".equals(envProfileCopy)) {
			return true;
		}
		return false;
	}

	public static boolean isUat() {
		if ("uat".equals(envProfileCopy)) {
			return true;
		}
		return false;
	}

	public static boolean isProd() {
		if ("prod".equals(envProfileCopy)) {
			return true;
		}
		return false;
	}

	public static String getDevProfile() {
		return envProfileCopy;
	}

	public static String getAppId() {
		return appId;
	}

}
