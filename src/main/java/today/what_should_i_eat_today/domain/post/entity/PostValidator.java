package today.what_should_i_eat_today.domain.post.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.favorite.dao.FavoriteRepository;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.likes.dao.LikesRepository;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;
import today.what_should_i_eat_today.global.error.exception.invalid.ResourceDuplicatedException;

import java.util.Optional;


@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostValidator {

    private final LikesRepository likesRepository;

    private final FavoriteRepository favoriteRepository;

    public void validateForLike(Member member, Post post) {
        Optional<Likes> find = likesRepository.findByPostAndMember(post, member);

        if (find.isPresent())
            throw new ResourceDuplicatedException(ErrorCode.RESOURCE_CONFLICT);
    }

    public void validateForFavorite(Member member, Post post) {
        Optional<Favorite> find = favoriteRepository.findByPostAndMember(post, member);

        if (find.isPresent())
            throw new ResourceDuplicatedException(ErrorCode.RESOURCE_CONFLICT);
    }

    public void validateForDislike(Member member, Post post) {
        Optional<Likes> find = likesRepository.findByPostAndMember(post, member);

        if (!find.isPresent())
            throw new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND);
    }

    public void validateForUnFavorite(Member member, Post post) {
        Optional<Favorite> find = favoriteRepository.findByPostAndMember(post, member);

        if (!find.isPresent())
            throw new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND);
    }

}
