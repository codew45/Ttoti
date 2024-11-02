package kr.co.ttoti.backend.domain.room.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.domain.animal.AnimalRepository;
import kr.co.ttoti.backend.domain.animal.dto.AnimalSelectDto;
import kr.co.ttoti.backend.domain.animal.entity.Animal;
import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.quiz.service.QuizInsertService;
import kr.co.ttoti.backend.domain.room.dto.RoomMemberAnimalSelectRequest;
import kr.co.ttoti.backend.domain.room.dto.TtotiMatchDto;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;
import kr.co.ttoti.backend.domain.room.repository.RoomMemberRepository;
import kr.co.ttoti.backend.domain.ttoti.entity.AnimalPersonality;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;
import kr.co.ttoti.backend.domain.ttoti.repository.TtotiRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomMemberAnimalSelectionServiceImpl implements RoomMemberAnimalSelectionService {

	private final RoomMemberRepository roomMemberRepository;
	private final TtotiRepository ttotiRepository;
	private final Validator validator;
	private final QuizInsertService quizInsertService;

	@Transactional
	public Integer createTtoti(Room room, List<RoomMember> roomMemberList, RoomMember roomMember) {

		List<RoomMember> shuffledRoomMemberList = new ArrayList<>(roomMemberList);
		Collections.shuffle(shuffledRoomMemberList);
		Integer myTtotiId = null;

		for (int i = 0; i < shuffledRoomMemberList.size(); i++) {
			RoomMember manitto = shuffledRoomMemberList.get(i);
			RoomMember maniti = shuffledRoomMemberList.get((i + 1) % shuffledRoomMemberList.size());

			Ttoti ttoti = Ttoti.builder()
				.room(room)
				.member(manitto.getMember())
				.animal(manitto.getAnimal())
				.manitiId(maniti.getMember().getMemberId())
				.ttotiAnimalName(AnimalPersonality.getRandomPersonality().getDescription() + " " + manitto.getAnimal()
					.getAnimalName())
				.ttotiTemperature(36.5F)
				.ttotiChatIsFinished(false)
				.build();
			Ttoti savedTtoti = ttotiRepository.save(ttoti);

			if (manitto.equals(roomMember)) {
				myTtotiId = savedTtoti.getTtotiId();
			}
		}

		List<Ttoti> ttotiList = ttotiRepository.findByRoom(room);
		for (Ttoti ttoti : ttotiList) {
			Ttoti titto = ttotiRepository.findByRoomAndManitiId(room, ttoti.getMember().getMemberId());
			ttoti.updateTittoId(titto.getTtotiId());
		}
		return myTtotiId;
	}

	@Transactional
	public Animal updateRoomMemberAnimal(Room room, Member member,
		RoomMemberAnimalSelectRequest roomMemberAnimalSelectRequest) {

		RoomMember roomMember = validator.validateMemberRoomAuthorization(room, member);

		Animal animal = validator.validateAnimal(roomMemberAnimalSelectRequest.getAnimalId());

		roomMember.updateAnimal(animal);
		roomMember.updateRoomMemberIsReady(true);
		roomMemberRepository.saveAndFlush(roomMember);

		return animal;
	}

	public TtotiMatchDto startRoom(Room room, List<RoomMember> readyRoomMemberList, RoomMember roomMember) {

		room.startRoom();
		Integer myTtotiId = createTtoti(room, readyRoomMemberList, roomMember);

		Ttoti ttoti = ttotiRepository.findByTtotiId(myTtotiId);
		Member myManiti = validator.validateMember(ttoti.getManitiId());
		Ttoti myManitto = ttotiRepository.findByTtotiId(ttoti.getTittoId());

		quizInsertService.insertQuiz(room.getRoomId());

		return TtotiMatchDto.builder()
			.myManittoAnimalName(myManitto.getTtotiAnimalName())
			.myManittoAnimalImageUrl(myManitto.getAnimal().getAnimalImageUrl())

			.myAnimalName(ttoti.getTtotiAnimalName())
			.myAnimalImageUrl(ttoti.getAnimal().getAnimalImageUrl())

			.manitiMemberName(myManiti.getMemberName())
			.manitiProfileImageUrl(myManiti.getMemberProfileImageUrl())
			.build();
	}

	@Override
	@Transactional
	public Object handleAnimalSelection(Integer memberId, Integer roomId,
		RoomMemberAnimalSelectRequest roomMemberAnimalSelectRequest) {

		Member member = validator.validateMember(memberId);
		Room room = validator.validateRoom(roomId);
		RoomMember roomMember = validator.validateMemberRoomAuthorization(room, member);
		Animal animal = updateRoomMemberAnimal(room, member, roomMemberAnimalSelectRequest);

		List<RoomMember> readyRoomMemberList = roomMemberRepository.findByRoomAndRoomMemberIsDeletedAndRoomMemberIsReady(
			room, false, true);

		if (readyRoomMemberList.size() != room.getRoomParticipants()) {
			return AnimalSelectDto.builder()
				.animalName(animal.getAnimalName())
				.animalImageUrl(animal.getAnimalImageUrl())
				.build();
		}
		return startRoom(room, readyRoomMemberList, roomMember);
	}
}
