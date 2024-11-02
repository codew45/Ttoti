package kr.co.ttoti.backend.domain.quiz.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.domain.quiz.dto.QuizHistoryDto;
import kr.co.ttoti.backend.domain.quiz.dto.QuizListGetResponse;
import kr.co.ttoti.backend.domain.quiz.entity.QuizAnswer;
import kr.co.ttoti.backend.domain.quiz.repository.QuizAnswerRepository;
import kr.co.ttoti.backend.domain.quiz.service.common.QuizServiceUtils;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;
import kr.co.ttoti.backend.global.exception.CustomException;
import kr.co.ttoti.backend.global.status.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuizListGetServiceImpl implements QuizListGetService {

	private final QuizAnswerRepository quizAnswerRepository;
	private final QuizServiceUtils quizServiceUtils;
	private final Validator validator;

	private QuizHistoryDto extractTodayQuiz(List<QuizHistoryDto> quizHistoryDtoList) {

		QuizHistoryDto todayQuiz = quizHistoryDtoList.getFirst();
		if (!todayQuiz.getQuizDate().isBefore(LocalDate.now())) {
			quizHistoryDtoList.removeFirst();
			return todayQuiz;
		}
		return null;
	}

	@Override
	public QuizListGetResponse getQuizList(Integer memberId, Integer ttotiId) {

		validator.validateMember(memberId);
		Ttoti ttoti = validator.validateTtoti(ttotiId);
		Ttoti titto = validator.validateTtoti(ttoti.getTittoId());

		List<QuizAnswer> manittoQuizAnswerList = quizAnswerRepository.findByTtotiIdOrderByQuizDateDesc(ttotiId);
		List<QuizAnswer> manitiQuizAnswerList = quizAnswerRepository.findByTtotiIdOrderByQuizDateDesc(
			titto.getTtotiId());

		if (manittoQuizAnswerList.isEmpty() || manitiQuizAnswerList.isEmpty()) {
			throw new CustomException(ErrorCode.QUIZ_LIST_NOT_FOUND);
		}

		List<QuizHistoryDto> manittoQuizHistoryDtoList = new LinkedList<>(manittoQuizAnswerList.stream()
			.map(quizServiceUtils::mapToQuizHistoryDto)
			.toList());
		List<QuizHistoryDto> manitiQuizHistoryDtoList = new LinkedList<>(manitiQuizAnswerList.stream()
			.map(quizServiceUtils::mapToQuizHistoryDto)
			.toList());

		QuizHistoryDto todayManittoQuiz = extractTodayQuiz(manittoQuizHistoryDtoList);
		QuizHistoryDto todayManitiQuiz = extractTodayQuiz(manitiQuizHistoryDtoList);

		return QuizListGetResponse.builder()
			.manittoQuizList(manittoQuizHistoryDtoList)
			.manitiQuizList(manitiQuizHistoryDtoList)
			.todayManittoQuiz(todayManittoQuiz)
			.todayManitiQuiz(todayManitiQuiz)
			.build();
	}
}
