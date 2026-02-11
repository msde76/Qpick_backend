package Qpick.server.domain.product.exception;

import Qpick.server.global.error.code.status.BaseErrorCode;
import Qpick.server.global.exception.GeneralException;

public class productException extends GeneralException {

    public productException(BaseErrorCode code) { super(code); }
}
