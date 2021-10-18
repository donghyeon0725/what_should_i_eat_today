package today.what_should_i_eat_today.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import today.what_should_i_eat_today.global.error.ErrorCode;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceDuplicatedException extends RuntimeException {

    private ErrorCode errorCode;

    public ResourceDuplicatedException(String entity, String field, String value) {
        super(String.format("%s is duplicated - entity: %s -field: %s", value, entity, field));
        this.errorCode = ErrorCode.Nickname_DUPLICATION;
    }

    public ResourceDuplicatedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
