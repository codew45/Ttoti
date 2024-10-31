package kr.co.ttoti.backend.domain.ttoti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ttoti.backend.domain.ttoti.entity.Ttoti;

public interface TtotiRepository extends JpaRepository<Ttoti, Integer> {
}
