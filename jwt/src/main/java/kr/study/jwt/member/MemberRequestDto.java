package kr.study.jwt.member;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
public class MemberRequestDto {

    private String email;
    private String password;
    private Set<Role> roles;

    @Builder
    public MemberRequestDto(String email, String password, Set<Role> roles) {
        this.email      = email;
        this.password   = password;
        this.roles      = roles;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .roles(roles)
                .build();
    }
}
