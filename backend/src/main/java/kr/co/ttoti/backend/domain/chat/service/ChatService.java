package kr.co.ttoti.backend.domain.chat.service;

public interface ChatService {

	void sendMessageByManitto(Integer ttotiId, Integer senderId, String message);

	void sendMessageByManiti(Integer ttotiId, Integer senderId, String message);
}
