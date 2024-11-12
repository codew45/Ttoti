package kr.co.ttoti.backend.domain.ttoti.repository;

import java.util.List;
import java.util.Optional;

import kr.co.ttoti.backend.domain.member.dto.MemberDetailDto;
import kr.co.ttoti.backend.global.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;

@Repository
public interface TtotiRepository extends JpaRepository<Ttoti, Integer> {

	Ttoti findByTtotiId(Integer ttotiId);

	List<Ttoti> findByRoom(Room room);

	Ttoti findByRoomAndManitiId(Room room, Integer manitiId);

	Optional<Ttoti> findByTtotiIdAndMember(Integer ttotiId, Member member);

	Optional<Ttoti> findByRoomAndMember(Room room, Member member);

	@Query("SELECT m.memberUuid, m.memberName, m.memberProfileImageUrl " +
			"FROM Ttoti t " +
			"JOIN TtotiEnding te ON t.ttotiId = te.ttotiId " +
			"JOIN Member m ON t.member.memberId = m.memberId " +
			"where t.room.roomId = :roomId AND te.endingCorrectScore BETWEEN :minScore AND :maxScore")
	List<MemberDetailDto> getBestCorrectMemberList(@Param("roomId") Integer roomId, @Param("minScore") float minScore, @Param("maxScore") float maxScore);

	@Query("SELECT m.memberUuid, m.memberName, m.memberProfileImageUrl " +
			"FROM Ttoti t " +
			"JOIN TtotiEnding te ON t.ttotiId = te.ttotiId " +
			"JOIN Member m ON t.member.memberId = m.memberId " +
			"where t.room.roomId = :roomId AND te.endingChatCount = :maxChatCount")
	List<MemberDetailDto> getBestChatMemberList(@Param("roomId") Integer roomId, @Param("maxChatCount") int maxChatCount);

	@Query("SELECT m.memberUuid, m.memberName, m.memberProfileImageUrl " +
			"FROM Ttoti t " +
			"JOIN TtotiEnding te ON t.ttotiId = te.ttotiId " +
			"JOIN Member m ON t.member.memberId = m.memberId " +
			"where t.room.roomId = :roomId AND te.endingFinalTemperature BETWEEN :minTemp AND :maxTemp")
	List<MemberDetailDto> getBestTemperatureMemberList(@Param("roomId") Integer roomId, @Param("minTemp") float minTemp, @Param("maxTemp") float maxTemp);

	Optional<Ttoti> findByRoom_RoomId(Integer roomId);
}
