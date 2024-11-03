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
import kr.co.ttoti.backend.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomGetServiceImpl implements RoomGetService {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final RedisUtil redisUtil;

    private RoomMemberListPendingDto getRoomMembers(Room room) {
        List<RoomMember> roomMembers = roomMemberRepository.findByRoomAndRoomMemberIsDeletedFalse(room);

        int currentParticipants = roomMembers.size();
        int totalParticipants = room.getRoomParticipants();
        List<RoomMemberDto> roomMemberDtos = roomMembers.stream().map(roomMember -> {
            Member member = roomMember.getMember();
            return RoomMemberDto.builder()
                    .name(member.getMemberName())
                    .profileImageUrl(member.getMemberProfileImageUrl())
                    .isReady(roomMember.getRoomMemberIsReady())
                    .build();
        }).toList();

        return RoomMemberListPendingDto.builder()
                .currentParticipants(currentParticipants)
                .totalParticipants(totalParticipants)
                .roomMemberList(roomMemberDtos)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean getRoomStatus(Integer memberId, Integer roomId) {
        Member member = memberRepository.findByMemberIdAndMemberIsDeletedFalse(memberId)
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
        Member member = memberRepository.findByMemberIdAndMemberIsDeletedFalse(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Room room = roomRepository.findByRoomIdAndRoomIsStartedFalse(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

        RoomMember roomMember = roomMemberRepository.findByMemberAndRoom(member, room)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_UNAUTHORIZED));

        if (room.getRoomIsStarted())
            throw new CustomException(ErrorCode.ROOM_IN_PROGRESS);

        Member hostMember = memberRepository.findByMemberIdAndMemberIsDeletedFalse(room.getRoomHostMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_HOST_MEMBER_NOT_FOUND));

        return RoomPendingDto.builder()
                .hostName(hostMember.getMemberName())
                .roomName(room.getRoomName())
                .roomMemberInfo(getRoomMembers(room))
                .profileImageUrl(roomMember.getRoomMemberIsReady() ? roomMember.getAnimal().getAnimalImageUrl() :
                        member.getMemberProfileImageUrl())
                .isReady(roomMember.getRoomMemberIsReady())
                .animalProfileImageUrl(roomMember.getRoomMemberIsReady() ? roomMember.getAnimal().getAnimalImageUrl() : null)
                .build();
    }

    @Override
    public RoomMemberListPendingDto getRoomMemberListIfPending(Integer memberId, Integer roomId) {
        Member member = memberRepository.findByMemberIdAndMemberIsDeletedFalse(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Room room = roomRepository.findByRoomIdAndRoomIsStartedFalse(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

        RoomMember roomMember = roomMemberRepository.findByMemberAndRoom(member, room)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_UNAUTHORIZED));

        return getRoomMembers(room);
    }

    @Override
    public String getRoomLink(Integer memberId, Integer roomId) {
        Member member = memberRepository.findByMemberIdAndMemberIsDeletedFalse(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Room room = roomRepository.findByRoomIdAndRoomIsStartedFalse(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

        RoomMember roomMember = roomMemberRepository.findByRoomAndMemberAndRoomMemberIsDeleted(room, member, false)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_UNAUTHORIZED));

        String roomLink = null;
        if ((roomLink = redisUtil.getRoomLink(room)) == null) {
            redisUtil.setRoomLink(room);
            roomLink = redisUtil.getRoomLink(room);
        }

        if (roomLink == null) throw new CustomException(ErrorCode.ROOM_LINK_NOT_CREATED);

        return roomLink;
    }

}
