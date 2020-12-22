package com.dgut.exception;

/**
 * @author Charles
 * @version 1.0
 * @date 2020/6/17
 */
public class UsernameIsExitedException extends BaseException {

    public UsernameIsExitedException(String msg) {
        super(msg);
    }

    public UsernameIsExitedException(String msg, Throwable t) {
        super(msg, t);
    }
}