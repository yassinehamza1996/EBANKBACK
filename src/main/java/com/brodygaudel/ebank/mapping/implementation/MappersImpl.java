
package com.brodygaudel.ebank.mapping.implementation;

import com.brodygaudel.ebank.dtos.AccountOperationDTO;
import com.brodygaudel.ebank.dtos.CurrentAccountDTO;
import com.brodygaudel.ebank.dtos.CustomerDTO;
import com.brodygaudel.ebank.dtos.SavingAccountDTO;
import com.brodygaudel.ebank.entities.AccountOperation;
import com.brodygaudel.ebank.entities.CurrentAccount;
import com.brodygaudel.ebank.entities.Customer;
import com.brodygaudel.ebank.entities.SavingAccount;
import com.brodygaudel.ebank.mapping.Mappers;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappersImpl implements Mappers {

    @Override
    public Customer fromCustomerDTO(@NotNull CustomerDTO customerDTO) {
        return Customer.builder()
                .id(customerDTO.id())
                .firstname(customerDTO.firstname())
                .name(customerDTO.name())
                .dateOfBirth(customerDTO.dateOfBirth())
                .placeOfBirth(customerDTO.placeOfBirth())
                .nationality(customerDTO.nationality())
                .cin(customerDTO.cin())
                .sex(customerDTO.sex())
                .build();
    }

    @Override
    public CustomerDTO fromCustomer(@NotNull Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getFirstname(),
                customer.getName(),
                customer.getDateOfBirth(),
                customer.getPlaceOfBirth(),
                customer.getNationality(),
                customer.getCin(),
                customer.getEmail(),
                customer.getSex()
        );
    }

    @Override
    public List<CustomerDTO> fromListOfCustomers(@NotNull List<Customer> customers) {
        return customers.stream().map(this::fromCustomer).toList();
    }

    @Override
    public CurrentAccount fromCurrentAccountDTO(@NotNull CurrentAccountDTO currentAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentAccountDTO.getCustomerDTO()));
        return currentAccount;
    }

    @Override
    public CurrentAccountDTO fromCurrentAccount(@NotNull CurrentAccount currentAccount) {
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentAccountDTO);
        currentAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentAccountDTO;
    }

    @Override
    public SavingAccount fromSavingAccountDTO(@NotNull SavingAccountDTO savingAccountDTO) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO, savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingAccountDTO.getCustomerDTO()));
        return savingAccount;
    }

    @Override
    public SavingAccountDTO fromSavingAccount(@NotNull SavingAccount savingAccount) {
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingAccountDTO);
        savingAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingAccountDTO;
    }

    @Override
    public AccountOperationDTO fromAccountOperation(@NotNull AccountOperation accountOperation) {
        return new AccountOperationDTO(
                accountOperation.getId(),
                accountOperation.getOperationDate(),
                accountOperation.getAmount(),
                accountOperation.getType(),
                accountOperation.getDescription()
        );
    }
}
