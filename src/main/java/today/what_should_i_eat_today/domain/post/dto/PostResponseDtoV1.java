package today.what_should_i_eat_today.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.food.dto.FoodResponseDto;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.post.entity.Post;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDtoV1 {
    private Long id;

    private FoodResponseDto food;

    private MemberResponseDto member;

    private String imageName;

    private String imagePath;

    private String title;

    private String content;

    private boolean archived;

    private Boolean isLikedByMe;

    private Boolean isFavoriteByMe;

    private Long numberOfLikes;

    private Long numberOfFavorites;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public PostResponseDtoV1(Post post) {
        this.id = post.getId();
        this.food = new FoodResponseDto();
        this.food.setId(post.getFood().getId());
        this.member = new MemberResponseDto(post.getMember());
        this.title = post.getTitle();
        this.imageName = post.getAttachment().getName();
        this.imagePath = post.getAttachment().getPath();
        this.content = post.getContent();
        this.archived = post.isArchived();
        this.isLikedByMe = post.getIsLikedByMe();
        this.numberOfLikes = post.getNumberOfLikes();
        this.isFavoriteByMe = post.getIsFavoriteByMe();
        this.numberOfFavorites = post.getNumberOfFavorites();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

    public static PostResponseDtoV1 fromPostRecentlyDto(Post post) {
        PostResponseDtoV1 dto = new PostResponseDtoV1();
        dto.id = post.getId();
        dto.member = new MemberResponseDto(post.getMember());
        dto.title = post.getTitle();
        dto.imageName = post.getAttachment().getName();
        dto.imagePath = post.getAttachment().getPath();
        dto.content = post.getContent();
        dto.archived = post.isArchived();
        dto.isLikedByMe = post.getIsLikedByMe();
        dto.numberOfLikes = post.getNumberOfLikes();
        dto.isFavoriteByMe = post.getIsFavoriteByMe();
        dto.numberOfFavorites = post.getNumberOfFavorites();
        dto.createdAt = post.getCreatedAt();
        dto.updatedAt = post.getUpdatedAt();
        return dto;
    }
}
