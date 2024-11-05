package kr.co.ttoti.backend.domain.room.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ttoti.backend.global.auth.entity.Member;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Integer> {

	List<RoomMember> findByMemberAndRoomMemberIsDeletedFalse(Member member);

	List<RoomMember> findByRoomAndRoomMemberIsDeletedFalse(Room room);

	Integer countByRoomAndRoomMemberIsDeletedFalse(Room room);

	Optional<RoomMember> findByMemberAndRoom(Member member, Room room);

	Optional<RoomMember> findByRoomAndMemberAndRoomMemberIsDeleted(Room room, Member member, Boolean roomMemberIsDeleted);

	List<RoomMember> findByRoomAndRoomMemberIsDeletedAndRoomMemberIsReady(Room room, Boolean roomMemberIsDeleted,
		Boolean roomMemberIsReady);
}