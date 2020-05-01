package kr.study.jwt.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRequestDto {

    private String email;
    private String password;

    @Builder
    public MemberRequestDto(String email, String password) {
        this.email      = email;
        this.password   = password;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}
