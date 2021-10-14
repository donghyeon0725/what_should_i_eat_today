package today.what_should_i_eat_today.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.food.dto.FoodDto;
import today.what_should_i_eat_today.domain.food.dto.FoodResponseDto;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.dto.MemberResponseDto;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.Attachment;
import today.what_should_i_eat_today.domain.model.AttachmentResponseDto;
import today.what_should_i_eat_today.domain.post.entity.Post;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    }
}
