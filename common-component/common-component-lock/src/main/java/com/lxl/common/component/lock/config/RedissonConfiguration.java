/**
 * 
 */
package com.lxl.common.component.lock.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(RedissonConfiguration.class);

	@Autowired
	RedisProperties redisProperties;

	@Configuration
	@ConditionalOnClass({ Redisson.class })
	@ConditionalOnExpression("'${lock.redisson.redis.mode}'=='single' or '${lock.redisson.redis.mode}'=='cluster' or '${lock.redisson.redis.mode}'=='sentinel'")
	protected class RedissonSingleClientConfiguration {

		/**
		 * 单机模式 redisson 客户端
		 */
		@Bean
		@ConditionalOnProperty(name = "lock.redisson.redis.mode", havingValue = "single")
		RedissonClient redissonSingle() {
			Config config = new Config();
			String node = redisProperties.getSingle().getAddress();
			node = node.startsWith("redis://") ? node : "redis://" + node;
			SingleServerConfig serverConfig = config.useSingleServer().setAddress(node)
					.setTimeout(redisProperties.getPool().getConnTimeout()).setConnectionPoolSize(redisProperties.getPool().getSize())
					.setConnectionMinimumIdleSize(redisProperties.getPool().getMinIdle());
			if (StringUtils.isNotBlank(redisProperties.getPassword())) {
				serverConfig.setPassword(redisProperties.getPassword());
			}
			return Redisson.create(config);
		}

		/**
		 * 集群模式的 redisson 客户端
		 *
		 * @return
		 */
		@Bean
		@ConditionalOnProperty(name = "lock.redisson.redis.mode", havingValue = "cluster")
		RedissonClient redissonCluster() {
			logger.info("redisson cluster redisProperties: [{}]", redisProperties.getCluster());
			Config config = new Config();
			List<String> nodes = redisProperties.getCluster().getNodes();
			List<String> newNodes = new ArrayList<>();
			for (String node : nodes) {
				if (!node.startsWith("redis://")) {
					newNodes.add("redis://" + node);
				} else {
					newNodes.add(node);
				}
			}

			ClusterServersConfig serverConfig = config.useClusterServers().addNodeAddress(newNodes.toArray(new String[0]))
					.setScanInterval(redisProperties.getCluster().getScanInterval())
					.setIdleConnectionTimeout(redisProperties.getPool().getSoTimeout())
					.setConnectTimeout(redisProperties.getPool().getConnTimeout())
					.setFailedSlaveCheckInterval(redisProperties.getCluster().getFailedAttempts())
					.setRetryAttempts(redisProperties.getCluster().getRetryAttempts())
					.setRetryInterval(redisProperties.getCluster().getRetryInterval())
					.setMasterConnectionPoolSize(redisProperties.getCluster().getMasterConnectionPoolSize())
					.setSlaveConnectionPoolSize(redisProperties.getCluster().getSlaveConnectionPoolSize())
					.setTimeout(redisProperties.getTimeout());
			if (StringUtils.isNotBlank(redisProperties.getPassword())) {
				serverConfig.setPassword(redisProperties.getPassword());
			}
			return Redisson.create(config);
		}

		/**
		 * 哨兵模式 redisson 客户端
		 * 
		 * @return
		 */
		@Bean
		@ConditionalOnProperty(name = "lock.redisson.redis.mode", havingValue = "sentinel")
		RedissonClient redissonSentinel() {
			logger.info("redisson sentinel redisProperties: [{}]", redisProperties.getSentinel());

			Config config = new Config();
			String[] nodes = redisProperties.getSentinel().getNodes().split(",");
			List<String> newNodes = new ArrayList(nodes.length);
			Arrays.stream(nodes).forEach((index) -> newNodes.add(index.startsWith("redis://") ? index : "redis://" + index));

			SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(newNodes.toArray(new String[0]))
					.setMasterName(redisProperties.getSentinel().getMaster()).setReadMode(ReadMode.SLAVE)
					.setFailedSlaveCheckInterval(redisProperties.getSentinel().getFailMax()).setTimeout(redisProperties.getTimeout())
					.setMasterConnectionPoolSize(redisProperties.getPool().getSize())
					.setSlaveConnectionPoolSize(redisProperties.getPool().getSize());

			if (StringUtils.isNotBlank(redisProperties.getPassword())) {
				serverConfig.setPassword(redisProperties.getPassword());
			}
			return Redisson.create(config);
		}
	}
}
