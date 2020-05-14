package kr.study.restdocs.scrap;

import kr.study.restdocs.scrap.dto.ScrapResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class Scrap {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    @Builder
    public Scrap(String title, String content) {
        this.title      = title;
        this.content    = content;
    }

    public ScrapResponseDto update(String title, String content) {
        this.title    = title;
        this.content   = content;

        return new ScrapResponseDto(this);
    }
}
