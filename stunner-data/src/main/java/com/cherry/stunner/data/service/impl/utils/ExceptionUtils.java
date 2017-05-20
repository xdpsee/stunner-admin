package com.cherry.stunner.data.service.impl.utils;


import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DuplicateKeyException;

public class ExceptionUtils {

    public static boolean hasDuplicateEntryException(Exception e) {

        if (e instanceof DuplicateKeyException) {
            return true;
        }

        if (e instanceof MyBatisSystemException && e.getMessage().contains("Duplicate entry")) {
            return true;
        }

        Throwable cause = e.getCause();

        if (cause != null && cause instanceof MyBatisSystemException && cause.getMessage().contains("Duplicate entry")) {
            return true;
        }

        if (cause != null && cause.getMessage().contains("Duplicate entry")) {
            return true;
        }

        return false;
    }

}
