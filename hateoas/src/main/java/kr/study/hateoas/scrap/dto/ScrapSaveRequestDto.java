package kr.study.hateoas.scrap.dto;

import kr.study.hateoas.scrap.Scrap;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScrapSaveRequestDto {

    private String title;
    private String content;

    @Builder
    public ScrapSaveRequestDto(String title, String content) {
        this.title      = title;
        this.content    = content;
    }

    public Scrap toEntity() {
        return Scrap.builder()
                .title(title)
                .content(content)
                .build();
    }
}
