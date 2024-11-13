package kr.co.ttoti.backend.domain.notification.scheduler;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.domain.notification.dto.UnAnsweredMemberDto;
import kr.co.ttoti.backend.domain.notification.entity.NotificationType;
import kr.co.ttoti.backend.domain.notification.service.NotificationInsertService;
import kr.co.ttoti.backend.domain.room.common.RoomServiceUtils;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import kr.co.ttoti.backend.global.fcm.service.FCMSendService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

	private final RoomServiceUtils roomServiceUtils;
	private final RoomRepository roomRepository;
	private final NotificationInsertService notificationInsertService;
	private final FCMSendService fcmSendService;

	@Scheduled(cron = "0 5 10 * * ?")
	@Transactional
	public void processMorningNotifications() {
		roomServiceUtils.getInProgressRoomList().forEach(
			room -> notificationInsertService.insertNotificationToAllMembersInRoom(room,
				NotificationType.TODAY_QUIZ_OPENED));

		roomRepository.findMemberIdsWithActiveGames().forEach(
			memberId -> {
				try {
					fcmSendService.sendToFCM(memberId, NotificationType.TODAY_QUIZ_OPENED);
				} catch (ExecutionException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		);
	}

	@Scheduled(cron = "0 5 20 * * ?")
	public void processQuizReminder() throws ExecutionException, InterruptedException {
		List<UnAnsweredMemberDto> unAnsweredMemberList = roomRepository.findMemberIdsWithUnansweredQuizzes(
			LocalDate.now());

		for (UnAnsweredMemberDto unAnsweredMemberDto : unAnsweredMemberList) {
			notificationInsertService.insertNotificationToMemberInRoom(unAnsweredMemberDto.getMemberId(),
				unAnsweredMemberDto.getRoomId(),
				NotificationType.QUIZ_ANSWER_REMINDER);

			fcmSendService.sendToFCMWithRoomName(unAnsweredMemberDto.getMemberId(),
				NotificationType.QUIZ_ANSWER_REMINDER, unAnsweredMemberDto.getRoomName());
		}
	}

}
