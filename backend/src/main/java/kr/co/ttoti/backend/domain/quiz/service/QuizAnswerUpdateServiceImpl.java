package kr.co.ttoti.backend.domain.quiz.service;

import kr.co.ttoti.backend.global.auth.entity.Member;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.domain.quiz.dto.QuizAnswerUpdateRequest;
import kr.co.ttoti.backend.domain.quiz.entity.Quiz;
import kr.co.ttoti.backend.domain.quiz.entity.QuizAnswer;
import kr.co.ttoti.backend.domain.quiz.repository.QuizAnswerRepository;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;
import kr.co.ttoti.backend.global.exception.CustomException;
import kr.co.ttoti.backend.global.status.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizAnswerUpdateServiceImpl implements QuizAnswerUpdateService {

	private final Validator validator;
	private final QuizAnswerRepository quizAnswerRepository;

	@Override
	public void updateQuizAnswer(Integer memberId, Integer ttotiId, Integer quizId,
		QuizAnswerUpdateRequest quizAnswerUpdateRequest) {

		Member member = validator.validateMember(memberId);
		Ttoti ttoti = validator.validateManittoByTtotiIdAndMember(ttotiId, member);
		Ttoti titto = validator.validateTtoti(ttoti.getTittoId());
		Quiz quiz = validator.validateQuizAvailability(quizId, true);
		Integer quizAnswerNumber = quizAnswerUpdateRequest.getQuizAnswerNumber();

		QuizAnswer manittoQuizAnswer = validator.validateQuizAnswerByTtotiIdAndQuiz(ttotiId, quiz);
		QuizAnswer manitiQuizAnswer = validator.validateQuizAnswerByTtotiIdAndQuiz(titto.getTtotiId(), quiz);

		if (manittoQuizAnswer.getQuizDate().isBefore(LocalDate.now())) {
			throw new CustomException(ErrorCode.QUIZ_EXPIRED);
		}

		manittoQuizAnswer.updateManittoQuizAnswer(quizAnswerNumber);

		manitiQuizAnswer.updateManitiQuizAnswer(quizAnswerNumber);

		checkAnswerIsCorrect(manittoQuizAnswer);
		checkAnswerIsCorrect(manitiQuizAnswer);
	}

	private void checkAnswerIsCorrect(QuizAnswer quizAnswer) {

		Integer manittoAnswerNumber = quizAnswer.getManittoAnswer();
		Integer manitiAnswerNumber = quizAnswer.getManitiAnswer();

		quizAnswer.updateQuizAnswerIsCorrect(manittoAnswerNumber != null && manittoAnswerNumber.equals(
			manitiAnswerNumber));
	}
}