package today.what_should_i_eat_today.domain.category.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.category.dao.CategoryRepository;
import today.what_should_i_eat_today.domain.category.dto.CategoryAdminCommand;
import today.what_should_i_eat_today.domain.category.dto.CategoryUserCommand;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("카테고리 생성 테스트")
    void test1() {

        CategoryAdminCommand command1 = CategoryAdminCommand.builder().name("카테고리").description("test").build();
        CategoryAdminCommand command2 = CategoryAdminCommand.builder().name("").description("test").visible(true).build();

        Long categoryId1 = categoryService.createCategory(command1);

        em.flush();
        em.clear();

        Category category = em.find(Category.class, categoryId1);

        assertEquals(categoryId1, category.getId());
        assertTrue(category.getVisible());
        assertThrows(InvalidStatusException.class, () -> categoryService.createCategory(command2), "이름을 입력하지 않으면 에러가 나야 한다.");
    }

    @Test
    @DisplayName("카테고리 리스트 테스트 (유저)")
    void test2() {
        for (int i=0; i<30; i++) {
            Category category = Category.builder().name("카테고리" + i).visible(true).build();
            em.persist(category);
        }

        CategoryUserCommand commandUser1 = CategoryUserCommand.builder().name("카테고리").build();
        CategoryUserCommand commandUser2 = CategoryUserCommand.builder().name("카테고리29").build();

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Category> categoryList1 = categoryService.getCategoryList(commandUser1, pageRequest);
        Page<Category> categoryList2 = categoryService.getCategoryList(commandUser2, pageRequest);

        assertEquals(10, categoryList1.getContent().size(), () -> "카테고리가 정확히 검색 되어야 한다.");
        assertEquals(1, categoryList2.getContent().size(), () -> "카테고리가 정확히 검색 되어야 한다.");
        assertEquals("카테고리29", categoryList2.getContent().get(0).getName(), () -> "카테고리가 정확히 검색 되어야 한다.");
    }

    @Test
    @DisplayName("카테고리 리스트 테스트 (관리자)")
    void test3() {
        Admin admin = Admin.builder().email("test").build();
        em.persist(admin);
        for (int i=0; i<30; i++) {
            Category category = Category.builder().name("카테고리" + i).admin(admin).visible(true).build();
            em.persist(category);
        }
        em.flush();
        em.clear();

        CategoryAdminCommand commandAdmin1 = CategoryAdminCommand.builder().name("카테고리").build();
        CategoryAdminCommand commandAdmin2 = CategoryAdminCommand.builder().name("카테고리29").build();

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Category> categoryList1 = categoryService.getCategoryList(commandAdmin1, pageRequest);
        Page<Category> categoryList2 = categoryService.getCategoryList(commandAdmin2, pageRequest);

        assertEquals(10, categoryList1.getContent().size(), () -> "카테고리가 정확히 검색 되어야 한다.");
        assertEquals(1, categoryList2.getContent().size(), () -> "카테고리가 정확히 검색 되어야 한다.");
        assertEquals("카테고리29", categoryList2.getContent().get(0).getName(), () -> "카테고리가 정확히 검색 되어야 한다.");
    }

    @Test
    @DisplayName("카테고리 업데이트")
    void test4() {
        Category category1 = Category.builder().name("test").description("test").visible(true).build();
        Category category2 = Category.builder().name("test").description("test").visible(true).build();

        em.persist(category1);
        em.persist(category2);
        em.flush();
        em.clear();

        CategoryAdminCommand command1 = CategoryAdminCommand.builder().id(category1.getId()).name("수정1").description("수정1").visible(false).build();
        CategoryAdminCommand command2 = CategoryAdminCommand.builder().id(category2.getId()).name("").description("수정1").visible(false).build();

        categoryService.updateCategory(command1);


        Category category = em.find(Category.class, command1.getId());

        assertEquals("수정1", category.getName(), () -> "카테고리 이름이 적절하게 수정되어야 한다.");
        assertEquals("수정1", category.getDescription(), () -> "카테고리 상세내용이 적절하게 수정되어야 한다.");
        assertFalse(category.getVisible(), () -> "카테고리 노출여부가 적절하게 수정되어야 한다.");
        assertThrows(InvalidStatusException.class, () -> categoryService.updateCategory(command2), () -> "이름이 없으면 카테고리를 수정할 수 없다.");
    }


    @Test
    @DisplayName("카테고리 삭제 테스트")
    void test5() {
        Category category = Category.builder().name("이름").visible(true).description("test").build();

        em.persist(category);
        em.flush();
        em.clear();

        categoryService.deleteById(category.getId());

        Category findCategory = em.find(Category.class, category.getId());

        assertNull(findCategory);
    }

    @Test
    @DisplayName("카테고리 조회 테스트")
    void test6() {
        // TODO ADMIN 까지 한번에 fetch 로 조회하는지 검사 => 영속성 관리 벗기기
        Admin admin = Admin.builder().email("email").build();
        Category category = Category.builder().name("category").admin(admin).build();

        em.persist(admin);
        em.persist(category);
        em.flush();
        em.clear();


        Category findCategory = categoryService.findById(category.getId());

        em.detach(findCategory);
        assertNotNull(findCategory.getAdmin());
    }
}
