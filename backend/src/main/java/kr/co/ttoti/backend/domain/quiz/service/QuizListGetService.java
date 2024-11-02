package kr.co.ttoti.backend.domain.quiz.service;

import kr.co.ttoti.backend.domain.quiz.dto.QuizListGetResponse;

public interface QuizListGetService {

	QuizListGetResponse getQuizList(Integer memberId, Integer ttotiId);
}
