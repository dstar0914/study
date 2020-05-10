package kr.study.response.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRequestDto {

    private String email;
    private String name;

    @Builder
    public MemberRequestDto(String email, String name) {
        this.email  = email;
        this.name   = name;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .build();
    }
}
