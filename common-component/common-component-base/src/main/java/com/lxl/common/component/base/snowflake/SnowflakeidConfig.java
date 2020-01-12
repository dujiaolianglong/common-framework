/**
 * 
 */
package com.lxl.common.component.base.snowflake;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Snowflake 配置
 * 
 * @author Administrator
 *
 */
@Component
@RefreshScope
public class SnowflakeidConfig {

	@Value("${app.wrokerId:}")
	private long wrokerId;

	@Value("${app.serviceId:}")
	private long serviceId;

	private SnowflakeIdGenerator snowflakeIdGenerator;

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public long getWrokerId() {
		return wrokerId;
	}

	public void setWrokerId(long wrokerId) {
		this.wrokerId = wrokerId;
	}

	@PostConstruct
	public void init() {
		snowflakeIdGenerator = new SnowflakeIdGenerator(wrokerId, serviceId);
	}

	public SnowflakeIdGenerator getSnowflakeIdGenerator() {
		return snowflakeIdGenerator;
	}

	public void setSnowflakeIdGenerator(SnowflakeIdGenerator snowflakeIdGenerator) {
		this.snowflakeIdGenerator = snowflakeIdGenerator;
	}

}
