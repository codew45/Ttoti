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
    private final OpenaiClient openaiClient;

    private final Validator validator;
    private final KafkaProducerUtil producerUtil;
    private final ChatMessageRepository chatMessageRepository;

    // 동물 말투로 변환하기
    private String convertToAnimalSpeak(Animal animal, String content) {
		List<OpenaiRequest.Message> messages = new ArrayList<>();
		messages.add(OpenaiRequest.Message.builder().role(role).content(content).build());
        OpenaiRequest openaiRequest = OpenaiRequest.builder()
                .model(model)
                .messages(messages)
                .build();

        OpenaiResponse converted = openaiClient.getPrompt("Bearer " + apiKey, openaiRequest);
        if (converted == null || converted.getChoices().isEmpty() || converted.getChoices().get(0) == null) {
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
                .senderRole("manitti")
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
