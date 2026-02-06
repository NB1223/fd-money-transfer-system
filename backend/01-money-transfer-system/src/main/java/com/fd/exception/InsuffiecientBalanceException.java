package com.fd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InsuffiecientBalanceException extends Exception{
    public InsuffiecientBalanceException(String message){
        super(message);

    }
}