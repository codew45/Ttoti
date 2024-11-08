package kr.co.ttoti.backend.domain.notification.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;

import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final RedisUtil redisUtil;
	private final Validator validator;

	private final String redisFCMTokenKey = "fcm_token:";
	private final Long redisFCMTokenPeriod = 3L;

	// FCM에게 알림 요청 사용자에게 푸시 알림 보내기
	public void pushNotification(Integer memberId, String title, String content) throws
		ExecutionException,
		InterruptedException {

		validator.validateMember(memberId);
		String fcmTokenKey = redisFCMTokenKey + memberId.toString();
		String token = redisUtil.getData(fcmTokenKey);

		if (token == null) {
			return;
		}
		Message message = Message.builder()
			.setToken(token)
			.setWebpushConfig(WebpushConfig.builder()
				.putHeader("ttl", "300")
				.setNotification(new WebpushNotification(title, content))
				.build())
			.build();
		String response = FirebaseMessaging.getInstance().sendAsync(message).get();
	}
}
