package kr.co.ttoti.backend.global.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.fasterxml.jackson.databind.ser.std.ByteArraySerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;

@Configuration
public class KafkaByteArrayProducerConfig {

	@Value("{spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public KafkaTemplate<String, byte[]> kafkaByteArrayTemplate() {
		return new KafkaTemplate<>(this.byteArrayProducerFactory());
	}

	@Bean
	public ProducerFactory<String, byte[]> byteArrayProducerFactory() {
		return new DefaultKafkaProducerFactory<>(this.byteArrayProducerConfig());
	}

	@Bean
	public Map<String, Object> byteArrayProducerConfig() {
		Map<String, Object> producerConfigs = new HashMap<>();
		producerConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		producerConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producerConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
			ByteArraySerializer.class); // or JsonSerializer for Object
		return producerConfigs;

	}
}
