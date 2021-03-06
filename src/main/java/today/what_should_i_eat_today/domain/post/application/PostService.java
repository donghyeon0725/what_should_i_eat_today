package today.what_should_i_eat_today.domain.post.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.activity.dto.PostCreateCommand;
import today.what_should_i_eat_today.domain.activity.dto.PostUpdateCommand;
import today.what_should_i_eat_today.domain.favorite.dao.FavoriteRepository;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.food.dao.FoodRepository;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.food.entity.FoodValidator;
import today.what_should_i_eat_today.domain.likes.dao.LikesRepository;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.Attachment;
import today.what_should_i_eat_today.domain.post.dao.PostRepository;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.post.entity.PostValidator;
import today.what_should_i_eat_today.domain.tag.dto.TagRequest;
import today.what_should_i_eat_today.global.common.application.file.StorageService;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;
import today.what_should_i_eat_today.global.security.UserPrincipal;

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
    private final PostValidator postValidator;


    public int myPostCount(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        return postRepository.countByMemberAndArchivedIsFalse(member);
    }

    public int likeCount(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        return likesRepository.countByMember(member);
    }

    @Transactional
    public void like(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        member.likePost(post, postValidator);
    }

    @Transactional
    public void dislike(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        member.dislikedPost(post, postValidator);
    }

    public int favoriteCount(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        return favoriteRepository.countByMember(member);
    }

    @Transactional
    public void favorite(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        member.favoritePost(post, postValidator);
    }


    @Transactional
    public void unFavorite(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        member.unFavoritePost(post, postValidator);
    }


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
    public Page<Post> getPostByMember(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        Page<Post> findPosts = postRepository.findByMember_IdAndArchivedIsFalse(memberId, pageable);

        findPosts.getContent().forEach(post -> {
            if (favoriteRepository.existsByPostAndMember(post, member)) {
                post.changeFavoriteStatus(true);
            } else {
                post.changeFavoriteStatus(false);
            }

            if (likesRepository.existsByPostAndMember(post, member)) {
                post.changeLikeStatus(true);
            } else {
                post.changeLikeStatus(false);
            }
        });


        return findPosts;
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

    public Post getPost(UserPrincipal principal, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        if (post.isArchived())
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE);

        post.getFood().getFoodTags().forEach(foodTag -> foodTag.getTag().getName());
        post.getFood().getFoodCategories().forEach(foodCategory -> foodCategory.getCategory().getName());

        post.getFood().getCountry().getName();

        if (principal != null) {
            Member member = memberRepository.findById(principal.getId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
            post.changeLikeStatus(false);
            post.changeFavoriteStatus(false);
            if (likesRepository.existsByPostAndMember(post, member))
                post.changeLikeStatus(true);
            if (favoriteRepository.existsByPostAndMember(post, member))
                post.changeFavoriteStatus(true);
        }

        return post;
    }

    public Page<Post> getPostsFavorite(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Page<Post> posts = favoriteRepository.findByMemberIdAndPost_ArchivedIsFalse(member.getId(), pageable).map(Favorite::getPost);
        posts.getContent().forEach(post -> post.getFood().getName());
        posts.getContent().forEach(post -> post.getMember().getName());

        posts.getContent().forEach(post -> {
            post.changeFavoriteStatus(true);
            if (likesRepository.existsByPostAndMember(post, member)) {
                post.changeLikeStatus(true);
            } else {
                post.changeLikeStatus(false);
            }
        });
        return posts;
    }

    public Page<Post> getPostsLiked(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Page<Post> posts = likesRepository.findByMemberId(member.getId(), pageable).map(Likes::getPost);
        posts.getContent().forEach(post -> post.getFood().getName());
        posts.getContent().forEach(post -> post.getMember().getName());

        posts.getContent().forEach(post -> {
            post.changeLikeStatus(true);
            if (favoriteRepository.existsByPostAndMember(post, member)) {
                post.changeFavoriteStatus(true);
            } else {
                post.changeFavoriteStatus(false);
            }
        });
        return posts;
    }

    public Page<Post> getPostsByFoodId(Long foodId, Pageable pageable) {

        Page<Post> posts = postRepository.findAllByFoodId(foodId, pageable);

        /*
         * post { id:1, name: ???????????? ???????????????, food: ??????????????? }
         * post { id:2, name: ????????? ???????????????, food: ??????????????? }
         * post { id:3, name: ???????????? ???????????????, food: ??????????????? }
         */

        return posts;
    }

    @Transactional
    public boolean updateFavorite(Long postId, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        // ??? ????????? ?????? ??? ???????????? ?????? ??? ??? ??????
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

        // ????????? post ??? 11????????? ?????? ??????
        if (total < 11) {
            for (int i = 0; i < total; i++) {
                int number = random.nextInt((int) total) + 1;

                if (!set.contains(number))
                    set.add(number);
            }
        } else {
            while (set.size() < 10) {
                int number = random.nextInt((int) total) + 1;

                if (!set.contains(number))
                    set.add(number);
            }
        }

        final List<Long> foodIds = foodRepository.findByRows(set.stream().collect(Collectors.toList())).stream().map(s -> Long.valueOf(s.getId())).collect(Collectors.toList());

        List<Post> randomPosts = postRepository.findPostByFoodTop1(foodIds);

        for (Post post : randomPosts) {
            post.getAttachment().getName();
            post.getMember().getName();
            post.getFood().getName();
        }

        return randomPosts;
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

    public Page<Post> getPostsRecently(UserPrincipal principal, Pageable pageable) {

        Page<Post> postPage = postRepository.findAllByArchived(false, pageable);

        postPage.getContent().forEach(post -> post.getMember().getName());

        if (principal != null) {

            Member member = memberRepository.findById(principal.getId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

            postPage.getContent().forEach(post -> {

                post.changeLikeStatus(false);
                post.changeFavoriteStatus(false);

                if (likesRepository.existsByPostAndMember(post, member)) {
                    post.changeLikeStatus(true);
                }

                // rating

                if (favoriteRepository.existsByPostAndMember(post, member)) {
                    post.changeFavoriteStatus(true);
                }
            });
        }

        return new PageImpl<>(postPage.getContent(), pageable, postPage.getTotalElements());
    }

    public Page<Post> getRecentPostsOfCurrentFood(UserPrincipal principal, Long foodId, Pageable pageable) {
        Page<Post> postPage = postRepository.findAllByFoodId(foodId, pageable);

        postPage.getContent().forEach(post -> post.getMember().getName());

        if (principal != null) {

            Member member = memberRepository.findById(principal.getId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

            postPage.getContent().forEach(post -> {

                post.changeLikeStatus(false);
                post.changeFavoriteStatus(false);

                if (likesRepository.existsByPostAndMember(post, member)) {
                    post.changeLikeStatus(true);
                }

                // rating

                if (favoriteRepository.existsByPostAndMember(post, member)) {
                    post.changeFavoriteStatus(true);
                }
            });
        }

        return new PageImpl<>(postPage.getContent(), pageable, postPage.getTotalElements());
    }
}
