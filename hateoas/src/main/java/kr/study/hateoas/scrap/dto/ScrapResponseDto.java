package kr.study.hateoas.scrap.dto;

import kr.study.hateoas.scrap.Scrap;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(collectionRelation = "lists")
public class ScrapResponseDto {

    private Long id;
    private String title;
    private String content;

    @Builder
    public ScrapResponseDto(Scrap scrap) {
        this.id         = scrap.getId();
        this.title      = scrap.getTitle();
        this.content    = scrap.getContent();
    }

    public Scrap toEntity() {
        return Scrap.builder()
                .title(title)
                .content(content)
                .build();
    }
}