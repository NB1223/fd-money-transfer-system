package com.fd.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fd.model.TransactionLog;

@Aspect
@Component
public class TransferServiceLoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(TransferServiceLoggingAspect.class);

    @Before("execution(public com.fd.model.TransactionLog com.fd.service.TransferService.executeTransfer(com.fd.dto.TransferRequest, java.util.List<com.fd.model.Account>))")
	    public void logBeforeTransfer() {
	        logger.info("[LOG INFO] Executing Transfer");
	   	}

    @AfterReturning(
            pointcut = "execution(public com.fd.model.TransactionLog com.fd.service.TransferService.executeTransfer(com.fd.dto.TransferRequest, java.util.List <com.fd.model.Account>))",
            returning = "result"
    )
    public void logAfterTransfer(TransactionLog result) {
        logger.info("[LOG INFO] Transfer executed with Id: {}", result.getTransactionId());
    }
}
