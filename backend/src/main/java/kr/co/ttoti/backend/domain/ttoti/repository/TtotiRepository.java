package kr.co.ttoti.backend.domain.ttoti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;

@Repository
public interface TtotiRepository extends JpaRepository<Ttoti, Integer> {
}
