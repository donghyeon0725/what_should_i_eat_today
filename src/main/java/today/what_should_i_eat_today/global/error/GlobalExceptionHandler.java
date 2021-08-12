package today.what_should_i_eat_today.global.error;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import today.what_should_i_eat_today.global.common.ErrorResponse;

/**
 * 예외 핸들러
 *
 * */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 이 메소드가 Exception Handler로 사용될 수 있음을 알리는 핸들러 클래스
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handlerAllException(Exception ex, WebRequest request) {

        final ErrorResponse exceptionResponse =
                // getDescription 은 에러 내용에 관한 것 같다.
                ErrorResponse.of(ErrorCode.UNEXPECTED_SERVER_ACTION, ex);

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
