package today.what_should_i_eat_today.global.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import today.what_should_i_eat_today.global.error.ErrorCode;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidStatusException extends BusinessException {

    public InvalidStatusException(ErrorCode errorCode) {
        super(errorCode.getMessageEn(), errorCode);
    }

}
