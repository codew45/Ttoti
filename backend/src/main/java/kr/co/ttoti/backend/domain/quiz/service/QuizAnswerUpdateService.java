package kr.co.ttoti.backend.domain.quiz.service;

import kr.co.ttoti.backend.domain.quiz.dto.QuizAnswerUpdateRequest;

public interface QuizAnswerUpdateService {

	void updateQuizAnswer(Integer memberId, Integer ttotiId, Integer quizId, QuizAnswerUpdateRequest quizAnswerUpdateRequest);
}
