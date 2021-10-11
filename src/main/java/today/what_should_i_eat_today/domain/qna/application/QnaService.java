package today.what_should_i_eat_today.domain.qna.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.admin.dao.AdminRepository;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.favorite.entity.Favorite;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.post.entity.Post;
import today.what_should_i_eat_today.domain.qna.dao.QnaRepository;
import today.what_should_i_eat_today.domain.qna.dao.QnaReviewRepository;
import today.what_should_i_eat_today.domain.qna.dto.QnaCommand;
import today.what_should_i_eat_today.domain.qna.dto.QnaDto;
import today.what_should_i_eat_today.domain.qna.dto.QnaReviewCommand;
import today.what_should_i_eat_today.domain.qna.entity.*;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;
import today.what_should_i_eat_today.global.error.exception.UserNotFoundException;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaService {

    private final QnaReviewRepository qnaReviewRepository;
    private final QnaRepository qnaRepository;
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    private final QnaValidator qnaValidator;

    public Member getMember() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public Admin getAdmin() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return adminRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public Long createQna(QnaCommand command) {
        command.createValidate();

        Member member =  getMember();
        Qna qna = Qna.builder().content(command.getQnaContent()).title(command.getTitle()).type(command.getType()).member(member).build();
        qna.notProcess();
        qnaRepository.save(qna);

        return qna.getId();
    }

    public Page<Qna> getQnaList(QnaCommand command, Pageable pageable) {
        return qnaRepository.findByTitleAndStatus(command.getTitle(), command.getQnaStatus(), pageable);
    }

    public Page<QnaDto> getMyQnaList(Pageable pageable) {
        Member member =  getMember();
        final Page<Qna> qnas = qnaRepository.findByMember(member, pageable);

        List<QnaDto> list = new ArrayList<>();
        qnas.forEach(s -> {
            if (s.getStatus() == QnaStatus.PROCESSED) {
                QnaDto dto = QnaDto.builder().qnaId(s.getId()).title(s.getTitle()).qnaContent(s.getContent()).qnaReviewContent(s.getQnaReview().getContent()).qnaStatus(s.getStatus()).build();
                list.add(dto);
            } else {
                QnaDto dto = QnaDto.builder().qnaId(s.getId()).title(s.getTitle()).qnaContent(s.getContent()).qnaStatus(s.getStatus()).build();
                list.add(dto);
            }
        });

        return new PageImpl<>(list, pageable, qnas.getTotalPages());
    }

    public Qna findById(Long qnaId) {
        return qnaRepository.findById(qnaId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Transactional
    public void updateQna(QnaCommand command) {
        final Qna qna = qnaRepository.findById(command.getQnaId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        qna.updateQna(command.getTitle(), command.getQnaContent(), qnaValidator);
    }

    @Transactional
    public void createQnaReview(QnaReviewCommand command) {
        final Qna qna = qnaRepository.findById(command.getQnaId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        Admin admin = getAdmin();

        QnaReview qnaReview = QnaReview.builder().content(command.getQnaReviewContent()).admin(admin).build();
        qna.addQnaReview(qnaReview, qnaValidator);
    }

    @Transactional
    public void updateQnaReview(QnaReviewCommand command) {
        final Qna qna = qnaRepository.findById(command.getQnaId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        qna.changeStatus(command.getQnaStatus(), qnaValidator);
        final QnaReview qnaReview = qna.getQnaReview();
        qnaReview.changeContentAndStatus(command.getQnaReviewContent(), qnaValidator);
    }

}
