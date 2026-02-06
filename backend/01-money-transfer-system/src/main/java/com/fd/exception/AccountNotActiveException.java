package com.fd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountNotActiveException extends Exception{
    public AccountNotActiveException(String message){
        super(message);
    }
}