package today.what_should_i_eat_today.domain.activity.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.activity.dto.ActivityCommand;
import today.what_should_i_eat_today.domain.activity.entity.Activity;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.member.mock.CustomMockUser;
import today.what_should_i_eat_today.global.error.exception.invalid.UnauthorizedUserException;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private EntityManager em;


    @Test
    @DisplayName("내 소식 모아보기")
    @CustomMockUser(email = "test1234@naver.com", roles = "USER")
    void test1() {
        Member member = Member.builder().name("test").nickName("test").email("test1234@naver.com").build();
        em.persist(member);

        for (int i=0; i<20; i++) {
            Activity activity = Activity.builder().member(member).build();
            em.persist(activity);
        }

        em.flush();
        em.clear();

        PageRequest pageRequest1 = PageRequest.of(0, 10);
        PageRequest pageRequest2 = PageRequest.of(0, 1);
        final Page<Activity> activityList1 = activityService.getActivityList(pageRequest1);
        final Page<Activity> activityList2 = activityService.getActivityList(pageRequest2);

        assertEquals(10, activityList1.getContent().size(), "정확하게 10건 검색되어야 한다.");
        assertEquals(1, activityList2.getContent().size(), "정확하게 1건 검색되어야 한다.");
    }


    @Test
    @DisplayName("내 소식 삭제하기")
    @CustomMockUser(email = "test1234@naver.com", roles = "USER")
    void test2() {
        Member member = Member.builder().name("test").nickName("test").email("test1234@naver.com").build();
        Member other = Member.builder().name("test").nickName("test").email("other1234@naver.com").build();

        Activity activity_member = Activity.builder().member(member).build();
        Activity activity_other = Activity.builder().member(other).build();

        em.persist(member);
        em.persist(other);
        em.persist(activity_member);
        em.persist(activity_other);
        em.flush();
        em.clear();

        ActivityCommand command_member = ActivityCommand.builder().id(activity_member.getId()).build();
        ActivityCommand command_other = ActivityCommand.builder().id(activity_other.getId()).build();
        activityService.deleteActivity(command_member);
        final Activity activity = em.find(Activity.class, activity_member.getId());


        assertNull(activity, "소슥을 지울 수 있다.");
        assertThrows(UnauthorizedUserException.class, () -> activityService.deleteActivity(command_other), "다른 사람의 소식을 지울 수 없다.");
    }


}
