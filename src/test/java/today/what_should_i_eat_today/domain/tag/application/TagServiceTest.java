package today.what_should_i_eat_today.domain.tag.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.tag.dao.TagRepository;
import today.what_should_i_eat_today.domain.tag.entity.Tag;
import today.what_should_i_eat_today.domain.world_cup.entity.Package;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

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

}
