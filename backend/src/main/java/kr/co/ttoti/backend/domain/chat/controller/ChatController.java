package kr.co.ttoti.backend.domain.chat.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ttoti.backend.domain.chat.dto.ChatMessageRequest;
import kr.co.ttoti.backend.domain.chat.service.ChatService;
import kr.co.ttoti.backend.global.util.KafkaProducerUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ttoti/chats")
public class ChatController {

	private final KafkaProducerUtil kafkaProducer;
	private final ChatService chatService;

	@MessageMapping("/maintto")
	public void sendMessageByManitto(@DestinationVariable Integer ttotiId, @Payload ChatMessageRequest chatMessageRequest) throws Exception {
		chatService.sendMessageByManitto(
			ttotiId, chatMessageRequest.getSenderId(), chatMessageRequest.getMessage());
	}

	@MessageMapping("/mainti")
	public void sendMessageByManiti(@DestinationVariable Integer ttotiId, @Payload ChatMessageRequest chatMessageRequest) {
		chatService.sendMessageByManiti(
			ttotiId, chatMessageRequest.getSenderId(), chatMessageRequest.getMessage());
	}
}
