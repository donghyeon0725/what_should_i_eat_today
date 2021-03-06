package today.what_should_i_eat_today.global.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import today.what_should_i_eat_today.global.error.ErrorCode;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BusinessException {


    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessageKr(), errorCode);
    }

    public ResourceNotFoundException(String entity, String field, Long id) {
        super(String.format("%s is not found - field: %s - id: %d", entity, field, id), ErrorCode.ENTITY_NOT_FOUND);
    }

}
