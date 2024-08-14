

package com.brodygaudel.ebank.services;

import com.brodygaudel.ebank.dtos.CustomerDTO;
import com.brodygaudel.ebank.exceptions.CinAlreadyExistsException;
import com.brodygaudel.ebank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {

    /**
     * get all customers
     * @return list of all customers
     */
    List<CustomerDTO> getAllCustomers();

    /**
     * search for one or more customers by firstname and/or name
     * @param keyword represent name or firstname of users you want to get
     * @return list of customers found with name or firstname like keyword
     */
    List<CustomerDTO> searchCustomers(String keyword);

    /**
     * get a customer by his id.
     * @param id the id of the user you want to get
     * @return Customer found
     * @throws CustomerNotFoundException raises this exception if no client matches this id
     */
    CustomerDTO getCustomerById(Long id) throws CustomerNotFoundException;

    /**
     * create a new customer
     * @param customerDTO the customer to register
     * @return customer saved
     * @throws CinAlreadyExistsException raises this exception if there is already a customer with the same cin
     */
    CustomerDTO saveCustomer(CustomerDTO customerDTO) throws CinAlreadyExistsException;

    /**
     * modify all customer's information such as name, firstname, etc.
     * @param id the id of customer you want to update
     * @param customerDTO new information for customer
     * @return Customer updated
     * @throws CustomerNotFoundException raises this exception if no customer matches this id
     * @throws CinAlreadyExistsException raises this exception if there is already a customer with the same cin
     */
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) throws CustomerNotFoundException, CinAlreadyExistsException;

    /**
     * delete a customer by his id
     * @param id the id of customer you want to delete
     */
    void deleteCustomerById(Long id);
}
