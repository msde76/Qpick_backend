package Qpick.server.domain.user.exception;

import Qpick.server.global.error.code.status.BaseErrorCode;
import Qpick.server.global.exception.GeneralException;

public class userException extends GeneralException {

    public userException(BaseErrorCode code) { super(code); }
}
