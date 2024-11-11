package kr.co.ttoti.backend.domain.notification.service.common;

import org.springframework.stereotype.Component;

import kr.co.ttoti.backend.domain.notification.entity.Notification;
import kr.co.ttoti.backend.domain.notification.entity.NotificationType;
import kr.co.ttoti.backend.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationServiceUtils {

	private final NotificationRepository notificationRepository;

	public void sendNotificationToMemberInRoom(Integer memberId, Integer roomId, NotificationType notificationType) {
		notificationRepository.save(Notification.builder()
			.memberId(memberId)
			.roomId(roomId)
			.notificationTitle(notificationType.getTitle())
			.notificationReason(notificationType.getReason())
			.notificationIsRead(false)
			.build());
	}
}
