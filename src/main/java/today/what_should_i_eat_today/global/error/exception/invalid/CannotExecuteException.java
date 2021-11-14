package today.what_should_i_eat_today.global.error.exception.invalid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import today.what_should_i_eat_today.global.error.ErrorCode;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CannotExecuteException extends RuntimeException {
    private ErrorCode errorCode;

    public CannotExecuteException(ErrorCode errorCode) {
        super(errorCode.getMessageEn());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
