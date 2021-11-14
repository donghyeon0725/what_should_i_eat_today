package today.what_should_i_eat_today.domain.tag.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.tag.dto.TagRequestFromAdmin;
import today.what_should_i_eat_today.domain.tag.dto.TagRequestFromMember;
import today.what_should_i_eat_today.domain.tag.entity.Tag;
import today.what_should_i_eat_today.domain.tag.entity.TagStatus;
import today.what_should_i_eat_today.global.error.exception.invalid.ResourceConflictException;
import today.what_should_i_eat_today.global.security.test.WithMockCustomUser;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TagServiceTest {


    @Autowired
    private EntityManager em;

    @Autowired
    private TagService tagService;

    @Test
    @DisplayName("태그 리스트 조회")
    void test1() {

        for (int i=0; i<50; i++) {
            Tag tag = Tag.builder().name("태그" + i).build();

            em.persist(tag);
        }

        em.flush();
        em.clear();

        PageRequest pageable = PageRequest.of(0, 10);
        Page<Tag> case1 = tagService.getTagList(null, pageable);
        Page<Tag> case2 = tagService.getTagList("태그", pageable);
        Page<Tag> case3 = tagService.getTagList("태그49", pageable);


        assertEquals(10, case1.getContent().size(), () -> "태그가 10건 검색 되어야 한다.");
        assertEquals(10, case2.getContent().size(), () -> "태그가 10건 검색 되어야 한다.");
        assertEquals(1, case3.getContent().size(), () -> "태그가 1건 검색 되어야 한다.");
        assertEquals("태그49", case3.getContent().get(0).getName(), () -> "적절한 태그가 검색되어야 한다.");
    }


    @Test
    @DisplayName("관리자 태그 생성 테스트")
    @WithMockCustomUser(username = "email1234@test.com", role = "ADMIN")
    void test2() {
        // given
        Admin admin = Admin.builder().email("email1234@test.com").build();
        Tag tag = Tag.builder().name("이미 존재하는 태그").build();

        em.persist(admin);
        em.persist(tag);
        em.flush();
        em.clear();

        // when
        TagRequestFromAdmin request1 = TagRequestFromAdmin.builder().name("test").status(TagStatus.USE).build();
        TagRequestFromAdmin request2 = TagRequestFromAdmin.builder().name("이미 존재하는 태그").status(TagStatus.USE).build();

        Long createTagId = tagService.createTag(request1);
        Tag findTag = em.find(Tag.class, createTagId);


        // then
        assertEquals(createTagId, findTag.getId(), () -> "태그가 정상 생성 되어야 합니다.");
        assertThrows(ResourceConflictException.class, () -> tagService.createTag(request2), () -> "중복된 이름으로 태그를 생성할 수 없습니다.");
    }


    @Test
    @DisplayName("유저 태그 생성 테스트")
    @WithMockCustomUser(username = "email1234@test.com", role = "USER")
    void test3() {
        // given
        Member member = Member.builder().email("email1234@test.com").build();
        Tag tag = Tag.builder().name("이미 존재하는 태그").build();

        em.persist(member);
        em.persist(tag);
        em.flush();
        em.clear();

        // when
        TagRequestFromMember request1 = TagRequestFromMember.builder().name("test").status(TagStatus.USE).build();
        TagRequestFromMember request2 = TagRequestFromMember.builder().name("이미 존재하는 태그").status(TagStatus.USE).build();

        Long createTagId = tagService.createTag(request1);
        Tag findTag = em.find(Tag.class, createTagId);


        // then
        assertEquals(createTagId, findTag.getId(), () -> "태그가 정상 생성 되어야 합니다.");
        assertThrows(ResourceConflictException.class, () -> tagService.createTag(request2), () -> "중복된 이름으로 태그를 생성할 수 없습니다.");
    }


    @Test
    @DisplayName("태그 삭제 테스트")
    @WithMockCustomUser(username = "email1234@test.com", role = "ADMIN")
    void test4() {
        // given
        Admin admin = Admin.builder().email("email1234@test.com").build();
        Tag tag = Tag.builder().build();
        em.persist(admin);
        em.persist(tag);
        em.flush();
        em.clear();

        // when
        TagRequestFromAdmin tagRequest1 = TagRequestFromAdmin.builder().tagId(tag.getId()).build();
        TagRequestFromAdmin tagRequest2 = TagRequestFromAdmin.builder().tagId(null).build();
        tagService.deleteTag(tagRequest1);

        Tag findTag = em.find(Tag.class, tag.getId());
//        IllegalArgumentException
//        EmptyResultDataAccessException
//        InvalidDataAccessApiUsageException

        // then
        assertNull(findTag);
        assertThrows(Exception.class, () -> tagService.deleteTag(tagRequest2), () -> "존재하지 않는 태그를 삭제할 때 에러가 나야 한다.");
    }


}
