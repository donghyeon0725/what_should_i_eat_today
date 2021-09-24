package today.what_should_i_eat_today.domain.category.entity;

import lombok.*;
import today.what_should_i_eat_today.domain.admin.entity.Admin;
import today.what_should_i_eat_today.global.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JoinColumn(nullable = false)
@ManyToOne(fetch = FetchType.LAZY)
private Admin admin;

    private String name;

    @Lob
    private String description;


    private Boolean visible;

    @Builder.Default
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodCategory> foodCategories = new ArrayList<>();

    public void addFoodMapping(FoodCategory foodCategory) {
        foodCategories.add(foodCategory);
        foodCategory.addCategoryMapping(this);
    }

    public void changeName(String name, CategoryValidator validator) {
        validator.validateName(name);
        this.name = name;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeVisible(Boolean visible) {
        if (visible != null)
            this.visible = visible;
    }

}
