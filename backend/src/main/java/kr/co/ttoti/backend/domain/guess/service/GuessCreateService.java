package kr.co.ttoti.backend.domain.guess.service;

import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;

public interface GuessCreateService {

	void insertMidtermGuess(RoomMember member, Room room);

	void insertFinalGuess();

}
