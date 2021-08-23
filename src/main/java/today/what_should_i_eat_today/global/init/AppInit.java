package today.what_should_i_eat_today.global.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import today.what_should_i_eat_today.global.common.application.file.StorageService;

@Component
@RequiredArgsConstructor
public class AppInit implements ApplicationRunner {

    private final StorageService storageService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        storageService.init();
    }
}
