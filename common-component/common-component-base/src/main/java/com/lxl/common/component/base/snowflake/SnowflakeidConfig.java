/**
 * 
 */
package com.lxl.common.component.base.snowflake;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Snowflake 配置
 * 
 * @author Administrator
 *
 */
@Component
@ConfigurationProperties(prefix = "snowflake")
@RefreshScope
public class SnowflakeidConfig {

	/** wrokerId: 来自application配置文件 */
	private long wrokerId;

	/** 业务组件Id: 来自application配置文件 */
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
