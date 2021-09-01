package today.what_should_i_eat_today.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.post.entity.Post;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Food food;

    private Page<Post> posts;

}
