package com.chenfei.where.to.go.exception;
/*
 * Created by chenfei on 2018/11/3 14:24
 */

public class RoceketMqException extends RuntimeException{
    public RoceketMqException() {
    }

    public RoceketMqException(String message) {
        super(message);
    }

    public RoceketMqException(Throwable cause) {
        super(cause);
    }
}
