package today.what_should_i_eat_today.domain.member.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import today.what_should_i_eat_today.domain.member.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

// @Import(MemberRepositoryTest.TestConfig.class) 내부에서 static class 로 생성한 경우 @Import 하지 않아도 된다
@DataJpaTest
@AutoConfigureDataJpa
class MemberRepositoryTest {

    @TestConfiguration
    static class TestConfig {
        @PersistenceContext
        private EntityManager entityManager;

        @Bean
        public JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(entityManager);
        }
    }

    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("자기자신을 제외한 중복된 닉네임이 있는 경우 true 를 반환한다")
    void returnsTrueIfThereIsADuplicateNicknameExceptForItself() {

        Member root = Member.builder()
                .nickName("root")
                .build();

        Member martin = Member.builder()
                .nickName("martin")
                .build();

        memberRepository.save(root);
        memberRepository.save(martin);

        boolean result1 = memberRepository.existsByNickNameAndIdIsNot("martin", root.getId());

        assertThat(result1).isTrue();
    }
}