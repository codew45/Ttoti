package kr.co.ttoti.backend.domain.auth.dto;

import lombok.Builder;

@Builder
public record ReissueResponse(String accessToken,
                              String refreshToken) {
}
