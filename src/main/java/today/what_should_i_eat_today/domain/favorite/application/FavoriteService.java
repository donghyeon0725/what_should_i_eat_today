package today.what_should_i_eat_today.domain.favorite.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.favorite.dao.FavoriteRepository;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.post.dao.PostRepository;
import today.what_should_i_eat_today.domain.post.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public List<Post> getMyFavorites(Long memberId) {

        List<Favorite> favorites = favoriteRepository.findAllByMemberId(memberId);

        List<Post> collect = favorites.stream().map(Favorite::getPost).collect(Collectors.toList());

        collect.forEach(post -> post.getTitle());

        return collect;
    }
}
