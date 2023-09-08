package com.facu.altisima.service.utils;

public class ServiceResult<T> {
    private T data;
    private String errorMessage;

    private ServiceResult(T data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> ServiceResult<T> success(T data) {
            return new ServiceResult<>(data, null);
    }

    public static <T> ServiceResult<T> error(String errorMessage) {
        return new ServiceResult<>(null, errorMessage);
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return errorMessage == null;
    }

    public boolean isError() {
        return errorMessage != null;
    }
}

