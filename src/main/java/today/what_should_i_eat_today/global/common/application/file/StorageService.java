package today.what_should_i_eat_today.global.common.application.file;

import org.springframework.web.multipart.MultipartFile;
import today.what_should_i_eat_today.domain.model.Attachment;

import java.io.IOException;

public interface StorageService {

    void init() throws IOException;

    Attachment store(MultipartFile file);

    void delete(String fileName);

}