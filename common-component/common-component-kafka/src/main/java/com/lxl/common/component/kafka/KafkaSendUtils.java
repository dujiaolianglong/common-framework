/**
 * 
 */
package com.lxl.common.component.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * kafka 发送工具类
 * 
 * @author Administrator
 *
 */
@Component
public class KafkaSendUtils {

	private static final Logger logger = LoggerFactory.getLogger(KafkaSendUtils.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String topic, Object data) {
		String dataJson = toJson(data);
		kafkaTemplate.send(topic, dataJson);
		logger.info("kafka send success: topic={}, data={}", topic, dataJson);
	}

	public void send(String topic, String key, Object data) {
		String dataJson = toJson(data);
		kafkaTemplate.send(topic, key, dataJson);
		logger.info("kafka send success: topic={}, key={}, data={}", topic, key, dataJson);
	}

	public void send(String topic, Integer partition, String key, Object data) {
		String dataJson = toJson(data);
		kafkaTemplate.send(topic, partition, key, dataJson);
		logger.info("kafka send success: topic={}, partition={}, key={}, data={}", topic, partition, key, dataJson);
	}

	private String toJson(Object data) {
		return JSON.toJSONStringWithDateFormat(data, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
	}

}
