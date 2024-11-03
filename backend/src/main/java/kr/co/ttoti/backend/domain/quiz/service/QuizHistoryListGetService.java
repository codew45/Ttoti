package kr.co.ttoti.backend.domain.quiz.service;

import kr.co.ttoti.backend.domain.quiz.dto.QuizHistoryListGetDto;

public interface QuizHistoryListGetService {

	QuizHistoryListGetDto getQuizHistoryList(Integer memberId, Integer ttotiId);
}
