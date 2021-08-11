package today.what_should_i_eat_today;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WhatShouldIEatTodayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatShouldIEatTodayApplication.class, args);
    }

}
