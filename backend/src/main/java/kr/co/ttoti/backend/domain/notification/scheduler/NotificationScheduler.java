package kr.co.ttoti.backend.domain.notification.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.ttoti.backend.domain.notification.dto.UnAnsweredMemberDto;
import kr.co.ttoti.backend.domain.notification.entity.NotificationType;
import kr.co.ttoti.backend.domain.notification.service.NotificationInsertService;
import kr.co.ttoti.backend.domain.notification.service.NotificationSendService;
import kr.co.ttoti.backend.domain.room.common.RoomServiceUtils;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;
import kr.co.ttoti.backend.domain.room.repository.RoomMemberRepository;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

	private final RoomMemberRepository roomMemberRepository;
	private final RoomServiceUtils roomServiceUtils;
	private final NotificationSendService notificationSendService;
	private final RoomRepository roomRepository;
	private final NotificationInsertService notificationInsertService;

	@Scheduled(cron = "0 0 10 * * ?")
	// @Scheduled(fixedDelay = 10000)
	public void sendMorningNotifications() {
		List<Room> inProgressRoomList = roomServiceUtils.getInProgressRoomListByFinishDateAndTime();

		for (Room room : inProgressRoomList) {
			List<RoomMember> roomMemberList = roomMemberRepository.findByRoomAndRoomMemberIsDeletedFalse(room);
			// for(RoomMember roomMember : roomMemberList) {
			// 	notificationInsertService.insertNotificationToMemberInRoom(roomMember.getMember().getMemberId(), room.getRoomId(), NotificationType.TODAY_QUIZ_OPENED);
			// }

			sendTodayQuizOpenNotification(roomMemberList);
		}
	}

	@Scheduled(cron = "0 0 20 * * ?")
	// @Scheduled(fixedDelay = 10000)
	public void sendQuizReminder() {
		List<UnAnsweredMemberDto> unAnsweredMemberList = roomRepository.findMemberIdsWithUnansweredQuizzes(
			LocalDate.now());

		for (UnAnsweredMemberDto unAnsweredMemberDto : unAnsweredMemberList) {
			notificationInsertService.insertNotificationToMemberInRoom(unAnsweredMemberDto.getMemberId(),
				unAnsweredMemberDto.getRoomId(),
				NotificationType.QUIZ_ANSWER_REMINDER);
		}

	}

	private void sendTodayQuizOpenNotification(List<RoomMember> roomMemberList) {
		notificationSendService.sendTodayQuizOpenNotification(roomMemberList);
	}

}
