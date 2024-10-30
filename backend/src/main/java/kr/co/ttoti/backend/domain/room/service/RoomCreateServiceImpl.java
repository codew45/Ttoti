package kr.co.ttoti.backend.domain.room.service;


import org.springframework.stereotype.Service;

import kr.co.ttoti.backend.domain.room.dto.RoomCreateRequest;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomCreateServiceImpl implements RoomCreateService {

	private final RoomRepository roomRepository;

	@Override
	public void createRoom(Integer memberId, RoomCreateRequest roomCreateRequest) {
		roomRepository.save(new Room(memberId, roomCreateRequest));
	}
}
