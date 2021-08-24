package today.what_should_i_eat_today.domain.post.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.activity.dto.PostCreateCommand;
import today.what_should_i_eat_today.domain.activity.dto.PostUpdateCommand;
import today.what_should_i_eat_today.domain.food.dao.FoodRepository;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.likes.dao.LikesRepository;
import today.what_should_i_eat_today.domain.likes.entity.Likes;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.model.Attachment;
import today.what_should_i_eat_today.domain.post.dao.PostRepository;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.global.common.application.file.StorageService;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final StorageService storageService;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final FoodRepository foodRepository;


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
}
