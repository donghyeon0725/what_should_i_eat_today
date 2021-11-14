package today.what_should_i_eat_today.domain.tag.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import today.what_should_i_eat_today.global.error.ErrorCode;
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidTagException extends RuntimeException {
    private ErrorCode errorCode;

    public InvalidTagException(ErrorCode errorCode) {
        super(errorCode.getMessageEn());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
