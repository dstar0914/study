package kr.study.validation.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member save(MemberSaveRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity());
    }

    public List<MemberResponseDto> getList() {
        return memberRepository.findAll()
                            .stream()
                            .map(MemberResponseDto::new)
                            .collect(Collectors.toList());
    }
}
