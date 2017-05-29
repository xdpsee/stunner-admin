package com.cherry.stunner.api.common;

import lombok.Data;

@Data
public class ResponseData<T> {

    private boolean success;

    private String message;

    private T data;

    public static <T> ResponseData<T> success(T data) {
        ResponseData<T> result = new ResponseData<>();
        result.setSuccess(true);
        result.setMessage("OK");
        result.setData(data);
        return result;
    }

    public static <T> ResponseData<T> error(String message) {
        ResponseData<T> result = new ResponseData<>();
        result.setSuccess(false);
        result.setMessage(message);
        result.setData(null);
        return result;
    }
}
