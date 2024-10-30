package kr.co.ttoti.backend.global.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    OK(200, "성공"),

    LOGIN_SUCCESS(200, "로그인 성공"),
    LOGOUT_SUCCESS(204, "로그아웃 성공"),
    REISSUE_SUCCESS(201, "토큰 재발급 성공"),

    ROOM_CREATE_SUCCESS(200, "방 생성 성공"),
    ;

    private final int httpStatus;
    private final String message;
}
