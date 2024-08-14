

package com.brodygaudel.ebank.restcontrollers;

import com.brodygaudel.ebank.dtos.*;
import com.brodygaudel.ebank.exceptions.BankAccountNotFoundException;
import com.brodygaudel.ebank.exceptions.CustomerNotFoundException;
import com.brodygaudel.ebank.services.BankAccountService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
@CrossOrigin(origins = "*")
public class BankAccountRestController {

    private final BankAccountService bankAccountService;

    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    /**
     * create new Current Bank Account
     * @param form the basic information of a Current Bank
     * @return Current Bank Account saved
     * @throws CustomerNotFoundException raises this exception if the customer whose current bank account you want to create does not exist.
     */
    @PostMapping("/save/current")
    public CurrentAccountDTO saveCurrentBankAccount(@RequestBody CurrentAccountCreationForm form) throws CustomerNotFoundException{
        return bankAccountService.saveCurrentBankAccount(form);
    }

    /**
     * create new Saving Bank Account
     * @param form the basic information of a Current Bank
     * @return Saving Bank Account saved
     * @throws CustomerNotFoundException raises this exception if the customer whose current bank account you want to create does not exist.
     */
    @PostMapping("/save/saving")
    public SavingAccountDTO saveSavingBankAccount(@RequestBody SavingAccountCreationForm form) throws CustomerNotFoundException{
        return bankAccountService.saveSavingBankAccount(form);
    }

    /**
     * get Bank Account by id
     * @param id the id of Bank Account you want to get
     * @return Bank Account found
     * @throws BankAccountNotFoundException raises this exception if the bank account you want to get does not exist.
     */
    @GetMapping("/get/{id}")
    public BankAccountDTO getBankAccountById(@PathVariable String id) throws BankAccountNotFoundException{
        return bankAccountService.getBankAccountById(id);
    }

    /**
     * get all customer's bank account
     * @param id the id of customer
     * @return the list of all customer's bank account
     * @throws BankAccountNotFoundException raises this exception if the bank account you want to get does not exist.
     */
    @GetMapping("/find/{customerId}")
    public List<BankAccountDTO> getBankAccountByCustomerId(@PathVariable(name = "customerId") Long id) throws BankAccountNotFoundException{
        return bankAccountService.getBankAccountByCustomerId(id);
    }

    /**
     * get all Bank Accounts
     * @return the list of all Bank Accounts
     */
    @GetMapping("/list")
    public List<BankAccountDTO> getAllBankAccounts(){
        return bankAccountService.getAllBankAccounts();
    }

    /**
     * update bank account status (activate or suspend)
     * @param id the id of Bank Account you want to update status
     * @param status new status
     * @return Bank Account updated
     * @throws BankAccountNotFoundException raises this exceptions if bank account not found
     */
    @PatchMapping("/update/{id}")
    public BankAccountDTO updateBankAccountStatus(@PathVariable String id, @RequestBody UpdateBankAccountStatus status) throws BankAccountNotFoundException{
        return bankAccountService.updateBankAccountStatus(id, status);
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
