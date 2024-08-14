

package com.brodygaudel.ebank.services.implementations;

import com.brodygaudel.ebank.dtos.*;
import com.brodygaudel.ebank.entities.AccountOperation;
import com.brodygaudel.ebank.entities.BankAccount;
import com.brodygaudel.ebank.enums.AccountStatus;
import com.brodygaudel.ebank.enums.OperationType;
import com.brodygaudel.ebank.exceptions.AccountOperationNotFoundException;
import com.brodygaudel.ebank.exceptions.BalanceNotSufficientException;
import com.brodygaudel.ebank.exceptions.BankAccountNotActivatedException;
import com.brodygaudel.ebank.exceptions.BankAccountNotFoundException;
import com.brodygaudel.ebank.mapping.Mappers;
import com.brodygaudel.ebank.repositories.AccountOperationRepository;
import com.brodygaudel.ebank.repositories.BankAccountRepository;
import com.brodygaudel.ebank.services.AccountOperationService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AccountOperationServiceImpl implements AccountOperationService {

    private final AccountOperationRepository accountOperationRepository;
    private final BankAccountRepository bankAccountRepository;
    private final Mappers mappers;

    public AccountOperationServiceImpl(AccountOperationRepository accountOperationRepository, BankAccountRepository bankAccountRepository, Mappers mappers) {
        this.accountOperationRepository = accountOperationRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.mappers = mappers;
    }

    /**
     * debit a bank account
     * @param debitDTO basic information to debit a bank account
     * @return basic information for a bank account debited
     * @throws BankAccountNotFoundException raises this exception if the bank account you want to debit does not exist.
     * @throws BalanceNotSufficientException raises this exception if balance not sufficient
     * @throws BankAccountNotActivatedException raises this exception if bank account's status is not activated
     */
    @Override
    public DebitDTO debit(@NotNull DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, BankAccountNotActivatedException {
        BankAccount bankAccount = bankAccountRepository.findById(debitDTO.accountId())
                .orElseThrow( () -> new BankAccountNotFoundException("BankAccount not found"));

        checkIfBankAccountIsReady(bankAccount);
        if(debitDTO.amount().compareTo(bankAccount.getBalance()) > 0) {
            throw new BalanceNotSufficientException("Balance not sufficient");
        }
        AccountOperation operation = accountOperationRepository.save(
                AccountOperation.builder()
                        .description(debitDTO.description())
                        .bankAccount(bankAccount)
                        .amount(debitDTO.amount())
                        .type(OperationType.DEBIT)
                        .operationDate(new Date())
                        .build()
        );
        log.info("debit operation saved");
        bankAccount.setBalance(bankAccount.getBalance().subtract(debitDTO.amount()));
        bankAccountRepository.save(bankAccount);
        log.info("BankAccount's balance updated");
        return new DebitDTO(operation.getBankAccount().getId(), operation.getAmount(), operation.getDescription());
    }

    /**
     * credit a bank account
     * @param creditDTO basic information to credit a bank account
     * @return basic information of bank account credited
     * @throws BankAccountNotFoundException raises this exception if the bank account you want to credit does not exist.
     * @throws BankAccountNotActivatedException raises this exception if bank account status is not activated
     */
    @Override
    public CreditDTO credit(@NotNull CreditDTO creditDTO) throws BankAccountNotFoundException, BankAccountNotActivatedException {
        BankAccount bankAccount = bankAccountRepository.findById(creditDTO.accountId())
                .orElseThrow( () -> new BankAccountNotFoundException("BankAccount not found"));
        checkIfBankAccountIsReady(bankAccount);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(creditDTO.amount());
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(creditDTO.description());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        log.info("credit operation saved");
        bankAccount.setBalance(bankAccount.getBalance().add(creditDTO.amount()));
        bankAccountRepository.save(bankAccount);
        log.info("BankAccount's balance updated");
        return new CreditDTO(bankAccount.getId(), accountOperation.getAmount(), accountOperation.getDescription());
    }

    /**
     * make a transfer from a bank account to another bank account
     * @param transferDTO basic information to make a transfer
     * @return basic information of transfer done
     * @throws BankAccountNotFoundException raises this exception if the bank account(s) you want to transfer does not exist.
     * @throws BalanceNotSufficientException raises this exception if balance not sufficient to make a transfer
     * @throws BankAccountNotActivatedException raises this exception if bank account status is not activated
     */
    @Override
    public TransferDTO transfer(@NotNull TransferDTO transferDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, BankAccountNotActivatedException {
        debit(new DebitDTO(transferDTO.accountIdSource(), transferDTO.amount(), "Transfer to : "+transferDTO.accountIdDestination()));
        credit(new CreditDTO(transferDTO.accountIdDestination(), transferDTO.amount(), "Transfer from : "+transferDTO.accountIdSource()));
        log.info("transfer done successfully");
        return transferDTO;
    }

    /**
     * get all operations by account id
     * @param accountId id of bank account that you want to retrieve operations
     * @return a list of AccountOperation where accountId equal id
     */
    @Override
    public List<AccountOperationDTO> getAllOperationsByAccountId(String accountId) {
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        log.info("all operation return for BankAccount with id :"+accountId);
        return accountOperations.stream()
                .map(mappers::fromAccountOperation)
                .toList();
    }

    /**
     * get AccountOperation by id
     * @param id the id of AccountOperation you want to get
     * @return AccountOperation found
     * @throws AccountOperationNotFoundException raises this exception if AccountOperation not found
     */
    @Override
    public AccountOperationDTO getOperationById(Long id) throws AccountOperationNotFoundException {
        AccountOperation operation = accountOperationRepository.findById(id)
                .orElseThrow( () -> new AccountOperationNotFoundException("AccountOperation Not Found"));
        log.info("AccountOperation found");
        return mappers.fromAccountOperation(operation);
    }

    /**
     * get the transaction history (credit, debit) of a bank account.
     * @param accountId the id of the bank account whose history you want to read
     * @param page the number of a page you want to get
     * @param size the size of a page
     * @return bank account history
     * @throws BankAccountNotFoundException raises this exception if the bank account whose history you want to consult does not exist.
     */
    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) {
            throw new BankAccountNotFoundException("Bank Account Not Found");
        }
        Page<AccountOperation> accountOperations = accountOperationRepository
                .findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent()
                .stream().map(mappers::fromAccountOperation)
                .toList();
        return new AccountHistoryDTO(
                bankAccount.getId(),
                bankAccount.getBalance(),
                page,
                accountOperations.getTotalPages(),
                size,
                accountOperationDTOS
        );
    }

    /**
     * check if bank account is not activated
     * @param bankAccount bank account
     * @throws BankAccountNotActivatedException raises this exceptions if bank account status is not activated
     */
    private void checkIfBankAccountIsReady(@NotNull BankAccount bankAccount) throws BankAccountNotActivatedException {
        if(bankAccount.getStatus().equals(AccountStatus.SUSPENDED)){
            throw new BankAccountNotActivatedException("Bank Account Suspended");
        }
        if (bankAccount.getStatus().equals(AccountStatus.CREATED)) {
            throw new BankAccountNotActivatedException("Bank Account Not Activated");
        }
    }
}
