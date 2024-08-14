
package com.brodygaudel.ebank.mapping;

import com.brodygaudel.ebank.dtos.AccountOperationDTO;
import com.brodygaudel.ebank.dtos.CurrentAccountDTO;
import com.brodygaudel.ebank.dtos.CustomerDTO;
import com.brodygaudel.ebank.dtos.SavingAccountDTO;
import com.brodygaudel.ebank.entities.AccountOperation;
import com.brodygaudel.ebank.entities.CurrentAccount;
import com.brodygaudel.ebank.entities.Customer;
import com.brodygaudel.ebank.entities.SavingAccount;

import java.util.List;

public interface Mappers {

    /**
     * map CustomerDTO to Customer
     * @param customerDTO CustomerDTO
     * @return Customer
     */
    Customer fromCustomerDTO(CustomerDTO customerDTO);

    /**
     * map Customer to CustomerDTO
     * @param customer Customer
     * @return CustomerDTO
     */
    CustomerDTO fromCustomer(Customer customer);

    /**
     * map list of customers to a list of customersDTOs
     * @param customers list of customers
     * @return list of customersDTOs
     */
    List<CustomerDTO> fromListOfCustomers(List<Customer> customers);

    /**
     * map CurrentAccountDTO to CurrentAccount
     * @param currentAccountDTO CurrentAccountDTO
     * @return CurrentAccount
     */
    CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO);

    /**
     * map CurrentAccount to CurrentAccountDTO
     * @param currentAccount CurrentAccount
     * @return CurrentAccountDTO
     */
    CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount);

    /**
     * map SavingAccountDTO to SavingAccount
     * @param savingAccountDTO SavingAccountDTO
     * @return SavingAccount
     */
    SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO);

    /**
     * map SavingAccount to SavingAccountDTO
     * @param savingAccount SavingAccount
     * @return SavingAccountDTO
     */
    SavingAccountDTO fromSavingAccount(SavingAccount savingAccount);

    /**
     * map AccountOperation to AccountOperationDTO
     * @param accountOperation AccountOperation
     * @return AccountOperationDTO
     */
    AccountOperationDTO fromAccountOperation(AccountOperation accountOperation);

}
