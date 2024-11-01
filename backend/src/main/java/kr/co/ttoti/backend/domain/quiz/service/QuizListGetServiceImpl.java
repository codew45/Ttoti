package kr.co.ttoti.backend.domain.quiz.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.co.ttoti.backend.domain.member.repository.MemberRepository;
import kr.co.ttoti.backend.domain.quiz.dto.QuizHistoryDto;
import kr.co.ttoti.backend.domain.quiz.dto.QuizListGetDto;
import kr.co.ttoti.backend.domain.quiz.entity.QuizAnswer;
import kr.co.ttoti.backend.domain.quiz.entity.QuizChoice;
import kr.co.ttoti.backend.domain.quiz.repository.QuizAnswerRepository;
import kr.co.ttoti.backend.domain.quiz.repository.QuizChoiceRepository;
import kr.co.ttoti.backend.domain.ttoti.repository.TtotiRepository;
import kr.co.ttoti.backend.global.status.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuizListGetServiceImpl implements QuizListGetService {

	private final QuizAnswerRepository quizAnswerRepository;
	private final QuizChoiceRepository quizChoiceRepository;
	private final MemberRepository memberRepository;
	private final TtotiRepository ttotiRepository;

	private void validateMemberAndTtoti(Integer memberId, Integer ttotiId) {
		memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException(
				ErrorCode.MEMBER_NOT_FOUND.getMessage() + " Member ID: " + memberId));

		ttotiRepository.findById(ttotiId)
			.orElseThrow(
				() -> new IllegalArgumentException(ErrorCode.TTOTI_NOT_FOUND.getMessage() + " Ttoti ID " + ttotiId));
	}

	private QuizHistoryDto extractTodayQuiz(List<QuizHistoryDto> quizHistoryDtoList) {
		QuizHistoryDto todayQuiz = quizHistoryDtoList.getLast();
		if (!todayQuiz.getQuizDate().isBefore(LocalDate.now())) {
			quizHistoryDtoList.removeLast();
			return todayQuiz;
		}
		return null;
	}

	private QuizHistoryDto mapToQuizHistoryDto(QuizAnswer quizAnswer) {
		Map<Integer, String> quizChoiceMap = quizChoiceRepository.findByQuizId(
			quizAnswer.getQuiz().getQuizId()).stream().collect(
			Collectors.toMap(
				QuizChoice::getQuizChoiceNumber,
				QuizChoice::getQuizChoiceContent));

		return QuizHistoryDto.builder()
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

	@Override
	public QuizListGetDto getQuizList(Integer memberId, Integer ttotiId) {
		validateMemberAndTtoti(memberId, ttotiId);

		List<QuizAnswer> quizAnswerList = quizAnswerRepository.findByTtotiIdOrderByQuizDateAsc(ttotiId);

		if (quizAnswerList.isEmpty()) {
			return null;
		}

		List<QuizHistoryDto> quizHistoryDtoList = new LinkedList<>(quizAnswerList.stream()
			.map(this::mapToQuizHistoryDto)
			.toList());

		QuizHistoryDto todayQuiz = extractTodayQuiz(quizHistoryDtoList);

		return QuizListGetDto.builder()
			.quizHistoryDtoList(quizHistoryDtoList)
			.todayQuiz(todayQuiz)
			.build();
	}
}
