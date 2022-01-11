package today.what_should_i_eat_today.domain.tag.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import today.what_should_i_eat_today.domain.tag.application.TagService;
import today.what_should_i_eat_today.domain.tag.dto.TagResponseDto;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TagApi {

    private final TagService tagService;

    /**
     * 태그 리스트 조회
     */
    @GetMapping("/v1/tags")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getTagsV1() {

        List<Tag> tags = tagService.getTags();

        List<TagResponseDto> responseDtos = tags.stream().map(TagResponseDto::from).collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }

    /**
     * 태그 페이징 조회
     *
     * @param name
     * @param pageable
     * @return
     */
    @GetMapping("/v2/tags")
    public ResponseEntity getTagsV2(@RequestParam("name") String name, @PageableDefault Pageable pageable) {
        Page<Tag> tags = tagService.getTags(name, pageable);
        return ResponseEntity.ok(tags.map(TagResponseDto::from));
    }
}
