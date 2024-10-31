package kr.co.ttoti.backend.domain.room.service;

import org.springframework.stereotype.Service;

import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.member.repository.MemberRepository;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;
import kr.co.ttoti.backend.domain.room.repository.RoomMemberRepository;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import kr.co.ttoti.backend.global.exception.CustomException;
import kr.co.ttoti.backend.global.status.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomGetServiceImpl implements RoomGetService {

	private final MemberRepository memberRepository;
	private final RoomRepository roomRepository;
	private final RoomMemberRepository roomMemberRepository;

	public Boolean getRoomStatus(Integer memberId, Integer roomId) {
		Member member = memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

		Room room = roomRepository.findByRoomId(roomId)
			.orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

		RoomMember roomMember = roomMemberRepository.findByMemberAndRoom(member, room)
			.orElseThrow(() -> new CustomException(ErrorCode.ROOM_UNAUTORIZED));

		return room.getRoomIsStarted();
	}
}
