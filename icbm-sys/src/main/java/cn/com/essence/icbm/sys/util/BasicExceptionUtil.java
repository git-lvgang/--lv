package cn.com.essence.icbm.sys.util;

import cn.com.essence.icbm.sys.constant.Constants;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.core.exception.ExceptionBuilder;
import cn.com.essence.wefa.core.exception.ExceptionUtil;
import cn.com.essence.wefa.core.exception.WefaBaseException;

/**
 * 创建异常工具类
 */
public class BasicExceptionUtil {

    public static WefaBaseException buildRequesetFail() {
        return ExceptionUtil.buildRequesetFail(Constants.MODULE_CODE_BASIC, null).crateAppEx();
    }

    public static WefaBaseException buildRequesetFail(String msg) {
        return ExceptionUtil.buildRequesetFail(Constants.MODULE_CODE_BASIC, msg, null).crateAppEx();
    }

    public static WefaBaseException buildRequesetFail(Throwable cause) {
        return ExceptionUtil.buildRequesetFail(Constants.MODULE_CODE_BASIC, cause).crateAppEx();
    }

    public static WefaBaseException buildRequesetFail(String msg, Throwable cause) {
        return ExceptionUtil.buildRequesetFail(Constants.MODULE_CODE_BASIC, msg, cause).crateAppEx();
    }

    public static WefaBaseException buildRequesetException() {
        return ExceptionUtil.buildRequesetException(Constants.MODULE_CODE_BASIC, null).crateAppEx();
    }

    public static WefaBaseException buildRequesetException(Throwable cause) {
        return ExceptionUtil.buildRequesetException(Constants.MODULE_CODE_BASIC, cause).crateAppEx();
    }

    public static WefaBaseException buildDataNotFound() {
        return ExceptionUtil.buildDataNotFound(Constants.MODULE_CODE_BASIC, null).crateAppEx();
    }

    public static WefaBaseException buildDataNotFound(String msg) {
        return ExceptionBuilder.builder()
                .msg(msg)
                .errorCode(Constants.MODULE_CODE_BASIC, ResultCode.C_DATA_NOT_FOUND.getCode())
                .crateAppEx();
    }

    public static WefaBaseException buildDataNotFound(Throwable cause) {
        return ExceptionUtil.buildDataNotFound(Constants.MODULE_CODE_BASIC, cause).crateAppEx();
    }

    public static WefaBaseException buildParamsError(String msg) {
        return ExceptionUtil.buildParamsError(Constants.MODULE_CODE_BASIC, msg, null).crateAppEx();
    }

    public static WefaBaseException buildParamsError(String msg, Throwable cause) {
        return ExceptionUtil.buildParamsError(Constants.MODULE_CODE_BASIC, msg, cause).crateAppEx();
    }
}
