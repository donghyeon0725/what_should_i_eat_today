package today.what_should_i_eat_today.domain.country.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.what_should_i_eat_today.domain.country.application.CountryService;
import today.what_should_i_eat_today.domain.country.entity.Country;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CountryApi {

    private final CountryService countryService;

    @GetMapping("countries")
    public ResponseEntity<?> getCountries() {

        List<Country> countries = countryService.getCountries();

        return ResponseEntity.ok(countries);
    }
}
