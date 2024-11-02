package kr.co.ttoti.backend.domain.quiz.service;

import kr.co.ttoti.backend.domain.quiz.entity.Quiz;

public interface QuizInsertService {

	Quiz getRandomQuiz(Integer roomId);

	void insertQuiz(Integer roomId);
}
