package kr.co.ttoti.backend.domain.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.room.entity.RoomMember;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Integer> {

	List<RoomMember> findByMemberAndRoomMemberIsDeletedFalse(Member member);

	List<RoomMember> findByRoomAndRoomMemberIsDeletedFalse(Room room);

	Integer countByRoomAndRoomMemberIsDeletedFalse(Room room);


}