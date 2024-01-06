package com.kalek.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecursoException extends RuntimeException{
    public RecursoException(String mensaje){
        super(mensaje);
    }
}
