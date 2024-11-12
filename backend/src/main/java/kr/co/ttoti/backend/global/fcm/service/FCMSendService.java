package kr.co.ttoti.backend.global.fcm.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import kr.co.ttoti.backend.domain.notification.entity.NotificationType;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;

public interface FCMSendService {

	void sendToFCM(Integer memberId, NotificationType notificationType) throws
		ExecutionException,
		InterruptedException;

	void sendToRoomMembers(List<RoomMember> roomMemberList, NotificationType notificationType);
}