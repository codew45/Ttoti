package kr.co.ttoti.backend.domain.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.global.auth.entity.Member;
import kr.co.ttoti.backend.global.auth.repository.MemberRepository;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;
import kr.co.ttoti.backend.domain.room.repository.RoomMemberRepository;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import kr.co.ttoti.backend.global.exception.CustomException;
import kr.co.ttoti.backend.global.status.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RoomMemberCreateServiceImpl implements RoomMemberCreateService {

	private final MemberRepository memberRepository;
	private final RoomRepository roomRepository;
	private final RoomMemberRepository roomMemberRepository;

	@Override
	public void createRoomMember(Integer memberId, Integer roomId) {
		Member member = memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

		Room room = roomRepository.findByRoomId(roomId)
			.orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

		if (roomMemberRepository.findByMemberAndRoom(member, room).isEmpty()) {
			roomMemberRepository.save(new RoomMember(room, member));
		}
	}

	@Override
	public void createRoomMemberByRoomCode(Integer memberId, String roomCode) {
		Member member = memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

		Room room = roomRepository.findByRoomCode(roomCode)
			.orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

		if(room.getRoomIsStarted()) {
			throw new CustomException(ErrorCode.ROOM_IN_PROGRESS);
		}

		if (roomMemberRepository.findByMemberAndRoom(member, room).isEmpty()) {
			roomMemberRepository.save(new RoomMember(room, member));
		}
	}
}
