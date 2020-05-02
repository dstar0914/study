package kr.study.jwt.auth;

import kr.study.jwt.configs.JwtTokenProvider;
import kr.study.jwt.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

        String accessToken = jwtTokenProvider.createToken(user.getUsername(), user.getAuthorities());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername(), user.getAuthorities());

        // 꼭 Map으로 해야될까? DTO로 바꿨는데 _형식으로 저장하기 애매해서 변경하긴 하였음
        Map<String, Object> response = new HashMap<>();
        response.put("access_token", accessToken);
        response.put("refresh_token", refreshToken);
        response.put("expire", jwtTokenProvider.getExpiration(accessToken).getTime()/1000);

        return ResponseEntity.ok(response);
    }
}
