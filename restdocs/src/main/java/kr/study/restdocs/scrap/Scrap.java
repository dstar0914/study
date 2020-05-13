package kr.study.restdocs.scrap;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
}
