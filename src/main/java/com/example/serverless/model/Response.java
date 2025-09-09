package com.example.serverless.model;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private final String status;
    private final T data;
    private final String message;
    private final Instant timestamp;

    @JsonIgnore
    private final HttpStatus statusCode;

    public static <T> Response<T> success() {
        return Response.success(null);
    }

    public static <T> Response<T> success(T data) {
        return Response.success(data, HttpStatus.OK);
    }

    public static <T> Response<T> success(T data, HttpStatus statusCode) {
        return new Response<>("success", data, null, statusCode);
    }

    public static <T> Response<T> fail(T data) {
        return Response.fail(data, HttpStatus.BAD_REQUEST);
    }

    public static <T> Response<T> fail(T data, HttpStatus statusCode) {
        return new Response<>("fail", data, null, statusCode);
    }

    public static Response<Void> error(String message) {
        return Response.error(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static Response<Void> error(String message, HttpStatus statusCode) {
        return new Response<>("error", null, message, statusCode);
    }

    private Response(String status, T data, String message, HttpStatus code) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.statusCode = code;
        this.timestamp = Instant.now();
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @JsonProperty("statusCode")
    public int getCodeValue() {
        return statusCode.value();
    }
}
