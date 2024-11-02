package kr.co.ttoti.backend.domain.ttoti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ttoti.backend.domain.member.entity.Member;
import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;

@Repository
public interface TtotiRepository extends JpaRepository<Ttoti, Integer> {

	Ttoti findByTtotiId(Integer ttotiId);

	List<Ttoti> findByRoom(Room room);

	Ttoti findByRoomAndManitiId(Room room, Integer manitiId);
}
