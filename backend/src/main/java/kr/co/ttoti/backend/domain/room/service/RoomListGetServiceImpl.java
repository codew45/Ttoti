package kr.co.ttoti.backend.domain.room.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.member.repository.MemberRepository;
import kr.co.ttoti.backend.domain.room.dto.RoomMemberDto;
import kr.co.ttoti.backend.domain.room.dto.RoomSummaryDto;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.repository.RoomMemberRepository;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import kr.co.ttoti.backend.global.exception.CustomException;
import kr.co.ttoti.backend.global.status.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomListGetServiceImpl implements RoomListGetService {

	private final MemberRepository memberRepository;
	private final RoomRepository roomRepository;
	private final RoomMemberRepository roomMemberRepository;

	@Override
	public List<RoomSummaryDto> getRoomList(Integer memberId) {
		Member member = memberRepository.findByMemberId(memberId);

		if(member == null) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);

		return roomMemberRepository.findByMemberAndRoomMemberIsDeletedFalse(member).stream()
			.filter(roomMember -> !roomMember.getRoom().getRoomIsFinished())
			.map(roomMember -> new RoomSummaryDto(roomMember,
				memberRepository.findById(roomMember.getRoom().getRoomHostMemberId()).orElseThrow(),
				roomMemberRepository.countByRoomAndRoomMemberIsDeletedFalse(roomMember.getRoom())))
			.toList();
	}

	@Override
	public List<RoomMemberDto> getRoomMemberList(Integer roomId) {
		Room room = roomRepository.findByRoomIdAndRoomIsDeletedFalse(roomId);

		if(room == null) throw new CustomException(ErrorCode.ROOM_NOT_FOUND);

		return roomMemberRepository.findByRoomAndRoomMemberIsDeletedFalse(room)
			.stream()
			.map(roomMember -> new RoomMemberDto())
			.toList();
	}

	@Override
	public Integer getCurrentRoomParticipants(Integer roomId) {
		Room room = roomRepository.findByRoomIdAndRoomIsDeletedFalse(roomId);

		if(room == null) throw new CustomException(ErrorCode.ROOM_NOT_FOUND);

		return roomMemberRepository.countByRoomAndRoomMemberIsDeletedFalse(room);
	}
}
