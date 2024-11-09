package kr.co.ttoti.backend.domain.member.service;

import kr.co.ttoti.backend.domain.common.Validator;
import kr.co.ttoti.backend.domain.member.dto.EndingDto;
import kr.co.ttoti.backend.domain.member.dto.ManittoGamesRequest;
import kr.co.ttoti.backend.domain.member.dto.ManittoGameDto;
import kr.co.ttoti.backend.domain.member.entity.Ending;
import kr.co.ttoti.backend.domain.member.repository.EndingRepository;
import kr.co.ttoti.backend.domain.member.dto.MemberDetailDto;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.repository.RoomMemberRepository;
import kr.co.ttoti.backend.domain.room.repository.RoomRepository;
import kr.co.ttoti.backend.global.auth.entity.Member;
import kr.co.ttoti.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static kr.co.ttoti.backend.global.status.ErrorCode.MEMBER_NAME_FORBIDDEN;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final Validator validator;
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final EndingRepository endingRepository;

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
    public EndingDto getEnding(Integer memberId, Integer endingId) {
        Member member = validator.validateMember(memberId);

        Ending ending = endingRepository.findByEndingId(endingId);

        return EndingDto.builder().build();
    }
}
