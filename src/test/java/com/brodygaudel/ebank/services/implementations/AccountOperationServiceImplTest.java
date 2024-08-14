package com.brodygaudel.ebank.services.implementations;

import com.brodygaudel.ebank.dtos.*;
import com.brodygaudel.ebank.entities.AccountOperation;
import com.brodygaudel.ebank.entities.BankAccount;
import com.brodygaudel.ebank.entities.Customer;
import com.brodygaudel.ebank.entities.SavingAccount;
import com.brodygaudel.ebank.enums.AccountStatus;
import com.brodygaudel.ebank.enums.OperationType;
import com.brodygaudel.ebank.enums.Sex;
import com.brodygaudel.ebank.exceptions.AccountOperationNotFoundException;
import com.brodygaudel.ebank.exceptions.BalanceNotSufficientException;
import com.brodygaudel.ebank.exceptions.BankAccountNotActivatedException;
import com.brodygaudel.ebank.exceptions.BankAccountNotFoundException;
import com.brodygaudel.ebank.mapping.implementation.MappersImpl;
import com.brodygaudel.ebank.repositories.AccountOperationRepository;
import com.brodygaudel.ebank.repositories.BankAccountRepository;
import com.brodygaudel.ebank.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountOperationServiceImplTest {

    @Autowired
    private AccountOperationRepository operationRepository;

    @Autowired
    private BankAccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private AccountOperationServiceImpl accountOperationService;

    @BeforeEach
    void setUp() {
        accountOperationService = new AccountOperationServiceImpl(operationRepository, accountRepository, new MappersImpl());
    }

    @Test
    void testDebit() throws BankAccountNotFoundException, BalanceNotSufficientException, BankAccountNotActivatedException {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        SavingAccount account = new SavingAccount();
        account.setCreatedAt(new Date());
        account.setBalance(new BigDecimal("10546.158"));
        account.setStatus(AccountStatus.ACTIVATED);
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        BankAccount bankAccount = accountRepository.save(account);
        DebitDTO debitDTO = new DebitDTO(bankAccount.getId(), new BigDecimal("546.158"), "Test Debit");
        DebitDTO response = accountOperationService.debit(debitDTO);
        assertNotNull(response);
        assertEquals(response.accountId(), debitDTO.accountId());
        assertEquals(response.amount(), debitDTO.amount());
        assertEquals(response.description(), debitDTO.description());
    }

    @Test
    void testCredit() throws BankAccountNotFoundException, BankAccountNotActivatedException {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        SavingAccount account = new SavingAccount();
        account.setCreatedAt(new Date());
        account.setBalance(new BigDecimal("10546.158"));
        account.setStatus(AccountStatus.ACTIVATED);
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        BankAccount bankAccount = accountRepository.save(account);
        CreditDTO creditDTO = new CreditDTO(bankAccount.getId(), new BigDecimal("546.158"), "Test Debit");
        CreditDTO response = accountOperationService.credit(creditDTO);
        assertNotNull(response);
        assertEquals(response.accountId(), creditDTO.accountId());
        assertEquals(response.amount(), creditDTO.amount());
        assertEquals(response.description(), creditDTO.description());
    }

    @Test
    void testTransfer() throws BankAccountNotFoundException, BalanceNotSufficientException, BankAccountNotActivatedException {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        SavingAccount account = new SavingAccount();
        account.setCreatedAt(new Date());
        account.setBalance(new BigDecimal("10546.158"));
        account.setStatus(AccountStatus.ACTIVATED);
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        BankAccount bankAccount = accountRepository.save(account);

        SavingAccount account2 = new SavingAccount();
        account2.setCreatedAt(new Date());
        account2.setBalance(new BigDecimal("10546.158"));
        account2.setStatus(AccountStatus.ACTIVATED);
        account2.setCustomer(customer);
        account2.setId(UUID.randomUUID().toString());
        BankAccount bankAccount2 = accountRepository.save(account2);

        TransferDTO transferDTO = new TransferDTO(bankAccount.getId(), bankAccount2.getId(), new BigDecimal("400"));
        TransferDTO savedTransferDTO = accountOperationService.transfer(transferDTO);
        assertNotNull(savedTransferDTO);
    }

    @Test
    void testGetAllOperationsByAccountId() {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        SavingAccount account = new SavingAccount();
        account.setCreatedAt(new Date());
        account.setBalance(new BigDecimal("10546.158"));
        account.setStatus(AccountStatus.CREATED);
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        BankAccount bankAccount = accountRepository.save(account);

        AccountOperation operation1 = AccountOperation.builder()
                .operationDate(new Date())
                .type(OperationType.DEBIT)
                .amount(new BigDecimal("56.75"))
                .description("DEBIT")
                .bankAccount(bankAccount)
                .build();

        AccountOperation operation2 = AccountOperation.builder()
                .operationDate(new Date())
                .type(OperationType.CREDIT)
                .amount(new BigDecimal("156.75"))
                .description("DEBIT")
                .bankAccount(bankAccount)
                .build();
        operationRepository.save(operation1);
        operationRepository.save(operation2);

        List<AccountOperationDTO> operationDTOS = accountOperationService
                .getAllOperationsByAccountId(account.getId());
        assertNotNull(operationDTOS);
        assertFalse(operationDTOS.isEmpty());
        assertTrue(operationDTOS.size()>=2);
    }

    @Test
    void testGetOperationById() throws AccountOperationNotFoundException {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        SavingAccount account = new SavingAccount();
        account.setCreatedAt(new Date());
        account.setBalance(new BigDecimal("10546.158"));
        account.setStatus(AccountStatus.CREATED);
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        BankAccount bankAccount = accountRepository.save(account);

        AccountOperation operation = AccountOperation.builder()
                .operationDate(new Date())
                .type(OperationType.CREDIT)
                .amount(new BigDecimal("56.75"))
                .description("CREDIT")
                .bankAccount(bankAccount)
                .build();
        AccountOperation accountOperation = operationRepository.save(operation);

        AccountOperationDTO operationDTO = accountOperationService
                .getOperationById(accountOperation.getId());

        assertNotNull(operationDTO);
        assertEquals(operationDTO.id(), accountOperation.getId());
        assertEquals(operationDTO.amount(), accountOperation.getAmount());
        assertEquals(operationDTO.type(), accountOperation.getType());
        assertEquals(operationDTO.description(), accountOperation.getDescription());
    }

    @Test
    void testGetAccountHistory() throws BankAccountNotFoundException {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        SavingAccount account = new SavingAccount();
        account.setCreatedAt(new Date());
        account.setBalance(new BigDecimal("10546.158"));
        account.setStatus(AccountStatus.CREATED);
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        BankAccount bankAccount = accountRepository.save(account);

        AccountOperation operation = AccountOperation.builder()
                .operationDate(new Date())
                .type(OperationType.CREDIT)
                .amount(new BigDecimal("56.75"))
                .description("CREDIT")
                .bankAccount(bankAccount)
                .build();
        operationRepository.save(operation);

        AccountHistoryDTO accountHistoryDTO = accountOperationService.getAccountHistory(bankAccount.getId(), 0,10);
        assertNotNull(accountHistoryDTO);
        assertEquals(accountHistoryDTO.accountId(), bankAccount.getId());
        List<AccountOperationDTO> operationDTOS = accountHistoryDTO.accountOperationDTOS();
        assertNotNull(operationDTOS);
        assertFalse(operationDTOS.isEmpty());
    }
}