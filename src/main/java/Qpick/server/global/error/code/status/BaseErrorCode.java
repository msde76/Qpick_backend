package Qpick.server.global.error.code.status;

public interface BaseErrorCode {

    String getCode();

    String getMessage();

    ErrorReasonDTO getReasonHttpStatus();
}
