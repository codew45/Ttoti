package kr.co.ttoti.backend.domain.room.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.member.repository.MemberRepository;
import kr.co.ttoti.backend.domain.room.dto.RoomMemberDto;
import kr.co.ttoti.backend.domain.room.dto.RoomMemberListPendingDto;
import kr.co.ttoti.backend.domain.room.dto.RoomPendingDto;
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

	// 방 시작 전, 현재 방 인원, 총 방 인원, 방 회원 목록들을 반환하는 내부 함수
	private RoomMemberListPendingDto getRoomMembers(Room room) {
		List<RoomMember> roomMembers = roomMemberRepository.findByRoomAndRoomMemberIsDeletedFalse(room);

		int currentParticipants = 0;
		int totalParticipants = room.getRoomParticipants();
		List<RoomMemberDto> roomMemberDtos = new ArrayList<>();
		for (RoomMember roomMember : roomMembers) {
			if (roomMember.getRoomMemberIsReady())
				currentParticipants += 1;

			Member member = roomMember.getMember();
			roomMemberDtos.add(RoomMemberDto.builder()
				.name(member.getMemberName())
				.profileImageUrl(member.getMemberProfileImageUrl())
				.isReady(roomMember.getRoomMemberIsReady())
				.build());
		}

		return RoomMemberListPendingDto.builder()
			.currentParticipants(currentParticipants)
			.totalParticipants(totalParticipants)
			.roomMembers(roomMemberDtos)
			.build();
	}

	@Transactional(readOnly = true)
	@Override
	public Boolean getRoomStatus(Integer memberId, Integer roomId) {
		Member member = memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

		Room room = roomRepository.findByRoomId(roomId)
			.orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

		RoomMember roomMember = roomMemberRepository.findByMemberAndRoom(member, room)
			.orElseThrow(() -> new CustomException(ErrorCode.ROOM_UNAUTHORIZED));

		return room.getRoomIsStarted();
	}

	@Transactional(readOnly = true)
	@Override
	public RoomPendingDto getRoomIfPending(Integer memberId, Integer roomId) {
		Member member = memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

		Room room = roomRepository.findByRoomId(roomId)
			.orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

		RoomMember roomMember = roomMemberRepository.findByMemberAndRoom(member, room)
			.orElseThrow(() -> new CustomException(ErrorCode.ROOM_UNAUTHORIZED));

		if (room.getRoomIsStarted())
			throw new CustomException(ErrorCode.ROOM_IN_PROGRESS);

		Member hostMember = memberRepository.findByMemberId(room.getRoomHostMemberId())
			.orElseThrow(() -> new CustomException(ErrorCode.ROOM_HOST_MEMBER_NOT_FOUND));

		return RoomPendingDto.builder()
			.hostName(hostMember.getMemberName())
			.roomName(room.getRoomName())
			.roomMemberInfo(getRoomMembers(room))
			.isReady(roomMember.getRoomMemberIsReady())
			.profileImageUrl(roomMember.getRoomMemberIsReady() ? roomMember.getAnimal().getAnimalImageUrl() : member.getMemberProfileImageUrl())
			.build();
	}
}
