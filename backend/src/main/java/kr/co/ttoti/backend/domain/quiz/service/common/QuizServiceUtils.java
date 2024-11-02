package kr.co.ttoti.backend.domain.quiz.service.common;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.co.ttoti.backend.domain.quiz.dto.QuizHistoryDto;
import kr.co.ttoti.backend.domain.quiz.entity.QuizAnswer;
import kr.co.ttoti.backend.domain.quiz.entity.QuizChoice;
import kr.co.ttoti.backend.domain.quiz.repository.QuizChoiceRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuizServiceUtils {

	private final QuizChoiceRepository quizChoiceRepository;

	public QuizHistoryDto mapToQuizHistoryDto(QuizAnswer quizAnswer) {
		Map<Integer, String> quizChoiceMap = quizChoiceRepository.findByQuizId(
			quizAnswer.getQuiz().getQuizId()).stream().collect(
			Collectors.toMap(
				QuizChoice::getQuizChoiceNumber,
				QuizChoice::getQuizChoiceContent));

		return QuizHistoryDto.builder()
			.ttotiId(quizAnswer.getTtotiId())
			.quizDate(quizAnswer.getQuizDate())
			.quizChoiceMap(quizChoiceMap)
			.quizChoiceContent(quizAnswer.getQuiz().getQuizContent())
			.quizType(quizAnswer.getQuiz().getQuizType().toString())
			.isManittoAnswered(quizAnswer.getIsManittoAnswered())
			.manittoAnswer(quizAnswer.getManittoAnswer())
			.isManitiAnswered(quizAnswer.getIsManitiAnswered())
			.manitiAnswer(quizAnswer.getManitiAnswer())
			.quizAnswerIsCorrect(quizAnswer.getQuizAnswerIsCorrect())
			.build();
	}
}
