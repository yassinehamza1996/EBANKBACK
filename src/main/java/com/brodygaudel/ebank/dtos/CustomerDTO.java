

package com.brodygaudel.ebank.dtos;

import com.brodygaudel.ebank.enums.Sex;

import java.util.Date;

public record CustomerDTO(
        Long id,
        String firstname,
        String name,
        Date dateOfBirth,
        String placeOfBirth,
        String nationality,
        String cin,
        String email,
        Sex sex) { }
