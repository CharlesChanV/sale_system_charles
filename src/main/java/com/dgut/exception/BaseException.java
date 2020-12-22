package com.dgut.exception;

/**
 * @author Charles
 * @version 1.0
 * @date 2020/6/17
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

