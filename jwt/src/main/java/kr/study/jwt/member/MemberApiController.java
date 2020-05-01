package kr.study.jwt.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApiController {

    private final MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity save(@RequestBody MemberRequestDto requestDto) {
        Member member = memberRepository.save(requestDto.toEntity());

        return ResponseEntity.ok(member);
    }
}
