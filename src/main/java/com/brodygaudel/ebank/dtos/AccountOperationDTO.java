
package com.brodygaudel.ebank.dtos;

import com.brodygaudel.ebank.enums.OperationType;

import java.math.BigDecimal;
import java.util.Date;

public record AccountOperationDTO(
        Long id, Date operationDate,
        BigDecimal amount, OperationType type,
        String description) {
}
