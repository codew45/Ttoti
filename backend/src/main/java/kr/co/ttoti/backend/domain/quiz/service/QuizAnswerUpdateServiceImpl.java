package kr.co.ttoti.backend.domain.quiz.service;

import org.springframework.stereotype.Service;

import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.quiz.dto.QuizAnswerUpdateRequest;
import kr.co.ttoti.backend.domain.quiz.entity.Quiz;
import kr.co.ttoti.backend.domain.quiz.entity.QuizAnswer;
import kr.co.ttoti.backend.domain.quiz.repository.QuizAnswerRepository;
import kr.co.ttoti.backend.domain.ttoti.entity.TemperatureChangeReason;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;
import kr.co.ttoti.backend.domain.ttoti.service.TemperatureInsertService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizAnswerUpdateServiceImpl implements QuizAnswerUpdateService {

	private final Validator validator;
	private final QuizAnswerRepository quizAnswerRepository;
	private final TemperatureInsertService temperatureInsertService;

	@Override
	public void updateQuizAnswer(Integer memberId, Integer ttotiId, Integer quizId,
		QuizAnswerUpdateRequest quizAnswerUpdateRequest) {

		Member member = validator.validateMember(memberId);
		Ttoti ttoti = validator.validateManittoByTtotiIdAndMember(ttotiId, member);
		Ttoti titto = validator.validateTtoti(ttoti.getTittoId());
		Quiz quiz = validator.validateQuizAvailability(quizId, true);

		QuizAnswer manittoQuizAnswer = validator.validateQuizAnswerByTtotiIdAndQuiz(ttotiId, quiz);
		QuizAnswer manitiQuizAnswer = validator.validateQuizAnswerByTtotiIdAndQuiz(titto.getTtotiId(), quiz);

		Integer quizAnswerNumber = quizAnswerUpdateRequest.getQuizAnswerNumber();
		Integer roomPeriod = validator.validateRoom(manittoQuizAnswer.getRoomId()).getRoomPeriod();

		manittoQuizAnswer.updateManittoQuizAnswer(quizAnswerNumber);
		manittoQuizAnswer = quizAnswerRepository.saveAndFlush(manittoQuizAnswer);

		manitiQuizAnswer.updateManitiQuizAnswer(quizAnswerNumber);
		manitiQuizAnswer = quizAnswerRepository.saveAndFlush(manitiQuizAnswer);

		if (ttoti.getMember().getMemberId().equals(memberId)) {
			temperatureInsertService.calculateTemperatureIncrease(
				ttoti.getTtotiId(),
				ttoti.getTtotiTemperature(),
				TemperatureChangeReason.QUIZ_ANSWER,
				roomPeriod
			);
		}

		handleCorrectAnswer(manittoQuizAnswer, roomPeriod, ttoti);
		handleCorrectAnswer(manitiQuizAnswer, roomPeriod, titto);
	}

	private void handleCorrectAnswer(QuizAnswer quizAnswer, Integer roomPeriod, Ttoti ttoti) {

		Integer manittoAnswerNumber = quizAnswer.getManittoAnswer();
		Integer manitiAnswerNumber = quizAnswer.getManitiAnswer();

		if (manittoAnswerNumber != null && manittoAnswerNumber.equals(
			manitiAnswerNumber)) {

			quizAnswer.updateQuizAnswerIsCorrect();
			quizAnswerRepository.saveAndFlush(quizAnswer);

			temperatureInsertService.calculateTemperatureIncrease(
				ttoti.getTtotiId(),
				ttoti.getTtotiTemperature(),
				TemperatureChangeReason.QUIZ_ANSWER_CORRECT,
				roomPeriod
			);
		}
	}
}