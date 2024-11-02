package kr.co.ttoti.backend.domain.room.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.domain.animal.repository.AnimalRepository;
import kr.co.ttoti.backend.domain.animal.dto.AnimalSelectDto;
import kr.co.ttoti.backend.domain.animal.entity.Animal;
import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.member.repository.MemberRepository;
import kr.co.ttoti.backend.domain.room.dto.RoomMemberAnimalSelectRequest;
import kr.co.ttoti.backend.domain.room.dto.TtotiMatchDto;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;
import kr.co.ttoti.backend.domain.room.repository.RoomMemberRepository;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import kr.co.ttoti.backend.domain.ttoti.entity.AnimalPersonality;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;
import kr.co.ttoti.backend.domain.ttoti.repository.TtotiRepository;
import kr.co.ttoti.backend.global.exception.CustomException;
import kr.co.ttoti.backend.global.status.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomMemberAnimalSelectionServiceImpl implements RoomMemberAnimalSelectionService {

	private final RoomRepository roomRepository;
	private final MemberRepository memberRepository;
	private final RoomMemberRepository roomMemberRepository;
	private final AnimalRepository animalRepository;
	private final TtotiRepository ttotiRepository;

	public static HashMap<RoomMember, RoomMember> createTtotiMap(List<RoomMember> roomMemberList) {

		List<RoomMember> shuffledRoomMemberList = new ArrayList<>(roomMemberList);
		Collections.shuffle(shuffledRoomMemberList);

		HashMap<RoomMember, RoomMember> map = new HashMap<>();

		for (int i = 0; i < shuffledRoomMemberList.size(); i++) {
			RoomMember manitto = shuffledRoomMemberList.get(i);
			RoomMember maniti = shuffledRoomMemberList.get((i + 1) % shuffledRoomMemberList.size());
			map.put(manitto, maniti);
		}
		return map;
	}

	private Member getValidMember(Integer memberId) {
		return memberRepository.findById(memberId).orElseThrow(
			() -> new IllegalArgumentException(ErrorCode.MEMBER_NOT_FOUND.getMessage() + " : " + memberId)
		);
	}

	private Room getValidRoom(Integer roomId) {
		return roomRepository.findById(roomId).orElseThrow(
			() -> new IllegalArgumentException(ErrorCode.ROOM_NOT_FOUND.getMessage() + " : " + roomId)
		);
	}

	private Animal updateRoomMemberAnimal(Room room, Member member,
		RoomMemberAnimalSelectRequest roomMemberAnimalSelectRequest) {
		RoomMember roomMember = roomMemberRepository.findByRoomAndMemberAndRoomMemberIsDeleted(room, member, false)
			.orElseThrow(() -> new CustomException(ErrorCode.ROOM_UNAUTHORIZED));
		Animal animal = animalRepository.findByAnimalIsAvailableAndAnimalId(true,
				roomMemberAnimalSelectRequest.getAnimalId())
			.orElseThrow(() -> new IllegalArgumentException(
				ErrorCode.ANIMAL_NOT_AVAILABLE.getMessage() + " : " + roomMemberAnimalSelectRequest.getAnimalId()));

		roomMember.updateAnimal(animal);
		roomMember.updateRoomMemberIsReady(true);
		roomMemberRepository.save(roomMember);
		return animal;
	}

	private List<TtotiMatchDto> createTtotiMatchDtoList(Room room, HashMap<RoomMember, RoomMember> ttotiMap) {
		List<TtotiMatchDto> ttotiMatchDtoList = new ArrayList<>();
		for (RoomMember manitto : ttotiMap.keySet()) {
			Member maniti = ttotiMap.get(manitto).getMember();
			Ttoti ttoti = Ttoti.builder()
				.room(room)
				.member(manitto.getMember())
				.animal(manitto.getAnimal())
				.manitiId(maniti.getMemberId())
				.tittoAnimalName(AnimalPersonality.getRandomPersonality().getDescription() + " " + manitto.getAnimal()
					.getAnimalName())
				.ttotiTemperature(36.5F)
				.ttotiChatIsFinished(false)
				.build();
			Ttoti savedTtoti = ttotiRepository.save(ttoti);
			savedTtoti.updateTittoId(savedTtoti.getTtotiId());

			ttotiMatchDtoList.add(
				TtotiMatchDto.builder()
					.ttotiAnimalName(savedTtoti.getTittoAnimalName())
					.ttotiAnimalImageUrl(manitto.getAnimal().getAnimalImageUrl())
					.manitiMemberName(maniti.getMemberName())
					.manitiProfileImageUrl(maniti.getMemberProfileImageUrl())
					.build()
			);
		}
		return ttotiMatchDtoList;
	}

	@Override
	@Transactional
	public Object handleAnimalSelection(Integer memberId, Integer roomId,
		RoomMemberAnimalSelectRequest roomMemberAnimalSelectRequest) {

		Member member = getValidMember(memberId);
		Room room = getValidRoom(roomId);
		Animal animal = updateRoomMemberAnimal(room, member, roomMemberAnimalSelectRequest);

		List<RoomMember> readyRoomMemberList = roomMemberRepository.findByRoomAndRoomMemberIsDeletedAndRoomMemberIsReady(
			room, false, true);

		if (readyRoomMemberList.size() != room.getRoomParticipants()) {
			return AnimalSelectDto.builder()
				.animalName(animal.getAnimalName())
				.animalImageUrl(animal.getAnimalImageUrl())
				.build();
		}
		room.startRoom();
		HashMap<RoomMember, RoomMember> ttotiMap = createTtotiMap(readyRoomMemberList);
		return createTtotiMatchDtoList(room, ttotiMap);
	}

}
