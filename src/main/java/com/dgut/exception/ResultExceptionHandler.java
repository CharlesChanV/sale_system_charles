package com.dgut.exception;

import com.dgut.utils.ResultUtils;
import com.dgut.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 这个用来处理系统抛出的异常,捕获到异常后抛出Json给前端
 *
 * @Author Charles
 * @Date 2020/12/5
 */

@RestControllerAdvice
public class ResultExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultExceptionHandler.class);

    /**
     * 处理所有不可知的异常
     *
     * @param e 异常对象
     * @return {@link Result<?>}
     */
    @ExceptionHandler(Exception.class)
    Result<?> handleException(Exception e) {
        return ResultUtils.error(-1, "异常信息:" + e.getMessage());
    }

//    /**
//     * 处理登陆方法异常
//     *
//     * @param e 异常对象
//     * @return {@link Result}
//     */
//    @Deprecated
//    @ExceptionHandler(AuthenticationServiceException.class)
//    Result<?> authenticationServiceException(Exception e) {
//        return Result.fail(GlobalServiceMsgCode.ERROR_FAIL, "异常信息:" + e.getMessage());
//    }
//
//    /**
//     * 处理注册方法异常
//     *
//     * @param e 异常对象
//     * @return {@link Result<?>}
//     */
//    @Deprecated
//    @ExceptionHandler(IllegalArgumentException.class)
//    Result<?> illegalArgumentExceptionException(Exception e) {
//        return Result<?>.error(GlobalServiceMsgCode.ERROR_FAIL, "错误:" + e.getMessage());
//    }

//    /**
//     * 处理权限不足异常
//     *
//     * @param e 异常对象
//     * @return {@link Result<?>}
//     */
//    @ExceptionHandler(AccessDeniedException.class)
//    Result<?> getMsgAccessDeniedException(Exception e) {
//        return Result<?>.error(GlobalServiceMsgCode.USER_NO_PERMISSION,
//                GlobalServiceMsgCode.USER_NO_PERMISSION.getMessage() + ":" + e.getMessage());
//    }

    /**
     * 处理所有接口数据验证异常
     *
     * @param e 异常对象
     * @return {@link Result<?>}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LOGGER.error(e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder errorMessage = new StringBuilder("校验失败：");

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getDefaultMessage()).append("; ");
        }
        return ResultUtils.error(-2,errorMessage.toString());
    }

}