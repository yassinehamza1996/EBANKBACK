

package com.brodygaudel.ebank.dtos;

import java.math.BigDecimal;

public record CurrentAccountCreationForm(BigDecimal initialBalance, double overDraft, Long customerId) {
}
