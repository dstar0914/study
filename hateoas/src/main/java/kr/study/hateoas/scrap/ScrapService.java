package kr.study.hateoas.scrap;

import kr.study.hateoas.scrap.dto.ScrapResponseDto;
import kr.study.hateoas.scrap.dto.ScrapSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;

    @Transactional
    public ScrapResponseDto save(ScrapSaveRequestDto requestDto) {
        Scrap saveScrap = scrapRepository.save(requestDto.toEntity());

        return new ScrapResponseDto(saveScrap);
    }
}
