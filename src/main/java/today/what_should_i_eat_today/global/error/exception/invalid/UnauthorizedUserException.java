package today.what_should_i_eat_today.global.error.exception.invalid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import today.what_should_i_eat_today.global.error.ErrorCode;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedUserException extends RuntimeException {
    private ErrorCode errorCode;

    public UnauthorizedUserException(ErrorCode errorCode) {
        super(errorCode.getMessageEn());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
