package ru.skypro.test_task.sock.warehouse.exception;

import org.springframework.http.HttpStatus;

/**
 * Настраиваемое исключение
 */
public class CustomException extends RuntimeException{
    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
