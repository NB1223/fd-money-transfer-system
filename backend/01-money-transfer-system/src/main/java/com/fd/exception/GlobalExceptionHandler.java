package com.fd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> accountNotFoundException(AccountNotFoundException accountNotFound, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), accountNotFound.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<?> accountNotActiveException(AccountNotActiveException accountNotActive, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), accountNotActive.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateTransferException.class)
    public ResponseEntity<?> duplicateTransferException(DuplicateTransferException duplicateTransfer, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), duplicateTransfer.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsuffiecientBalanceException.class)
    public ResponseEntity<?> insuffiecientBalanceException(InsuffiecientBalanceException insuffiecientBalance, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), insuffiecientBalance.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(SelfTransferException.class)
    public ResponseEntity<?> globleExcpetionHandler(SelfTransferException selfTransferException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), selfTransferException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
     @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<?> duplicateUsernameException(DuplicateUsernameException duplicateUsername, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), duplicateUsername.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}