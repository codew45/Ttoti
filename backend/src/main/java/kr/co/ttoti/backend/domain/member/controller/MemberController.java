package kr.co.ttoti.backend.domain.member.controller;

import kr.co.ttoti.backend.domain.member.dto.MemberDetailResponse;
import kr.co.ttoti.backend.domain.member.service.MemberService;
import kr.co.ttoti.backend.global.auth.annotation.MemberId;
import kr.co.ttoti.backend.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.co.ttoti.backend.global.status.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ttoti/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/detail")
    public ResponseEntity<ResponseDto<MemberDetailResponse>> getMemberDetail(@MemberId Integer memberId) {

        MemberDetailResponse memberDetailResponse = memberService.getMemberDetail(memberId);

        return ResponseEntity.ok(ResponseDto.success(MEMBER_DETAIL_SUCCESS, memberDetailResponse));
    }

}
