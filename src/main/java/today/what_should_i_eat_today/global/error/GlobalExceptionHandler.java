package today.what_should_i_eat_today.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import today.what_should_i_eat_today.global.common.ErrorResponse;
import today.what_should_i_eat_today.global.error.exception.BusinessException;

/**
 * 예외 핸들러
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /** @Valid */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /** enum type */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        final ErrorResponse response = ErrorResponse.of(e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /** @PathVariable */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    // << 이 부분 추가
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /** BusinessException */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("handleBusinessException", e);
        final ErrorResponse response = ErrorResponse.of(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // 이 메소드가 Exception Handler로 사용될 수 있음을 알리는 핸들러 클래스
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handlerAllException(Exception ex, WebRequest request) {

        final ErrorResponse exceptionResponse =
                // getDescription 은 에러 내용에 관한 것 같다.
                ErrorResponse.of(ErrorCode.UNEXPECTED_SERVER_ACTION, ex);

        log.error(ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
