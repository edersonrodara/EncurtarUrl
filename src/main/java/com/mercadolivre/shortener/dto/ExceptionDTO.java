package com.mercadolivre.shortener.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDTO {
    private int status;
    private String message;
    private Instant timestamp;
    private String path;

    public static ExceptionDTO badRequest(String message, String path) {
        return new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), message, Instant.now(), path);
    }

    public static ExceptionDTO notFound(String message, String path) {
        return new ExceptionDTO(HttpStatus.NOT_FOUND.value(), message, Instant.now(), path);
    }
}
