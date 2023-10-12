package com.boilerplate.server.exception;

/**
 * 重复请求异常
 */
public class DuplicateException extends RuntimeException {
    /**
     * @param message 异常提示
     */
    public DuplicateException(String message) {
        super(message);
    }
}
