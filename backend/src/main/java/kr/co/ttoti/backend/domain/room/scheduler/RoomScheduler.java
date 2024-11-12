package kr.co.ttoti.backend.domain.room.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.domain.notification.entity.NotificationType;
import kr.co.ttoti.backend.domain.notification.service.NotificationInsertService;
import kr.co.ttoti.backend.domain.room.common.RoomServiceUtils;
import kr.co.ttoti.backend.domain.ttoti.common.TtotiServiceUtils;
import kr.co.ttoti.backend.domain.ttoti.scheduler.TtotiScheduler;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoomScheduler {

	private final TtotiScheduler ttotiScheduler;
	private final RoomServiceUtils roomServiceUtils;
	private final TtotiServiceUtils ttotiServiceUtils;
	private final NotificationInsertService notificationInsertService;

	@Scheduled(cron = "0 0/30 * * * ?")
	@Transactional
	public void checkFinishedRooms() {

		roomServiceUtils.getInProgressRoomListByFinishDateAndTime().forEach(room -> {
			ttotiScheduler.processRoomTemperatureChanges(room);

			ttotiServiceUtils.finishTtotiChat(room);

			roomServiceUtils.finishInProgressRooms(room);

			notificationInsertService.insertNotificationToAllMembersInRoom(room, NotificationType.GAME_END);
		});
	}
}