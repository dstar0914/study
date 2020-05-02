package kr.study.jwt.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenRequestDto {

    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenRequestDto(String accessToken, String refreshToken) {
        this.accessToken    = accessToken;
        this.refreshToken   = refreshToken;
    }
}
