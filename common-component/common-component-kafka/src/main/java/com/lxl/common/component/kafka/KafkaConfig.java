/**
 * 
 */
package com.lxl.common.component.kafka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import com.lxl.common.component.kafka.producer.KafkaSendResultHandler;

/**
 * @author Administrator
 *
 */
@Component
public class KafkaConfig {
	
	@Bean
	@Primary
	@ConditionalOnMissingBean(KafkaTemplate.class)
	public KafkaTemplate<?, ?> kafkaTemplate(ProducerFactory<Object, Object> kafkaProducerFactory) {
		KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate<Object, Object>(kafkaProducerFactory);
		kafkaTemplate.setProducerListener(kafkaSendResultHandler());
		return kafkaTemplate;
	}
	
	@Bean
	KafkaSendResultHandler kafkaSendResultHandler() {
		return new KafkaSendResultHandler();
	}

//	@SuppressWarnings("unchecked")
//	@Primary
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory filterContainerFactory() {
//		ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
//		factory.setConsumerFactory(consumerFactory);
//		// 配合RecordFilterStrategy使用，被过滤的信息将被丢弃
//		factory.setAckDiscarded(true);
//		factory.setRecordFilterStrategy(kafkaRecordFilterStrategy());
//		return factory;
//	}

//	@Bean
//	ConsumerAwareErrorHandler consumerAwareErrorHandler() {
//		return new ConsumerAwareErrorHandler();
//	}

//	@Bean
//	KafkaRecordFilterStrategy kafkaRecordFilterStrategy() {
//		return new KafkaRecordFilterStrategy();
//	}

}
