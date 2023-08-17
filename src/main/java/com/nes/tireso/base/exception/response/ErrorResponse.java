package com.nes.tireso.base.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private HttpStatus code;
    private String message;
}