package kr.study.jwt.auth;

import kr.study.jwt.configs.JwtTokenProvider;
import kr.study.jwt.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService detailService;

    /**
     * 토큰 생성 메소드
     */
    @PostMapping("/auth/token")
    public ResponseEntity createToken(@RequestBody Map<String, String> responseBody) {
        UserDetails user = detailService.loadUserByUsername(responseBody.get("email"));

        String jwt = jwtTokenProvider.createToken(user.getUsername(), user.getAuthorities());

        return ResponseEntity.ok(jwt);
    }
}
