package kr.co.ttoti.backend.domain.chat.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import kr.co.ttoti.backend.domain.animal.entity.Animal;
import kr.co.ttoti.backend.domain.chat.entity.ChatMessage;
import kr.co.ttoti.backend.domain.chat.repository.ChatMessageRepository;
import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.member.repository.MemberRepository;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;
import kr.co.ttoti.backend.domain.room.repository.RoomMemberRepository;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;
import kr.co.ttoti.backend.domain.ttoti.repository.TtotiRepository;
import kr.co.ttoti.backend.global.exception.CustomException;
import kr.co.ttoti.backend.global.status.ErrorCode;
import kr.co.ttoti.backend.global.util.KafkaConsumerUtil;
import kr.co.ttoti.backend.global.util.KafkaProducerUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

	private final Validator validator;
	private final KafkaProducerUtil producerUtil;
	private final KafkaConsumerUtil consumerUtil;

	private final ChatMessageRepository chatMessageRepository;

	// 동물 말투로 변환하기
	private String convertToAnimalSpeak(Animal animal, String Cotent) {

		return "";
	}

	@Override
	public void sendMessageByManitto(Integer ttotiId, Integer senderId, String message) {
		Member member = validator.validateMember(senderId);

		Ttoti ttoti = validator.validateTtoti(ttotiId);

		if (!ttoti.getMember().equals(member)) {
			throw new CustomException(ErrorCode.AUTHENTICATION_REQUIRED);
		}

		chatMessageRepository.save(ChatMessage.builder().ttotiId(ttotiId)
			.senderId(ttotiId)
			.messageSendAt(LocalDateTime.now())
			.messageContent(convertToAnimalSpeak(ttoti.getAnimal(), message))
			.messageIsRead(Boolean.FALSE)
			.build());

		producerUtil.sendMessage(ttotiId.toString(), message);
	}

	@Override
	public void sendMessageByManiti(Integer ttotiId, Integer senderId, String message) {
		Member member = validator.validateMember(senderId);

		Ttoti ttoti = validator.validateTtoti(ttotiId);

		if (!ttoti.getMember().equals(member)) {
			throw new CustomException(ErrorCode.AUTHENTICATION_REQUIRED);
		}

		chatMessageRepository.save(ChatMessage.builder().ttotiId(ttotiId)
			.senderId(ttotiId)
			.messageSendAt(LocalDateTime.now())
			.messageContent(message)
			.messageIsRead(Boolean.FALSE)
			.build());

		producerUtil.sendMessage(ttotiId.toString(), message);
	}
}
