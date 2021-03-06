package today.what_should_i_eat_today.global.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import today.what_should_i_eat_today.global.error.ErrorCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 예외 Response
 * */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(value = {"id", "detail"})
public class ErrorResponse {
    // 이 필드는 외부에 노출 되지 않도록 한다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String messageEn;

    @Column(nullable = false)
    private String messageKr;

    @Column(nullable = false)
    private int status;

    // Entity에서 제외하고 싶은 필드는 아래와 같은 어노테이션으로 처리한다.
    @Transient
    private List<FieldError> errors;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Date date;

    // 관리자가 알 수 있도록 자세한 내용을 담는다.
    @Column(nullable = false, length = 100000)
    private String detail;


    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
        this.messageEn = code.getMessageEn();
        this.messageKr = code.getMessageKr();
        this.status = code.getStatus();
        this.errors = errors;
        this.detail = "error List";
        this.code = code.getCode();
        this.date = new Date();
    }

    private ErrorResponse(final ErrorCode code, Exception e) {
        this.messageEn = code.getMessageEn();
        this.messageKr = code.getMessageKr();
        this.status = code.getStatus();
        this.detail = e.getMessage() != null ? e.getMessage() : e.getStackTrace().toString();
//        Arrays.stream(e.getStackTrace()).map(stackTraceElement ->
//                stackTraceElement.getLineNumber() + " : " + stackTraceElement.getMethodName() + " : " + stackTraceElement.getClassName() + "\n"
//        ).collect(Collectors.joining()) + e.getCause().getMessage()
        this.code = code.getCode();
        this.errors = new ArrayList<>();
        this.date = new Date();
    }

    private ErrorResponse(final ErrorCode code, String messageEn) {
        this.messageEn = code.getMessageEn();
        this.messageKr = code.getMessageKr();
        this.status = code.getStatus();
        this.detail = messageEn;
        this.code = code.getCode();
        this.errors = new ArrayList<>();
        this.date = new Date();
    }


    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(final ErrorCode code, Exception e) {
        return new ErrorResponse(code, e);
    }

    public static ErrorResponse of(final ErrorCode code, String message) {
        return new ErrorResponse(code, message);
    }



    public static ErrorResponse of(final ErrorCode code, final List<FieldError> errors) {
        return new ErrorResponse(code, errors);
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = ErrorResponse.FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
