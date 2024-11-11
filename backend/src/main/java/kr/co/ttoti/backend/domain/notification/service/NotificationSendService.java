package kr.co.ttoti.backend.domain.notification.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import kr.co.ttoti.backend.domain.notification.entity.NotificationType;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;

public interface NotificationSendService {

	void sendNotification(Integer memberId, NotificationType notificationType) throws
		ExecutionException,
		InterruptedException;

	void sendGameStartNotification(List<RoomMember> roomMemberList);

	void sendTodayQuizOpenNotification(List<RoomMember> roomMemberList);
}