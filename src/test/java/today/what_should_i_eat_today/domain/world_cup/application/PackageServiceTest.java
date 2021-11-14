package today.what_should_i_eat_today.domain.world_cup.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.world_cup.dto.PackageRequest;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;
import today.what_should_i_eat_today.domain.world_cup.entity.*;
import today.what_should_i_eat_today.global.error.exception.invalid.CannotExecuteException;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PackageServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private PackageService packageService;

    @Test
    @DisplayName("패키지 리스트 테스트")
    void test1() {

        for (int i=0; i<50; i++) {
            Package p = Package.builder().createAt(LocalDateTime.now()).subject("주제" + i).build();

            em.persist(p);
        }

        em.flush();
        em.clear();


        PageRequest pageable = PageRequest.of(0, 10);
        Page<Package> case1 = packageService.getPackageList(null, pageable);
        Page<Package> case2 = packageService.getPackageList("주제", pageable);
        Page<Package> case3 = packageService.getPackageList("주제49", pageable);

        assertEquals(10, case1.getContent().size(), () -> "페이징이 정확하게 처리 되어야 한다");
        assertEquals(10, case2.getContent().size(), () -> "페이징이 정확하게 처리 되어야 한다");
        assertEquals(1, case3.getContent().size(), () -> "검색이 알맞게 되어야 한다");
        assertEquals("주제49", case3.getContent().get(0).getSubject(), () -> "검색이 알맞게 되어야 한다");
    }

    @Test
    @DisplayName("질문을 포함하는 패키지 리스트 테스트")
    void test2() {


        Package p1 = Package.builder().createAt(LocalDateTime.now()).subject("주제1").build();
        Package p2 = Package.builder().createAt(LocalDateTime.now()).subject("주제2").build();
        Package p3 = Package.builder().createAt(LocalDateTime.now()).subject("주제3").build();
        Package p4 = Package.builder().createAt(LocalDateTime.now()).subject("주제4").build();
        Package p5 = Package.builder().createAt(LocalDateTime.now()).subject("주제5").build();
        Package p6 = Package.builder().createAt(LocalDateTime.now()).subject("주제6").build();
        Package p7 = Package.builder().createAt(LocalDateTime.now()).subject("주제7").build();
        Package p8 = Package.builder().createAt(LocalDateTime.now()).subject("주제8").build();
        Package p9 = Package.builder().createAt(LocalDateTime.now()).subject("주제9").build();
        Package p10 = Package.builder().createAt(LocalDateTime.now()).subject("주제10").build();


        Question question = Question.builder().content("질문 1").build();
        em.persist(question);
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.persist(p5);
        em.persist(p6);
        em.persist(p7);
        em.persist(p8);
        em.persist(p9);
        em.persist(p10);

        p1.addQuestionMapping(QuestionPackage.builder().question(question).build());
        p2.addQuestionMapping(QuestionPackage.builder().question(question).build());
        p5.addQuestionMapping(QuestionPackage.builder().question(question).build());
        p9.addQuestionMapping(QuestionPackage.builder().question(question).build());

        em.flush();
        em.clear();

        PageRequest request = PageRequest.of(0, 10);


        // when
        Page<Package> packages = packageService.getPackageListByQuestion(question.getId(), request);

        assertThat(packages).extracting("subject").containsExactly("주제1", "주제2", "주제5", "주제9").withFailMessage(() -> "질문과 연관이 있는 패키지만 검색이 되어야 한다.");
    }

    @Test
    @DisplayName("묶음 생성하기")
    void test3() {
//        Package packages = Package.builder().subject("묶음").build();

        PackageRequest packageRequest = new PackageRequest();
        packageRequest.setSubject("주제");

        Long aPackage = packageService.createPackage(packageRequest);

        Package findPackage = em.find(Package.class, aPackage);

        assertEquals(aPackage, findPackage.getId(), () -> "묶음을 저장할 수 있어야 한다.");
    }

    @Test
    @DisplayName("묶음 수정하기 1")
    void test4() {
        // TODO 패키지에 질문 추가하기
        Package packages = Package.builder().subject("묶음").build();

        Question question1 = Question.builder().content("질문1").build();
        Question question2 = Question.builder().content("질문2").build();
        Question question3 = Question.builder().content("질문3").build();
        Question question4 = Question.builder().content("질문4").build();

        em.persist(packages);
        em.persist(question1);
        em.persist(question2);
        em.persist(question3);
        em.persist(question4);
        em.flush();
        em.clear();

        PackageRequest packageRequest1 = PackageRequest.builder().packageId(packages.getId()).questionId(question1.getId()).build();
        PackageRequest packageRequest2 = PackageRequest.builder().packageId(packages.getId()).questionId(question2.getId()).build();
        PackageRequest packageRequest3 = PackageRequest.builder().packageId(packages.getId()).questionId(question3.getId()).build();
        PackageRequest packageRequest4 = PackageRequest.builder().packageId(packages.getId()).questionId(question4.getId()).build();



        packageService.addQuestionToPackage(packageRequest1);
        packageService.addQuestionToPackage(packageRequest2);
        packageService.addQuestionToPackage(packageRequest3);
        packageService.addQuestionToPackage(packageRequest4);

        Package aPackage = em.find(Package.class, packages.getId());

        assertEquals(4, aPackage.getQuestionPackages().size(), () -> "묶음에 질문을 추가할 수 있어야 한다.");
        assertThat(aPackage.getQuestionPackages())
                .extracting("question")
                .extracting("id")
                .containsExactly(question1.getId(), question2.getId(), question3.getId(), question4.getId())
                .withFailMessage("묶음에 질문을 추가할 수 있어야 한다.");
    }
    @Test
    @DisplayName("묶음 수정하기 2")
    void test5() {
        // TODO 패키지에 질문 삭제하기

        // given
        Package packages = Package.builder().subject("묶음").build();

        Question question1 = Question.builder().content("질문1").build();
        Question question2 = Question.builder().content("질문2").build();
        Question question3 = Question.builder().content("질문3").build();
        Question question4 = Question.builder().content("질문4").build();

        QuestionPackage questionPackage1 = QuestionPackage.builder().question(question1).build();
        QuestionPackage questionPackage2 = QuestionPackage.builder().question(question2).build();
        QuestionPackage questionPackage3 = QuestionPackage.builder().question(question3).build();
        QuestionPackage questionPackage4 = QuestionPackage.builder().question(question4).build();

        packages.addQuestionMapping(questionPackage1);
        packages.addQuestionMapping(questionPackage2);
        packages.addQuestionMapping(questionPackage3);
        packages.addQuestionMapping(questionPackage4);

        // question 이 저장 되기 전에 package 를 먼저 persist 하면 QuestionPackage 에 question 이 없는 채로 insert 된 뒤, update 가 된다.
        // 따라서 question 을 먼저 저장한 뒤에, package 를 저장해서 QuestionPackage 가 저장 되는 시점을 Question 저장 이후 시점으로 고정한다.
        em.persist(question1);
        em.persist(question2);
        em.persist(question3);
        em.persist(question4);
        em.persist(packages);
        em.flush();
        em.clear();

        // when
        PackageRequest packageRequest1 = PackageRequest.builder().packageId(packages.getId()).questionId(question1.getId()).build();
        PackageRequest packageRequest2 = PackageRequest.builder().packageId(packages.getId()).questionId(question2.getId()).build();

        packageService.removeQuestionFromPackage(packageRequest1);
        packageService.removeQuestionFromPackage(packageRequest2);

        Package aPackage = em.find(Package.class, packages.getId());

        // then
        assertEquals(2, aPackage.getQuestionPackages().size(), () -> "묶음의 질문을 삭제하면 그 수 만큼 질문묶음이 사라진다.");
        assertThat(aPackage.getQuestionPackages())
                .extracting("question")
                .extracting("id")
                .containsExactly(question3.getId(), question4.getId())
                .withFailMessage("삭제하지 않은 질문의 질문묶음의 아이디만 남아있어야 한다.");

    }

    @Test
    @DisplayName("묶음 수정하기 3")
    void test6() {
        // TODO 패키지 주제 변경하기

        // given
        Package packages = Package.builder().subject("묶음").build();

        em.persist(packages);
        em.flush();
        em.clear();


        // when
        PackageRequest packageRequest = PackageRequest.builder().packageId(packages.getId()).subject("묶음 주제 변경").build();
        Long aLong = packageService.updatePackage(packageRequest);

        Package aPackage = em.find(Package.class, aLong);

        assertEquals("묶음 주제 변경", aPackage.getSubject(), () -> "묶음의 주제를 변경할 수 있어야 한다.");
    }


    @Test
    @DisplayName("묶음 삭제하기")
    void test7() {
        // given
        Package packages1 = Package.builder().subject("묶음1").build();
        Package packages2 = Package.builder().subject("묶음2").build();

        Course course = Course.builder().subject("과정").build();
        course.addPackageMapping(PackageCourse.builder().packages(packages1).build());

        // 묶음으로 만든 과정이 있는 경우 삭제할 수 없음 => CannotExecuteException


        em.persist(packages1);
        em.persist(packages2);
        em.persist(course);
        em.flush();
        em.clear();


        packageService.deletePackage(packages2.getId());

        // when
        Package findPackage = em.find(Package.class, packages2.getId());

        assertNull(findPackage);
        assertThrows(CannotExecuteException.class, () -> packageService.deletePackage(packages1.getId()), () -> "묶음으로 만든 과정이 존재하는 경우 삭제할 수 없다.");

    }

}
