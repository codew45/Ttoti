package kr.co.ttoti.backend.domain.guess.service;

import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;

public interface GuessService {

	void insertMidtermGuess(RoomMember member, Room room);

	void insertFinalGuess();

	void updateGuess(Integer memberId, Integer roomId, Integer guessMemberId);
}
