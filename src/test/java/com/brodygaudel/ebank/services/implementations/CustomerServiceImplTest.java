package com.brodygaudel.ebank.services.implementations;

import com.brodygaudel.ebank.dtos.CustomerDTO;
import com.brodygaudel.ebank.entities.Customer;
import com.brodygaudel.ebank.enums.Sex;
import com.brodygaudel.ebank.exceptions.CinAlreadyExistsException;
import com.brodygaudel.ebank.exceptions.CustomerNotFoundException;
import com.brodygaudel.ebank.mapping.implementation.MappersImpl;
import com.brodygaudel.ebank.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    private CustomerRepository customerRepository;
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, new MappersImpl());
    }

    @Test
    void testGetAllCustomers() {
        customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        customerRepository.save(
                Customer.builder().name("BOUKA").firstname("GAUDEL")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();
        assertNotNull(customerDTOS);
        assertFalse(customerDTOS.isEmpty());
        assertTrue(customerDTOS.size() >= 2);
    }

    @Test
    void testSearchCustomers() {
        customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        customerRepository.save(
                Customer.builder().name("SPRING").firstname("BOOT")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        String keyword = "BOOT";
        List<CustomerDTO> customerDTOS = customerService.searchCustomers("%"+keyword+"%");
        assertNotNull(customerDTOS);
        assertFalse(customerDTOS.isEmpty());
    }

    @Test
    void testGetCustomerById() throws CustomerNotFoundException {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        CustomerDTO customerDTO = customerService.getCustomerById(customer.getId());
        assertNotNull(customerDTO);
        assertEquals(customerDTO.id(), customer.getId());
        assertEquals(customerDTO.cin(), customer.getCin());
        assertEquals(customerDTO.name(), customer.getName());
        assertEquals(customerDTO.firstname(), customer.getFirstname());
        assertEquals(customerDTO.nationality(), customer.getNationality());
        assertEquals(customerDTO.placeOfBirth(), customer.getPlaceOfBirth());
        assertEquals(customerDTO.sex(), customer.getSex());
    }

    @Test
    void testSaveCustomer() throws CinAlreadyExistsException {
        Date date = new Date();
        String cin = UUID.randomUUID().toString();
        CustomerDTO customerDTO  = customerService.saveCustomer(
                new CustomerDTO(
                        (long)1,
                        "Jakarta EE",
                        "Java",
                        date,
                        "USA",
                        "WORLD",
                        cin,
                        "john.doe@example.com",
                        Sex.F
                )
        );
        assertNotNull(customerDTO);
        assertEquals("Jakarta EE", customerDTO.firstname());
        assertEquals("Java", customerDTO.name());
        assertEquals("USA", customerDTO.placeOfBirth());
        assertEquals("WORLD", customerDTO.nationality());
        assertEquals(cin, customerDTO.cin());
        assertEquals(Sex.F, customerDTO.sex());
    }

    @Test
    void testUpdateCustomer() throws CinAlreadyExistsException, CustomerNotFoundException {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        CustomerDTO customerDTO  = customerService.updateCustomer(customer.getId(),
                new CustomerDTO(
                        (long)1,
                        "Jakarta EE",
                        "Java",
                        new Date(),
                        "USA",
                        "WORLD",
                        "john.doe@example.com",
                        UUID.randomUUID().toString(),
                        Sex.F
                )
        );
        assertNotNull(customerDTO);
        assertEquals(customer.getId(), customerDTO.id());
        assertNotEquals(customer.getFirstname(), customerDTO.firstname());
        assertNotEquals(customer.getName(), customerDTO.name());
        assertNotEquals(customer.getPlaceOfBirth(), customerDTO.placeOfBirth());
        assertNotEquals(customer.getNationality(), customerDTO.nationality());
        assertNotNull(customerDTO.email());
        assertNotEquals(customer.getEmail(), customerDTO.email());
        assertNotEquals(customer.getCin(), customerDTO.cin());
        assertNotEquals(customer.getSex(), customerDTO.sex());
    }

    @Test
    void testDeleteCustomerById() {
        Customer customer = customerRepository.save(
                Customer.builder().name("MOUNANGA").firstname("BRODY")
                        .sex(Sex.M).nationality("GABON").placeOfBirth("GABON").dateOfBirth(new Date())
                        .cin(UUID.randomUUID().toString()).build()
        );
        customerService.deleteCustomerById(customer.getId());
        Customer customerDeleted = customerRepository.findById(customer.getId()).orElse(null);
        assertNull(customerDeleted);
    }
}