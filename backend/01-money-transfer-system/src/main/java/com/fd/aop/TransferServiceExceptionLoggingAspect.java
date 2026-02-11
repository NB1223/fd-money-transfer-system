package com.fd.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransferServiceExceptionLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(TransferServiceExceptionLoggingAspect.class);

    @Before("execution(public java.util.List<com.fd.model.Account> com.fd.service.TransferService.transactionValidation(com.fd.dto.TransferRequest))")
	    public void logBeforeTransferValidation() {
	        logger.info("[LOG INFO] Performing Transfer Validation");
	   	}

    @AfterReturning(
            pointcut = "execution(public java.util.List<com.fd.model.Account> com.fd.service.TransferService.transactionValidation(com.fd.dto.TransferRequest))",
            returning = "result"
    )
    public void logAfterTransferValidation(java.util.List<com.fd.model.Account> result) {
        logger.info("[LOG INFO] Transfer Validation completed with {} accounts", result.size());
    }

    @AfterThrowing(
            pointcut = "execution(public java.util.List<com.fd.model.Account> com.fd.service.TransferService.transactionValidation(com.fd.dto.TransferRequest))",
            throwing = "ex"
    )
    public void logTransferValidationException(Exception ex) {
        logger.error("[LOG ERROR] Exception during Transfer Validation: {}", ex.getMessage());
    }

    
}