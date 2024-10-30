package kr.co.ttoti.backend.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ttoti.backend.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	Member findByMemberId(Integer memberId);
}
