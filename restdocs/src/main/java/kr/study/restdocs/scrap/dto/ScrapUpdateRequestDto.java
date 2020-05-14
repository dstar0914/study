package kr.study.restdocs.scrap.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScrapUpdateRequestDto {

    private String title;
    private String content;

    @Builder
    public ScrapUpdateRequestDto(String title, String content) {
        this.title    = title;
        this.content   = content;
    }
}
