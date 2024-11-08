package kr.co.ttoti.backend.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ttoti.backend.domain.member.entity.Ending;

@Repository
public interface EndingRepository extends JpaRepository<Ending, Integer> {

	Ending findByEndingId(Integer gameId);
}
