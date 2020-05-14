package kr.study.restdocs.scrap;

import kr.study.restdocs.scrap.dto.ScrapSaveRequestDto;
import kr.study.restdocs.scrap.dto.ScrapUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/scrap")
public class ScrapApiController {

    private final ScrapService scrapService;

    @PostMapping
    public ResponseEntity save(@RequestBody ScrapSaveRequestDto requestDto) {
        return ResponseEntity.ok(scrapService.save(requestDto));
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody ScrapUpdateRequestDto requestDto) {
        return ResponseEntity.ok(scrapService.update(id, requestDto));
    }

    @GetMapping("{id}")
    public ResponseEntity getScrap(@PathVariable Long id) {
        return ResponseEntity.ok(scrapService.findById(id));
    }

    @GetMapping
    public ResponseEntity getList() {
        return ResponseEntity.ok(scrapService.findAll());
    }
}
