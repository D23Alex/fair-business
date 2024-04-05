package com.d23alex.fairbusiness.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonPayload {
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate dateOfBirth;
}
