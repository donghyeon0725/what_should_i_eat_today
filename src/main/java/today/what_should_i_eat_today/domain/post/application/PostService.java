package today.what_should_i_eat_today.domain.post.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.activity.dto.PostCreateCommand;
import today.what_should_i_eat_today.domain.activity.dto.PostUpdateCommand;
import today.what_should_i_eat_today.domain.favorite.dao.FavoriteRepository;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.food.dao.FoodRepository;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.likes.dao.LikesRepository;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.Attachment;
import today.what_should_i_eat_today.domain.post.dao.PostRepository;
import today.what_should_i_eat_today.domain.post.entity.FoodValidator;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.tag.dao.TagRepository;
import today.what_should_i_eat_today.domain.tag.dto.TagRequest;
import today.what_should_i_eat_today.global.common.application.file.StorageService;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final StorageService storageService;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final FoodRepository foodRepository;
    private final FavoriteRepository favoriteRepository;
    private final FoodValidator foodValidator;

    @Transactional
    public boolean updateLike(Long postId, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Optional<Likes> postLike = likesRepository.findByPostAndMember(post, member);

        if (postLike.isPresent()) {
            post.removeLike(postLike.get());
            return false;
        } else {
            Likes newPostLike = Likes.builder().member(member).post(post).build();
            post.addLike(newPostLike);
            likesRepository.save(newPostLike);
            return true;
        }
    }

    @Transactional
    public Post createPost(PostCreateCommand command) {
        Member member = memberRepository.findById(command.getMemberId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Food food = foodRepository.findById(command.getFoodId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Attachment attachment = storageService.store(command.getFile());

        Post post = Post.builder()
                .food(food)
                .member(member)
                .attachment(attachment)
                .title(command.getTitle())
                .content(command.getContent())
                .build();

        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(PostUpdateCommand command) {

        Member member = memberRepository.findById(command.getMemberId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Post post = postRepository.findById(command.getPostId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        if (!post.isPostCreator(member.getId()))
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        Attachment attachment = storageService.store(command.getFile());

        return post.update(attachment, command.getTitle(), command.getContent());
    }

    @Transactional
    public void deletePost(Long postId, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        if (!post.isPostCreator(member.getId()))
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        post.delete();
    }

    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    public Page<Post> getPostsByFoodId(Long foodId, Pageable pageable) {

        Page<Post> posts = postRepository.findAllByFoodId(foodId, pageable);

        /*
         * post { id:1, name: 김밥천국 김치볶음밥, food: 김치볶음밥 }
         * post { id:2, name: 김가네 김치볶음밥, food: 김치볶음밥 }
         * post { id:3, name: 김밥나라 김치볶음밥, food: 김치볶음밥 }
         */

        return posts;
    }

    @Transactional
    public boolean updateFavorite(Long postId, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        // 글 작성자 본인 인 경우에는 찜을 할 수 없다
        if (post.isPostCreator(member.getId()))
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        Optional<Favorite> postFavorite = favoriteRepository.findByPostAndMember(post, member);

        if (postFavorite.isPresent()) {
            post.removeFavorite(postFavorite.get());
            return false;
        } else {
            Favorite newPostFavorite = Favorite.builder().member(member).post(post).build();
            post.addFavorite(newPostFavorite);
            favoriteRepository.save(newPostFavorite);
            return true;
        }
    }


    public List<Post> getRandomPostList() {

        final long total = foodRepository.countFoodByDeletedFalse();
        Random random = new Random();
        Set<Integer> set = new HashSet<>();

        while (set.size() < 10) {
            int number = random.nextInt((int) total) + 1;

            if (!set.contains(number))
                set.add(number);
        }

        final List<Long> foodIds = foodRepository.findByRows(set.stream().collect(Collectors.toList())).stream().map(s->Long.valueOf(s.getId())).collect(Collectors.toList());

        return postRepository.findPostByFoodTop1(foodIds);
    }

    public List<Post> findByFoodsWithTags(List<TagRequest> tagRequests) {
        final List<Long> foodIds =
                getFoodByTags(tagRequests).stream().map(s -> Long.valueOf(s.getId())).collect(Collectors.toList());

        return postRepository.findPostByFoodTop1(foodIds);
    }

    public Post findByFoodWithTags(List<TagRequest> tagRequests) {
        final List<Long> foodIds =
                getFoodByTags(tagRequests).stream().map(s -> Long.valueOf(s.getId())).collect(Collectors.toList());

        Random random = new Random();
        int randomChoice = random.nextInt(foodIds.size());

        return postRepository.findPostByFood1(foodIds.get(randomChoice));
    }

    private List<Food> getFoodByTags(List<TagRequest> tagRequests) {
        final List<Long> tagIds = tagRequests.stream().map(s -> s.getTagId()).collect(Collectors.toList());
        foodValidator.validateTags(tagIds);

        return foodRepository.findByTags(tagIds);
    }

}
