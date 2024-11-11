package kr.co.ttoti.backend.domain.notification.service;

import org.springframework.stereotype.Service;

import kr.co.ttoti.backend.domain.notification.entity.Notification;
import kr.co.ttoti.backend.domain.notification.entity.NotificationType;
import kr.co.ttoti.backend.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationInsertServiceImpl implements NotificationInsertService {

	private final NotificationRepository notificationRepository;

	public void insertNotificationToMemberInRoom(Integer memberId, Integer roomId, NotificationType notificationType) {
		notificationRepository.save(Notification.builder()
			.memberId(memberId)
			.roomId(roomId)
			.notificationTitle(notificationType.getTitle())
			.notificationReason(notificationType.getReason())
			.notificationIsRead(false)
			.build());
	}
}
