package com.hospital.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO<T> {

    private boolean ok;
    private Integer statusCode;
    private String message;
    private T data;
    private Long count;

    public ApiResponseDTO() {
    }

    public ApiResponseDTO(boolean ok, Integer statusCode, String message, T data) {
        this.ok = ok;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ApiResponseDTO(boolean ok, Integer statusCode, String message, T data, Long count) {
        this.ok = ok;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
