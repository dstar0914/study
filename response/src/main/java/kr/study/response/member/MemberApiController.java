package kr.study.response.member;

import kr.study.response.exception.CMemberNotFoundException;
import kr.study.response.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApiController {

    private final MemberRepository memberRepository;
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity save(@RequestBody MemberRequestDto requestDto) {
        Member member = memberRepository.save(requestDto.toEntity());

        return ResponseEntity.ok(responseService.getSingleResult(member));
    }

    @GetMapping("/{id}")
    public ResponseEntity getMember(@PathVariable Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(CMemberNotFoundException::new); // () -> new CMemberNotFoundException(msg)

        return ResponseEntity.ok(responseService.getSingleResult(member));
    }
}
