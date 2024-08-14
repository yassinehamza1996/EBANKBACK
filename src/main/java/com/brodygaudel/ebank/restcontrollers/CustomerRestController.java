

package com.brodygaudel.ebank.restcontrollers;

import com.brodygaudel.ebank.dtos.CustomerDTO;
import com.brodygaudel.ebank.exceptions.CinAlreadyExistsException;
import com.brodygaudel.ebank.exceptions.CustomerNotFoundException;
import com.brodygaudel.ebank.services.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
@CrossOrigin(origins = "*")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * get all customers
     * @return list of all customers
     */
    @GetMapping("/list")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    /**
     * search for one or more customers by firstname and/or name
     * @param keyword represent name or firstname of users you want to get
     * @return list of customers found with name or firstname like keyword
     */
    @GetMapping("/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword", defaultValue = "") String keyword){
        return customerService.searchCustomers(keyword);
    }

    /**
     * get a customer by his id.
     * @param id the id of the user you want to get
     * @return Customer found
     * @throws CustomerNotFoundException raises this exception if no client matches this id
     */
    @GetMapping("/get/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) throws CustomerNotFoundException{
        return customerService.getCustomerById(id);
    }

    /**
     * create a new customer
     * @param customerDTO the customer to register
     * @return customer saved
     * @throws CinAlreadyExistsException raises this exception if there is already a customer with the same cin
     */
    @PostMapping("/save")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) throws CinAlreadyExistsException{
        return customerService.saveCustomer(customerDTO);
    }

    /**
     * modify all customer's information such as name, firstname, etc.
     * @param id the id of customer you want to update
     * @param customerDTO new information for customer
     * @return Customer updated
     * @throws CustomerNotFoundException raises this exception if no customer matches this id
     * @throws CinAlreadyExistsException raises this exception if there is already a customer with the same cin
     */
    @PutMapping("/update/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException, CinAlreadyExistsException{
        return customerService.updateCustomer(id, customerDTO);
    }

    /**
     * delete a customer by his id
     * @param id the id of customer you want to delete
     */
    @DeleteMapping("/delete/{id}")
    public void deleteCustomerById(@PathVariable Long id){
        customerService.deleteCustomerById(id);
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
