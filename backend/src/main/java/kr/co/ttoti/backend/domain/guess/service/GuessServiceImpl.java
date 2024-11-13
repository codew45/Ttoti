package kr.co.ttoti.backend.domain.guess.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.domain.guess.entity.Guess;
import kr.co.ttoti.backend.domain.guess.repository.GuessRepository;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;
import kr.co.ttoti.backend.domain.room.repository.RoomMemberRepository;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import kr.co.ttoti.backend.global.auth.entity.Member;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GuessServiceImpl implements GuessService {

    private final Validator validator;
    private final GuessRepository guessRepository;
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;

    @Transactional
    @Override
    public void insertMidtermGuess(RoomMember member, Room room) {
        guessRepository.save(Guess.builder().memberId(member.getMember().getMemberId())
                .roomId(room.getRoomId())
                .guessIsCorrect(false)
                .guessIsFinal(false)
                .guessIsAnswered(false)
                .build());
    }

    @Transactional
    @Override
    @Scheduled(cron = "0 5 0 * * ?")
    public void insertFinalGuess() {
        System.out.println(LocalDate.now());
        roomRepository.getRoomByMidDate(LocalDate.now())
                .forEach(room ->
                        roomMemberRepository.findByRoom(room)
                                .forEach(roomMember ->
                                        guessRepository.save(Guess.builder()
                                                .memberId(roomMember.getMember().getMemberId())
                                                .roomId(room.getRoomId())
                                                .guessIsCorrect(false)
                                                .guessIsFinal(true)
                                                .guessIsAnswered(false)
                                                .build())
                                )
                );

    }

    @Override
    public void updateGuess(Integer memberId, Integer roomId, Integer guessMemberId) {
    }
}
