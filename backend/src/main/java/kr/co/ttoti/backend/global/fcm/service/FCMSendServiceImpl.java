package kr.co.ttoti.backend.global.fcm.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushFcmOptions;
import com.google.firebase.messaging.WebpushNotification;

import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.domain.notification.entity.NotificationType;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;
import kr.co.ttoti.backend.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FCMSendServiceImpl implements FCMSendService {

	private final RedisUtil redisUtil;
	private final Validator validator;

	private final String redisFCMTokenKey = "fcm_token:";

	public void sendToFCM(Integer memberId, NotificationType notificationType) throws
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
				.setNotification(new WebpushNotification(notificationType.getTitle(), notificationType.getContent()))
				.setFcmOptions(WebpushFcmOptions.withLink("www.naver.com"))
				.build())
			.build();
		FirebaseMessaging.getInstance().sendAsync(message).get();
	}

	public void sendGameStartToFCM(List<RoomMember> roomMemberList) {
		roomMemberList.forEach(roomMember -> {
			try {
				sendToFCM(
					roomMember.getMember().getMemberId(),
					NotificationType.GAME_START
				);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void sendTodayQuizOpenToFCM(List<RoomMember> roomMemberList) {
		roomMemberList.forEach(roomMember -> {
			try {
				sendToFCM(
					roomMember.getMember().getMemberId(),
					NotificationType.TODAY_QUIZ_OPENED
				);
			} catch (ExecutionException | InterruptedException e) {
			}
		});
	}
}
