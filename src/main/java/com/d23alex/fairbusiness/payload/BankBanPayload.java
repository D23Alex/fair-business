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
public class BankBanPayload {
    private String bank;
    private LocalDate banDate;
    private LocalDate bannedTill;
    private String reason;
}
