package kr.co.ttoti.backend.domain.room.service;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.member.repository.MemberRepository;
import kr.co.ttoti.backend.domain.room.dto.RoomPendingDetailGetDto;
import kr.co.ttoti.backend.domain.room.entity.Room;
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

	@Override
	public RoomPendingDetailGetDto getRoomPendingDetail(Integer memberId, Integer roomId) {

		Member member = memberRepository.findByMemberId(memberId).orElseThrow(
			() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
		);

		Room room = roomRepository.findByRoomIdAndRoomIsDeletedFalse(roomId).orElseThrow(
			() -> new CustomException(ErrorCode.ROOM_NOT_FOUND)
		);

		roomMemberRepository.findByRoomAndMemberAndRoomMemberIsDeleted(room, member, false)
			.orElseThrow(
				() -> new CustomException(ErrorCode.ROOM_UNAUTHORIZED)
			);

		Member hostMember = memberRepository.findByMemberId(room.getRoomHostMemberId()).orElseThrow(
			() -> new CustomException(ErrorCode.ROOM_HOST_MEMBER_NOT_FOUND)
		);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		return RoomPendingDetailGetDto.builder()
			.roomName(room.getRoomName())
			.roomHostMemberName(hostMember.getMemberName())
			.roomFinishTime(room.getRoomFinishTime().format(formatter))
			.roomPeriod(room.getRoomPeriod())
			.build();
	}
}
