package kr.co.ttoti.backend.domain.quiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ttoti.backend.domain.quiz.service.QuizListGetService;
import kr.co.ttoti.backend.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ttoti/ttotis")
public class QuizListGetController {

	private final QuizListGetService quizListGetService;

	@GetMapping("/{ttoti-id}/quiz")
	ResponseEntity<ResponseDto<?>> getQuizList(@RequestHeader Integer memberId,
		@PathVariable("ttoti-id") Integer ttotiId) {

		quizListGetService.getQuizList(memberId, ttotiId);
		return null;
	}
}
