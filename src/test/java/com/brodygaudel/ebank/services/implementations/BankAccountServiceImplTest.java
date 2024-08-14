package com.brodygaudel.ebank.services.implementations;

import com.brodygaudel.ebank.dtos.*;
import com.brodygaudel.ebank.entities.BankAccount;
import com.brodygaudel.ebank.entities.Customer;
import com.brodygaudel.ebank.entities.SavingAccount;
import com.brodygaudel.ebank.enums.AccountStatus;
import com.brodygaudel.ebank.enums.Sex;
import com.brodygaudel.ebank.exceptions.BankAccountNotFoundException;
import com.brodygaudel.ebank.exceptions.CustomerNotFoundException;
import com.brodygaudel.ebank.mapping.implementation.MappersImpl;
import com.brodygaudel.ebank.repositories.BankAccountRepository;
import com.brodygaudel.ebank.repositories.CustomerRepository;
import com.brodygaudel.ebank.util.generator.implementations.CompterRepository;
import com.brodygaudel.ebank.util.generator.implementations.IdGeneratorImpl;
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
class BankAccountServiceImplTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CompterRepository compterRepository;

    private BankAccountServiceImpl bankAccountService;

    @BeforeEach
    void setUp() {
        IdGeneratorImpl idGenerator = new IdGeneratorImpl(compterRepository);
        bankAccountService = new BankAccountServiceImpl(bankAccountRepository,
                        customerRepository, new MappersImpl(), idGenerator);
    }

    @Test
    void testSaveCurrentBankAccount() throws CustomerNotFoundException {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        CurrentAccountCreationForm form = new CurrentAccountCreationForm(new BigDecimal("5000.0"), 2.5, customer.getId());
        CurrentAccountDTO accountDTO = bankAccountService.saveCurrentBankAccount(form);
        assertNotNull(accountDTO);
        assertEquals(accountDTO.getCustomerDTO().id(), form.customerId());
        assertEquals(accountDTO.getBalance(), form.initialBalance());
        assertEquals(accountDTO.getOverDraft(), form.overDraft());
        assertEquals(AccountStatus.CREATED, accountDTO.getStatus());
    }

    @Test
    void testSaveSavingBankAccount() throws CustomerNotFoundException {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        SavingAccountCreationForm form = new SavingAccountCreationForm(new BigDecimal("6000.0"), 6.5, customer.getId());
        SavingAccountDTO accountDTO = bankAccountService.saveSavingBankAccount(form);
        assertNotNull(accountDTO);
        assertEquals(accountDTO.getCustomerDTO().id(), form.customerId());
        assertEquals(accountDTO.getInterestRate(), form.interestRate());
        assertEquals(accountDTO.getBalance(), form.initialBalance());
        assertEquals(AccountStatus.CREATED, accountDTO.getStatus());
    }

    @Test
    void testGetBankAccountById() throws BankAccountNotFoundException {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        SavingAccount account = new SavingAccount();
        account.setCreatedAt(new Date());
        account.setBalance(new BigDecimal("897546.158"));
        account.setStatus(AccountStatus.CREATED);
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        BankAccount savedBankAccount = bankAccountRepository.save(account);
        BankAccountDTO bankAccountDTO = bankAccountService.getBankAccountById(savedBankAccount.getId());
        assertNotNull(bankAccountDTO);
    }

    @Test
    void testGetBankAccountByCustomerId() throws BankAccountNotFoundException {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        SavingAccount account = new SavingAccount();
        account.setCreatedAt(new Date());
        account.setBalance(new BigDecimal("897546.158"));
        account.setStatus(AccountStatus.CREATED);
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        bankAccountRepository.save(account);
        List<BankAccountDTO> bankAccountDTOList = bankAccountService.getBankAccountByCustomerId(customer.getId());
        assertNotNull(bankAccountDTOList);
        assertFalse(bankAccountDTOList.isEmpty());
    }

    @Test
    void testGetAllBankAccounts() {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        SavingAccount account = new SavingAccount();
        account.setCreatedAt(new Date());
        account.setBalance(new BigDecimal("7546.15"));
        account.setStatus(AccountStatus.CREATED);
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        bankAccountRepository.save(account);
        List<BankAccountDTO> bankAccountDTOList = bankAccountService.getAllBankAccounts();
        assertNotNull(bankAccountDTOList);
        assertFalse(bankAccountDTOList.isEmpty());
    }

    @Test
    void testUpdateBankAccountStatus() throws BankAccountNotFoundException {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        SavingAccount account = new SavingAccount();
        account.setCreatedAt(new Date());
        account.setBalance(new BigDecimal("7546.15"));
        account.setStatus(AccountStatus.CREATED);
        account.setCustomer(customer);
        account.setId(UUID.randomUUID().toString());
        BankAccount bankAccount = bankAccountRepository.save(account);

        BankAccountDTO bankAccountDTO = bankAccountService
                .updateBankAccountStatus(bankAccount.getId(), new UpdateBankAccountStatus(AccountStatus.ACTIVATED));
        assertNotNull(bankAccountDTO);
        SavingAccountDTO savingAccount = (SavingAccountDTO) bankAccountDTO;
        assertNotNull(savingAccount);
        assertNotEquals(savingAccount.getStatus(), account.getStatus());
    }
}