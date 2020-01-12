/**
 * 
 */
package com.lxl.common.component.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;

/**
 * @author Administrator
 *
 */
public class KafkaSendResultHandler implements ProducerListener<Object, Object> {

	private static final Logger logger = LoggerFactory.getLogger(KafkaSendResultHandler.class);

	@Override
	public void onSuccess(ProducerRecord<Object, Object> producerRecord, RecordMetadata recordMetadata) {
		logger.info("Message send to kafka server success : {} ", producerRecord.toString());
	}

	@Override
	public void onError(ProducerRecord<Object, Object> producerRecord, Exception exception) {
		logger.info("Message send to kafka server  error : {}", producerRecord.toString());
	}

}
