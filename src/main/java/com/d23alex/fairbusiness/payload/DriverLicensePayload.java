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
public class DriverLicensePayload {
    private Long Id;
    private String givenBy;
    private LocalDate issued;
    private LocalDate expires;
}
