package com.fd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.fd.dto.TransferRequest;
import com.fd.dto.TransferResponse;
import com.fd.exception.InsuffiecientBalanceException;
import com.fd.model.TransactionLog;
import com.fd.repository.ITransactionLogRepository;
import com.fd.service.TransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fd.dto.AccountDTO;
import com.fd.model.Account;
import com.fd.model.AccountStatus;
import com.fd.repository.IAccountRepository;
import com.fd.service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {

    @Mock
    private IAccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    @InjectMocks
    private TransferService transferService;

    @Mock
    private ITransactionLogRepository transactionLogRepository;

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

    @Test
    void debitSuccess() throws Exception {

        Account fromAccount = new Account("Nitika", 1000.0);
        Account toAccount = new Account("Neha", 1000.0);

        TransferRequest request = new TransferRequest(1L, 2L, 200.0, "idem-123");
        
        fromAccount.setAccountStatus(AccountStatus.ACTIVE);
        toAccount.setAccountStatus(AccountStatus.ACTIVE);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        List<Account> accounts = transferService.transactionValidation(request);

        when(transactionLogRepository.save(any(TransactionLog.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TransferResponse response = transferService.executeTransaction(request, accounts);

        assertEquals(800.0, fromAccount.getBalance());
        assertEquals(1200.0, toAccount.getBalance());

        assertEquals(1L, response.getFromAccountId());
        assertEquals(2L, response.getToAccountId());
        assertEquals(200.0, response.getTransferAmount());

        verify(transactionLogRepository, times(1)).save(any(TransactionLog.class));
    }

    @Test
    void creditSuccess() throws Exception {

        Account fromAccount = new Account("Nitika", 1500.0);
        Account toAccount = new Account("Neha", 500.0);

        TransferRequest request = new TransferRequest(1L, 2L, 300.0, "idem-456");
        
        fromAccount.setAccountStatus(AccountStatus.ACTIVE);
        toAccount.setAccountStatus(AccountStatus.ACTIVE);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        List<Account> accounts = transferService.transactionValidation(request);

        TransactionLog savedLog = new TransactionLog(1L, 2L, 300.0, "idem-456");

        when(transactionLogRepository.save(any(TransactionLog.class))).thenReturn(savedLog);

        TransferResponse response = transferService.executeTransaction(request, accounts);

        assertEquals(1200.0, fromAccount.getBalance());
        assertEquals(800.0, toAccount.getBalance());

        assertEquals(1L, response.getFromAccountId());
        assertEquals(2L, response.getToAccountId());
        assertEquals(300.0, response.getTransferAmount());

        verify(transactionLogRepository, times(1)).save(any(TransactionLog.class));
    }

    @Test
    void debitInsufficientBalance() {

        Account fromAccount = new Account("Nitika", 100.0);
        Account toAccount = new Account("Neha", 500.0);

        TransferRequest request =
                new TransferRequest(1L, 2L, 300.0, "idem-789");

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        assertThrows(InsuffiecientBalanceException.class, () -> {
            transferService.transactionValidation(request);
        });

        verify(transactionLogRepository, never()).save(any());
    }
}
