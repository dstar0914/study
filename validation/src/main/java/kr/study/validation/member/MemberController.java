package kr.study.validation.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/member")
    public ResponseEntity save(@RequestBody  @Valid MemberSaveRequestDto requestDto) {
        return ResponseEntity.ok(memberService.save(requestDto));
    }

    @GetMapping("/api/member")
    public ResponseEntity getMemberList() {
        return ResponseEntity.ok(memberService.getList());
    }
}
