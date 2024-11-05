package kr.co.ttoti.backend.global.config;

import com.fasterxml.jackson.databind.JsonSerializer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(this.kafkaProducerFactory());
	}

	@Bean
	public ProducerFactory<String, Object> kafkaProducerFactory() {
		return new DefaultKafkaProducerFactory<>(this.producerFactory());
	}

	@Bean
	public Map<String, Object> producerFactory() {
		Map<String, Object> producerConfigurations = new HashMap<>();
		producerConfigurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		producerConfigurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producerConfigurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return producerConfigurations;
	}

}
