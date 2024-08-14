

package com.brodygaudel.ebank.dtos;

import java.math.BigDecimal;

public record SavingAccountCreationForm(BigDecimal initialBalance, double interestRate, Long customerId) {
}
