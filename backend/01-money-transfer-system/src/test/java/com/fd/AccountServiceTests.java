package com.fd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fd.dto.AccountDTO;
import com.fd.model.Account;
import com.fd.repository.IAccountRepository;
import com.fd.service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {

    @Mock
    private IAccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccount_success() {
        AccountDTO account1 = new AccountDTO("Nitika" , 1000.0);

        when(accountRepository.save(any(Account.class))).thenReturn(AccountDTO.fromDTOToEntity(account1));
        
        Account result = accountService.createAccount(account1);

        assertEquals(AccountDTO.fromDTOToEntity(account1).getAccountId(), result.getAccountId());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void getAllAccounts_success() {
        
        Account account1 = new Account("Nitika", 1000.0);
        Account account2 = new Account("Neha", 1000.0);

        List<Account> accounts = List.of(account1, account2);

        when(accountRepository.findAll()).thenReturn(accounts);
        
        List<Account> result = accountService.findAllAccounts();

        assertEquals(accounts, result);
        verify(accountRepository, times(1)).findAll();
    }   

    
}
