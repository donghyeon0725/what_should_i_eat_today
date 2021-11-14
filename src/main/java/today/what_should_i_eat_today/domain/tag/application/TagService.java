package today.what_should_i_eat_today.domain.tag.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.admin.dao.AdminRepository;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.tag.dao.TagRepository;
import today.what_should_i_eat_today.domain.tag.dto.TagRequestFromAdmin;
import today.what_should_i_eat_today.domain.tag.dto.TagRequestFromMember;
import today.what_should_i_eat_today.domain.tag.entity.Tag;
import today.what_should_i_eat_today.domain.tag.entity.TagStatus;
import today.what_should_i_eat_today.domain.tag.entity.TagValidator;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.UserNotFoundException;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;

    private final AdminRepository adminRepository;

    private final MemberRepository memberRepository;

    private final TagValidator tagValidator;

    public Page<Tag> getTagList(String name, Pageable pageable) {
        return tagRepository.findByNameContains(name, pageable);
    }

    public Long createTag(TagRequestFromAdmin tagRequest) {
        UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Admin admin = adminRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        TagStatus status = tagRequest.getStatus() == null ? TagStatus.USE : tagRequest.getStatus();
        Tag tag = Tag.builder().name(tagRequest.getName()).status(status).admin(admin).build();

        tagValidator.validateForCreate(tag);
        tagRepository.save(tag);


        return tag.getId();
    }

    public Long createTag(TagRequestFromMember tagRequest) {
        UserPrincipal principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        TagStatus status = tagRequest.getStatus() == null ? TagStatus.USE : tagRequest.getStatus();
        Tag tag = Tag.builder().name(tagRequest.getName()).status(status).member(member).build();

        tagValidator.validateForCreate(tag);
        tagRepository.save(tag);

        return tag.getId();
    }

    public void deleteTag(TagRequestFromAdmin tagRequest) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Admin admin = adminRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        tagValidator.validateForDelete(admin);
        tagRepository.deleteById(tagRequest.getTagId());
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public Page<Tag> getTags(String name, Pageable pageable) {
        return tagRepository.findByNameContains(name, pageable);
    }
}
