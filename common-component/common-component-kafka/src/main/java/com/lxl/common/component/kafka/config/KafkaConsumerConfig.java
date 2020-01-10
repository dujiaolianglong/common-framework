/**
 * 
 */
package com.lxl.common.component.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

/**
 * @author Administrator
 *
 */
@Configuration
@ConditionalOnExpression("${kafka.enable:true}")
public class KafkaConsumerConfig {

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String brokers;

	@Value("${spring.kafka.consumer.ack}")
	private int ack;
	
	@Value("${spring.kafka.consumer.enable-auto-commit}")
	private boolean enableAutoCommit;
	
	@Value("${spring.kafka.consumer.auto-commit-interval}")
	private boolean autoCommitInterval;
	
	@Value("${spring.kafka.consumer.earliest}")
	private boolean earliest;
	
	@Value("${spring.kafka.consumer.fetch-max-wait}")
	private boolean fetchMaxWait;
	
	@Value("${spring.kafka.consumer.heartbeat-interval}")
	private boolean heartbeatInterval;
	
	@Value("${spring.kafka.producer.key-deserializer}")
	private String keyDeserializer;
	
	@Value("${spring.kafka.producer.value-deserializer}")
	private String valueDeserializer;
	
	@Value("${spring.kafka.producer.max-poll-records}")
	private String maxPollRecords;

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

	public KafkaConsumerConfig() {
		super();
		logger.info("KafkaConsumerConfig init...");
	}

	/* 消费者配置 */
	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, earliest);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
		props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, fetchMaxWait);
		props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heartbeatInterval);
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
		return props;
	}

	@Bean
	public ConsumerFactory<String, Object> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	@Primary
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(2);
		factory.getContainerProperties().setPollTimeout(4000);
		// 如果enable.auto.commit设为false，但是不设置AckMode，则交由spring自动提交偏移量
		factory.getContainerProperties().setAckCount(ack);
		return factory;
	}

}
