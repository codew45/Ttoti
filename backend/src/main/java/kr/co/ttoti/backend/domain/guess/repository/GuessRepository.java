package kr.co.ttoti.backend.domain.guess.repository;

import kr.co.ttoti.backend.domain.room.entity.Room;
import kr.co.ttoti.backend.global.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ttoti.backend.domain.guess.entity.Guess;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GuessRepository extends JpaRepository<Guess, Integer> {

    Optional<Guess> findByMemberIdAndRoomIdAndGuessDate(Integer memberId, Integer roomId, LocalDate today);
}
