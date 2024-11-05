package kr.co.ttoti.backend.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageRequest {

	Integer senderId;

	String message;
}
