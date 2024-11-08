package kr.co.ttoti.backend.domain.chat.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.ttoti.backend.domain.chat.dto.OpenaiRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.domain.animal.entity.Animal;
import kr.co.ttoti.backend.domain.chat.dto.OpenaiResponse;
import kr.co.ttoti.backend.domain.chat.dto.MessageDto;
import kr.co.ttoti.backend.domain.chat.entity.ChatMessage;
import kr.co.ttoti.backend.domain.chat.repository.ChatMessageRepository;
import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;
import kr.co.ttoti.backend.global.auth.entity.Member;
import kr.co.ttoti.backend.global.client.OpenaiClient;
import kr.co.ttoti.backend.global.exception.CustomException;
import kr.co.ttoti.backend.global.status.ErrorCode;
import kr.co.ttoti.backend.global.util.KafkaProducerUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

	@Value("${openai.api.key}")
	private String apiKey;
	private final String model = "gpt-4o-mini";
	private final String role = "user";
	private final String prePrompt = "당신은 사람들과 대화하는 특별한 대화체를 가진 챗봇입니다. 사용자가 글을 남깁니다. 당신의 임무는 사용자가 입력한 글을 다른 대화체로 바꾸는 것입니다. 사용자는 먼저 원하는 대화체로 바꿔달라고 명시할 것이고, 몇가지 예시를 준 뒤에 변경해야 하는 글을 줄 것입니다. 대답할 때 무슨 말투인지 설명하지 마시고 바로 바꾼 말을 하세요.";
	private final OpenaiClient openaiClient;

	private final Validator validator;
	private final KafkaProducerUtil producerUtil;
	private final ChatMessageRepository chatMessageRepository;

	private String convertToAnimalSpeak(Animal animal, String content) {
		List<OpenaiRequest.Message> messages = new ArrayList<>();
		messages.add(OpenaiRequest.Message.builder().role(role).content(prePrompt).build());
		messages.add(OpenaiRequest.Message.builder().role(role).content(animal.getAnimalSpeakInstruction()).build());
		messages.add(OpenaiRequest.Message.builder().role(role).content(content).build());

		OpenaiRequest openaiRequest = OpenaiRequest.builder()
			.model(model)
			.messages(messages)
			.build();

		OpenaiResponse converted = openaiClient.getPrompt("Bearer " + apiKey, openaiRequest);
		if (converted == null || converted.getChoices().isEmpty()
			|| converted.getChoices().get(0).getMessage() == null) {
			throw new CustomException(ErrorCode.AUTHENTICATION_REQUIRED);
		}

		return converted.getChoices().get(0).getMessage().getContent();
	}

	@Transactional
	@Override
	public MessageDto sendMessageByManitto(Integer ttotiId, String message) {
		Ttoti ttoti = validator.validateTtoti(ttotiId);

		Member member = validator.validateMember(ttoti.getMember().getMemberId());

		ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.builder()
			.ttotiId(ttotiId)
			.senderRole("manitto")
			.senderId(member.getMemberId())
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
	public MessageDto sendMessageByManiti(Integer tittoId, String message) {
		Ttoti ttoti = validator.validateTtoti(tittoId);

		Member member = validator.validateMember(ttoti.getManitiId());

		ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.builder()
			.ttotiId(tittoId)
			.senderRole("maniti")
			.senderId(member.getMemberId())
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
				.role(chatMessage.getSenderRole())
				.sendTime(chatMessage.getMessageSendAt())
				.message(chatMessage.getMessageContent())
				.build()).toList();
	}

	@Override
	public List<MessageDto> getMessageByManiti(Integer memberId, Integer tittoId) {
		Member member = validator.validateMember(memberId);

		Ttoti ttoti = validator.validateTtoti(tittoId);

		if (!ttoti.getManitiId().equals(memberId)) {
			throw new CustomException(ErrorCode.AUTHENTICATION_REQUIRED);
		}

		return chatMessageRepository.findByTtotiIdOrderByMessageSendAt(tittoId)
			.stream()
			.map(chatMessage -> MessageDto.builder()
				.role(chatMessage.getSenderRole())
				.sendTime(chatMessage.getMessageSendAt())
				.message(chatMessage.getMessageContent())
				.build()).toList();
	}
}
