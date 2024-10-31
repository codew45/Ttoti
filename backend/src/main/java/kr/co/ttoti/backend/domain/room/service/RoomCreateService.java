package kr.co.ttoti.backend.domain.room.service;

import kr.co.ttoti.backend.domain.room.dto.RoomCreateRequest;

public interface RoomCreateService {

	/**
	 * 회원은 방의 이름, 방의 인원, 방의 기간, 방 종료 시간을 설정해 방을 생성할 수 있다
	 * @param memberId
	 * @param roomCreateRequest
	 */
	Integer createRoom(Integer memberId, RoomCreateRequest roomCreateRequest);
}
