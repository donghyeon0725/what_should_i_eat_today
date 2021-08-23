package today.what_should_i_eat_today.global.common.application.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import today.what_should_i_eat_today.domain.model.Attachment;
import today.what_should_i_eat_today.global.config.AppProperties;
import today.what_should_i_eat_today.global.error.ErrorCode;
import today.what_should_i_eat_today.global.error.exception.InvalidStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(AppProperties appProperties) {
        this.rootLocation = Paths.get(appProperties.getFileRootLocation());
    }

    @Override
    public void init() {
        try {
            boolean exists = Files.exists(rootLocation);
            if (!exists) Files.createDirectory(rootLocation);
        } catch (IOException e) {
            log.error("{} 디렉토리를 생성할 수 없습니다 (상위 디렉토리가 없는 경우 발생합니다)", rootLocation);
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Attachment store(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE_ARGUMENT);
            }

            Path resolvePath = this.rootLocation.resolve(Objects.requireNonNull(file.getOriginalFilename()));

            Files.copy(file.getInputStream(), resolvePath, StandardCopyOption.REPLACE_EXISTING); // 덮어씌우기

            return Attachment.builder()
                    .path(resolvePath.toUri().getPath())
                    .name(file.getOriginalFilename())
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE_ARGUMENT);
        }
    }
}
