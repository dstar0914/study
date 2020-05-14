package kr.study.restdocs.scrap;

import kr.study.restdocs.scrap.dto.ScrapResponseDto;
import kr.study.restdocs.scrap.dto.ScrapSaveRequestDto;
import kr.study.restdocs.scrap.dto.ScrapUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;

    @Transactional
    public ScrapResponseDto save(ScrapSaveRequestDto requestDto) {
        Scrap saveScrap = scrapRepository.save(requestDto.toEntity());

        return new ScrapResponseDto(saveScrap);
    }

    @Transactional
    public ScrapResponseDto update(Long id, ScrapUpdateRequestDto requestDto) {
        Scrap scrap = scrapRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id) );

        return scrap.update(requestDto.getTitle(), requestDto.getContent());
        /* scrap 자체를 리턴해보기 */
    }

    @Transactional(readOnly = true)
    public ScrapResponseDto findById(Long id) {
        Scrap entity = scrapRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id) );

        return new ScrapResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<ScrapResponseDto> findAll() {
        List<ScrapResponseDto> result =  scrapRepository.findAll().stream()
                .map(ScrapResponseDto::new)
                .collect(Collectors.toList());

        return result;
    }
}
