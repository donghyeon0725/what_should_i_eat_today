package today.what_should_i_eat_today.domain.tag.dto;

import lombok.*;
import today.what_should_i_eat_today.domain.food.entity.FoodTag;
import today.what_should_i_eat_today.domain.tag.entity.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TagResponseDto {
    private Long id;

    private String name;

    public TagResponseDto(FoodTag foodTag) {
        this.id = foodTag.getTag().getId();
        this.name = foodTag.getTag().getName();
    }

    public static List<TagResponseDto> from(List<FoodTag> foodTags) {
        return foodTags.stream().map(TagResponseDto::from).collect(Collectors.toList());
    }

    public static TagResponseDto from(FoodTag foodTag) {
        return new TagResponseDto(foodTag);
    }

    public static TagResponseDto from(Tag tag) {
        return TagResponseDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
