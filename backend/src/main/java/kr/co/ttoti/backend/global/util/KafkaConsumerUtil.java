package kr.co.ttoti.backend.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaConsumerUtil {

    private final SimpMessageSendingOperations messageSending;

    @KafkaListener(topics = "topics", groupId = "#{ T(java.util.UUID).randomUUID().toString() }")
    public String consume(String topic, Integer ttotiId, String message) {
        messageSending.convertAndSend("/chat", message);
        return message;
    }
}
