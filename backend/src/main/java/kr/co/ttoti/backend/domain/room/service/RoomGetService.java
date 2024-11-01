package kr.co.ttoti.backend.domain.room.service;

import kr.co.ttoti.backend.domain.room.dto.RoomPendingDto;

public interface RoomGetService {

	/**
	 * 방의 상태값을 반환해주며, 방이 시작되기 전이면 false, 방이 시작된 후 진행중이면 true를 반환한다
	 * @param memberId
	 * @param roomId
	 * @return
	 */
	Boolean getRoomStatus(Integer memberId, Integer roomId);

	/**
	 * 게임 시작 전, 방의 정보를 반환한다
	 * @param memberId
	 * @param roomId
	 * @return
	 */
	RoomPendingDto getRoomIfPending(Integer memberId, Integer roomId);
}
