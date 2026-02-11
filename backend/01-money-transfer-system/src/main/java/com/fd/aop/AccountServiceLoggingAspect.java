package com.fd.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fd.model.Account;

@Aspect
@Component
public class AccountServiceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceLoggingAspect.class);

    @Before("execution(public com.fd.model.Account com.fd.service.AccountService.createAccount(com.fd.dto.AccountDTO))")
	    public void logBeforeCreatingAccount() {
	        logger.info("[LOG INFO] Creating Account");
	   	}

    @AfterReturning(
            pointcut = "execution(public com.fd.model.Account com.fd.service.AccountService.createAccount(com.fd.dto.AccountDTO))",
            returning = "result"
    )
    public void logAfterCreatingAccount(Account result) {
        logger.info("[LOG INFO]Created account with Id: {}", result.getAccountId());
    }

}
