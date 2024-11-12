package kr.co.ttoti.backend.domain.notification.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ttoti.backend.domain.notification.entity.NotificationType;
import kr.co.ttoti.backend.global.auth.annotation.MemberId;
import kr.co.ttoti.backend.domain.notification.dto.NotificationDeviceTokenCreateRequest;
import kr.co.ttoti.backend.domain.notification.service.NotificationSendServiceImpl;
import kr.co.ttoti.backend.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ttoti/notifications")
public class FCMController {

	private final RedisUtil redisUtil;
	private final NotificationSendServiceImpl notificationService;

	// 테스트용으로 쓰는 것이삼
	@PostMapping("/device-token")
	public ResponseEntity<Void> saveFcmToken(@RequestBody NotificationDeviceTokenCreateRequest deviceTokenCreateRequst,
		@MemberId Integer memberId) throws ExecutionException, InterruptedException {
		redisUtil.setDeviceToken(deviceTokenCreateRequst, memberId);
		System.out.println(deviceTokenCreateRequst.getDeviceToken());
		// notificationService.sendNotification(memberId, NotificationType.FINAL_MANITTO_GUESS_OPENED);
		return ResponseEntity.ok(null);
	}
}
