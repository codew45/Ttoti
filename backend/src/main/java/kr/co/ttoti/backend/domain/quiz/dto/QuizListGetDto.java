package kr.co.ttoti.backend.domain.quiz.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class QuizListGetDto {

	List<QuizHistoryDto> quizHistoryDtoList;
	QuizHistoryDto todayQuiz;
}
