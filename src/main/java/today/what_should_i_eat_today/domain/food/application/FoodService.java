package today.what_should_i_eat_today.domain.food.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.admin.dao.AdminRepository;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.category.dao.CategoryRepository;
import today.what_should_i_eat_today.domain.category.dao.FoodCategoryRepository;
import today.what_should_i_eat_today.domain.category.entity.Category;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;
import today.what_should_i_eat_today.domain.food.dao.FoodRepository;
import today.what_should_i_eat_today.domain.food.dto.FoodAdminCommand;
import today.what_should_i_eat_today.domain.food.dto.FoodDto;
import today.what_should_i_eat_today.domain.food.dto.FoodMemberCommand;
import today.what_should_i_eat_today.domain.food.dto.FoodWithTagsAndCountryResponseDto;
import today.what_should_i_eat_today.domain.food.entity.Food;
import today.what_should_i_eat_today.domain.food.entity.FoodTag;
import today.what_should_i_eat_today.domain.food.entity.FoodValidator;
import today.what_should_i_eat_today.domain.member.dao.MemberRepository;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.domain.tag.dao.TagRepository;
import today.what_should_i_eat_today.domain.tag.entity.Tag;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.ResourceNotFoundException;
import today.what_should_i_eat_today.global.error.exception.notfound.UserNotFoundException;
import today.what_should_i_eat_today.global.security.UserPrincipal;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

    private final FoodRepository foodRepository;

    private final FoodValidator foodValidator;

    private final CategoryRepository categoryRepository;

    private final TagRepository tagRepository;

    private final FoodCategoryRepository foodCategoryRepository;

    private final MemberRepository memberRepository;

    private final AdminRepository adminRepository;

    public Member getMember() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public Admin getAdmin() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return adminRepository.findByEmail(principal.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public Page<Food> getFoodListByCategory(Long categoryId, Pageable pageable) {
        return foodRepository.findFoodsByCategory(categoryId, pageable);
    }

    public List<Food> getRandomFood(Integer count) {
        // todo : 2021.08.31 ????????? ?????? ?????? (?????? ??????, tag, country)
        return foodRepository.findFoodsRand(count);
    }

    public Long createFood(FoodMemberCommand foodCommand) {
        List<Long> categoryIds = foodCommand.getCategoryIds();
        List<Long> tagIds = foodCommand.getTagIds();


        final Food food = Food.builder().member(getMember()).build();
        food.initName(foodCommand.getName(), foodValidator);

        // food ??? food ????????? ??????
        addTagToFood(food, tagIds);

        // category ??? category ????????? ??????
        addCategoryToFood(food, categoryIds);

        foodRepository.save(food);
        food.changeStatus(false, foodValidator);

        return food.getId();
    }

    public Long createFood(FoodAdminCommand command) {
        command.validateForCreateAndUpdate();

        List<Long> categoryIds = command.getCategoryIds();
        List<Long> tagIds = command.getTagIds();

        final Food food = Food.builder().admin(getAdmin()).build();
        food.initName(command.getName(), foodValidator);

        // food ??? food ????????? ??????
        addTagToFood(food, tagIds);

        // category ??? category ????????? ??????
        addCategoryToFood(food, categoryIds);

        foodRepository.save(food);
        food.changeStatus(command.isDeleted(), foodValidator);

        return food.getId();
    }

    public void updateFood(FoodAdminCommand command) {
        command.validateForCreateAndUpdate();

        // ????????? ???????????? & tag ????????? ?????? setting

        final Food food = foodRepository.findById(command.getId()).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        food.getFoodTags().clear();

        final List<FoodCategory> foodCategories = foodCategoryRepository.findByFood(food);
        foodCategories.forEach(foodCategory -> foodCategory.getCategory().getFoodCategories().remove(foodCategory));

        List<Long> categoryIds = command.getCategoryIds();
        List<Long> tagIds = command.getTagIds();

        // food ??? food ????????? ??????
        addTagToFood(food, tagIds);

        // category ??? category ????????? ??????
        addCategoryToFood(food, categoryIds);

        foodRepository.save(food);

        food.changeStatus(command.isDeleted(), foodValidator);
        food.changeAdmin(getAdmin());
    }

    public void addCategoryToFood(Food food, List<Long> categoryIds) {
        // category ??? category ????????? ??????
        final List<Category> categories = categoryRepository.findByIdIn(categoryIds);
        categories.forEach(category -> category.addFoodMapping(FoodCategory.builder().food(food).build()));
    }

    public void addTagToFood(Food food, List<Long> tagIds) {
        final List<Tag> tags = tagRepository.findByIdIn(tagIds);
        List<FoodTag> foodTags = tags.stream().map(tag -> FoodTag.builder().tag(tag).build()).collect(Collectors.toList());
        for (FoodTag foodTag : foodTags)
            food.addFoodTags(foodTag);
    }

    public Page<Food> getFoodList(FoodDto foodDto, Pageable pageable) {
        final Page<Food> bySearch = foodRepository.findBySearch(foodDto, pageable);
        bySearch.getContent().forEach(p -> p.getFoodTags().forEach(r -> r.getTag().getName()));
        bySearch.getContent().forEach(p -> p.getFoodCategories().forEach(r -> r.getCategory().getName()));
        return bySearch;
    }


    /**
     * ??????, ???????????? ????????? ????????? ?????? ?????? ????????????
     */
    public Page<FoodWithTagsAndCountryResponseDto> getFoodListWithTagsAndCountry(String country, String tag, String search, Pageable pageable, Long categoryId) {

        Page<Food> foodPage = foodRepository.findAllWthCountryAndTagsAndCategories(country, tag, search, pageable);

        Page<FoodWithTagsAndCountryResponseDto> map = foodPage.map(food -> new FoodWithTagsAndCountryResponseDto(food, categoryId));

        return map;
    }

    /**
     * ??????, ???????????? ????????? ????????? ?????? ?????? ????????????
     */
    public Page<Food> getFoodList(String search, Pageable pageable) {

        Page<Food> findFood = foodRepository.findByNameContainingAndDeletedIsFalse(search, pageable);
        findFood.getContent().forEach(food -> food.getCountry().getName());

        return findFood;
    }

    public Food findById(Long id) {
        Food findFood = foodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        findFood.getFoodTags().forEach(r -> r.getTag().getName());
        findFood.getFoodCategories().forEach(r -> r.getCategory().getName());
        return findFood;
    }
}
