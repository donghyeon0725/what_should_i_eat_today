package today.what_should_i_eat_today.domain.qna.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ScopedMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.activity.dao.ActivityRepository;
import today.what_should_i_eat_today.domain.activity.entity.Activity;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.member.mock.CustomMockUser;
import today.what_should_i_eat_today.domain.qna.dao.QnaRepository;
import today.what_should_i_eat_today.domain.qna.dto.QnaCommand;
import today.what_should_i_eat_today.domain.qna.dto.QnaDto;
import today.what_should_i_eat_today.domain.qna.dto.QnaReviewCommand;
import today.what_should_i_eat_today.domain.qna.entity.*;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QnaServiceTest {

    @Autowired
    private QnaService qnaService;

    @Autowired
    private QnaValidator qnaValidator;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("qna 작성하기")
    @CustomMockUser(email = "test1234@test.com", roles = "USER")
    void test1() {
        Member qnaCreator = Member.builder().email("test1234@test.com").build();

        em.persist(qnaCreator);
        em.flush();
        em.clear();

        QnaCommand command = QnaCommand.builder().title("title").qnaContent("질문좀요..").type(QnaType.ETC).build();
        QnaCommand command_fail1 = QnaCommand.builder().title("").qnaContent("질문좀요..").type(QnaType.ETC).build();
        QnaCommand command_fail2 = QnaCommand.builder().title("title").qnaContent("").type(QnaType.ETC).build();
        final Long qnaId = qnaService.createQna(command);

        final Qna findQna = em.find(Qna.class, qnaId);

        assertEquals(qnaId, findQna.getId(), "qna가 정상 생성 되어야 한다.");
        assertThrows(RuntimeException.class, () -> qnaService.createQna(command_fail1), "타이틀 길이가 1 ~ 255 자가 아니면 예외");
        assertThrows(RuntimeException.class, () -> qnaService.createQna(command_fail2), "콘텐츠가 없으면 예외");
    }

    @Test
    @DisplayName("qna 작성하기")
    @CustomMockUser(email = "test1234@test.com", roles = "USER")
    void test2() {
        Member qnaCreator = Member.builder().email("test1234@test.com").build();
        Member other = Member.builder().email("123123@test.com").build();

        for (int i=0; i<10; i++) {
            Qna qna = Qna.builder().member(qnaCreator).status(QnaStatus.NOT_PROCESSED).title("title"+i).content("content").type(QnaType.ETC).build();
            em.persist(qna);
        }

        for (int i=10; i<20; i++) {
            Qna qna = Qna.builder().member(other).status(QnaStatus.NOT_PROCESSED).title("title"+i).content("content").type(QnaType.ETC).build();
            em.persist(qna);
        }

        em.persist(qnaCreator);
        em.persist(other);
        em.flush();
        em.clear();


        PageRequest pageRequest = PageRequest.of(0, 20);
        final Page<QnaDto> qnaList = qnaService.getMyQnaList(pageRequest);

        assertEquals(10, qnaList.getContent().size(), "자신이 작성한 Qna 만 검색 되어야 한다.");

//        assertThat(qnaList.getContent())
//                .extracting("member").extracting("id")
//                .containsOnly(qnaCreator.getId()).withFailMessage("자신이 작성한 Qna 만 검색 되어야 한다.");
    }

    @Test
    @DisplayName("질문 조회")
    @CustomMockUser(email = "test1234@test.com", roles = "USER")
    void test3() {
        Member qnaCreator = Member.builder().email("test1234@test.com").build();
        Member other = Member.builder().email("123123@test.com").build();
        Qna qna_creator = Qna.builder().member(qnaCreator).status(QnaStatus.NOT_PROCESSED).title("title1").content("content").type(QnaType.ETC).build();
        Qna qna_other = Qna.builder().member(other).status(QnaStatus.NOT_PROCESSED).title("title2").content("content").type(QnaType.ETC).build();

        em.persist(qna_creator);
        em.persist(qna_other);
        em.persist(qnaCreator);
        em.persist(other);
        em.flush();
        em.clear();

        final Qna myQna = qnaService.findById(qna_creator.getId());
        final Qna otherQna = qnaService.findById(qna_other.getId());

        assertNotNull(myQna, "자신의 Qna 는 검색 되어야 한다.");
        assertNull(otherQna, "User 인 경우 남의 Qna 는 검색할 수 없다.");
    }

    @Test
    @DisplayName("QNA 업데이트 테스트")
    @CustomMockUser(email = "test1234@test.com", roles = "USER")
    void test4() {

        Member qnaCreator = Member.builder().email("test1234@test.com").build();
        Admin admin = Admin.builder().email("admin1234@test.com").build();

        Qna qna_before_process = Qna.builder().member(qnaCreator).status(QnaStatus.NOT_PROCESSED).title("title1").content("content").type(QnaType.ETC).build();
        Qna qna_after_process = Qna.builder().member(qnaCreator).status(QnaStatus.NOT_PROCESSED).title("title1").content("content").type(QnaType.ETC).build();
        QnaReview qnaReview = QnaReview.builder().content("답변").admin(admin).build();
        qna_after_process.addQnaReview(qnaReview, qnaValidator);

        em.persist(qnaCreator);
        em.persist(admin);
        em.persist(qna_before_process);
        em.persist(qna_after_process);
        em.flush();
        em.clear();


        QnaCommand command_before = QnaCommand.builder().title("수정하기").qnaContent("내용변경").qnaId(qna_before_process.getId()).build();
        QnaCommand command_empty_content = QnaCommand.builder().title("수정하기").qnaContent("").qnaId(qna_before_process.getId()).build();
        QnaCommand command_after = QnaCommand.builder().title("수정하기").qnaContent("내용변경").qnaId(qna_after_process.getId()).build();
        qnaService.updateQna(command_before);
        Qna findQna = em.find(Qna.class, qna_before_process.getId());

        assertEquals("내용변경", findQna.getContent(), "수정이 정확하게 되어야 한다.");
        assertThrows(RuntimeException.class, () -> qnaService.updateQna(command_after), "처리가 완료된 QNA 는 수정할 수 없다.");
        assertThrows(RuntimeException.class, () -> qnaService.updateQna(command_empty_content), "비어있는 컨텐츠로 업데이트할 수 없다.");
    }


    // 처리 후 상태는 Process 여야 한다.
    @Test
    @DisplayName("답글 테스트")
    @CustomMockUser(email = "admin1234@test.com", roles = "ADMIN")
    void test5() {
        Member member = Member.builder().email("user1234@test.com").build();
        Admin admin = Admin.builder().email("admin1234@test.com").build();
        Qna qna = Qna.builder().member(member).status(QnaStatus.NOT_PROCESSED).title("title1").content("content").type(QnaType.ETC).build();

        em.persist(member);
        em.persist(admin);
        em.persist(qna);
        em.flush();
        em.clear();

        QnaReviewCommand command = new QnaReviewCommand(qna.getId(), "답변을 달아줍니다");
        qnaService.createQnaReview(command);

        Qna findQna = em.find(Qna.class, qna.getId());
        final QnaReview findQnaReview = findQna.getQnaReview();
        final Page<Activity> findActivities = activityRepository.findByMember(member, PageRequest.of(0, 10));

        assertEquals("답변을 달아줍니다", findQnaReview.getContent(), "답변을 달 수 있다.");
        assertEquals(QnaStatus.PROCESSED, findQna.getStatus(), "qna는 처리 상태가 되어야 한다.");
        assertEquals(1, findActivities.getContent().size(), "qna 답변이 달리면 user에게 소식이 가야 한다.");
    }

    // TODO 답글을 달아도 미승인 처리하면 보이지 않아야 한다.
    @Test
    @DisplayName("답글 리스트 가져오기")
    @CustomMockUser(email = "test1234@test.com", roles = "USER")
    void test6() {
        Member qnaCreator = Member.builder().email("test1234@test.com").build();

        Qna qna1 = Qna.builder().member(qnaCreator).status(QnaStatus.NOT_PROCESSED).title("title1").content("content").type(QnaType.ETC).build();
        Qna qna2 = Qna.builder().member(qnaCreator).status(QnaStatus.NOT_PROCESSED).title("title2").content("content").type(QnaType.ETC).build();
        Qna qna3 = Qna.builder().member(qnaCreator).status(QnaStatus.NOT_PROCESSED).title("title3").content("content").type(QnaType.ETC).build();
        Admin admin = Admin.builder().email("admin1234@test.com").build();
        QnaReview qnaReview1 = QnaReview.builder().content("답변").admin(admin).build();
        QnaReview qnaReview2 = QnaReview.builder().content("답변").admin(admin).build();
        qna1.addQnaReview(qnaReview1, qnaValidator);
        qna2.addQnaReview(qnaReview2, qnaValidator);

        // 미승인 처리
        qna2.notProcess();

        em.persist(qna1);
        em.persist(qna2);
        em.persist(qna3);
        em.persist(admin);
        em.persist(qnaCreator);
        em.flush();
        em.clear();


        PageRequest pageRequest = PageRequest.of(0, 20);
        final Page<QnaDto> qnaList = qnaService.getMyQnaList(pageRequest);

        final List<QnaDto> result1 = qnaList.getContent().stream().filter(s -> s.getTitle().equals("title1")).collect(Collectors.toList());
        final List<QnaDto> result2 = qnaList.getContent().stream().filter(s -> s.getTitle().equals("title2")).collect(Collectors.toList());

        assertEquals("답변", result1.get(0).getQnaReviewContent(), "처리 상태가 PROCESSED 이면 달린 답변이 보인다.");
        assertNull(result2.get(0).getQnaReviewContent(), "처리 상태가 NOT_PROCESSED 이면 달린 답변이 보이지 않는다.");
    }

    @Test
    @DisplayName("답글 리스트 가져오기")
    @CustomMockUser(email = "test1234@test.com", roles = "USER")
    void test7() {
        Member qnaCreator = Member.builder().email("test1234@test.com").build();
        Qna qna1 = Qna.builder().member(qnaCreator).status(QnaStatus.PROCESSED).title("title1").content("content").type(QnaType.ETC).build();
        Qna qna2 = Qna.builder().member(qnaCreator).status(QnaStatus.NOT_PROCESSED).title("title2").content("content").type(QnaType.ETC).build();
        Qna qna3 = Qna.builder().member(qnaCreator).status(QnaStatus.NOT_PROCESSED).title("title3").content("content").type(QnaType.ETC).build();
        Admin admin = Admin.builder().email("admin1234@test.com").build();
        QnaReview qnaReview1 = QnaReview.builder().content("답변").admin(admin).build();
        QnaReview qnaReview2 = QnaReview.builder().content("답변").admin(admin).build();
        qna1.addQnaReview(qnaReview1, qnaValidator);
        qna2.addQnaReview(qnaReview2, qnaValidator);
        qna2.notProcess();

        em.persist(qna1);
        em.persist(qna2);
        em.persist(qna3);
        em.persist(admin);
        em.persist(qnaCreator);
        em.flush();
        em.clear();


        QnaCommand command1 = QnaCommand.builder().qnaStatus(QnaStatus.NOT_PROCESSED).build();
        QnaCommand command2 = QnaCommand.builder().title("title").build();

        PageRequest pageRequest = PageRequest.of(0, 20);
        final Page<Qna> qnaList1 = qnaService.getQnaList(command1, pageRequest);
        final Page<Qna> qnaList2 = qnaService.getQnaList(command2, pageRequest);

        assertEquals(2, qnaList1.getContent().size(), "status 로 검색");
        assertEquals(3, qnaList2.getContent().size(), "title 로 검색");
    }

    @Test
    @DisplayName("기존 답글 수정하기")
    @CustomMockUser(email = "admin1234@test.com", roles = "ADMIN")
    void test8() {
        Member member = Member.builder().email("user1234@test.com").build();
        Admin admin = Admin.builder().email("admin1234@test.com").build();
        Qna qna = Qna.builder().member(member).status(QnaStatus.NOT_PROCESSED).title("title1").content("content").type(QnaType.ETC).build();
        QnaReview qnaReview = QnaReview.builder().content("답변").admin(admin).build();
        qna.addQnaReview(qnaReview, qnaValidator);

        em.persist(member);
        em.persist(admin);
        em.persist(qna);
        em.flush();
        em.clear();


        QnaReviewCommand command = new QnaReviewCommand(qna.getId(), "변경");
        command.setQnaStatus(QnaStatus.NOT_PROCESSED);
        qnaService.updateQnaReview(command);


        final Qna findQna = em.find(Qna.class, qna.getId());

        assertEquals("변경", findQna.getQnaReview().getContent(), "답글 내용을 변경할 수 있다.");
        assertEquals(QnaStatus.NOT_PROCESSED, findQna.getStatus(), "답글 상태를 변경할 수 있다.");

    }

}
