/**
 * 
 */
package com.lxl.common.component.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

/**
 * @author Administrator
 *
 */
public class KafkaRecordFilterStrategy implements RecordFilterStrategy<Object, Object> {

	private static final Logger logger = LoggerFactory.getLogger(KafkaRecordFilterStrategy.class);

	@Override
	public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
		// 此类可以对即将消费的信息进行一些列的过滤
		// 比如写日志的时候，过滤掉一些日志不消费，也是可以的，但是不消费，那条消息就会被丢弃
		// 为true则丢弃消息
		logger.info("topic={}, partition={}, value={}", consumerRecord.topic(), consumerRecord.partition(),
				consumerRecord.value());
		return false;
	}

}
