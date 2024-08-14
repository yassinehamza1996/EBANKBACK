
package com.brodygaudel.ebank.dtos;

import java.math.BigDecimal;

public record TransferDTO(String accountIdSource, String accountIdDestination, BigDecimal amount) {
}
