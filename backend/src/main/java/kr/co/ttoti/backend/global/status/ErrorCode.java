package kr.co.ttoti.backend.global.status;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Global
    BAD_REQUEST(400, "실패"),
    METHOD_NOT_ALLOWED(405, "유효하지 않은 요청입니다."),
    INTERNAL_SERVER_ERROR(500, "서버 에러"),

    // JWT
    JWT_ERROR(401, "토큰 에러가 발생했습니다."),
    EXPIRED_TOKEN(401, "만료된 토큰입니다."),
    MALFORMED_TOKEN(401, "유효하지 않은 토큰입니다."),
    UNSUPPORTED_TOKEN(401, "허용되지 않은 토큰입니다."),

    // Auth
    AUTHORIZATION_HEADER_NOT_EXIST(401, "인증 헤더가 존재하지 않습니다."),
    UNAUTHORIZED(401, "인증되지 않은 요청입니다."),
    ACCESS_DENIED(403, "허용되지 않는 접근입니다."),
    AUTHENTICATION_REQUIRED(401, "올바르지 않은 권한입니다."),
    ALREADY_LOGOUT(401, "이미 로그아웃한 토큰입니다."),

    // OAuth
    WRONG_PROVIDER(404, "지원하지 않는 소셜 로그인입니다."),
    KAKAO_PARAMETER_ERROR(400, "유효하지 않은 카카오 로그인 요청입니다."),
    KAKAO_SERVER_ERROR(500, "카카오 서버 오류입니다."),


    // member
    MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다"),

    // room
    ROOM_NOT_FOUND(404, "존재하지 않는 방입니다");
    ;

    private final int httpStatus;
    private final String message;
}
