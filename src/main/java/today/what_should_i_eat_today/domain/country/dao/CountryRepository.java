package today.what_should_i_eat_today.domain.country.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import today.what_should_i_eat_today.domain.country.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
