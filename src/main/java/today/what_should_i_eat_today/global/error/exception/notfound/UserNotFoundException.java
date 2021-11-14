package today.what_should_i_eat_today.global.error.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import today.what_should_i_eat_today.global.error.ErrorCode;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessageEn());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
