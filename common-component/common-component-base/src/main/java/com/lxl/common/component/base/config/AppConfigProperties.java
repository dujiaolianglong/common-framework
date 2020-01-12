/**
 * 
 */
package com.lxl.common.component.base.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component
@ConfigurationProperties(prefix = "app.conf")
public class AppConfigProperties implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(AppConfigProperties.class);

	private int workerId;

	private int serviceId;

	private String shortName;

	private String sprofile;

	public int getWorkerId() {
		return workerId;
	}

	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSprofile() {
		return sprofile;
	}

	public void setSprofile(String sprofile) {
		this.sprofile = sprofile;
	}

	@Override
	public String toString() {
		return "AppConfigProperties [workerId=" + workerId + ", serviceId=" + serviceId + ", shortName=" + shortName
				+ ", sprofile=" + sprofile + "]";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("init app conf: workerId={}, serviceId={}, shortName={}, sprofile={}", workerId, serviceId,
				shortName, sprofile);
	}

}
