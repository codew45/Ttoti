package kr.co.ttoti.backend.global.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.fasterxml.jackson.databind.JsonDeserializer;

@EnableKafka
@Configuration
public class kafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Object> listenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
		listenerContainerFactory.setConsumerFactory(this.listenerFactory());
		return listenerContainerFactory;
	}

	@Bean
	public ConsumerFactory<String, Object> listenerFactory() {
		Map<String, Object> consumerConfigurations = new HashMap<String, Object>();

		consumerConfigurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		consumerConfigurations.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		consumerConfigurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerConfigurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		consumerConfigurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		return new DefaultKafkaConsumerFactory<>(consumerConfigurations);
	}
}
