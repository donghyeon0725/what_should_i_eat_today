package today.what_should_i_eat_today.domain.world_cup.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.tag.dao.TagRepository;
import today.what_should_i_eat_today.domain.tag.entity.Tag;
import today.what_should_i_eat_today.domain.tag.entity.TagValidator;
import today.what_should_i_eat_today.domain.world_cup.dao.QuestionRepository;
import today.what_should_i_eat_today.domain.world_cup.dto.QuestionRequest;
import today.what_should_i_eat_today.domain.world_cup.entity.Question;
import today.what_should_i_eat_today.domain.world_cup.entity.QuestionValidator;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final TagRepository tagRepository;

    private final TagValidator tagValidator;

    private final QuestionValidator questionValidator;


    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
    }



    public Page<Question> getQuestionList(String content, Pageable pageable) {
        return questionRepository.findByContent(content, pageable);
    }

    @Transactional
    public Long createQuestion(QuestionRequest request) {

        Tag findTag = tagRepository.findById(request.getTagId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        Question question = Question.builder()
                .content(request.getContent())
                .tag(findTag)
                .createAt(LocalDateTime.now())
                .build();

        questionRepository.save(question);

        return question.getId();
    }

    @Transactional
    public Long updateQuestion(QuestionRequest request) {
        request.updateValidateCheck();

        Question question = questionRepository.findById(request.getQuestionId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        Tag findTag = tagRepository.findById(request.getTagId()).orElse(null);


        // tag ????????? ????????? Question Entity ?????? ?????? => tag ??? ???????????? ????????? Question ?????? ?????????
        question.changeTag(findTag, tagValidator);
        question.changeContent(request.getContent());

        return question.getId();
    }

    @Transactional
    public void deleteQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        // ???????????? ?????? ????????? ?????? ?????? ?????? => Question ??? ???????????? ???????????? ????????? Question ?????? ????????? ????????? ????????? ??? ?????? ????????? ????????? ??????
        questionValidator.validateForDelete(question);
        questionRepository.delete(question);
    }

    public Page<Question> findQuestionsByTag(String tagName, Pageable pageable) {
        return questionRepository.findByContent(tagName, pageable);
    }

}
