package kr.study.hateoas.scrap;

import kr.study.hateoas.scrap.dto.ScrapResponseDto;
import kr.study.hateoas.scrap.dto.ScrapSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/scrap", produces = MediaTypes.HAL_JSON_VALUE)
public class ScrapApiController {

    private final ScrapService scrapService;
    private final ScrapRepository scrapRepository;

    @PostMapping
    public ResponseEntity save(@RequestBody ScrapSaveRequestDto requestDto) {
        ScrapResponseDto createScrap = scrapService.save(requestDto);

        ScrapResource scrapResource = new ScrapResource(createScrap);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(ScrapApiController.class).slash(createScrap.getId());
        URI createdUri = selfLinkBuilder.toUri();

        scrapResource.add(selfLinkBuilder.withRel("query-scrap"));
        scrapResource.add(selfLinkBuilder.withRel("update-scrap"));
        scrapResource.add(new Link("/docs/index.html#resources-scrap-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(scrapResource);
    }

    @GetMapping
    public ResponseEntity getList(Pageable pageable, PagedResourcesAssembler<Scrap> assembler) {
        PagedModel<EntityModel<ScrapResponseDto>> pageResource = assembler.toModel(scrapRepository.findAll(pageable), ScrapResource::new);

        pageResource.add(new Link("/docs/index.html#resources-scrap-list").withRel("profile"));

        return ResponseEntity.ok(pageResource);
    }
}
