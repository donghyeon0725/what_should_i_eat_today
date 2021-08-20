package today.what_should_i_eat_today.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    RESOURCE_NOT_FOUND(404, "C007", " Resource Not Found"),
    USER_NOT_FOUND(404, "C008", "User Not Found"),
    RESOURCE_CONFLICT(409, "C009", " Resource Conflict"),
    ALREADY_EXIST_RESOURCE(409, "C010", " Resource is Already Exist"),
    INVALID_INPUT_VALUE_ARGUMENT(400, "C011", "Invalid Input Value Argument"),


    // Member
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),

    // Coupon
    COUPON_ALREADY_USE(400, "CO001", "Coupon was already used"),
    COUPON_EXPIRE(400, "CO002", "Coupon was already expired"),

    // Server
    UNEXPECTED_SERVER_ACTION(500, "S001", "Unexpected server action is accrued"),

    // Mail
    TRY_LATER(400, "E001", "Unexpected server action is accrued. try it later"),

    // Login
    WRONG_PASSWORD(401, "L001", "Wrong password"),

    // auth
    UNAUTHORIZED_VALUE(401, "", "No auth about this resource"),
    UNAUTHORIZED_USER(401, "", "No auth about this resource");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
