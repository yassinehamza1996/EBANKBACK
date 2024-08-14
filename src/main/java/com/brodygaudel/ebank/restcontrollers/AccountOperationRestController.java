

package com.brodygaudel.ebank.restcontrollers;

import com.brodygaudel.ebank.dtos.*;
import com.brodygaudel.ebank.exceptions.AccountOperationNotFoundException;
import com.brodygaudel.ebank.exceptions.BalanceNotSufficientException;
import com.brodygaudel.ebank.exceptions.BankAccountNotActivatedException;
import com.brodygaudel.ebank.exceptions.BankAccountNotFoundException;
import com.brodygaudel.ebank.services.AccountOperationService;
import com.brodygaudel.ebank.util.pdf.InvoiceGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/operations")
@CrossOrigin(origins = "*")
public class AccountOperationRestController {

    private final AccountOperationService accountOperationService;
    private final InvoiceGenerator invoiceGenerator;

    public AccountOperationRestController(AccountOperationService accountOperationService, InvoiceGenerator invoiceGenerator) {
        this.accountOperationService = accountOperationService;
        this.invoiceGenerator = invoiceGenerator;
    }

    /**
     * debit a bank account
     * @param debitDTO basic information to debit a bank account
     * @return basic information for a bank account debited
     * @throws BankAccountNotFoundException raises this exception if the bank account you want to debit does not exist.
     * @throws BalanceNotSufficientException raises this exception if balance not sufficient
     * @throws BankAccountNotActivatedException raises this exception if bank account's status is not activated
     */
    @PostMapping("/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, BankAccountNotActivatedException {
        return accountOperationService.debit(debitDTO);
    }

    /**
     * credit a bank account
     * @param creditDTO basic information to credit a bank account
     * @return basic information of bank account credited
     * @throws BankAccountNotFoundException raises this exception if the bank account you want to credit does not exist.
     * @throws BankAccountNotActivatedException raises this exception if bank account status is not activated
     */
    @PostMapping("/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, BankAccountNotActivatedException {
        return accountOperationService.credit(creditDTO);
    }

    /**
     * make a transfer from a bank account to another bank account
     * @param transferDTO basic information to make a transfer
     * @return basic information of transfer done
     * @throws BankAccountNotFoundException raises this exception if the bank account(s) you want to transfer does not exist.
     * @throws BalanceNotSufficientException raises this exception if balance not sufficient to make a transfer
     * @throws BankAccountNotActivatedException raises this exception if bank account status is not activated
     */
    @PostMapping("/transfer")
    public TransferDTO transfer(@RequestBody TransferDTO transferDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, BankAccountNotActivatedException {
        return accountOperationService.transfer(transferDTO);
    }

    /**
     * get all operations by account id
     * @param accountId id of bank account that you want to retrieve operations
     * @return a list of AccountOperation where accountId equal id
     */
    @GetMapping("/list/{accountId}")
    public List<AccountOperationDTO> getAllOperationsByAccountId(@PathVariable String accountId){
        return accountOperationService.getAllOperationsByAccountId(accountId);
    }

    /**
     * get AccountOperation by id
     * @param id the id of AccountOperation you want to get
     * @return AccountOperation found
     * @throws AccountOperationNotFoundException raises this exception if AccountOperation not found
     */
    @GetMapping("/get/{id}")
    public AccountOperationDTO getOperationById(@PathVariable Long id) throws AccountOperationNotFoundException{
        return accountOperationService.getOperationById(id);
    }

    /**
     * get the transaction history (credit, debit) of a bank account.
     * @param accountId the id of the bank account whose history you want to read
     * @param page the number of a page you want to get
     * @param size the size of a page
     * @return bank account history
     * @throws BankAccountNotFoundException raises this exception if the bank account whose history you want to consult does not exist.
     */
    @GetMapping("/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name ="page", defaultValue = "0") int page,
                                               @RequestParam(name ="size", defaultValue = "5") int size) throws BankAccountNotFoundException{
        return accountOperationService.getAccountHistory(accountId, page, size);
    }

    /**
     * generate pdf invoice
     * @param id the operation's id
     * @return the path of pdf stored
     * @throws AccountOperationNotFoundException raise this exception if operation does not exist
     */
    @GetMapping("/generate/{id}")
    public String generateInvoice(@PathVariable Long id) throws AccountOperationNotFoundException {
        return invoiceGenerator.generateInvoice(id);
    }

    /**
     * exception handler
     * @param exception the exception to handler
     * @return the exception's message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
