package kr.study.jwt.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private String refreshToken;

    @Builder
    public Member(String email, String password, Set<Role> roles) {
        this.email      = email;
        this.password   = password;
        this.roles      = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * refresh_token 업데이트
     */
    public Member updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;

        return this;
    }
}
