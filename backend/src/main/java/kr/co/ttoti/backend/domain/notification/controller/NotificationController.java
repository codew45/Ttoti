package kr.co.ttoti.backend.domain.notification.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ttoti.backend.global.auth.annotation.MemberId;
import kr.co.ttoti.backend.domain.notification.dto.NotificationDeviceTokenCreateRequest;
import kr.co.ttoti.backend.domain.notification.service.NotificationServiceImpl;
import kr.co.ttoti.backend.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ttoti/notifications")
public class NotificationController {

	private final RedisUtil redisUtil;
	private final NotificationServiceImpl notificationService;

	// 이걸 원래 로그인 controller에서 해야 함
	@PostMapping("/device-token")
	public ResponseEntity<Void> saveFcmToken(@RequestBody NotificationDeviceTokenCreateRequest deviceTokenCreateRequst,
		@MemberId Integer memberId) throws ExecutionException, InterruptedException {
		redisUtil.setDeviceToken(deviceTokenCreateRequst, memberId);
		notificationService.pushNotification(memberId, "알림제목", "알림내용");
		return ResponseEntity.ok(null);
	}
}
