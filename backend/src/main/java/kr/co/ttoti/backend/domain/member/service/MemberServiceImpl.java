package kr.co.ttoti.backend.domain.member.service;

import kr.co.ttoti.backend.global.auth.entity.Member;
import kr.co.ttoti.backend.global.auth.repository.MemberRepository;
import kr.co.ttoti.backend.global.auth.security.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void join(CustomOAuth2User customOAuth2User) {
        memberRepository.save(Member.builder()
                .memberUuid(UUID.randomUUID().toString())
                .memberSocialCategory(customOAuth2User.getProviderName())
                .memberProviderId(customOAuth2User.getProviderId())
                .memberEmail(customOAuth2User.getEmail())
                .memberName(customOAuth2User.getName())
                .memberProfileImageUrl(customOAuth2User.getProfileImageUrl())
                .memberIsDeleted(false)
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> getMemberIdByProviderId(Long id) {
        return memberRepository.findByMemberProviderId(id);
    }
}
