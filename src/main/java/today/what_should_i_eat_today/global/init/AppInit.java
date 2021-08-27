package today.what_should_i_eat_today.global.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import today.what_should_i_eat_today.domain.favorite.dao.FavoriteRepository;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.member.application.MemberService;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.application.PostService;
import today.what_should_i_eat_today.domain.post.dao.PostRepository;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.global.common.application.file.StorageService;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class AppInit implements ApplicationRunner {

    private final StorageService storageService;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        storageService.init();

        Member member = Member.builder().name("martin").build();

        memberRepository.save(member);

        Post post1 = Post.builder().title("글1").build();
        Post post2 = Post.builder().title("글2").build();
        Post post3 = Post.builder().title("글3").build();

        postRepository.saveAll(Arrays.asList(post1, post2, post3));


        Favorite favorite1 = Favorite.builder().member(member).post(post1).build();
        Favorite favorite2 = Favorite.builder().member(member).post(post2).build();

        favoriteRepository.save(favorite1);
        favoriteRepository.save(favorite2);
    }
}
