package com.chenfei.where.to.go.utils;

import java.util.UUID;

public abstract class UUIDUtils {

    public static String randomConciseUUID() {
        return UUID.randomUUID().toString()
                .replaceAll("-", "")
                .toUpperCase();
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString()
                .toUpperCase();
    }

}
