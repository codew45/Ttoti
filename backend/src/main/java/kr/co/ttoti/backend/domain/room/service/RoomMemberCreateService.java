package kr.co.ttoti.backend.domain.room.service;

import kr.co.ttoti.backend.domain.room.entity.RoomMember;

public interface RoomMemberCreateService {

	/**
	 * 방 생성 후, 방장을 방 회원으로 등록한다
	 * @param memberId
	 * @param roomId
	 */
	void createRoomMember(Integer memberId, Integer roomId);

	RoomMember createRoomMember(Integer memberId, Integer roomId, String roomCode);
}
