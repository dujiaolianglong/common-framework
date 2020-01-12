/**
 * 
 */
package com.lxl.common.component.base.snowflake;

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
public class IdGeneratorFactory implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(IdGeneratorFactory.class);

	@Autowired
	private AppConfigProperties appConfigProperties;

	private SnowflakeIdGenerator snowflakeIdGenerator;

	@Override
	public void afterPropertiesSet() throws Exception {
		int workerId = appConfigProperties.getWorkerId();
		int serviceId = appConfigProperties.getServiceId();
		logger.info("init ID generator: wrokerId={}, serviceid={}", workerId, serviceId);
		snowflakeIdGenerator = new SnowflakeIdGenerator(workerId, serviceId);
	}

	public SnowflakeIdGenerator getSnowflakeIdGenerator() {
		return snowflakeIdGenerator;
	}

}
