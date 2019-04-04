package com.chenfei.where.to.go.exception;
/*
 * Created by chenfei on 2019/3/29 15:56
 */

public class UnableToAquireLockException extends RuntimeException{
    public UnableToAquireLockException() {
    }
    public UnableToAquireLockException(String message) {
        super(message);
    }
    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
