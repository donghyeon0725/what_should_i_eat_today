package today.what_should_i_eat_today.domain.country.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.what_should_i_eat_today.domain.country.dao.CountryRepository;
import today.what_should_i_eat_today.domain.country.entity.Country;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CountryService {

    private final CountryRepository countryRepository;


    public List<Country> getCountries() {

        return countryRepository.findAll();
    }
}
