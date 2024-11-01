package kr.co.ttoti.backend.domain.room.service;

import kr.co.ttoti.backend.domain.room.dto.RoomPendingDetailGetDto;

public interface RoomDetailGetService {

	RoomPendingDetailGetDto getRoomPendingDetail(Integer memberId, Integer roomId);
}
