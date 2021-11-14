package today.what_should_i_eat_today.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value", "잘못된 입력 값"),
    ALREADY_PROCESSED(400, "C001-1", "Can not update  cuz it already processed", "이미 처리되어 업데이트할 수 없습니다."),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value", "잘못된 입력 값"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found", "엔티티를 찾을 수 없음"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error", "서버 오류"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value", "잘못된 유형 값"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied", "접근이 불가합니다"),
    RESOURCE_NOT_FOUND(404, "C007", " Resource Not Found", "리소스를 찾을 수 없음"),
    USER_NOT_FOUND(404, "C008", "User Not Found", "사용자를 찾을 수 없음"),
    RESOURCE_CONFLICT(409, "C009", " Resource Conflict", "자원 충돌"),
    ALREADY_EXIST_RESOURCE(409, "C010", " Resource is Already Exist", "리소스가 이미 있습니다."),
    INVALID_INPUT_VALUE_ARGUMENT(400, "C011", "Invalid Input Value Argument", "잘못된 입력 값 인수"),


    // Member
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication", "이메일은 중복입니다"),
    Nickname_DUPLICATION(400, "M001", "Nickname is Duplication", "별명은 중복"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid", "로그인 입력이 잘못되었습니다"),

    // Coupon
    COUPON_ALREADY_USE(400, "CO001", "Coupon was already used", "로그인 입력이 잘못되었습니다"),
    COUPON_EXPIRE(400, "CO002", "Coupon was already expired", "쿠폰이 이미 만료되었습니다."),

    // Server
    UNEXPECTED_SERVER_ACTION(500, "S001", "Unexpected server action is accrued", "예기치 않은 서버 작업이 발생했습니다."),

    // Mail
    TRY_LATER(400, "E001", "Unexpected server action is accrued. try it later", "예기치 않은 서버 작업이 발생했습니다. 나중에 시도"),

    // Login
    WRONG_PASSWORD(401, "L001", "Wrong password", "잘못된 비밀번호"),

    // auth
    UNAUTHORIZED_VALUE(401, "", "No auth about this resource", "이 리소스에 대한 인증이 없습니다."),
    UNAUTHORIZED_USER(401, "", "No auth about this resource", "이 리소스에 대한 인증이 없습니다.");

    private int status;
    private final String code;
    private final String messageEn;
    private final String messageKr;

    ErrorCode(final int status, final String code, final String messageEn, final String messageKr) {
        this.status = status;
        this.messageEn = messageEn;
        this.messageKr = messageKr;
        this.code = code;
    }

    public String getMessageEn() {
        return this.messageEn;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public String getMessageKr() {
        return messageKr;
    }
}
