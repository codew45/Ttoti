package kr.co.ttoti.backend.domain.room.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.domain.room.dto.RoomCreateRequest;
import kr.co.ttoti.backend.domain.room.dto.RoomIdDto;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RoomCreateServiceImpl implements RoomCreateService {

	private final RoomRepository roomRepository;

	@Override
	public RoomIdDto createRoom(Integer memberId, RoomCreateRequest roomCreateRequest) {
		return RoomIdDto.builder()
			.roomId(roomRepository.save(new Room(memberId, roomCreateRequest)).getRoomId())
			.build();
	}
}
