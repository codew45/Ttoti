package kr.co.ttoti.backend.domain.room.common;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoomServiceUtils {

	private final RoomRepository roomRepository;

	public List<Room> getInProgressRoomList(){
		return roomRepository.findByRoomIsDeletedFalseAndRoomIsStartedTrueAndRoomIsFinishedFalse();
	}

	public List<Room> getInProgressRoomListByFinishDateAndTime() {
		return roomRepository.findByRoomIsDeletedFalseAndRoomIsStartedTrueAndRoomIsFinishedFalseAndRoomFinishDateLessThanEqualAndRoomFinishTimeLessThanEqual(
			LocalDate.now(), LocalTime.now());
	}

	@Transactional
	public void finishInProgressRooms(Room room){
		room.finishRoom();
	}

}
