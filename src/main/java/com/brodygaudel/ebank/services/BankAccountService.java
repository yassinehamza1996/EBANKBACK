

package com.brodygaudel.ebank.services;

import com.brodygaudel.ebank.dtos.*;
import com.brodygaudel.ebank.exceptions.BankAccountNotFoundException;
import com.brodygaudel.ebank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    /**
     * create new Current Bank Account
     * @param form the basic information of a Current Bank
     * @return Current Bank Account saved
     * @throws CustomerNotFoundException raises this exception if the customer whose current bank account you want to create does not exist.
     */
    CurrentAccountDTO saveCurrentBankAccount(CurrentAccountCreationForm form) throws CustomerNotFoundException;

    /**
     * create new Saving Bank Account
     * @param form the basic information of a Current Bank
     * @return Saving Bank Account saved
     * @throws CustomerNotFoundException raises this exception if the customer whose current bank account you want to create does not exist.
     */
    SavingAccountDTO saveSavingBankAccount(SavingAccountCreationForm form) throws CustomerNotFoundException;

    /**
     * get Bank Account by id
     * @param id the id of Bank Account you want to get
     * @return Bank Account found
     * @throws BankAccountNotFoundException raises this exception if the bank account you want to get does not exist.
     */
    BankAccountDTO getBankAccountById(String id) throws BankAccountNotFoundException;

    /**
     * get all customer's bank account
     * @param id the id of customer
     * @return the list of all customer's bank account
     * @throws BankAccountNotFoundException raises this exception if the bank account you want to get does not exist.
     */
    List<BankAccountDTO> getBankAccountByCustomerId(Long id) throws BankAccountNotFoundException;

    /**
     * get all Bank Accounts
     * @return the list of all Bank Accounts
     */
    List<BankAccountDTO> getAllBankAccounts();

    /**
     * update bank account status (activate or suspend)
     * @param id the id of Bank Account you want to update status
     * @param status new status
     * @return Bank Account updated
     * @throws BankAccountNotFoundException raises this exceptions if bank account not found
     */
    BankAccountDTO updateBankAccountStatus(String id, UpdateBankAccountStatus status) throws BankAccountNotFoundException;
}
