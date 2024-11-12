package kr.co.ttoti.backend.global.fcm.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ttoti.backend.domain.notification.entity.NotificationType;
import kr.co.ttoti.backend.global.auth.annotation.MemberId;
import kr.co.ttoti.backend.global.fcm.dto.FCMDeviceTokenCreateRequest;
import kr.co.ttoti.backend.global.fcm.service.FCMSendService;
import kr.co.ttoti.backend.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ttoti/fcm")
public class FCMController {

	private final RedisUtil redisUtil;
	private final FCMSendService fcmSendService;

	@PostMapping("/device-token")
	public ResponseEntity<Void> saveFCMToken(@RequestBody FCMDeviceTokenCreateRequest fcmDeviceTokenCreateRequest,
		@MemberId Integer memberId) throws ExecutionException, InterruptedException {
		redisUtil.setDeviceToken(fcmDeviceTokenCreateRequest, memberId);
		System.out.println(fcmDeviceTokenCreateRequest.getDeviceToken());
		// 링크 이동 되나 테스트
		fcmSendService.sendToFCM(memberId, NotificationType.FINAL_GUESS_ANSWER_REMINDER);
		return ResponseEntity.ok(null);
	}
}
