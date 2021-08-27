package today.what_should_i_eat_today.domain.likes.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.likes.dao.LikesRepository;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.post.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {

    private final LikesRepository likesRepository;

    public List<Post> getMyLikesPosts(Long memberId, Pageable pageable) {

        List<Likes> likes = likesRepository.findAllByMemberId(memberId, pageable);

        List<Post> collect = likes.stream().map(Likes::getPost).collect(Collectors.toList());

        collect.forEach(post -> post.getTitle());

        return collect;
    }
}
