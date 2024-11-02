package kr.co.ttoti.backend.domain.common;

import org.springframework.stereotype.Component;

import kr.co.ttoti.backend.domain.animal.AnimalRepository;
import kr.co.ttoti.backend.domain.animal.entity.Animal;
import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.member.repository.MemberRepository;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;
import kr.co.ttoti.backend.domain.room.repository.RoomMemberRepository;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;
import kr.co.ttoti.backend.domain.ttoti.repository.TtotiRepository;
import kr.co.ttoti.backend.global.exception.CustomException;
import kr.co.ttoti.backend.global.status.ErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Validator {

	private final TtotiRepository ttotiRepository;
	private final MemberRepository memberRepository;
	private final RoomRepository roomRepository;
	private final RoomMemberRepository roomMemberRepository;
	private final AnimalRepository animalRepository;

	public Member validateMember(Integer memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public Room validateRoom(Integer roomId) {
		return roomRepository.findByRoomIdAndRoomIsDeletedFalse(roomId).orElseThrow(
			() -> new CustomException(ErrorCode.ROOM_NOT_FOUND)
		);
	}

	public RoomMember validateMemberRoomAuthorization(Room room, Member member) {
		return roomMemberRepository.findByRoomAndMemberAndRoomMemberIsDeleted(room, member, false)
			.orElseThrow(
				() -> new CustomException(ErrorCode.ROOM_UNAUTHORIZED)
			);
	}

	public Ttoti validateTtoti(Integer ttotiId) {
		return ttotiRepository.findById(ttotiId)
			.orElseThrow(
				() -> new CustomException(ErrorCode.TTOTI_NOT_FOUND));
	}

	public Animal validateAnimal(Integer animalId) {
		return animalRepository.findByAnimalIsAvailableAndAnimalId(true, animalId)
			.orElseThrow(() -> new CustomException(ErrorCode.ANIMAL_NOT_AVAILABLE));

	}

}
