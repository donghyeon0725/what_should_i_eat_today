package today.what_should_i_eat_today.domain.world_cup.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.tag.entity.Tag;
import today.what_should_i_eat_today.domain.tag.entity.TagStatus;
import today.what_should_i_eat_today.domain.world_cup.dto.QuestionRequest;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;
import today.what_should_i_eat_today.domain.world_cup.entity.Question;
import today.what_should_i_eat_today.domain.world_cup.entity.QuestionPackage;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;
import today.what_should_i_eat_today.global.error.exception.invalid.CannotExecuteException;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 질문, 묶음, 과정 일련 테스트
 * */
@SpringBootTest
@Transactional
class QuestionServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("질문 리스트 테스트")
    void test2() {
        for (int i=0; i<50; i++) {
            Question question = Question.builder().createAt(LocalDateTime.now()).content("내용" + i).build();

            em.persist(question);
        }

        em.flush();
        em.clear();


        PageRequest pageable = PageRequest.of(0, 10);
        Page<Question> case1 = questionService.getQuestionList(null, pageable);
        Page<Question> case2 = questionService.getQuestionList("내용", pageable);
        Page<Question> case3 = questionService.getQuestionList("내용49", pageable);

        assertEquals(10, case1.getContent().size(), () -> "페이징이 정확하게 처리 되어야 한다");
        assertEquals(10, case2.getContent().size(), () -> "페이징이 정확하게 처리 되어야 한다");
        assertEquals(1, case3.getContent().size(), () -> "검색이 알맞게 되어야 한다");
        assertEquals("내용49", case3.getContent().get(0).getContent(), () -> "검색이 알맞게 되어야 한다");
    }

    @Test
    @DisplayName("질문 생성 테스트")
    void test3() {
        Tag tag = Tag.builder().name("맛있는").status(TagStatus.USE).build();

        em.persist(tag);
        em.flush();
        em.clear();

        QuestionRequest request1 = QuestionRequest.builder().tagId(tag.getId()).content("맛있는 것을 좋아하세요?").build();
        QuestionRequest request2 = QuestionRequest.builder().tagId(-1L).content("맛있는 것을 좋아하세요?").build();

        Long questionId = questionService.createQuestion(request1);
        Question question = em.find(Question.class, questionId);


        assertEquals(questionId, question.getId(), () -> "질문을 저장할 수 있어야 한다.");
        assertThrows(ResourceNotFoundException.class, () -> questionService.createQuestion(request2), () -> "태그가 검색되지 않을 경우 예외가 발생해야 한다.");
    }

    @Test
    @DisplayName("질문 수정 테스트")
    void test4() {
        Tag tag1 = Tag.builder().name("맛있는").status(TagStatus.USE).build();
        Tag tag2 = Tag.builder().name("차가운").status(TagStatus.USE).build();
        Question question1 = Question.builder().tag(tag1).content("맛있는 것을 좋아하세요?").build();
        Question question2 = Question.builder().tag(tag1).content("맛있는 것을 좋아하세요?").build();

        em.persist(tag1);
        em.persist(tag2);
        em.persist(question1);
        em.persist(question2);
        em.flush();
        em.clear();

        QuestionRequest request1 = QuestionRequest.builder().questionId(question1.getId()).tagId(tag1.getId()).content("수정하였습니다").build();
        QuestionRequest request2 = QuestionRequest.builder().questionId(question2.getId()).tagId(tag2.getId()).build();
        QuestionRequest request3 = QuestionRequest.builder().build();

        Long questionId1 = questionService.updateQuestion(request1);
        Long questionId2 = questionService.updateQuestion(request2);

        // 쿼리가 나가는 것을 보려면 flush 필요
//        em.flush();
//        em.clear();

        Question findQuestion1 = em.find(Question.class, questionId1);
        Question findQuestion2 = em.find(Question.class, questionId2);

        assertEquals("수정하였습니다", findQuestion1.getContent(), () -> "질문 이름이 수정 되어야 한다.");
        assertEquals(tag2.getId(), findQuestion2.getTag().getId(), () -> "질문 태그가 수정 되어야 한다.");
        assertThrows(ResourceNotFoundException.class, () -> questionService.updateQuestion(request3), () -> "질문 아이디를 명시하지 않았을 때에는 예외가 발생해야 한다.");
    }


    @Test
    @DisplayName("질문 삭제 테스트")
    void test5() {
        Question question1 = Question.builder().content("맛있는 것을 좋아하세요?").build();

        // 의존관계가 있는 질문
        Question question2 = Question.builder().content("차가운 음식을 좋아하세요?").build();
        QuestionPackage questionPackage = QuestionPackage.builder().question(question2).build();
        Package packages = Package.builder().subject("맛").build();
        packages.addQuestionMapping(questionPackage);

        em.persist(question1);
        em.persist(question2);
        em.persist(packages);
        em.flush();
        em.clear();

        questionService.deleteQuestion(question1.getId());
        Question findQuestion = em.find(Question.class, question1.getId());

        QuestionRequest request2 = QuestionRequest.builder().questionId(question2.getId()).build();


        assertNull(findQuestion);
        assertThrows(CannotExecuteException.class, () -> questionService.deleteQuestion(question2.getId()), () -> "질문으로 만든 묶음이 존재하는 경우 삭제할 수 없습니다");
    }

    @Test
    @DisplayName("태그로 질문 찾기")
    void test6() {
        Tag tagHot = Tag.builder().name("차가운").status(TagStatus.USE).build();
        Tag tagCold = Tag.builder().name("뜨거운").status(TagStatus.USE).build();
        Tag tagOily = Tag.builder().name("기름진").status(TagStatus.USE).build();

        Question questionHot = Question.builder().content("차가운 음식 좋아하세요?").tag(tagHot).build();
        Question questionCold = Question.builder().content("뜨거운 음식 좋아하세요?").tag(tagCold).build();
        Question questionOily = Question.builder().content("기름진 음식 좋아하세요?").tag(tagOily).build();

        em.persist(tagHot);
        em.persist(tagCold);
        em.persist(tagOily);
        em.persist(questionHot);
        em.persist(questionCold);
        em.persist(questionOily);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Question> nothing = questionService.findQuestionsByTag("아무것도 없는", pageable);
        Page<Question> resultHot = questionService.findQuestionsByTag("뜨거운", pageable);
        Page<Question> resultCold = questionService.findQuestionsByTag("차가운", pageable);
        Page<Question> resultOily = questionService.findQuestionsByTag("기름진", pageable);

        assertAll(
                () -> assertEquals(0, nothing.getContent().size()),
                () -> assertEquals(1, resultHot.getContent().size()),
                () -> assertEquals(1, resultCold.getContent().size()),
                () -> assertEquals(1, resultOily.getContent().size())
        );
        assertAll(
                () -> assertEquals("뜨거운 음식 좋아하세요?", resultHot.getContent().get(0).getContent()),
                () -> assertEquals("차가운 음식 좋아하세요?", resultCold.getContent().get(0).getContent()),
                () -> assertEquals("기름진 음식 좋아하세요?", resultOily.getContent().get(0).getContent())
        );
    }

}
