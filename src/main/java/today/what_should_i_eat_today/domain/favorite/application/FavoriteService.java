package today.what_should_i_eat_today.domain.favorite.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.favorite.dao.FavoriteRepository;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.post.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    /**
     *
     */
    public List<Post> getMyFavoritePosts(Long memberId) {

        // 내가 찜한 게시물을 가져온다 (단, 내가 작성한 글에는 찜을 할 수 없으며, 불러온 찜목록에서도 내가 작성한 글에 대해서는 제외 조건이 있어야 한다)
        // query dsl 로 개선할 필요가 있어보인다
        List<Favorite> favorites = favoriteRepository.findAllByMemberId(memberId);

        List<Post> collect = favorites.stream().map(Favorite::getPost).collect(Collectors.toList());

        collect.forEach(post -> post.getTitle());

        return collect;
    }
}
