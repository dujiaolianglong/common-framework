/**
 * 
 */
package com.lxl.common.component.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lxl.common.component.base.utils.EnvProfileUtils;
import com.lxl.common.component.kafka.model.KafkaMessage;

/**
 * kafka 发送工具类
 * 
 * @author Administrator
 *
 */
@Component
public class KafkaSendUtils implements InitializingBean {

	@Autowired
	private KafkaTemplate<Object, Object> kafkaTemplateTmp;

	private static KafkaTemplate<Object, Object> kafkaTemplate;

	public static void send(String topic, Object data) {
		Map<String, Object> map = new HashMap<>();
		map.put(KafkaHeaders.TOPIC, topic);
		MessageHeaders headers = new MessageHeaders(map);

		KafkaMessage kafkaMessage = new KafkaMessage(headers.getId().toString(), headers.getTimestamp(),
				EnvProfileUtils.getAppId(), data);
		GenericMessage<String> message = new GenericMessage<>(toJson(kafkaMessage), headers);
		kafkaTemplate.send(message);
	}

	public static void send(String topic, String key, Object data) {
		Map<String, Object> map = new HashMap<>();
		map.put(KafkaHeaders.TOPIC, topic);
		map.put(KafkaHeaders.MESSAGE_KEY, key);
		MessageHeaders headers = new MessageHeaders(map);

		KafkaMessage kafkaMessage = new KafkaMessage(headers.getId().toString(), headers.getTimestamp(),
				EnvProfileUtils.getAppId(), data);
		GenericMessage<String> message = new GenericMessage<>(toJson(kafkaMessage), headers);
		kafkaTemplate.send(message);
	}

	public static void send(String topic, Integer partition, String key, Object data) {
		Map<String, Object> map = new HashMap<>();
		map.put(KafkaHeaders.TOPIC, topic);
		map.put(KafkaHeaders.MESSAGE_KEY, key);
		map.put(KafkaHeaders.PARTITION_ID, partition);
		MessageHeaders headers = new MessageHeaders(map);

		KafkaMessage kafkaMessage = new KafkaMessage(headers.getId().toString(), headers.getTimestamp(),
				EnvProfileUtils.getAppId(), data);
		GenericMessage<String> message = new GenericMessage<>(toJson(kafkaMessage), headers);
		kafkaTemplate.send(message);
	}

	public static void send(ProducerRecord<Object, Object> producerRecord) {
		kafkaTemplate.send(producerRecord);
	}

	private static String toJson(Object data) {
		return JSON.toJSONStringWithDateFormat(data, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		KafkaSendUtils.kafkaTemplate = kafkaTemplateTmp;
	}

}
