package today.what_should_i_eat_today.domain.world_cup.application;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.tag.dao.TagRepository;
import today.what_should_i_eat_today.domain.tag.entity.Tag;
import today.what_should_i_eat_today.domain.tag.entity.TagValidator;
import today.what_should_i_eat_today.domain.world_cup.dao.QuestionPackageRepository;
import today.what_should_i_eat_today.domain.world_cup.dao.QuestionRepository;
import today.what_should_i_eat_today.domain.world_cup.dto.QuestionRequest;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;
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


        // tag 유효성 검사는 Question Entity 에서 호출 => tag 를 변경하는 주체가 Question 이기 때문에
        question.changeTag(findTag, tagValidator);
        question.changeContent(request.getContent());

        return question.getId();
    }

    @Transactional
    public void deleteQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        // 질문으로 만든 묶음이 있을 경우 예외 => Question 를 삭제하는 상황이기 때문에 Question 에서 유효성 검사를 진행할 수 없고 서비스 측에서 호출
        questionValidator.validateForDelete(question);
        questionRepository.delete(question);
    }

    public Page<Question> findQuestionsByTag(String tagName, Pageable pageable) {
        return questionRepository.findByContent(tagName, pageable);
    }

}
