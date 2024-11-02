package kr.co.ttoti.backend.domain.room.dto;

import kr.co.ttoti.backend.domain.quiz.dto.QuizHistoryDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomStartResponse {

	TtotiMatchDto ttotiMatchInfo;

	QuizHistoryDto todayManittoQuiz;
	QuizHistoryDto todayManitiQuiz;
}
