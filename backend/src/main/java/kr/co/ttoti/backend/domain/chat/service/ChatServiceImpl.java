package kr.co.ttoti.backend.domain.chat.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.domain.animal.entity.Animal;
import kr.co.ttoti.backend.domain.chat.dto.MessageDto;
import kr.co.ttoti.backend.domain.chat.entity.ChatMessage;
import kr.co.ttoti.backend.domain.chat.repository.ChatMessageRepository;
import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;
import kr.co.ttoti.backend.global.auth.entity.Member;
import kr.co.ttoti.backend.global.exception.CustomException;
import kr.co.ttoti.backend.global.status.ErrorCode;
import kr.co.ttoti.backend.global.util.KafkaProducerUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

	private final Validator validator;
	private final KafkaProducerUtil producerUtil;

	private final ChatMessageRepository chatMessageRepository;

	// 동물 말투로 변환하기
	private String convertToAnimalSpeak(Animal animal, String Cotent) {

		return "";
	}

	@Transactional
	@Override
	public MessageDto sendMessageByManitto(Integer ttotiId, Integer senderId, String message) {
		Member member = validator.validateMember(senderId);

		Ttoti ttoti = validator.validateTtoti(ttotiId);

		if (!ttoti.getMember().equals(member)) {
			throw new CustomException(ErrorCode.AUTHENTICATION_REQUIRED);
		}

		ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.builder().ttotiId(ttotiId)
			.senderId(ttotiId)
			.messageSendAt(LocalDateTime.now())
			.messageContent(convertToAnimalSpeak(ttoti.getAnimal(), message))
			.messageIsRead(Boolean.FALSE)
			.build());

		producerUtil.sendMessage(ttotiId.toString(), message);

		return MessageDto.builder()
			.role("manitto")
			.sendTime(chatMessage.getMessageSendAt())
			.message(chatMessage.getMessageContent())
			.build();
	}

	@Transactional
	@Override
	public MessageDto sendMessageByManiti(Integer tittoId, Integer senderId, String message) {
		// 해당 멤버가 존재하는지 확인
		Member member = validator.validateMember(senderId);
		// 해당 멤버가 해당 또띠의 마니띠인지 확인
		Ttoti ttoti = validator.validateTtoti(tittoId);

		if (!ttoti.getManitiId().equals(senderId)) {
			throw new CustomException(ErrorCode.AUTHENTICATION_REQUIRED);
		}

		ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.builder().ttotiId(tittoId)
			.senderId(tittoId)
			.messageSendAt(LocalDateTime.now())
			.messageContent(message)
			.messageIsRead(Boolean.FALSE)
			.build());

		producerUtil.sendMessage(tittoId.toString(), message);

		return MessageDto.builder()
			.role("maniti")
			.sendTime(chatMessage.getMessageSendAt())
			.message(chatMessage.getMessageContent())
			.build();
	}

	@Override
	public List<MessageDto> getMessageByManitto(Integer memberId, Integer ttotiId) {
		Member member = validator.validateMember(memberId);

		Ttoti ttoti = validator.validateTtoti(ttotiId);

		if (!ttoti.getMember().equals(member)) {
			throw new CustomException(ErrorCode.AUTHENTICATION_REQUIRED);
		}

		return chatMessageRepository.findByTtotiIdOrderByMessageSendAt(ttotiId)
			.stream()
			.map(chatMessage -> MessageDto.builder()
				.role(ttoti.getMember().equals(member) ? "manitto" : "maniti")
				.sendTime(chatMessage.getMessageSendAt())
				.message(chatMessage.getMessageContent())
				.build()).toList();
	}

	@Override
	public List<MessageDto> getMessageByManiti(Integer memberId, Integer tittoId) {
		// 해당 멤버가 존재하는지 확인
		Member member = validator.validateMember(memberId);
		// 해당 멤버가 해당 또띠의 마니띠인지 확인
		Ttoti ttoti = validator.validateTtoti(tittoId);

		if (!ttoti.getManitiId().equals(memberId)) {
			throw new CustomException(ErrorCode.AUTHENTICATION_REQUIRED);
		}

		return chatMessageRepository.findByTtotiIdOrderByMessageSendAt(tittoId)
			.stream()
			.map(chatMessage -> MessageDto.builder()
				.role(ttoti.getManitiId().equals(memberId) ? "maniti" : "manitto")
				.sendTime(chatMessage.getMessageSendAt())
				.message(chatMessage.getMessageContent())
				.build()).toList();
	}
}
