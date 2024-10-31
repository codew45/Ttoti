package kr.co.ttoti.backend.domain.room.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ttoti.backend.domain.room.service.RoomMemberCreateService;
import kr.co.ttoti.backend.global.dto.ResponseDto;
import kr.co.ttoti.backend.global.status.SuccessCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ttoti/rooms")
public class RoomMemberCreateController {

	private final RoomMemberCreateService roomMemberCreateService;

	@GetMapping("/code/{room-code}")
	public ResponseDto<Void> createRoomMemberByRoomCode(@RequestHeader Integer memberId, @PathVariable("room-code") String roomCode) {
		roomMemberCreateService.createRoomMemberByRoomCode(memberId, roomCode);
		return ResponseDto.success(SuccessCode.OK);
	}

}