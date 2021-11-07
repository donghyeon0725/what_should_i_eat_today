package today.what_should_i_eat_today.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.food.dto.FoodResponseDto;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.post.entity.Post;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long id;

    private FoodResponseDto food;

    private MemberResponseDto member;

    private String imageName;

    private String imagePath;

    private String title;

    private String content;

    private Long likes;

    private Long favorites;

    private boolean archived;

    private Boolean isLikedByMe;

    private Boolean isFavoriteByMe;

    private Long numberOfLikes;

    private Long numberOfFavorites;

    private LocalDateTime createdAt;


    public static PostResponseDto from(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.id = post.getId();
        dto.title = post.getTitle();
        dto.content = post.getContent();
        dto.archived = post.isArchived();
        dto.isLikedByMe = post.getIsLikedByMe();
        dto.numberOfLikes = post.getNumberOfLikes();
        dto.isFavoriteByMe = post.getIsFavoriteByMe();
        dto.numberOfFavorites = post.getNumberOfFavorites();

        dto.imageName = post.getAttachment().getName();
        dto.imagePath = post.getAttachment().getPath();
        dto.createdAt = post.getCreatedAt();


        dto.member = new MemberResponseDto(post.getMember());
        dto.food = FoodResponseDto.from(post.getFood());
        dto.food.addAllFoodTags(post.getFood().getFoodTags());
        dto.food.addAllFoodCategories(post.getFood().getFoodCategories());

        return dto;
    }
}
