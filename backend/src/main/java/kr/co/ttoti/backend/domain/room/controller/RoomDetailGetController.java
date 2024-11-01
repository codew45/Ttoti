package kr.co.ttoti.backend.domain.room.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ttoti.backend.domain.room.dto.RoomPendingDetailGetDto;
import kr.co.ttoti.backend.domain.room.service.RoomDetailGetService;
import kr.co.ttoti.backend.global.dto.ResponseDto;
import kr.co.ttoti.backend.global.status.SuccessCode;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ttoti/")
public class RoomDetailGetController {

	private final RoomDetailGetService roomDetailGetService;

	@GetMapping("rooms/pending/detail/{room-id}")
	ResponseEntity<ResponseDto<?>> getRoomPendingDetail(@RequestHeader Integer memberId,
		@PathVariable(name="room-id") Integer roomId) {

		RoomPendingDetailGetDto result = roomDetailGetService.getRoomPendingDetail(memberId, roomId);
		return ResponseEntity.ok(ResponseDto.success(SuccessCode.ROOM_PENDING_DETAIL_SUCCESS, result));
	}
}
