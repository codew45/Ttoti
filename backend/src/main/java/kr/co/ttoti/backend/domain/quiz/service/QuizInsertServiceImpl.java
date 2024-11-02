package kr.co.ttoti.backend.domain.quiz.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.domain.quiz.entity.Quiz;
import kr.co.ttoti.backend.domain.quiz.entity.QuizAnswer;
import kr.co.ttoti.backend.domain.quiz.repository.QuizAnswerRepository;
import kr.co.ttoti.backend.domain.quiz.repository.QuizRepository;
import kr.co.ttoti.backend.domain.quiz.service.common.QuizServiceUtils;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;
import kr.co.ttoti.backend.domain.ttoti.repository.TtotiRepository;
import kr.co.ttoti.backend.global.exception.CustomException;
import kr.co.ttoti.backend.global.status.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizInsertServiceImpl implements QuizInsertService {

	private final QuizRepository quizRepository;
	private final QuizAnswerRepository quizAnswerRepository;
	private final QuizServiceUtils quizServiceUtils;
	private final Validator validator;
	private final TtotiRepository ttotiRepository;

	public Quiz getRandomQuiz(Integer roomId) {

		List<Integer> answeredQuizIdList = quizAnswerRepository.findByRoomId(roomId).stream()
			.map(quizAnswer -> quizAnswer.getQuiz().getQuizId())
			.toList();
		if (answeredQuizIdList.isEmpty()) {
			List<Quiz> allQuizList = quizRepository.findByQuizIsAvailable(true);
			return allQuizList.get(new java.util.Random().nextInt(allQuizList.size()));
		}

		List<Quiz> unAnsweredQuizList = quizRepository.findByQuizIdNotIn(answeredQuizIdList);
		if (unAnsweredQuizList.isEmpty()) {
			throw new CustomException(ErrorCode.UNANSWERED_QUIZ_NOT_FOUND);
		}

		return unAnsweredQuizList.get(new java.util.Random().nextInt(unAnsweredQuizList.size()));
	}

	@Transactional
	public void insertQuiz(Integer roomId) {

		Room room = validator.validateRoom(roomId);

		Quiz quiz = getRandomQuiz(roomId);
		List<Ttoti> ttotiList = ttotiRepository.findByRoom(room);

		for (Ttoti ttoti : ttotiList) {
			quizAnswerRepository.save(QuizAnswer.builder()
				.ttotiId(ttoti.getTtotiId())
				.roomId(roomId)
				.quiz(quiz)
				.isManittoAnswered(false)
				.isManitiAnswered(false)
				.quizDate(LocalDate.now())
				.quizAnswerIsCorrect(false)
				.build());
		}
	}
}
