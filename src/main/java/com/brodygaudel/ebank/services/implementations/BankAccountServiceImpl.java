

package com.brodygaudel.ebank.services.implementations;

import com.brodygaudel.ebank.dtos.*;
import com.brodygaudel.ebank.entities.BankAccount;
import com.brodygaudel.ebank.entities.CurrentAccount;
import com.brodygaudel.ebank.entities.Customer;
import com.brodygaudel.ebank.entities.SavingAccount;
import com.brodygaudel.ebank.enums.AccountStatus;
import com.brodygaudel.ebank.exceptions.BankAccountNotFoundException;
import com.brodygaudel.ebank.exceptions.CustomerNotFoundException;
import com.brodygaudel.ebank.mapping.Mappers;
import com.brodygaudel.ebank.repositories.BankAccountRepository;
import com.brodygaudel.ebank.repositories.CustomerRepository;
import com.brodygaudel.ebank.services.BankAccountService;
import com.brodygaudel.ebank.util.generator.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private static final String BANK_ACCOUNT_NOT_FOUND = "Bank Account Not Found";

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;
    private final Mappers mappers;
    private final IdGenerator idGenerator;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository, Mappers mappers, IdGenerator idGenerator) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.mappers = mappers;
        this.idGenerator = idGenerator;
    }

    /**
     * create new Current Bank Account
     * @param form the basic information of a Current Bank
     * @return Current Bank Account saved
     * @throws CustomerNotFoundException raises this exception if the customer whose current bank account you want to create does not exist.
     */
    @Override
    public CurrentAccountDTO saveCurrentBankAccount(@NotNull CurrentAccountCreationForm form) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(form.customerId())
                .orElseThrow( () -> new CustomerNotFoundException("customer not found"));
        CurrentAccount account = new CurrentAccount();
        account.setCustomer(customer);
        account.setBalance(form.initialBalance());
        account.setStatus(AccountStatus.CREATED);
        account.setOverDraft(form.overDraft());
        account.setCreatedAt(new Date());
        account.setId(idGenerator.autoGenerate());
        CurrentAccount savedCurrentAccount = bankAccountRepository.save(account);
        log.info("current account saved");
        return mappers.fromCurrentAccount(savedCurrentAccount);
    }

    /**
     * create new Saving Bank Account
     * @param form the basic information of a Current Bank
     * @return Saving Bank Account saved
     * @throws CustomerNotFoundException raises this exception if the customer whose current bank account you want to create does not exist.
     */
    @Override
    public SavingAccountDTO saveSavingBankAccount(@NotNull SavingAccountCreationForm form) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(form.customerId())
                .orElseThrow( () -> new CustomerNotFoundException("customer not found"));
        SavingAccount account = new SavingAccount();
        account.setCustomer(customer);
        account.setBalance(form.initialBalance());
        account.setStatus(AccountStatus.CREATED);
        account.setInterestRate(form.interestRate());
        account.setCreatedAt(new Date());
        account.setId(idGenerator.autoGenerate());
        SavingAccount savedSavingAccount = bankAccountRepository.save(account);
        log.info("saving account saved");
        return mappers.fromSavingAccount(savedSavingAccount);
    }

    /**
     * get Bank Account by id
     * @param id the id of Bank Account you want to get
     * @return Bank Account found
     * @throws BankAccountNotFoundException raises this exception if the bank account you want to get does not exist.
     */
    @Override
    public BankAccountDTO getBankAccountById(String id) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow( () -> new BankAccountNotFoundException(BANK_ACCOUNT_NOT_FOUND));
        log.info("BankAccount found");
        if(bankAccount instanceof SavingAccount savingAccount) {
            return mappers.fromSavingAccount(savingAccount);
        }
        CurrentAccount currentAccount = (CurrentAccount) bankAccount;
        return mappers.fromCurrentAccount(currentAccount);
    }

    /**
     * get all customer's bank account
     * @param id the id of customer
     * @return the list of all customer's bank account
     * @throws BankAccountNotFoundException raises this exception if the bank account you want to get does not exist.
     */
    @Override
    public List<BankAccountDTO> getBankAccountByCustomerId(Long id) throws BankAccountNotFoundException {
        List<BankAccount> bankAccounts = bankAccountRepository.findByCustomerId(id);
        if(bankAccounts.isEmpty()){
            throw new BankAccountNotFoundException(BANK_ACCOUNT_NOT_FOUND);
        }
        log.info("BankAccount(s) found");
        return bankAccounts.stream()
                .map( bankAccount -> {
                    if(bankAccount instanceof SavingAccount savingAccount){
                        return mappers.fromSavingAccount(savingAccount);
                    }
                    CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                    return mappers.fromCurrentAccount(currentAccount);
                })
                .toList();
    }

    /**
     * get all Bank Accounts
     * @return the list of all Bank Accounts
     */
    @Override
    public List<BankAccountDTO> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        log.info("return all bank accounts");
        return bankAccounts.stream()
                .map( bankAccount -> {
                    if(bankAccount instanceof SavingAccount savingAccount){
                        return mappers.fromSavingAccount(savingAccount);
                    }
                    CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                    return mappers.fromCurrentAccount(currentAccount);
                })
                .toList();
    }

    /**
     * update bank account status (activate or suspend)
     * @param id the id of Bank Account you want to update status
     * @param status new status
     * @return Bank Account updated
     * @throws BankAccountNotFoundException raises this exceptions if bank account not found
     */
    @Override
    public BankAccountDTO updateBankAccountStatus(String id, @NotNull UpdateBankAccountStatus status) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow( () -> new BankAccountNotFoundException(BANK_ACCOUNT_NOT_FOUND));
        bankAccount.setStatus(status.status());
        BankAccount updatedBankAccount = bankAccountRepository.save(bankAccount);
        log.info("Bank Account Status Updated");
        if(updatedBankAccount instanceof SavingAccount savingAccount) {
            return mappers.fromSavingAccount(savingAccount);
        }
        CurrentAccount currentAccount = (CurrentAccount) bankAccount;
        return mappers.fromCurrentAccount(currentAccount);
    }
}
