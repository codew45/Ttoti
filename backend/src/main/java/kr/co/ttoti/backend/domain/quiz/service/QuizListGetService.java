package kr.co.ttoti.backend.domain.quiz.service;

import kr.co.ttoti.backend.domain.quiz.dto.QuizListGetDto;

public interface QuizListGetService {

	QuizListGetDto getQuizList(Integer memberId, Integer ttotiId);
}
