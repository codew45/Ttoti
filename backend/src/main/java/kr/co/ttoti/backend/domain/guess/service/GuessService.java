package kr.co.ttoti.backend.domain.guess.service;

import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.global.auth.entity.Member;

public interface GuessService {

	void insertMidtermGuess(Member member, Room room);

	void insertFinalGuess();

	void updateGuess(Integer memberId, Integer roomId, Integer guessMemberId);
}
