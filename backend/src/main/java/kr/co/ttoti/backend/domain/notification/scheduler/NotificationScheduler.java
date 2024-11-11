package kr.co.ttoti.backend.domain.notification.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.ttoti.backend.domain.notification.dto.UnAnsweredMemberDto;
import kr.co.ttoti.backend.domain.notification.entity.NotificationType;
import kr.co.ttoti.backend.domain.notification.repository.NotificationRepository;
import kr.co.ttoti.backend.domain.notification.service.NotificationSendService;
import kr.co.ttoti.backend.domain.notification.service.common.NotificationServiceUtils;
import kr.co.ttoti.backend.domain.quiz.repository.QuizAnswerRepository;
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
	private final QuizAnswerRepository quizAnswerRepository;
	private final NotificationSendService notificationSendService;
	private final RoomRepository roomRepository;
	private final NotificationRepository notificationRepository;
	private final NotificationServiceUtils notificationServiceUtils;

	@Scheduled(cron = "0 0 10 * * ?")
	public void sendMorningNotifications() {
		List<Room> inProgressRoomList = roomServiceUtils.getInProgressRoomListByFinishDateAndTime();

		for (Room room : inProgressRoomList) {
			List<RoomMember> roomMemberList = roomMemberRepository.findByRoomAndRoomMemberIsDeletedFalse(room);

			sendTodayQuizOpenNotification(roomMemberList);
		}
	}

	@Scheduled(cron = "0 0 20 * * ?")
	// @Scheduled(fixedDelay = 10000)
	public void sendQuizReminder() {
		List<UnAnsweredMemberDto> unAnsweredMemberList = roomRepository.findMemberIdsWithUnansweredQuizzes(
			LocalDate.now());

		for (UnAnsweredMemberDto unAnsweredMemberDto : unAnsweredMemberList) {
			notificationServiceUtils.sendNotificationToMemberInRoom(unAnsweredMemberDto.getMemberId(),
				unAnsweredMemberDto.getRoomId(),
				NotificationType.QUIZ_ANSWER_REMINDER);
		}

	}

	private void sendTodayQuizOpenNotification(List<RoomMember> roomMemberList) {
		notificationSendService.sendTodayQuizOpenNotification(roomMemberList);
	}

}
