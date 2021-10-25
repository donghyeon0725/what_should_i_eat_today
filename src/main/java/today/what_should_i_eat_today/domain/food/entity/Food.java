package today.what_should_i_eat_today.domain.food.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.domain.category.entity.FoodCategory;
import today.what_should_i_eat_today.domain.country.entity.Country;
import today.what_should_i_eat_today.domain.member.entity.Member;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;

    private String name;

    @ColumnDefault(value = "false")
    @Column(nullable = false)
    private Boolean deleted;

    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    @Builder.Default
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodTag> foodTags = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "food")
    private List<FoodCategory> foodCategories = new ArrayList<>();

    public void addFoodTag(FoodTag foodTag) {
        this.foodTags.add(foodTag);
        foodTag.mappingToFood(this);
    }

    public void addFoodTags(FoodTag... foodTag) {
        for (FoodTag tag : foodTag) {
            addFoodTag(tag);
        }
    }

    public void initName(String name, FoodValidator foodValidator) {
        foodValidator.validateName(name);
        this.name = name;
    }

    public void changeName(String name, FoodValidator foodValidator) {
        foodValidator.validateName(this, name);
        this.name = name;
    }

    public void changeStatus(boolean deleted, FoodValidator foodValidator) {
        foodValidator.validateFoodForCreate(this);
        this.deleted = deleted;
    }

    public void changeAdmin(Admin admin) {
        if (admin != null)
            this.admin = admin;
    }

}
