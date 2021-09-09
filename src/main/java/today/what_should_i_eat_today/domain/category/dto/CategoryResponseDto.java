package today.what_should_i_eat_today.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryResponseDto {
    private Long id;

    private String name;
}
