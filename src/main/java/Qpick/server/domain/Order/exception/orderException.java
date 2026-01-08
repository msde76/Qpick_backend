package Qpick.server.domain.Order.exception;

import Qpick.server.global.error.code.status.BaseErrorCode;
import Qpick.server.global.exception.GeneralException;

public class orderException extends GeneralException {

    public orderException(BaseErrorCode code) { super(code); }
}
