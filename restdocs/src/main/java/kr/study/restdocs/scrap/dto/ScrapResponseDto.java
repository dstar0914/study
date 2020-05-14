package kr.study.restdocs.scrap.dto;

import kr.study.restdocs.scrap.Scrap;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScrapResponseDto {

    private Long id;
    private String title;
    private String content;

    @Builder
    public ScrapResponseDto(Scrap scrap) {
        this.id           = scrap.getId();
        this.title        = scrap.getTitle();
        this.content      = scrap.getContent();
    }
}
