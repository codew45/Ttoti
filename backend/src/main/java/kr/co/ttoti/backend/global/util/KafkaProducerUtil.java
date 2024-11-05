package kr.co.ttoti.backend.global.util;

import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaProducerUtil {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String Topic, String message) {
        this.kafkaTemplate.send(Topic, message);
    }
}
