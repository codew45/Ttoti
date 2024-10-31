package kr.co.ttoti.backend.domain.quiz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ttoti.backend.domain.quiz.dto.QuizListGetDto;
import kr.co.ttoti.backend.domain.quiz.service.QuizListGetService;
import kr.co.ttoti.backend.global.dto.ResponseDto;
import kr.co.ttoti.backend.global.status.SuccessCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ttoti/ttotis")
public class QuizListGetController {

	private final QuizListGetService quizListGetService;

	@GetMapping("/{ttoti-id}/quiz")
	ResponseEntity<ResponseDto<?>> getQuizList(@RequestHeader Integer memberId,
		@PathVariable("ttoti-id") Integer ttotiId) {

		QuizListGetDto result = quizListGetService.getQuizList(memberId, ttotiId);

		if(result == null){
			return ResponseEntity.ok(ResponseDto.success(SuccessCode.QUIZ_LIST_GET_SUCCESS_NO_CONTENT));
		}

		return ResponseEntity.ok(ResponseDto.success(
			SuccessCode.QUIZ_LIST_GET_SUCCESS, result));
	}
}
