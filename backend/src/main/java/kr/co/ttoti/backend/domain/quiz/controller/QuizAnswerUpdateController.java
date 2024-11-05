package kr.co.ttoti.backend.domain.quiz.controller;

import kr.co.ttoti.backend.global.auth.annotation.MemberId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ttoti.backend.domain.quiz.dto.QuizAnswerUpdateRequest;
import kr.co.ttoti.backend.domain.quiz.service.QuizAnswerUpdateService;
import kr.co.ttoti.backend.global.dto.ResponseDto;
import kr.co.ttoti.backend.global.status.SuccessCode;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ttoti/ttotis")
public class QuizAnswerUpdateController {

	private final QuizAnswerUpdateService quizAnswerUpdateService;

	@PatchMapping("/{ttoti-id}/quiz/{quiz-id}")
	ResponseEntity<ResponseDto<?>> updateQuizAnswer(@MemberId Integer memberId,
		@PathVariable(name = "ttoti-id") Integer ttotiId,
		@PathVariable(name = "quiz-id") Integer quizId,
		@RequestBody QuizAnswerUpdateRequest quizAnswerUpdateRequest) {

		quizAnswerUpdateService.updateQuizAnswer(memberId, ttotiId, quizId, quizAnswerUpdateRequest);

		return ResponseEntity.ok(ResponseDto.success(SuccessCode.QUIZ_ANSWER_SUCCESS));
	}
}
