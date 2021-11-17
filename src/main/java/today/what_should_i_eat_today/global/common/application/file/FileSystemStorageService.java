package today.what_should_i_eat_today.global.common.application.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
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
@Profile(value = "local")
public class FileSystemStorageService implements StorageService {


    private final Path rootLocation; // static/file
    private Path uploadPath; // c:/~~~~/static/file
    private static final String SAVED_LOCAL_URL_PATH = "http://localhost:8081/static/file/";

    public FileSystemStorageService(AppProperties appProperties) {
        this.rootLocation = Paths.get(appProperties.getFileRootLocation());
    }

    @Override
    public void init() throws IOException {

        ClassPathResource cr = new ClassPathResource(rootLocation.toString());

        if (!cr.exists()) {
            log.error("{} 디렉토리가 존재하지 않습니다", cr.getPath());
        }

        log.info("파일 업로드 디렉토리 경로 -> {}", cr.getURI().toString());

        this.uploadPath = Paths.get(cr.getURI());

    }

    @Override
    public Attachment store(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE_ARGUMENT);
            }

            Path resolvePath = this.uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));

            Files.copy(file.getInputStream(), resolvePath, StandardCopyOption.REPLACE_EXISTING); // 덮어씌우기

            return Attachment.builder()
                    .path(SAVED_LOCAL_URL_PATH + file.getOriginalFilename())
                    .name(file.getOriginalFilename())
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE_ARGUMENT);
        }
    }

    @Override
    public void delete(String fileName) {
        try {

            FileSystemUtils.deleteRecursively(uploadPath.resolve(fileName));

        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidStatusException(ErrorCode.INVALID_INPUT_VALUE_ARGUMENT);
        }
    }
}
