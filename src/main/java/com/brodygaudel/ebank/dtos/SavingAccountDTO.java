
package com.brodygaudel.ebank.dtos;

import com.brodygaudel.ebank.enums.AccountStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class SavingAccountDTO extends BankAccountDTO {
    private String id;
    private BigDecimal balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;

    public SavingAccountDTO() {
        super();
    }

    public SavingAccountDTO(String type, String id, BigDecimal balance, Date createdAt, AccountStatus status, CustomerDTO customerDTO, double interestRate) {
        super(type);
        this.id = id;
        this.balance = balance;
        this.createdAt = createdAt;
        this.status = status;
        this.customerDTO = customerDTO;
        this.interestRate = interestRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "SavingAccountDTO{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", customerDTO=" + customerDTO +
                ", interestRate=" + interestRate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SavingAccountDTO that = (SavingAccountDTO) o;
        return Double.compare(that.interestRate, interestRate) == 0 && Objects.equals(id, that.id) && Objects.equals(balance, that.balance) && Objects.equals(createdAt, that.createdAt) && status == that.status && Objects.equals(customerDTO, that.customerDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, balance, createdAt, status, customerDTO, interestRate);
    }
}