package ru.skypro.test_task.sock.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Настраиваемое исключение
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomException extends RuntimeException{
    private final String message;

    public CustomException(String message) {
        this.message = message;
       }
    @Override
    public String getMessage() {
        return message;
    }
}

