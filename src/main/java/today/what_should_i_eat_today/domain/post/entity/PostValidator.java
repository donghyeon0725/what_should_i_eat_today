package today.what_should_i_eat_today.domain.post.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.likes.dao.LikesRepository;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceDuplicatedException;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

import java.util.Optional;


@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostValidator {

    private final LikesRepository likesRepository;

    public void validateForLikeOrFavorite(Member member, Post post) {
        Optional<Likes> find = likesRepository.findByPostAndMember(post, member);

        if (find.isPresent())
            throw new ResourceDuplicatedException(ErrorCode.RESOURCE_CONFLICT);
    }

    public void validateForDislikeOrUnFavorite(Member member, Post post) {
        Optional<Likes> find = likesRepository.findByPostAndMember(post, member);

        if (!find.isPresent())
            throw new ResourceDuplicatedException(ErrorCode.RESOURCE_CONFLICT);
    }

}
