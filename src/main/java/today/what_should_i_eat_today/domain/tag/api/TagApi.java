package today.what_should_i_eat_today.domain.tag.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.what_should_i_eat_today.domain.tag.application.TagService;
import today.what_should_i_eat_today.domain.tag.dto.TagResponseDto;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TagApi {

    private final TagService tagService;

    @GetMapping("tags")
    public ResponseEntity<?> getTags() {

        List<Tag> tags = tagService.getTags();

        List<TagResponseDto> responseDtos = tags.stream().map(TagResponseDto::from).collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }
}
