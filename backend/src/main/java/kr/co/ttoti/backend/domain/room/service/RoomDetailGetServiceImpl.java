package kr.co.ttoti.backend.domain.room.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.member.repository.MemberRepository;
import kr.co.ttoti.backend.domain.room.dto.RoomInProgressDetailGetDto;
import kr.co.ttoti.backend.domain.room.dto.RoomPendingDetailGetDto;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;
import kr.co.ttoti.backend.domain.room.repository.RoomMemberRepository;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import kr.co.ttoti.backend.global.exception.CustomException;
import kr.co.ttoti.backend.global.status.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomDetailGetServiceImpl implements RoomDetailGetService {

	private final RoomRepository roomRepository;
	private final RoomMemberRepository roomMemberRepository;
	private final MemberRepository memberRepository;

	public Room validateRoom(Integer roomId) {
		return roomRepository.findByRoomIdAndRoomIsDeletedFalse(roomId).orElseThrow(
			() -> new CustomException(ErrorCode.ROOM_NOT_FOUND)
		);
	}

	public Member validateMember(Integer memberId) {
		return memberRepository.findByMemberId(memberId).orElseThrow(
			() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
		);
	}

	public Member validateRoomHost(Room room) {
		return memberRepository.findByMemberId(room.getRoomHostMemberId()).orElseThrow(
			() -> new CustomException(ErrorCode.ROOM_HOST_MEMBER_NOT_FOUND)
		);
	}

	public String timeFormatt(LocalTime time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return time.format(formatter);
	}

	public RoomMember validateMemberRoomAuthorization(Room room, Member member) {
		return roomMemberRepository.findByRoomAndMemberAndRoomMemberIsDeleted(room, member, false)
			.orElseThrow(
				() -> new CustomException(ErrorCode.ROOM_UNAUTHORIZED)
			);
	}

	@Override
	public RoomPendingDetailGetDto getRoomPendingDetail(Integer memberId, Integer roomId) {

		Room room = validateRoom(roomId);
		if (room.getRoomIsStarted()) {
			throw new CustomException(ErrorCode.ROOM_IN_PROGRESS);
		}
		Member hostMember = validateRoomHost(room);
		Member member = validateMember(memberId);
		validateMemberRoomAuthorization(room, member);

		return RoomPendingDetailGetDto.builder()
			.roomName(room.getRoomName())
			.roomHostMemberName(hostMember.getMemberName())
			.roomFinishTime(timeFormatt(room.getRoomFinishTime()))
			.roomPeriod(room.getRoomPeriod())
			.build();
	}

	@Override
	public RoomInProgressDetailGetDto getRoomInProgressDetail(Integer memberId, Integer roomId) {

		Room room = validateRoom(roomId);
		if (!room.getRoomIsStarted()) {
			throw new CustomException(ErrorCode.ROOM_IS_PENDING);
		}
		Member member = validateMember(memberId);
		Member hostMember = validateRoomHost(room);
		validateMemberRoomAuthorization(room, member);

		List<String> roomMemberNameList = roomMemberRepository.findByRoomAndRoomMemberIsDeletedFalse(room).stream()
			.map(roomMember -> roomMember.getMember().getMemberName()).toList();

		return RoomInProgressDetailGetDto.builder()
			.roomName(room.getRoomName())
			.roomHostMemberName(hostMember.getMemberName())
			.roomParticipants(room.getRoomParticipants())
			.roomParticipantsNameList(roomMemberNameList)
			.roomFinishTime(timeFormatt(room.getRoomFinishTime()))
			.roomStartDate(room.getRoomStartDate())
			.roomFinishDate(room.getRoomFinishDate())
			.roomPeriod(room.getRoomPeriod())
			.build();
	}
}
