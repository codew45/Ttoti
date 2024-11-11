package kr.co.ttoti.backend.domain.notification.service;

import kr.co.ttoti.backend.domain.notification.entity.NotificationType;

public interface NotificationInsertService {

	void insertNotificationToMemberInRoom(Integer memberId, Integer roomId, NotificationType notificationType);
}
