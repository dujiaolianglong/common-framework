/**
 * 
 */
package com.lxl.common.component.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lxl.common.component.base.config.AppConfigProperties;

/**
 * @author Administrator
 *
 */
@Component
public class EnvProfileUtils implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(EnvProfileUtils.class);

	@Autowired
	private AppConfigProperties appConfigProperties;

	private static String envProfile;

	private static String appId;

	@Override
	public void afterPropertiesSet() throws Exception {
		EnvProfileUtils.envProfile = appConfigProperties.getSprofile();
		EnvProfileUtils.appId = appConfigProperties.getShortName() + ":" + appConfigProperties.getWorkerId();
		logger.info("init envProfile: envProfile={}, appId={}", EnvProfileUtils.envProfile, EnvProfileUtils.appId);
	}

	public static boolean isDev() {
		if ("dev".equals(envProfile)) {
			return true;
		}
		return false;
	}

	public static boolean isTest() {
		if ("test".equals(envProfile)) {
			return true;
		}
		return false;
	}

	public static boolean isUat() {
		if ("uat".equals(envProfile)) {
			return true;
		}
		return false;
	}

	public static boolean isProd() {
		if ("prod".equals(envProfile)) {
			return true;
		}
		return false;
	}

	public static String getEnvProfile() {
		return envProfile;
	}

	public static String getAppId() {
		return appId;
	}

}
