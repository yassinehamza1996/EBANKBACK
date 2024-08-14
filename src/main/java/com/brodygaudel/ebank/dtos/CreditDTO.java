

package com.brodygaudel.ebank.dtos;

import java.math.BigDecimal;

public record CreditDTO(String accountId, BigDecimal amount, String description) {
}
