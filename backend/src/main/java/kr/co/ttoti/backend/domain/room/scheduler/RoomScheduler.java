package kr.co.ttoti.backend.domain.room.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

	@Scheduled(cron = "0 0/30 * * * ?")
	public void checkFinishedRooms() {

		roomServiceUtils.getInProgressRoomListByFinishDateAndTime().forEach(room -> {
			ttotiScheduler.processRoomTemperatureChanges(room);

			ttotiServiceUtils.finishTtotiChat(room);

			roomServiceUtils.finishInProgressRooms(room);

		});
	}
}