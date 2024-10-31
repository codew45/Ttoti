package kr.co.ttoti.backend.domain.room.service;

import kr.co.ttoti.backend.domain.room.dto.RoomMemberAnimalSelectRequest;

public interface RoomMemberAnimalSelectionService {

	/**
	 * 동물을 선택해 준비 상태로 전환한다.
	 * 모든 사람들이 준비 상태가 되면 또띠 관계를 매칭해 배열로 반환한다.
	 * @param memberId
	 * @param roomId
	 * @param roomMemberAnimalSelectRequest
	 * @return
	 */
	Object handleAnimalSelection(Integer memberId,
		Integer roomId,
		RoomMemberAnimalSelectRequest roomMemberAnimalSelectRequest);
}
