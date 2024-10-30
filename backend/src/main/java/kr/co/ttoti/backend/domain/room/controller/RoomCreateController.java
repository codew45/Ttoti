package kr.co.ttoti.backend.domain.room.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.co.ttoti.backend.domain.room.dto.RoomCreateRequest;
import kr.co.ttoti.backend.domain.room.service.RoomCreateService;
import kr.co.ttoti.backend.global.dto.ResponseDto;
import kr.co.ttoti.backend.global.status.SuccessCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ttoti/rooms")
public class RoomCreateController {

	private final RoomCreateService roomCreateService;

	@PostMapping()
	public ResponseDto<Void> createRoom(@RequestHeader Integer memberId,
		@Valid @RequestBody RoomCreateRequest roomCreateRequest) {

		roomCreateService.createRoom(memberId, roomCreateRequest);

		return ResponseDto.success(SuccessCode.ROOM_CREATE_SUCCESS);
	}
}
