package Qpick.server.global.exception;

import Qpick.server.global.error.code.status.BaseErrorCode;
import Qpick.server.global.error.code.status.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
