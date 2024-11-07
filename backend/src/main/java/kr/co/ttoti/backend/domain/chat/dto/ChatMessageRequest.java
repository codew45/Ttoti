package kr.co.ttoti.backend.domain.chat.dto;

import lombok.Data;

@Data
public class ChatMessageRequest {

	Integer senderId;

	String message;
}
