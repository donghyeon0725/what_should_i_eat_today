package today.what_should_i_eat_today;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import today.what_should_i_eat_today.global.config.AppProperties;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class WhatShouldIEatTodayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatShouldIEatTodayApplication.class, args);
    }

}
