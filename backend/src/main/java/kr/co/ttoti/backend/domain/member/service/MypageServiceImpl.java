package kr.co.ttoti.backend.domain.member.service;

import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.domain.quiz.dto.QuizHistoryDto;
import kr.co.ttoti.backend.domain.quiz.entity.QuizAnswer;
import kr.co.ttoti.backend.domain.quiz.repository.QuizAnswerRepository;
import kr.co.ttoti.backend.domain.quiz.service.common.QuizServiceUtils;
import kr.co.ttoti.backend.domain.room.repository.RoomEndingRepository;
import kr.co.ttoti.backend.domain.ttoti.dto.TtotiEndingDto;
import kr.co.ttoti.backend.domain.member.dto.ManittoGamesRequest;
import kr.co.ttoti.backend.domain.member.dto.ManittoGameDto;
import kr.co.ttoti.backend.domain.member.repository.EndingRepository;
import kr.co.ttoti.backend.domain.member.dto.MemberDetailDto;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.repository.RoomMemberRepository;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;
import kr.co.ttoti.backend.domain.ttoti.entity.TtotiEnding;
import kr.co.ttoti.backend.domain.ttoti.repository.TtotiEndingRepository;
import kr.co.ttoti.backend.domain.ttoti.repository.TtotiRepository;
import kr.co.ttoti.backend.global.auth.entity.Member;
import kr.co.ttoti.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static kr.co.ttoti.backend.global.status.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final Validator validator;
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final RoomEndingRepository roomEndingRepository;
    private final TtotiEndingRepository ttotiEndingRepository;
    private final TtotiRepository ttotiRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final QuizServiceUtils quizServiceUtils;

    @Override
    @Transactional
    public void updateMemberName(Integer memberId, String newName) {
        if (newName.isBlank()){
            throw new CustomException(MEMBER_NAME_FORBIDDEN);
        }
        if (newName.length() > 10){
            throw new CustomException(MEMBER_NAME_FORBIDDEN);
        }

        Member member = validator.validateMember(memberId);
        member.changeMemberName(newName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManittoGameDto> getManittoGameList(Integer memberId, ManittoGamesRequest manittoGamesRequest) {
        List<Room> roomList;
        List<ManittoGameDto> manittoGameDtoList = new ArrayList<>();
        if (manittoGamesRequest.getFriendId().isBlank()){
            roomList = roomRepository.findRoomsByMemberIdAndDate(memberId, manittoGamesRequest.getStartDate(), manittoGamesRequest.getEndDate());
        }else{
            Member friend = validator.validateMember(manittoGamesRequest.getFriendId());
            roomList = roomRepository.findRoomsByMemberIdAndDateAndFriendId(memberId, manittoGamesRequest.getStartDate(), manittoGamesRequest.getEndDate(), friend.getMemberId());
        }

        for (Room room : roomList) {
            ManittoGameDto manittoGameDto = ManittoGameDto.builder()
                    .roomId(room.getRoomId())
                    .roomName(room.getRoomName())
                    .startDate(room.getRoomStartDate())
                    .endDate(room.getRoomFinishDate())
                    .membersName(roomMemberRepository.getNamesByRoomId(room.getRoomId()))
                    .build();

            manittoGameDtoList.add(manittoGameDto);
        }

        return manittoGameDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberDetailDto> getManittoFriendList(Integer memberId) {

        List<MemberDetailDto> memberDetailDtoList = new ArrayList<>();
        List<Member> friendList = roomMemberRepository.getFriendsByMemberId(memberId);

        HashSet<Integer> duplicateCheckSet = new HashSet<>();

        for (Member member : friendList) {
            if (!duplicateCheckSet.contains(member.getMemberId())) {
                duplicateCheckSet.add(member.getMemberId());

                MemberDetailDto memberDetailDto = MemberDetailDto.builder()
                        .memberId(member.getMemberUuid())
                        .memberName(member.getMemberName())
                        .memberProfileImageUrl(member.getMemberProfileImageUrl())
                        .build();

                memberDetailDtoList.add(memberDetailDto);
            }
        }

        return memberDetailDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public TtotiEndingDto getEnding(Integer memberId, Integer roomId) {
        Ttoti ttoti = ttotiRepository.findByRoom_RoomId(roomId).orElseThrow(() -> new CustomException(TTOTI_NOT_FOUND));
        TtotiEnding ttotiEnding = ttotiEndingRepository.findByTtotiId(ttoti.getTtotiId()).orElseThrow(() -> new CustomException(TTOTI_ENDING_NOT_FOUND));

        List<QuizAnswer> manittoQuizAnswerList = quizAnswerRepository.findByTtotiIdOrderByQuizDateDesc(ttoti.getTtotiId());
        List<QuizAnswer> manitiQuizAnswerList = quizAnswerRepository.findByTtotiIdOrderByQuizDateDesc(ttoti.getTittoId());

        List<QuizHistoryDto> manittoQuizHistoryDtoList = new LinkedList<>(manittoQuizAnswerList.stream()
                .map(quizServiceUtils::mapToQuizHistoryDto)
                .toList());
        List<QuizHistoryDto> manitiQuizHistoryDtoList = new LinkedList<>(manitiQuizAnswerList.stream()
                .map(quizServiceUtils::mapToQuizHistoryDto)
                .toList());

        return TtotiEndingDto.builder()
                .roomEnding(roomEndingRepository.findById(roomId).orElseThrow(() -> new CustomException(ROOM_ENDING_NOT_FOUND)))
                .manittoQuizList(manittoQuizHistoryDtoList)
                .manitiQuizList(manitiQuizHistoryDtoList)
                // 내 추측 결과
                // private List<GuessDto>
                .endingCorrectScore(ttotiEnding.getEndingCorrectScore())
                .endingChatCount(ttotiEnding.getEndingChatCount())
                .endingFinalTemperature(ttotiEnding.getEndingFinalTemperature())
                .build();
    }
}
