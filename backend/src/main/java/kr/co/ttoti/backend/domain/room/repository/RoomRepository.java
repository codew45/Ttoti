package kr.co.ttoti.backend.domain.room.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.co.ttoti.backend.domain.room.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

	Optional<Room> findByRoomId(Integer roomId);

	Optional<Room> findByRoomCode(String roomCode);

	Optional<Room> findByRoomIdAndRoomIsDeletedFalse(Integer roomId);

	Optional<Room> findByRoomIdAndRoomIsStartedFalse(Integer roomId);

	List<Room> findByRoomIsDeletedFalseAndRoomIsStartedTrueAndRoomIsFinishedFalse();

	List<Room> findByRoomIsDeletedFalseAndRoomIsStartedTrueAndRoomIsFinishedFalseAndRoomFinishDateLessThanEqualAndRoomFinishTimeLessThanEqual(LocalDate roomFinishDate, LocalTime roomFinishTime);

	@Query("SELECT r " +
			"FROM RoomMember rm " +
			"JOIN rm.room r " +
			"WHERE rm.member.memberId = :memberId " +
			"AND r.roomIsDeleted = false " +
			"AND r.roomIsFinished = true " +
			"AND r.roomStartDate >= :startDate " +
			"AND r.roomFinishDate <= :endDate")
	List<Room> findRoomsByMemberIdAndDate(@Param("memberId") Integer memberId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query("SELECT DISTINCT r " +
			"FROM RoomMember rm " +
			"JOIN rm.room r " +
			"JOIN RoomMember rm2 ON rm2.room = r " +
			"WHERE rm.member.memberId = :memberId " +
			"AND rm2.member.memberId = :friendId " +
			"AND r.roomIsDeleted = false " +
			"AND r.roomIsFinished = true " +
			"AND r.roomStartDate >= :startDate " +
			"AND r.roomFinishDate <= :endDate")
	List<Room> findRoomsByMemberIdAndDateAndFriendId(@Param("memberId") Integer memberId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("friendId") Integer friendId);
}